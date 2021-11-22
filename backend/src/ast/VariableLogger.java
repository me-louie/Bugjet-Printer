package ast;

import annotation.Scope;
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
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<Scope, Output> outputMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void log(String variableName, Object variableValue, Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
//        String name = lineInfo.getName();
        String enclosingMethod = lineInfo.getEnclosingMethod();
        Scope scope = new Scope(enclosingMethod, variableName);
        Output output = (outputMap.containsKey(scope)) ?
                outputMap.get(scope) :
                new Output(variableName, scope, lineInfo.getNickname(), lineInfo.getType());
        output.addMutation(lineInfo.getStatement(), lineInfo.getEnclosingClass(), lineInfo.getEnclosingMethod(),
                variableValue, lineInfo.getLineNum());
        outputMap.put(scope, output);
    }

    public static void writeOutputToDisk() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(gson.toJson(outputMap.values()));
        writer.close();
    }

    private static class Output {

        private String name, nickname, type;
        private Scope scope;
        private List<Mutation> history;

        public Output(String name, Scope scope, String nickname, String type) {
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
