package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) throws IOException {
        SimpleTest st = new SimpleTest();
        st.calc();
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "x", nickname = "x")
    @Track(var = "y", nickname = "y")
    public Double calc() {
        double a = -1;
        int[] x = { 1, 2, 3 };
        if (!VariableReferenceLogger.refToVarMap.containsKey(x.toString()))
            VariableReferenceLogger.refToVarMap.put(x.toString(), new HashSet<>());
        else
            ;
        VariableReferenceLogger.refToVarMap.get(x.toString()).add("x");
        VariableReferenceLogger.varToRefMap.put(x, x.toString());
        VariableLogger.log("x", x, 0);
        int[] y = x;
        if (!VariableReferenceLogger.refToVarMap.containsKey(y.toString()))
            VariableReferenceLogger.refToVarMap.put(y.toString(), new HashSet<>());
        else
            ;
        VariableReferenceLogger.refToVarMap.get(y.toString()).add("y");
        VariableReferenceLogger.varToRefMap.put(y, y.toString());
        VariableLogger.log("y", x, 2);
        y[0] = 200;
        if (!y.toString().equals(VariableReferenceLogger.varToRefMap.get(y)))
            System.out.println("todo update references in both maps");
        else
            ;
        if (VariableReferenceLogger.refToVarMap.containsKey(y.toString()))
            for (String var : VariableReferenceLogger.refToVarMap.get(y.toString())) {
                VariableLogger.log(var, y, 4);
            }
        else
            ;
        x[1] = 300;
        if (!x.toString().equals(VariableReferenceLogger.varToRefMap.get(x)))
            System.out.println("todo update references in both maps");
        else
            ;
        if (VariableReferenceLogger.refToVarMap.containsKey(x.toString()))
            for (String var : VariableReferenceLogger.refToVarMap.get(x.toString())) {
                VariableLogger.log(var, x, 5);
            }
        else
            ;
        return a;
    }
}
