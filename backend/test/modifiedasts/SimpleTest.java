import ast.Track;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) {
        SimpleTest st = new SimpleTest();
        st.calc();
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", alias = "alias_for_a")
    public Double calc() {
        double a;
        double b;
        a = 5;
        b = 6;
        if (a < 10) {
            b++;
        } else {
            b--;
        }
        while (a < 20) {
            a++;
            b *= b / a;
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            a = b + 1;
        }
        return a;
    }
}
package ast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VariableLogger {

    private static final String FILE_PATH = "backend/out/output.json";
    // uniqueId -> LineInfo for a line that causes variable mutation
    private static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<String, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void log(String variableName, Object variableValue, Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
        Output output = (outputMap.containsKey(variableName)) ?
            outputMap.get(variableName) :
            new Output(variableName, lineInfo.getAlias(), lineInfo.getType());
        output.addMutation(lineInfo.getStatement(), lineInfo.getEnclosingClass(), lineInfo.getEnclosingMethod(),
                variableValue, lineInfo.getLineNum());
    }

    public static void writeOutputToDisk() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(gson.toJson(outputMap.values()));
        writer.close();
    }

    public static void log(String variableName, boolean variableValue, Integer id) {
        log(variableName, (Boolean) variableValue, id);
    }

    public static void log(String variableName, int variableValue, Integer id) {
        log(variableName, (Integer) variableValue, id);
    }

    public static void log(String variableName, long variableValue, Integer id) {
        log(variableName, (Long) variableValue, id);
    }

    public static void log(String variableName, char variableValue, Integer id) {
        log(variableName, (Character) variableValue, id);
    }

    public static void log(String variableName, float variableValue, Integer id) {
        log(variableName, (Float) variableValue, id);
    }

    public static void log(String variableName, double variableValue, Integer id) {
        log(variableName, (Double) variableValue, id);
    }

    public static void log(String variableName, short variableValue, Integer id) {
        log(variableName, (Short) variableValue, id);
    }

    public static void log(String variableName, int[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, boolean[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, long[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, char[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, float[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, double[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, short[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, Object[] variableValue, Integer id) {
        log(variableName, List.of(variableValue), id);
    }

    public static void log(String variableName, int[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, boolean[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, long[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, char[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, float[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, double[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, short[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    public static void log(String variableName, Object[][] variableValue, Integer id) {
        log(variableName, List.of(List.of(variableValue)), id);
    }

    private static class Output {

        private String name, alias, type;
        private List<Mutation> history;

        public Output(String name, String alias, String type) {
            this.name = name;
            this.alias = alias;
            this.type = type;
            history = new ArrayList<>();
        }

        public void addMutation(String statement, String enclosingClass, String enclosingMethod, Object variableValue, int lineNum) {
            history.add(new Mutation(statement, enclosingClass, enclosingMethod, variableValue, lineNum));
        }

        private class Mutation {

            private String statement, enclosingClass, enclosingMethod, value;
            private int line;

            public Mutation(String statement, String enclosingClass, String enclosingMethod, Object variableValue, int lineNum) {
                this.statement = statement;
                this.enclosingClass = enclosingClass;
                this.enclosingMethod = enclosingMethod;
                this.value = gson.toJson(variableValue);
                this.line = lineNum;
            }
        }

    }
}
package ast;

public class LineInfo {

    private String name, alias, type, statement, enclosingClass, enclosingMethod;
    private Integer lineNum, uniqueIdentifier;

    public LineInfo(String name, String alias, String type, Integer lineNum, String statement, String enclosingClass,
                    String enclosingMethod, int uniqueIdentifier) {
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
