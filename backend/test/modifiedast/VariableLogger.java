package modifiedast;

import annotation.VariableScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modifiedast.VariableReferenceLogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VariableLogger {

    private static final String FILE_PATH = "out/output.json";
    // uniqueId -> LineInfo for a line that causes variable mutation
    public static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{
		put(35, new LineInfo("alias", "null", "null",66, "alias.setNum(4);", "ObjectTest", "private void nestedMethod(A alias)", 35));
		put(36, new LineInfo("alias", "null", "null",67, "alias.setArr(new int[] { 12, 13, 14 });", "ObjectTest", "private void nestedMethod(A alias)", 36));
		put(37, new LineInfo("alias", "null", "null",68, "alias.setObj(new B());", "ObjectTest", "private void nestedMethod(A alias)", 37));
		put(39, new LineInfo("alias", "null", "null",70, "alias.obj.setbInt(9);", "ObjectTest", "private void nestedMethod(A alias)", 39));
		put(40, new LineInfo("alias", "null", "null",72, "alias = new A(-100, new int[] { -100, 200, 300 }, new B());", "ObjectTest", "private void nestedMethod(A alias)", 40));
		put(3, new LineInfo("a.arr", "null", "null",21, "a.arr = new int[] { 3, 4, 5 };", "ObjectTest", "public void calc()", 3));
		put(4, new LineInfo("a.arr", "null", "null",22, "a.arr[0] = 0;", "ObjectTest", "public void calc()", 4));
		put(47, new LineInfo("this.obj", "null", "null",97, "this.obj = obj;", "A", "null", 47));
		put(17, new LineInfo("notTrackedA.num", "null", "null",40, "notTrackedA.num = 5;", "ObjectTest", "public void calc()", 17));
		put(30, new LineInfo("a", "null", "null",58, "a.setNum(1);", "ObjectTest", "private static void nestedStaticMethod(A a)", 30));
		put(0, new LineInfo("ot", "null", "null",12, "ot.calc();", "ObjectTest", "public static void main(String[] args)", 0));
		put(21, new LineInfo("notTrackedA", "null", "null",44, "notTrackedA.obj.setbInt(11);", "ObjectTest", "public void calc()", 21));
		put(23, new LineInfo("notTrackedA", "null", "null",46, "notTrackedA.setNum(6);", "ObjectTest", "public void calc()", 23));
		put(24, new LineInfo("notTrackedA", "null", "null",47, "notTrackedA.setArr(new int[] { 18, 19, 20 });", "ObjectTest", "public void calc()", 24));
		put(25, new LineInfo("notTrackedA", "null", "null",48, "notTrackedA.setObj(new B());", "ObjectTest", "public void calc()", 25));
		put(27, new LineInfo("notTrackedA", "null", "null",52, "notTrackedA.setObj(nestedB);", "ObjectTest", "public void calc()", 27));
		put(29, new LineInfo("notTrackedA", "null", "null",54, "notTrackedA.obj.setbInt(7);", "ObjectTest", "public void calc()", 29));
		put(45, new LineInfo("this.num", "null", "null",95, "this.num = num;", "A", "null", 45));
		put(13, new LineInfo("anotherA", "anotherA", "A",34, "A anotherA;", "ObjectTest", "public void calc()", 13));
		put(14, new LineInfo("anotherA", "anotherA", "null",35, "anotherA = null;", "ObjectTest", "public void calc()", 14));
		put(15, new LineInfo("anotherA", "anotherA", "null",36, "anotherA = a;", "ObjectTest", "public void calc()", 15));
		put(16, new LineInfo("anotherA", "anotherA", "null",37, "anotherA = new A(-2, new int[] { -2 }, new B());", "ObjectTest", "public void calc()", 16));
		put(48, new LineInfo("this.bInt", "null", "null",106, "this.bInt = bInt;", "B", "public void setbInt(int bInt)", 48));
		put(26, new LineInfo("nestedB", "nestedB", "B",51, "B nestedB = new B();", "ObjectTest", "public void calc()", 26));
		put(34, new LineInfo("alias.obj", "null", "null",65, "alias.obj = null;", "ObjectTest", "private void nestedMethod(A alias)", 34));
		put(43, new LineInfo("this.arr", "null", "null",85, "this.arr = arr;", "A", "public void setArr(int[] arr)", 43));
		put(7, new LineInfo("a.obj", "null", "null",25, "a.obj = null;", "ObjectTest", "public void calc()", 7));
		put(22, new LineInfo("notTrackedA.obj", "null", "null",45, "notTrackedA.obj = null;", "ObjectTest", "public void calc()", 22));
		put(31, new LineInfo("alias.num", "null", "null",62, "alias.num = 3;", "ObjectTest", "private void nestedMethod(A alias)", 31));
		put(41, new LineInfo("alias.num", "null", "null",73, "alias.num = -10000000;", "ObjectTest", "private void nestedMethod(A alias)", 41));
		put(38, new LineInfo("alias.obj.bInt", "null", "null",69, "alias.obj.bInt = 8;", "ObjectTest", "private void nestedMethod(A alias)", 38));
		put(5, new LineInfo("a.obj.bInt", "null", "null",23, "a.obj.bInt = 6;", "ObjectTest", "public void calc()", 5));
		put(42, new LineInfo("num", "null", "null",81, "num = num2;", "A", "public void setNum(int num2)", 42));
		put(20, new LineInfo("notTrackedA.obj.bInt", "null", "null",43, "notTrackedA.obj.bInt = 10;", "ObjectTest", "public void calc()", 20));
		put(28, new LineInfo("notTrackedA.obj.bInt", "null", "null",53, "notTrackedA.obj.bInt = 6;", "ObjectTest", "public void calc()", 28));
		put(44, new LineInfo("this.obj", "null", "null",89, "this.obj = obj;", "A", "public void setObj(B obj)", 44));
		put(2, new LineInfo("a.num", "null", "null",20, "a.num = 1;", "ObjectTest", "public void calc()", 2));
		put(46, new LineInfo("this.arr", "null", "null",96, "this.arr = arr;", "A", "null", 46));
		put(1, new LineInfo("a", "a", "A",19, "A a = new A(5, new int[] { 1, 2, 3 }, new B());", "ObjectTest", "public void calc()", 1));
		put(6, new LineInfo("a", "a", "null",24, "a.obj.setbInt(7);", "ObjectTest", "public void calc()", 6));
		put(8, new LineInfo("a", "a", "null",26, "a.setNum(2);", "ObjectTest", "public void calc()", 8));
		put(9, new LineInfo("a", "a", "null",27, "a.setArr(new int[] { 6, 7, 8 });", "ObjectTest", "public void calc()", 9));
		put(10, new LineInfo("a", "a", "null",28, "a.setObj(new B());", "ObjectTest", "public void calc()", 10));
		put(11, new LineInfo("a", "a", "null",31, "a = null;", "ObjectTest", "public void calc()", 11));
		put(12, new LineInfo("a", "a", "null",32, "a = new A(-1, new int[] { -1 }, new B());", "ObjectTest", "public void calc()", 12));
		put(32, new LineInfo("alias.arr", "null", "null",63, "alias.arr = new int[] { 9, 10, 11 };", "ObjectTest", "private void nestedMethod(A alias)", 32));
		put(33, new LineInfo("alias.arr", "null", "null",64, "alias.arr[0] = 1;", "ObjectTest", "private void nestedMethod(A alias)", 33));
		put(18, new LineInfo("notTrackedA.arr", "null", "null",41, "notTrackedA.arr = new int[] { 15, 16, 17 };", "ObjectTest", "public void calc()", 18));
		put(19, new LineInfo("notTrackedA.arr", "null", "null",42, "notTrackedA.arr[0] = 123;", "ObjectTest", "public void calc()", 19));
    }};
    // variable name -> Output object containing all info tracked about variable
    private static Map<VariableScope, Output> outputMap = new HashMap<>();
    private static Set<VariableScope> trackedScopes = new HashSet<>() {{
		add(new VariableScope("a", "public void calc()", "ObjectTest"));
		add(new VariableScope("anotherA", "public void calc()", "ObjectTest"));
		add(new VariableScope("nestedB", "public void calc()", "ObjectTest"));
    }};
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void log(Object variableValue, String variableName, String enclosingMethod, String enclosingClass,
                           Integer id) {
        LineInfo lineInfo = lineInfoMap.get(id);
        VariableScope scope = new VariableScope(variableName, enclosingMethod, enclosingClass);
        Set<VariableScope> scopesToLog = variableValue!= null && !trackedScopes.contains(scope) ?
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
