package modifiedast;

public class LineInfo {

    private String name, alias, type, statement, enclosingClass, enclosingMethod;

    private Integer lineNum, uniqueIdentifier;

    public LineInfo(String name, String alias, String type, Integer lineNum, String statement, String enclosingClass, String enclosingMethod, int uniqueIdentifier) {
        this.name = name;
        this.alias = alias;
        this.type = type;
        this.lineNum = lineNum;
        this.statement = statement;
        this.enclosingClass = enclosingClass;
        this.enclosingMethod = enclosingMethod;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public LineInfo(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getEnclosingClass() {
        return enclosingClass;
    }

    public void setEnclosingClass(String enclosingClass) {
        this.enclosingClass = enclosingClass;
    }

    public String getEnclosingMethod() {
        return enclosingMethod;
    }

    public void setEnclosingMethod(String enclosingMethod) {
        this.enclosingMethod = enclosingMethod;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(int uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
