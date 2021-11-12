package ast;

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
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, boolean[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, long[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, char[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, float[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, double[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, short[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
    }

    protected static void log(String variableName, Object[] variableValue, Integer id) {
        logToOutputMap(variableName, List.of(variableValue), id);
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
