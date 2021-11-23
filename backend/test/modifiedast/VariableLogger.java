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
		put(5, new LineInfo("arg2", "arg2", "String",12, "public void methodArgs(int arg1, String arg2)", "MethodArgsTest", "public void methodArgs(int arg1, String arg2)", 5));
		put(0, new LineInfo("mat", "null", "null",7, "mat.methodArgs(5, \"test\");", "MethodArgsTest", "public static void main(String[] args)", 0));
		put(1, new LineInfo("arg1", "arg1", "null",15, "arg1 = 4;", "MethodArgsTest", "public void methodArgs(int arg1, String arg2)", 1));
		put(2, new LineInfo("arg1", "arg1", "null",17, "arg1 = 10;", "MethodArgsTest", "public void methodArgs(int arg1, String arg2)", 2));
		put(3, new LineInfo("arg1", "arg1", "null",19, "arg1 = 33;", "MethodArgsTest", "public void methodArgs(int arg1, String arg2)", 3));
		put(4, new LineInfo("arg1", "arg1", "int",12, "public void methodArgs(int arg1, String arg2)", "MethodArgsTest", "public void methodArgs(int arg1, String arg2)", 4));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<String, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

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
