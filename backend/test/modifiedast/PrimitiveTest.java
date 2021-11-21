package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PrimitiveTest {

    public static int ONE_BILLION = 1000000000;

    public double memory = 0;

    public static void main(String[] args) throws IOException {
        PrimitiveTest st = new PrimitiveTest();
        st.calc();
        VariableReferenceLogger.checkBaseAndNestedObjects(st, 0);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "a")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", 1);
        a = 4;
        VariableReferenceLogger.evaluateAssignment(this, "this", 2);
        VariableReferenceLogger.evaluateAssignment(a, "a", 2);
        double b = a;
        b = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", 3);
        VariableReferenceLogger.evaluateAssignment(b, "b", 3);
        a++;
        VariableReferenceLogger.evaluateAssignment(this, "this", 4);
        VariableReferenceLogger.evaluateAssignment(a, "a", 4);
        a--;
        VariableReferenceLogger.evaluateAssignment(this, "this", 5);
        VariableReferenceLogger.evaluateAssignment(a, "a", 5);
        a *= 2;
        VariableReferenceLogger.evaluateAssignment(this, "this", 6);
        VariableReferenceLogger.evaluateAssignment(a, "a", 6);
        a = b;
        VariableReferenceLogger.evaluateAssignment(this, "this", 7);
        VariableReferenceLogger.evaluateAssignment(a, "a", 7);
        nestedMethod(a);
        return a;
    }

    private void nestedMethod(double alias) {
        alias = -1;
        VariableReferenceLogger.evaluateAssignment(this, "this", 8);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 8);
    }
}
