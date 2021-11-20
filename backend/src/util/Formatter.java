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
        if (statement.contains("VariableReferenceLogger") || statement.contains("for")) {
            String escapedStatement = statement
                    .replaceAll("\n", "\\\\n")
                    .replaceAll("\r", "\\\\r")
                    .replaceAll("\"", "\\\\\"");
            String[] parsed = escapedStatement.split("\\\\n");
            StringBuilder sb = new StringBuilder();
            for (String s : parsed) {
                if (!s.contains("VariableReferenceLogger")) {
                    sb.append(s);
                }
            }
            return sb.toString();
        }
        return statement;
    }
}
