package util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.*;

public final class StatementCreator {

    public static Statement logVariable(String name, String value, int uniqueIdentifier) {
        // TODO: possibly change this to something better
        if (value == null) {
            value = "\"uninitialized\"";
        }
        return log(name, value, uniqueIdentifier);
    }

    public static Statement evaluateVarDeclarationWithoutInitializerStatement(String name, int id) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer(\""
                + name
                + "\","
                + id
                + ");");
    }

    private static Statement log(String name, String value, int id) {
        return StaticJavaParser.parseStatement("VariableLogger.log(\""
                + name + "\", "
                + value + ", "
                + id + ");");
    }

    public static Statement evaluateAssignmentStatement(String name, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateAssignment("
                + name
                + ", \""
                + name
                + "\", "
                + uniqueNum
                + ");");
    }

    public static Statement evaluateVarDeclarationStatement(String name, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateVarDeclaration("
                + name
                + ", \""
                + name
                + "\", "
                + uniqueNum
                + ");");
    }

    public static Statement checkBaseAndNestedObjectsStatement(String name, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.checkBaseAndNestedObjects("
                + name
                + ", "
                + uniqueNum
                + ");");
    }
}
