package util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

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
        return new ForEachStmt(StaticJavaParser.parseVariableDeclarationExpr("String var"),
                StaticJavaParser.parseExpression("VariableReferenceLogger.refToVarMap.get(" + var + ".toString())"),
                StaticJavaParser.parseStatement("VariableLogger.log(var," + var + ", "
                        + uniqueIdentifier + ");"));

    }

    public static Statement createRefToVarMapChecks(String var, int uniqueIdentifier) {
        Statement forLoop = createForLoop(var, uniqueIdentifier);
        return new IfStmt(
                StaticJavaParser.parseExpression("VariableReferenceLogger.refToVarMap.containsKey(" + var +
                        ".toString())"), forLoop, emptyElse());

    }

    public static Statement lineInfoMapPut(String name, int uniqueIdentifier) {
        return StaticJavaParser.parseStatement("VariableLogger.lineInfoMap.put("
                + uniqueIdentifier + ", new LineInfo("
                + name + ".toString(), "
                + "\"TODO\", "
                + "\"TODO\", "
                + 000 + ", "
                + "\"TODO\", "
                + "\"TODO\", "
                + "\"TODO\", "
                + uniqueIdentifier + "));");
    }

    // TODO: fix parsing errors so the below works
//    public static Statement lineInfoMapPut(LineInfo lineInfo) {
//        return StaticJavaParser.parseStatement("VariableLogger.lineInfoMap.put("
//                + lineInfo.getUniqueIdentifier() + ", new LineInfo("
//                + lineInfo.getName() + ", "
//                + lineInfo.getAlias() +  ", "
//                + lineInfo.getType() + ", "
//                + lineInfo.getLineNum() + ", "
//                + lineInfo.getStatement() + ", "
//                + lineInfo.getEnclosingClass() + ", "
//                + lineInfo.getEnclosingMethod() +  ", "
//                + lineInfo.getUniqueIdentifier() + "));");
//    }

    // TODO: figure out how to actually generate an empty else blk
    private static Statement emptyElse() {
        return new EmptyStmt();
    }
}
