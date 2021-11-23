package ast;

import annotation.VariableScope;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import util.NodeParser;
import util.Scoper;
import util.StatementCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VariableHistoryModifier extends ModifierVisitor<Map<VariableScope, List<LineInfo>>> {

    @Override
    public VariableDeclarationExpr visit(VariableDeclarationExpr vde, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        super.visit(vde, lineInfoMap);
        for (VariableDeclarator vd : vde.getVariables()) {
            String name = vd.getNameAsString();
            VariableScope scope = Scoper.createScope(name, vd);
            if (!isTrackedVariable(scope, lineInfoMap)) {
                continue;
            }
            Statement nodeContainingEntireStatement = (Statement) vd.getParentNode().get().getParentNode().get();
            String type = vd.getType().toString();
            int id = UniqueNumberGenerator.generate();
            if (isDeclaredButNotInitialized(vd)) {
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.evaluateVarDeclarationWithoutInitializerStatement(name,
                                scope.getEnclosingMethod(), scope.getEnclosingClass(), id));
            } else if (nodeContainingEntireStatement instanceof ForStmt) {
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.logVariable(name, scope.getEnclosingMethod(), scope.getEnclosingClass(),
                                null, id));
                StatementCreator.evaluateForLoopVarDeclarationStatement(name, scope.getEnclosingMethod(),
                        scope.getEnclosingClass(), id);
            } else {
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.evaluateVarDeclarationStatement(name, scope.getEnclosingMethod(),
                                scope.getEnclosingClass(), id));
            }
            addToLineInfoMap(scope, type, nodeContainingEntireStatement, vd, lineInfoMap, id);
        }
        return vde;
    }

    @Override
    public FieldDeclaration visit(FieldDeclaration fd, Map<VariableScope, List<LineInfo>> lineInfo) {
        super.visit(fd, lineInfo);
        // we set all fields to public access so that VariableReferenceLogger.checkBaseAndNestedObjects() can access
        // each field
        setAccessToPublic(fd);
        // todo: FieldDeclarations aren't inside of a BlockStatement, which means we can't add a log statement directly
        //       below them. This makes tracking these variables a bit trickier. Leaving it for now
        return fd;
    }

    @Override
    public AssignExpr visit(AssignExpr ae, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        super.visit(ae, lineInfoMap);
        // If making an array access assignment we want the name of the variable without the square brackets
        String name = (isArrayAccessAssignment(ae)) ?
                ((ArrayAccessExpr) ae.getTarget()).getName().toString() :
                ae.getTarget().toString();
        VariableScope scope = Scoper.createScope(name, ae);
        Statement nodeContainingEntireStatement = (Statement) ae.getParentNode().get();
        trackVariableMutation(name, scope, nodeContainingEntireStatement, ae, lineInfoMap);
        return ae;
    }

    @Override
    public UnaryExpr visit(UnaryExpr ue, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        super.visit(ue, lineInfoMap);
        String name = ue.getExpression().toString();
        VariableScope scope = Scoper.createScope(name, ue);
        if (isTrackedVariable(scope, lineInfoMap)) {
            Statement nodeContainingEntireStatement = (Statement) ue.getParentNode().get();
            trackVariableMutation(name, scope, nodeContainingEntireStatement, ue, lineInfoMap);
        }
        return ue;
    }

    @Override
    public MethodCallExpr visit(MethodCallExpr mce, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        super.visit(mce, lineInfoMap);
        if (mce.getScope().isPresent()) {
            NameExpr exprScope = (NameExpr) getBaseScope(mce.getScope().get());
            String name = exprScope.getNameAsString();
            VariableScope scope = Scoper.createScope(name, mce);
            Statement nodeContainingEntireStatement = (Statement) mce.getParentNode().get();
            int id = UniqueNumberGenerator.generate();
            addToLineInfoMap(scope, null, nodeContainingEntireStatement, mce, lineInfoMap, id);
            Statement injectedLine = StatementCreator.checkBaseAndNestedObjectsStatement(name,
                    scope.getEnclosingMethod(), scope.getEnclosingClass(), id);
            injectCodeOnNextLine(nodeContainingEntireStatement, mce, injectedLine);
        }
        return mce;
    }

    private Expression getBaseScope(Expression scope) {
        if (scope instanceof FieldAccessExpr scopeAsFAE) {
            return getBaseScope(scopeAsFAE.getScope());
        }
        return scope;
    }

    private void trackVariableMutation(String name, VariableScope scope, Statement nodeContainingEntireStatement,
                                       Node node, Map<VariableScope,
            List<LineInfo>> lineInfoMap) {
        int id = UniqueNumberGenerator.generate();
        addToLineInfoMap(scope, null, nodeContainingEntireStatement, node, lineInfoMap, id);
        String[] subObjects = name.split("\\.");
        String objName = subObjects[0];
        Statement injectedLine = StatementCreator.evaluateAssignmentStatement(objName, scope.getEnclosingMethod(),
                scope.getEnclosingClass(), id);
        injectCodeOnNextLine(nodeContainingEntireStatement, node, injectedLine);
        for (int i = 1; i < subObjects.length; i++) {
            objName = objName + "." + subObjects[i];
            injectedLine = StatementCreator.checkBaseAndNestedObjectsStatement(objName, scope.getEnclosingMethod(),
                    scope.getEnclosingClass(), id);
            injectCodeOnNextLine(nodeContainingEntireStatement, node, injectedLine);
        }
        MethodDeclaration enclosingMethod = node.findAncestor(MethodDeclaration.class).isPresent() ?
                node.findAncestor(MethodDeclaration.class).get() : null;
        boolean isEnclosedByConstructor = node.findAncestor(ConstructorDeclaration.class).isPresent();
        if (isEnclosedByConstructor ||
                (enclosingMethod != null && !enclosingMethod.isStatic())) {
            injectedLine = StatementCreator.evaluateAssignmentStatement("this", scope.getEnclosingMethod(),
                    scope.getEnclosingClass(), id);
            injectCodeOnNextLine(nodeContainingEntireStatement, node, injectedLine);
        }
    }

    private void addToLineInfoMap(VariableScope scope, String type,
                                  Statement nodeContainingEntireStatement, Node node,
                                  Map<VariableScope, List<LineInfo>> lineInfoMap, int id) {
        String nickname = isTrackedVariable(scope, lineInfoMap) ? lineInfoMap.get(scope).get(0).getNickname() : null;
        Integer lineNum = getLineNum(node);
        String enclosingClass = NodeParser.getEnclosingClass(node);
        String enclosingMethod = NodeParser.getEnclosingMethod(node);
        if (!isTrackedVariable(scope, lineInfoMap)) {
            // add with a dummy item to make nickname hack work
            lineInfoMap.put(scope, new ArrayList<>(List.of(new LineInfo())));
        }
        lineInfoMap.get(scope).add(new LineInfo(scope.getVarName(), nickname, type, lineNum,
                nodeContainingEntireStatement.toString(),
                enclosingClass, enclosingMethod, id));
    }

    private Integer getLineNum(Node node) {
        return node.getBegin().isPresent() ? node.getBegin().get().line : null;
    }

    private void injectCodeOnNextLine(Statement anchorStatement, Node node, Statement loggingStatement) {
        if (anchorStatement instanceof ForStmt forStmt) {
            // if (node instanceof UnaryExpr || node instanceof AssignExpr){
            // If it's a for statement, don't include variable declaration (will be reclared each loop)
            if (forStmt.getBody() instanceof BlockStmt body) {
                body.addStatement(0, loggingStatement);
            } else if (forStmt.getBody() instanceof ExpressionStmt body) {
                // todo: handle the case where the body of the for statement isn't wrapped in curly brackets
                //       also need to handle the case where the thing we're interested in is the body of the
                //       for statement and it's not in brackets
                //       same for if blocks
                //       also this if/else statement very bad, should try to double dispatch instead
            }
        } else if (node.findAncestor(SwitchEntry.class).isPresent()) {
            NodeList<Statement> switchBlockStatements = node.findAncestor(SwitchEntry.class).get().getStatements();
            switchBlockStatements.add(switchBlockStatements.indexOf(anchorStatement) + 1, loggingStatement);
        } else {
            node.findAncestor(BlockStmt.class)
                    .ifPresent(block -> block.addStatement(1 + block.getStatements().indexOf(anchorStatement),
                            loggingStatement));
        }
    }

    private boolean isArrayAccessAssignment(AssignExpr ae) {
        return ae.getTarget() instanceof ArrayAccessExpr;
    }

    private boolean isTrackedVariable(VariableScope scope, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        return lineInfoMap.containsKey(scope);
    }

    private boolean isDeclaredButNotInitialized(VariableDeclarator vd) {
        return vd.getInitializer().isEmpty();
    }

    private void setAccessToPublic(FieldDeclaration fd) {
        NodeList<Modifier> modifiers = fd.getModifiers();
        for (Modifier modifier : modifiers) {
            if (isAccessSpecifier(modifier)) {
                modifiers.replace(modifier, Modifier.publicModifier());
                return;
            }
        }
        modifiers.add(0, Modifier.publicModifier());
        fd.setModifiers(modifiers);
    }

    private boolean isAccessSpecifier(Modifier modifier) {
        return modifier.getKeyword() == Modifier.Keyword.PRIVATE ||
                modifier.getKeyword() == Modifier.Keyword.PROTECTED ||
                modifier.getKeyword() == Modifier.Keyword.PUBLIC;
    }
}
