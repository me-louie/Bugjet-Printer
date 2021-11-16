package util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;

public final class StatementCreator {

    public static Statement logVariable(String name, String value, int uniqueIdentifier) {
        if (value == null) {
            value = "\"uninitialized\"";
        }
        return StaticJavaParser.parseStatement("VariableLogger.log(\""
                + name + "\", "
                + value + ", "
                + uniqueIdentifier + ");");
    }

    public static Statement putVarInVarToRefMap(String var) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.varToRefMap.put("
                + var + ", "
                + var + ".toString());");
    }

    public static Statement putSetToRefToVarMap(String var) {
        return
                new IfStmt(StaticJavaParser.parseExpression("!VariableReferenceLogger.refToVarMap.containsKey(" + var +
                        ".toString())"),
                        StaticJavaParser.parseStatement("VariableReferenceLogger.refToVarMap.put("
                                + var + ".toString(), new HashSet<>());"),
                        emptyElse());
    }

    public static Statement addVarToRefToVarMap(String var) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.refToVarMap.get("
                + var + ".toString()).add(\"" + var + "\");");
    }

    public static Statement createVarToRefMapChecks(String var) {
        return new IfStmt(
                StaticJavaParser.parseExpression("!" + var + ".toString().equals(VariableReferenceLogger" +
                        ".varToRefMap.get(" + var + "))"),
                StaticJavaParser.parseStatement(
                        "System.out" +
                                ".println" +
                                "(\"todo update references in both maps\");"), emptyElse());

    }

    public static Statement createForLoop(String var, int uniqueIdentifier) {
        NodeList<Statement> stmts = new NodeList<>();
        stmts.add(StaticJavaParser.parseStatement("VariableLogger.log(var,"
                + var + ", "
                + uniqueIdentifier + ");"));
        return new ForEachStmt(StaticJavaParser.parseVariableDeclarationExpr("String var"),
                "VariableReferenceLogger.refToVarMap.get(" + var + ".toString())",
                new BlockStmt(stmts));

    }


    public static Statement createRefToVarMapChecks(String var, int uniqueIdentifier) {
        Statement forLoop = createForLoop(var, uniqueIdentifier);
        return new IfStmt(
                StaticJavaParser.parseExpression("VariableReferenceLogger.refToVarMap.containsKey(" + var +
                        ".toString())"), forLoop, emptyElse());

    }

    // TODO: figure out how to actually generate an empty else blk
    private static Statement emptyElse() {
        return new EmptyStmt();
    }
}
