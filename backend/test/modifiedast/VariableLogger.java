package modifiedast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VariableLogger {

    private static final String FILE_PATH = "out/output.json";
    // uniqueId -> LineInfo for a line that causes variable mutation
    private static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
		put(0, new LineInfo("a", "alias_for_a", "double",16, "double a;", "SimpleTest", "public Double calc()", 0));
		put(1, new LineInfo("a", "alias_for_a", "null",18, "a = 5;", "SimpleTest", "public Double calc()", 1));
		put(2, new LineInfo("a", "alias_for_a", "null",26, "a++;", "SimpleTest", "public Double calc()", 2));
		put(3, new LineInfo("a", "alias_for_a", "null",32, "a = b + 1;", "SimpleTest", "public Double calc()", 3));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<String, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    // TODO: Make this work with other types
    public static void log(String variableName, double variableValue, Integer id) {
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

//    public static void log(String variableName, boolean variableValue, Integer id) {
//        log(variableName, (Boolean) variableValue, id);
//    }
//
//    public static void log(String variableName, int variableValue, Integer id) {
//        log(variableName, (Integer) variableValue, id);
//    }
//
//    public static void log(String variableName, long variableValue, Integer id) {
//        log(variableName, (Long) variableValue, id);
//    }
//
//    public static void log(String variableName, char variableValue, Integer id) {
//        log(variableName, (Character) variableValue, id);
//    }
//
//    public static void log(String variableName, float variableValue, Integer id) {
//        log(variableName, (Float) variableValue, id);
//    }
//
//    public static void log(String variableName, double variableValue, Integer id) {
//        log(variableName, (Double) variableValue, id);
//    }
//
//    public static void log(String variableName, short variableValue, Integer id) {
//        log(variableName, (Short) variableValue, id);
//    }
//
//    public static void log(String variableName, int[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, boolean[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, long[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, char[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, float[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, double[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, short[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, Object[] variableValue, Integer id) {
//        log(variableName, List.of(variableValue), id);
//    }
//
//    public static void log(String variableName, int[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, boolean[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, long[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, char[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, float[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, double[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, short[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }
//
//    public static void log(String variableName, Object[][] variableValue, Integer id) {
//        log(variableName, List.of(List.of(variableValue)), id);
//    }

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
