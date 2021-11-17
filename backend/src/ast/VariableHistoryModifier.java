package ast;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import util.StatementCreator;

import java.util.List;
import java.util.Map;

public class VariableHistoryModifier extends ModifierVisitor<Map<String, List<LineInfo>>> {

    @Override
    public VariableDeclarationExpr visit(VariableDeclarationExpr vde, Map<String, List<LineInfo>> lineInfo) {
        super.visit(vde, lineInfo);
        for (VariableDeclarator vd : vde.getVariables()) {
            Statement nodeContainingEntireStatement = (Statement) vd.getParentNode().get().getParentNode().get();
            String name = vd.getNameAsString();
            String value;
            // If initializing arr with an expression, e.g. int[] m = {1, 2, 3} the logging var is the var name (m)
            if (vd.getInitializer().isPresent() && vd.getInitializer().get() instanceof ArrayInitializerExpr) {
                value = name;
            } else {
                // The logging value is the initializer value or null if variable was not initialized
                value = (vd.getInitializer().isPresent()) ? vd.getInitializer().get().toString() : null;
            }
            // The declared variable is tracked
            if (lineInfo.containsKey(vd.getNameAsString())) {
                String type = vd.getType().toString();
                trackVar(name, value, type, nodeContainingEntireStatement, vd, lineInfo);
                if (vd.getType() instanceof ReferenceType) {
                    trackVarReference(name, nodeContainingEntireStatement, vd, lineInfo);
                }
            } else if (lineInfo.containsKey(vd.getInitializer().get().toString())) {
                // The value that is being assigned to the declared variable is tracked
                Statement addToRefToVarMap = StatementCreator.createRefToVarMapAdd(name, value);
                addLoggingStatement(nodeContainingEntireStatement, vd, addToRefToVarMap);

                Statement addToVarToRefMap = StatementCreator.varToRefMapPut(name);
                addLoggingStatement(nodeContainingEntireStatement, vd, addToVarToRefMap);
            }
        }
        return vde;
    }

    @Override
    public FieldDeclaration visit(FieldDeclaration fd, Map<String, List<LineInfo>> lineInfo) {
        super.visit(fd, lineInfo);
        // todo: FieldDeclarations aren't inside of a BlockStatement, which means we can't add a log statement directly
        //       below them. This makes tracking these variables a bit trickier. Leaving it for now
        return fd;
    }

    @Override
    public AssignExpr visit(AssignExpr ae, Map<String, List<LineInfo>> lineInfo) {
        super.visit(ae, lineInfo);
        // If making an array access assignment, we want the name of the variable only, without the square brackets
        String name = (ae.getTarget() instanceof ArrayAccessExpr ?
                ((ArrayAccessExpr) ae.getTarget()).getName().toString() :
                ae.getTarget().toString());
        String value = ae.getValue().toString();
        Statement nodeContainingEntireStatement = (Statement) ae.getParentNode().get();
        Integer lineNum = ae.getBegin().isPresent() ? ae.getBegin().get().line : null;
        String enclosingClass = ae.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() ?
                ae.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString() :
                null;
        String enclosingMethod = ae.findAncestor(MethodDeclaration.class).isPresent() ?
                ae.findAncestor(MethodDeclaration.class).get().getDeclarationAsString(true, true, true) :
                null;

        // Assignment to a variable which is tracked
        if (lineInfo.containsKey(name)) {
            // write down anything about this line that we might want to know
            String alias = lineInfo.get(name).get(0).getAlias();
            int uniqueNum = UniqueNumberGenerator.generate();
            LineInfo li = new LineInfo(name, alias, null, lineNum, nodeContainingEntireStatement.toString(),
                    enclosingClass,
                    enclosingMethod, uniqueNum);

            lineInfo.get(name).add(li);
            Statement refToVarMapChecks = StatementCreator.createRefToVarMapChecks(name, uniqueNum);
            addLoggingStatement(nodeContainingEntireStatement, ae, refToVarMapChecks);

            Statement varToRefMapChecks = StatementCreator.createVarToRefMapChecks(name);
            addLoggingStatement(nodeContainingEntireStatement, ae, varToRefMapChecks);
        } else {
            // Assignment to a variable which is not explicitly tracked, but the variable may be pointing to an object
            // which is tracked. Therefore, we need to add additional logging to determine whether we need to log
            // changes to this variable as well.
            int uniqueNum = UniqueNumberGenerator.generate();
            // TODO: does name/alias matter here?
            LineInfo li = new LineInfo("", "", null, lineNum, nodeContainingEntireStatement.toString(),
                    enclosingClass, enclosingMethod, uniqueNum);
            Statement s = StatementCreator.createReferencedVarLogging(name, li, uniqueNum);
            addLoggingStatement(nodeContainingEntireStatement, ae, s);
        }
        return ae;
    }


