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
    public static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
		put(0, new LineInfo("a", "a", "double",19, "double a = -1;", "SimpleTest", "public Double calc()", 0));
		put(1, new LineInfo("a", "a", "null",20, "a = 4;", "SimpleTest", "public Double calc()", 1));
		put(2, new LineInfo("x", "x", "int[]",22, "int[] x;", "SimpleTest", "public Double calc()", 2));
		put(3, new LineInfo("x", "x", "null",23, "x = null;", "SimpleTest", "public Double calc()", 3));
		put(4, new LineInfo("x", "x", "null",24, "x = new int[] { 1, 2, 3 };", "SimpleTest", "public Double calc()", 4));
		put(6, new LineInfo("x", "x", "null",27, "x = y;", "SimpleTest", "public Double calc()", 6));
		put(8, new LineInfo("x", "x", "null",29, "x[1] = 300;", "SimpleTest", "public Double calc()", 8));
		put(10, new LineInfo("x", "x", "null",31, "x = new int[] { 4, 5, 6 };", "SimpleTest", "public Double calc()", 10));
		put(5, new LineInfo("y", "y", "int[]",25, "int[] y = x;", "SimpleTest", "public Double calc()", 5));
		put(7, new LineInfo("y", "y", "null",28, "y[0] = 200;", "SimpleTest", "public Double calc()", 7));
		put(11, new LineInfo("y", "y", "null",32, "y = new int[] { 7, 8, 9 };", "SimpleTest", "public Double calc()", 11));
		put(12, new LineInfo("alias", "null", "null",38, "alias[0] = -1;", "SimpleTest", "private void nestedMethod(int[] alias)", 12));
		put(13, new LineInfo("alias", "null", "null",39, "alias = new int[2];", "SimpleTest", "private void nestedMethod(int[] alias)", 13));
		put(9, new LineInfo("z", "null", "null",30, "z[2] = 400;", "SimpleTest", "public Double calc()", 9));
		put(14, new LineInfo("m", "m", "int",44, "int m;", "SimpleTest", "public void helloWorld()", 14));
		put(15, new LineInfo("m", "m", "null",46, "m++;", "SimpleTest", "public void helloWorld()", 15));
		put(16, new LineInfo("m", "m", "null",45, "", "SimpleTest", "public void helloWorld()", 16));
		put(17, new LineInfo("m", "m", "null",45, "", "SimpleTest", "public void helloWorld()", 17));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<String, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void log(String variableName, Object variableValue, Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
        Output output = (outputMap.containsKey(variableName)) ?
                outputMap.get(variableName) :
                new Output(variableName, lineInfo.getNickname(), lineInfo.getType());
        output.addMutation(lineInfo.getStatement(), lineInfo.getEnclosingClass(), lineInfo.getEnclosingMethod(),
                variableValue, lineInfo.getLineNum());
        outputMap.put(variableName, output);
    }

    public static void writeOutputToDisk() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(gson.toJson(outputMap.values()));
        writer.close();
    }

    private static class Output {

        private String name, nickname, type;
        private List<Mutation> history;

        public Output(String name, String nickname, String type) {
            this.name = name;
            this.nickname = nickname;
            this.type = type;
            history = new ArrayList<>();
        }

        public void addMutation(String statement, String enclosingClass, String enclosingMethod, Object variableValue,
                                int lineNum) {
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
