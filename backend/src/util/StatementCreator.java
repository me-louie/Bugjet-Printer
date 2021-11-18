package util;

import ast.LineInfo;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;

public final class StatementCreator {

    public static Statement logVariable(String name, String value, int uniqueIdentifier) {
        // TODO: possibly change this to something better
        if (value == null) {
            value = "\"uninitialized\"";
        }
        return log(name, value, uniqueIdentifier);
    }

    public static Statement refToVarMapNewPut(String var) {
        return new IfStmt(refToVarMapDoesNotContainKey(var), refToVarMapPutNewHashSet(var), emptyElse());
    }

    public static Statement refToVarMapAdd(String var) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.refToVarMap.get("
                + var + ".toString()).add(\"" + var + "\");");
    }

    public static Statement varToRefMapPut(String name) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.varToRefMap.put"
                + "(\"" + name + "\", " + name + ".toString());");
    }

    public static Statement createVarToRefMapChecks(String var) {
        return new IfStmt(
                StaticJavaParser.parseExpression("!" + var + ".toString().equals(VariableReferenceLogger"
                        + ".varToRefMap.get(" + var + "))"),
                StaticJavaParser.parseStatement(
                        "System.out.println(\"todo update references in both maps\");"),
                emptyElse());

    }

    public static Statement createRefToVarMapChecks(String var, int uniqueIdentifier) {
        String iterable = refToVarMapGetName(var);
        Statement forLoop = createForLoopLog(var, iterable, uniqueIdentifier);
        return new IfStmt(refToVarMapContainsKeyString(var), forLoop, emptyElse());

    }

    public static Statement createRefToVarMapAdd(String name, String value) {
        return StaticJavaParser.parseStatement(
                "VariableReferenceLogger.refToVarMap.get(" + value + ".toString()).add(\"" + name + "\");");
    }

    public static Statement lineInfoMapPut(LineInfo lineInfo) {
        return StaticJavaParser.parseStatement("VariableLogger.lineInfoMap.put("
                + lineInfo.getUniqueIdentifier() + ", new LineInfo(\""
                + lineInfo.getName() + "\", \""
                + lineInfo.getAlias() + "\", \""
                + lineInfo.getType() + "\", "
                + lineInfo.getLineNum() + ", \""
                + lineInfo.getStatement() + "\", \""
                + lineInfo.getEnclosingClass() + "\", \""
                + lineInfo.getEnclosingMethod() + "\", "
                + lineInfo.getUniqueIdentifier() + "));");
    }

    public static Statement createReferencedVarLogging(String name, LineInfo lineInfo, int uniqueIdentifier) {
        NodeList<Statement> statements = new NodeList<>();
        statements.add(getObjAddress(name));
        statements.add(getReferencedVars());
        statements.add(lineInfoMapPut(lineInfo));
        statements.add(createForLoopLog(name, "referencedVars", uniqueIdentifier));
        return new IfStmt(varToRefMapContainsKey(name), new BlockStmt(statements), emptyElse());

    }

    private static Statement emptyElse() {
        return new EmptyStmt();
    }

    private static Statement log(String name, String value, int id) {
        return StaticJavaParser.parseStatement("VariableLogger.log(\""
                + name + "\", "
                + value + ", "
                + id + ");");
    }

    private static Statement logVar(String value, int id) {
        return StaticJavaParser.parseStatement("VariableLogger.log(var,"
                + value + ", "
                + id + ");");
    }

    private static Statement getObjAddress(String name) {
        return StaticJavaParser.parseStatement("String objAddress = VariableReferenceLogger.varToRefMap.get"
                + "(\"" + name + "\");");
    }

    private static Statement getReferencedVars() {
        return StaticJavaParser.parseStatement("Set<String> referencedVars = VariableReferenceLogger"
                + ".refToVarMap.get(objAddress);");
    }

    private static Expression varToRefMapContainsKey(String name) {
        return StaticJavaParser.parseExpression("VariableReferenceLogger.varToRefMap.containsKey(\"" + name + "\")");
    }

    private static Expression refToVarMapContainsKeyString(String name) {
        return StaticJavaParser.parseExpression("VariableReferenceLogger.refToVarMap.containsKey("
                + name + ".toString())");
    }

    private static Expression refToVarMapDoesNotContainKey(String name) {
        return StaticJavaParser.parseExpression("!VariableReferenceLogger.refToVarMap.containsKey("
                + name + ".toString())");
    }

    private static Statement refToVarMapPutNewHashSet(String name) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.refToVarMap.put("
                + name + ".toString(), new HashSet<>());");
    }

    private static String refToVarMapGetName(String name) {
        return "VariableReferenceLogger.refToVarMap.get(" + name + ".toString())";
    }

    private static Statement createForLoopLog(String value, String iterable, int uniqueIdentifier) {
        NodeList<Statement> statements = new NodeList<>();
        statements.add(logVar(value, uniqueIdentifier));
        return new ForEachStmt(StaticJavaParser.parseVariableDeclarationExpr("String var"), iterable,
                new BlockStmt(statements));
    }
}
