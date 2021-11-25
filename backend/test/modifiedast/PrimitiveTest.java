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
        VariableReferenceLogger.checkBaseAndNestedObjects(st, "st", "public static void main(String[] args)", "PrimitiveTest", 122);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "a")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", "public Double calc()", "PrimitiveTest", 123);
        a = 4;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 124);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "PrimitiveTest", 124);
        double b = a;
        b = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 125);
        VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "PrimitiveTest", 125);
        a++;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 126);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "PrimitiveTest", 126);
        a--;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 127);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "PrimitiveTest", 127);
        a *= 2;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 128);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "PrimitiveTest", 128);
        a = b;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "PrimitiveTest", 129);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "PrimitiveTest", 129);
        nestedMethod(a);
        return a;
    }

    private void nestedMethod(double alias) {
        alias = -1;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(double alias)", "PrimitiveTest", 130);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(double alias)", "PrimitiveTest", 130);
    }
}
