package modifiedast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableLogger {

    private static final String FILE_PATH = "out/output.json";
    // uniqueId -> LineInfo for a line that causes variable mutation
    private static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
		put(9, new LineInfo("arr", "alias_for_arr", "int[]",36, "int[] arr;", "SimpleTest", "public Double calc()", 9));
		put(10, new LineInfo("arr", "alias_for_arr", "null",37, "arr = new int[3];", "SimpleTest", "public Double calc()", 10));
		put(11, new LineInfo("arr", "alias_for_arr", "null",38, "arr[1] = 123;", "SimpleTest", "public Double calc()", 11));
		put(0, new LineInfo("a", "alias_for_a", "double",18, "double a;", "SimpleTest", "public Double calc()", 0));
		put(2, new LineInfo("a", "alias_for_a", "null",20, "a = 5;", "SimpleTest", "public Double calc()", 2));
		put(6, new LineInfo("a", "alias_for_a", "null",28, "a++;", "SimpleTest", "public Double calc()", 6));
		put(8, new LineInfo("a", "alias_for_a", "null",33, "a = b + 1;", "SimpleTest", "public Double calc()", 8));
		put(1, new LineInfo("b", "alias_for_b", "double",19, "double b;", "SimpleTest", "public Double calc()", 1));
		put(3, new LineInfo("b", "alias_for_b", "null",21, "b = 6;", "SimpleTest", "public Double calc()", 3));
		put(4, new LineInfo("b", "alias_for_b", "null",25, "b--;", "SimpleTest", "public Double calc()", 4));
		put(5, new LineInfo("b", "alias_for_b", "null",23, "b++;", "SimpleTest", "public Double calc()", 5));
		put(7, new LineInfo("b", "alias_for_b", "null",29, "b *= b / a;", "SimpleTest", "public Double calc()", 7));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<String, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void logToOutputMap(String variableName, Object variableValue, Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
        Output output = (outputMap.containsKey(variableName)) ?
                outputMap.get(variableName) :
                new Output(variableName, lineInfo.getAlias(), lineInfo.getType());
        output.addMutation(lineInfo.getStatement(), lineInfo.getEnclosingClass(), lineInfo.getEnclosingMethod(),
                variableValue, lineInfo.getLineNum());
        outputMap.put(variableName, output);
    }

    public static void writeOutputToDisk() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(gson.toJson(outputMap.values()));
        writer.close();
    }

    // TODO: maybe document that we don't handle the tracking of Strings normally, this case is just for handling
    //  null variable values. Alternatively, figure out a better way to handle the nulls.
    protected static void log(String variableName, String variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, boolean variableValue, Integer id) {
        logToOutputMap(variableName, (Boolean) variableValue, id);
    }

    protected static void log(String variableName, int variableValue, Integer id) {
        logToOutputMap(variableName, (Integer) variableValue, id);
    }

    protected static void log(String variableName, long variableValue, Integer id) {
        logToOutputMap(variableName, (Long) variableValue, id);
    }

    protected static void log(String variableName, char variableValue, Integer id) {
        logToOutputMap(variableName, (Character) variableValue, id);
    }

    protected static void log(String variableName, float variableValue, Integer id) {
        logToOutputMap(variableName, (Float) variableValue, id);
    }

    protected static void log(String variableName, double variableValue, Integer id) {
        logToOutputMap(variableName, (Double) variableValue, id);
    }

    protected static void log(String variableName, short variableValue, Integer id) {
        logToOutputMap(variableName, (Short) variableValue, id);
    }

    protected static void log(String variableName, int[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, boolean[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, long[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, char[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, float[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, double[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, short[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, Object[] variableValue, Integer id) {
        logToOutputMap(variableName, variableValue, id);
    }

    protected static void log(String variableName, int[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, boolean[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, long[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, char[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, float[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, double[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, short[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
    }

    protected static void log(String variableName, Object[][] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(List.of(variableValue)), id);
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

        public void addMutation(String statement, String enclosingClass, String enclosingMethod, Object variableValue
                , int lineNum) {
            history.add(new Mutation(statement, enclosingClass, enclosingMethod, variableValue, lineNum));
        }

        private class Mutation {

            private String statement, enclosingClass, enclosingMethod, value;
            private int line;

            public Mutation(String statement, String enclosingClass, String enclosingMethod, Object variableValue,
                            int lineNum) {
                this.statement = statement;
                this.enclosingClass = enclosingClass;
                this.enclosingMethod = enclosingMethod;
                this.value = gson.toJson(variableValue);
                this.line = lineNum;
            }
        }

    }
}