    @Override
    public UnaryExpr visit(UnaryExpr ue, Map<String, List<LineInfo>> lineInfo) {
        super.visit(ue, lineInfo);
        String name = ue.getExpression().toString();
        if (lineInfo.containsKey(name)) {
            // write down anything about this line that we might want to know
            String value = name;
            Statement nodeContainingEntireStatement = (Statement) ue.getParentNode().get();
            trackVar(name, value, null /* type info isn't contained in unary expressions */,
                    nodeContainingEntireStatement, ue, lineInfo);
        }
        return ue;
    }

    private void trackVar(String name, String value, String type, Statement nodeContainingEntireStatement, Node node,
                          Map<String,
                                  List<LineInfo>> lineInfo) {
        String alias = lineInfo.get(name).get(0).getAlias();
        Integer lineNum = node.getBegin().isPresent() ? node.getBegin().get().line : null;
        String enclosingClass = node.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() ?
                node.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString() :
                null;
        String enclosingMethod = node.findAncestor(MethodDeclaration.class).isPresent() ?
                node.findAncestor(MethodDeclaration.class).get().getDeclarationAsString(true, true, true) :
                null;
        int uniqueIdentifier = UniqueNumberGenerator.generate();
        // store all of the info about a statement we might want later
        lineInfo.get(name).add(new LineInfo(name, alias, type, lineNum, nodeContainingEntireStatement.toString(),
                enclosingClass, enclosingMethod, uniqueIdentifier));
        // add logging statement below this line
        Statement variableLoggerLog = StatementCreator.logVariable(name, value, uniqueIdentifier);
        addLoggingStatement(nodeContainingEntireStatement, node, variableLoggerLog);
    }

    private void trackVarReference(String name, Statement nodeContainingEntireStatement, Node node, Map<String,
            List<LineInfo>> lineInfo) {
        String alias = lineInfo.get(name).get(0).getAlias();
        Integer lineNum = node.getBegin().isPresent() ? node.getBegin().get().line : null;
        String enclosingClass = node.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() ?
                node.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString() :
                null;
        String enclosingMethod = node.findAncestor(MethodDeclaration.class).isPresent() ?
                node.findAncestor(MethodDeclaration.class).get().getDeclarationAsString(true, true, true) :
                null;
        int uniqueIdentifier = UniqueNumberGenerator.generate();
        lineInfo.get(name).add(new LineInfo(name, alias, null, lineNum, nodeContainingEntireStatement.toString(),
                enclosingClass, enclosingMethod, uniqueIdentifier));

        Statement varToRefPut = StatementCreator.varToRefMapPut(name);
        addLoggingStatement(nodeContainingEntireStatement, node, varToRefPut);

        Statement addVarToRefToVarMap = StatementCreator.refToVarMapAdd(name);
        addLoggingStatement(nodeContainingEntireStatement, node, addVarToRefToVarMap);

        Statement putSetToRefToVar = StatementCreator.refToVarMapNewPut(name);
        addLoggingStatement(nodeContainingEntireStatement, node, putSetToRefToVar);
    }

    private void addLoggingStatement(Statement anchorStatement, Node node, Statement loggingStatement) {
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

}
