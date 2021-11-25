package modifiedast;

import annotation.VariableScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VariableLogger {

    private static final String FILE_PATH = "out/output.json";
    // uniqueId -> LineInfo for a line that causes variable mutation
    public static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
		put(144, new LineInfo("st", "null", "null",11, "st.calc();", "SimpleTest_primitives", "public static void main(String[] args)", 144));
		put(154, new LineInfo("i", "nickname_for_i", "null",35, "i += 2;", "SimpleTest_primitives", "public Double calc()", 154));
		put(155, new LineInfo("i", "nickname_for_i", "int",33, "for (int i = 0; i < 10; i++) {\r    a = b + 1;\r    i += 2;\r}", "SimpleTest_primitives", "public Double calc()", 155));
		put(156, new LineInfo("i", "nickname_for_i", "null",33, "for (int i = 0; i < 10; i++) {\r    a = b + 1;\r    i += 2;\r}", "SimpleTest_primitives", "public Double calc()", 156));
		put(146, new LineInfo("b", "nickname_for_b", "double",20, "double b;", "SimpleTest_primitives", "public Double calc()", 146));
		put(148, new LineInfo("b", "nickname_for_b", "null",22, "b = 6;", "SimpleTest_primitives", "public Double calc()", 148));
		put(149, new LineInfo("b", "nickname_for_b", "null",26, "b--;", "SimpleTest_primitives", "public Double calc()", 149));
		put(150, new LineInfo("b", "nickname_for_b", "null",24, "b++;", "SimpleTest_primitives", "public Double calc()", 150));
		put(152, new LineInfo("b", "nickname_for_b", "null",30, "b *= b / a;", "SimpleTest_primitives", "public Double calc()", 152));
		put(145, new LineInfo("a", "nickname_for_a", "double",19, "double a = -1;", "SimpleTest_primitives", "public Double calc()", 145));
		put(147, new LineInfo("a", "nickname_for_a", "null",21, "a = 5;", "SimpleTest_primitives", "public Double calc()", 147));
		put(151, new LineInfo("a", "nickname_for_a", "null",29, "a++;", "SimpleTest_primitives", "public Double calc()", 151));
		put(153, new LineInfo("a", "nickname_for_a", "null",34, "a = b + 1;", "SimpleTest_primitives", "public Double calc()", 153));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<VariableScope, Output> outputMap = new HashMap<>();
    private static Set<VariableScope> trackedScopes = new HashSet<>() {{
		add(new VariableScope("i", "public Double calc()", "SimpleTest_primitives"));
		add(new VariableScope("b", "public Double calc()", "SimpleTest_primitives"));
		add(new VariableScope("a", "public Double calc()", "SimpleTest_primitives"));
    }};
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    public static void log(Object variableValue, String variableName, String enclosingMethod, String enclosingClass,
                           Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
        System.out.println("lineInfo: " + lineInfo);
        VariableScope scope = new VariableScope(variableName, enclosingMethod, enclosingClass);
        Output output = (outputMap.containsKey(scope)) ?
                outputMap.get(scope) :
                new Output(variableName, scope, lineInfo.getNickname(), lineInfo.getType());
        output.addMutation(lineInfo.getStatement(), enclosingClass, enclosingMethod,
                variableValue, lineInfo.getLineNum());
        outputMap.put(scope, output);
    }

    public static void writeOutputToDisk() throws IOException {
        System.out.println("writing to disk");
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(gson.toJson(outputMap.values()));
        writer.close();
    }

    private static class Output {

        private String name, nickname, type;
        private VariableScope scope;
        private List<Mutation> history;

        public Output(String name, VariableScope scope, String nickname, String type) {
            this.name = name;
            this.scope = scope;
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
