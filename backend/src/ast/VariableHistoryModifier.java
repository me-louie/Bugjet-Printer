package ast;

import annotation.VariableScope;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import util.NodeParser;
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
            VariableScope scope = createVariableScope(name, vd);
            if (!isTrackedVariable(scope, lineInfoMap)) {
                continue;
            }
            Statement nodeContainingEntireStatement = (Statement) vd.getParentNode().get().getParentNode().get();
            String type = vd.getType().toString();
            int id = UniqueNumberGenerator.generate();
            if (isDeclaredButNotInitialized(vd)) {
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.evaluateVarDeclarationWithoutInitializerStatement(name));
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.logVariable(name, null, id));
            } else {
                injectCodeOnNextLine(nodeContainingEntireStatement, vd,
                        StatementCreator.evaluateVarDeclarationStatement(name, id));
            }
            addToLineInfoMap(scope, type, nodeContainingEntireStatement, vd, lineInfoMap, id);
        }
        return vde;
    }

    @Override
    public FieldDeclaration visit(FieldDeclaration fd, Map<VariableScope, List<LineInfo>> lineInfo) {
        super.visit(fd, lineInfo);
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
        VariableScope scope = createVariableScope(name, ae);
        Statement nodeContainingEntireStatement = (Statement) ae.getParentNode().get();
        trackVariableMutation(name, scope, nodeContainingEntireStatement, ae, lineInfoMap);
        return ae;
    }

    @Override
    public UnaryExpr visit(UnaryExpr ue, Map<VariableScope, List<LineInfo>> lineInfoMap) {
        super.visit(ue, lineInfoMap);
        String name = ue.getExpression().toString();
        VariableScope scope = createVariableScope(name, ue);
        if (isTrackedVariable(scope, lineInfoMap)) {
            Statement nodeContainingEntireStatement = (Statement) ue.getParentNode().get();
            trackVariableMutation(name, scope, nodeContainingEntireStatement, ue, lineInfoMap);
        }
        return ue;
    }

    private void trackVariableMutation(String name, VariableScope scope, Statement nodeContainingEntireStatement,
                                       Node node, Map<VariableScope,
            List<LineInfo>> lineInfoMap) {
        int id = UniqueNumberGenerator.generate();
        addToLineInfoMap(scope, null, nodeContainingEntireStatement, node, lineInfoMap, id);
        Statement injectedLine = StatementCreator.evaluateAssignmentStatement(name, id);
        injectCodeOnNextLine(nodeContainingEntireStatement, node, injectedLine);
    }

    private void addToLineInfoMap(VariableScope scope, String type,
                                  Statement nodeContainingEntireStatement, Node node,
                                  Map<VariableScope, List<LineInfo>> lineInfoMap, int id) {
        String nickname = isTrackedVariable(scope, lineInfoMap) ? lineInfoMap.get(scope).get(0).getNickname() : null;
        Integer lineNum = getLineNum(node);
        String enclosingClass = NodeParser.getEnclosingClass(node);
        String enclosingMethod = NodeParser.getEnclosingMethod(node);
        if (!isTrackedVariable(scope, lineInfoMap)) {
            lineInfoMap.put(scope, new ArrayList<>(List.of(new LineInfo()))); // add with a dummy item to make nickname
            // hack work
        }
        lineInfoMap.get(scope).add(new LineInfo(scope.getVarName(), nickname, type, lineNum,
                nodeContainingEntireStatement.toString(),
                enclosingClass, enclosingMethod, id));
    }

    private Integer getLineNum(Node node) {
        return node.getBegin().isPresent() ? node.getBegin().get().line : null;
    }

    private void injectCodeOnNextLine(Statement anchorStatement, Node node, Statement loggingStatement) {
        if (node.getParentNode().isPresent() && node.getParentNode().get() instanceof ForStmt forStmt) {
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

    private VariableScope createVariableScope(String name, Node node) {
        String enclosingClass = NodeParser.getEnclosingClass(node);
        String enclosingMethod = NodeParser.getEnclosingMethod(node);
        return new VariableScope(name, enclosingMethod, enclosingClass);
    }
}
