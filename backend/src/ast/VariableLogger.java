package ast;

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
    }};
    private static Map<VariableScope, Output> outputMap = new HashMap<>();
    private static Set<VariableScope> trackedScopes = new HashSet<>() {{
    }};
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void log(Object variableValue, String variableName, String enclosingMethod, String enclosingClass,
                           Integer id) {
        // TODO: When assignment to null, enclosing class is null. Confirm with Ben best way to handle this case
        if (enclosingClass == null) return;
        LineInfo lineInfo = lineInfoMap.get(id);
        VariableScope scope = new VariableScope(variableName, enclosingMethod, enclosingClass);
        Set<VariableScope> scopesToLog = !trackedScopes.contains(scope) ?
                VariableReferenceLogger.refToScopeMap.get(variableValue.toString()) : Set.of(scope);
        for (VariableScope s: scopesToLog) {
            Output output = (outputMap.containsKey(s)) ?
                    outputMap.get(s) :
                    new Output(variableName, s, lineInfo.getNickname(), lineInfo.getType());
            output.addMutation(lineInfo.getStatement(), enclosingClass, enclosingMethod,
                    variableValue, lineInfo.getLineNum());
            outputMap.put(s, output);
        }
    }

    public static void writeOutputToDisk() throws IOException {
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
