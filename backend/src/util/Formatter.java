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
                + parsedStatement  + "\", \""
                + enclosingClass + "\", \""
                + enclosingMethod + "\", "
                + id + "));" + "\n";
    }

    /**
     * Returns statement preceding the curly braces if it exists, otherwise returns the original string
     */
    private static String parseStatement(String statement) {
        if (statement.contains("{")){
            return statement.split("\\{")[0];
        }
        return statement;
    }
}
