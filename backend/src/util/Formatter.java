package util;

public final class Formatter {

    public static String generatePutStatement(int id, String name, String alias, String type, int lineNum,
                                              String statement, String enclosingClass, String enclosingMethod) {
        String parsedStatement = parseStatement(statement);
        return "\t\tput("
                + id + ", new LineInfo(\""
                + name + "\", \""
                + alias + "\", \""
                + type + "\","
                + lineNum + ", \""
                + parsedStatement + "\", \""
                + enclosingClass + "\", \""
                + enclosingMethod + "\", "
                + id + "));" + "\n";
    }

    private static String parseStatement(String statement) {
        if (containsLoggingStatements(statement) || shouldAsParseCodeBlock(statement)) {
            String escapedStatement = statement
                    .replaceAll("\n", "\\\\n")
                    .replaceAll("\r", "\\\\r")
                    .replaceAll("\"", "\\\\\"");
            String[] parsed = escapedStatement.split("\\\\n");
            StringBuilder sb = new StringBuilder();
            for (String s : parsed) {
                if (!containsLoggingStatements(statement)) {
                    sb.append(s);
                }
            }
            return sb.toString();
        }
        return statement;
    }

    private static boolean containsLoggingStatements(String statement){
        return statement.contains("VariableReferenceLogger") || statement.contains("VariableLogger");
    }

    // TODO: add other cases as needed
    private static boolean shouldAsParseCodeBlock(String statement){
        return statement.contains("for");
    }
}
