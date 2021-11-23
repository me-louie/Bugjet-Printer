package util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.Statement;

public final class StatementCreator {

    public static Statement logVariable(String name, String enclosingMethod, String enclosingClass, String value,
                                        int uniqueIdentifier) {
        // TODO: possibly change this to something better
        if (value == null) {
            value = "\"uninitialized\"";
        }
        return log(name, enclosingMethod, enclosingClass, value, uniqueIdentifier);
    }

    public static Statement evaluateVarDeclarationWithoutInitializerStatement(String name, String enclosingMethod,
                                                                              String enclosingClass, int id) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer(\""
                + name + "\", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", "
                + id
                + ");");
    }

    private static Statement log(String name, String enclosingMethod, String enclosingClass, String value, int id) {
        return StaticJavaParser.parseStatement("VariableLogger.log(\""
                + name + "\", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", \""
                + value + ", "
                + id + ");");
    }

    public static Statement evaluateAssignmentStatement(String name, String enclosingMethod, String enclosingClass,
                                                        int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateAssignment("
                + name
                + ", \""
                + name + "\", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", "
                + uniqueNum
                + ");");
    }

    public static Statement evaluateVarDeclarationStatement(String name, String enclosingMethod,
                                                            String enclosingClass, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateVarDeclaration("
                + name
                + ", \""
                + name + "\", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", "
                + uniqueNum
                + ");");
    }

    public static Statement checkBaseAndNestedObjectsStatement(String name, String enclosingMethod,
                                                               String enclosingClass, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.checkBaseAndNestedObjects("
                + name
                + ", \""
                + name + "\", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", "
                + uniqueNum
                + ");");
    }

    public static Statement evaluateForLoopVarDeclarationStatement(String name, String enclosingMethod,
                                                                   String enclosingClass, int uniqueNum) {
        return StaticJavaParser.parseStatement("VariableReferenceLogger.evaluateForLoopVarDeclaration("
                + name
                + ", \""
                + enclosingMethod + "\", \""
                + enclosingClass + "\", \""
                + ", \""
                + name
                + "\", "
                + uniqueNum
                + ");");
    }
}
