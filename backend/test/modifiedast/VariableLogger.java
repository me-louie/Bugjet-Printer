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
		put(1, new LineInfo("a", "nickname_for_a", "double",19, "double a = -1;", "SimpleTest_primitives", "public Double calc()", 1));
		put(3, new LineInfo("a", "nickname_for_a", "null",21, "a = 5;", "SimpleTest_primitives", "public Double calc()", 3));
		put(7, new LineInfo("a", "nickname_for_a", "null",29, "a++;", "SimpleTest_primitives", "public Double calc()", 7));
		put(10, new LineInfo("a", "nickname_for_a", "null",34, "a = b + 1;", "SimpleTest_primitives", "public Double calc()", 10));
		put(0, new LineInfo("st", "null", "null",11, "st.calc();", "SimpleTest_primitives", "public static void main(String[] args)", 0));
		put(2, new LineInfo("b", "nickname_for_b", "double",20, "double b;", "SimpleTest_primitives", "public Double calc()", 2));
		put(4, new LineInfo("b", "nickname_for_b", "null",22, "b = 6;", "SimpleTest_primitives", "public Double calc()", 4));
		put(5, new LineInfo("b", "nickname_for_b", "null",26, "b--;", "SimpleTest_primitives", "public Double calc()", 5));
		put(6, new LineInfo("b", "nickname_for_b", "null",24, "b++;", "SimpleTest_primitives", "public Double calc()", 6));
		put(8, new LineInfo("b", "nickname_for_b", "null",30, "b *= b / a;", "SimpleTest_primitives", "public Double calc()", 8));
		put(11, new LineInfo("i", "nickname_for_i", "null",35, "i += 2;", "SimpleTest_primitives", "public Double calc()", 11));
		put(12, new LineInfo("i", "nickname_for_i", "int",33, "for (int i = 0; i < 10; i++) {\r    a = b + 1;\r    i += 2;\r}", "SimpleTest_primitives", "public Double calc()", 12));
		put(13, new LineInfo("i", "nickname_for_i", "null",33, "for (int i = 0; i < 10; i++) {\r    a = b + 1;\r    i += 2;\r}", "SimpleTest_primitives", "public Double calc()", 13));
		put(9, new LineInfo("System", "null", "null",31, "System.out.println(\"hello world\");", "SimpleTest_primitives", "public Double calc()", 9));
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
