package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) throws IOException {
        SimpleTest st = new SimpleTest();
        st.calc();
        st.helloWorld();
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "x", nickname = "x")
    @Track(var = "y", nickname = "y")
    @Track(var = "a", nickname = "a")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", 0);
        a = 4;
        VariableReferenceLogger.evaluateAssignment(a, "a", 1);
        int[] x;
        VariableLogger.log("x", "uninitialized", 2);
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("x");
        x = null;
        VariableReferenceLogger.evaluateAssignment(x, "x", 3);
        x = new int[] { 1, 2, 3 };
        VariableReferenceLogger.evaluateAssignment(x, "x", 4);
        int[] y = x;
        VariableReferenceLogger.evaluateVarDeclaration(y, "y", 5);
        int[] z = y;
        x = y;
        VariableReferenceLogger.evaluateAssignment(x, "x", 6);
        y[0] = 200;
        VariableReferenceLogger.evaluateAssignment(y, "y", 7);
        x[1] = 300;
        VariableReferenceLogger.evaluateAssignment(x, "x", 8);
        z[2] = 400;
        VariableReferenceLogger.evaluateAssignment(z, "z", 9);
        x = new int[] { 4, 5, 6 };
        VariableReferenceLogger.evaluateAssignment(x, "x", 10);
        y = new int[] { 7, 8, 9 };
        VariableReferenceLogger.evaluateAssignment(y, "y", 11);
        nestedMethod(x);
        return a;
    }

    private void nestedMethod(int[] alias) {
        alias[0] = -1;
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 12);
        alias = new int[2];
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 13);
    }

    @Track(var = "x", nickname = "x")
    public void helloWorld() {
        int x;
        VariableLogger.log("x", "uninitialized", 14);
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("x");
        for (x = 100; x < 103; x++) {
            VariableReferenceLogger.evaluateAssignment(x, "x", 17);
            VariableReferenceLogger.evaluateAssignment(x, "x", 16);
            x++;
            VariableReferenceLogger.evaluateAssignment(x, "x", 15);
        }
    }
}
