package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleTest_primitives {

    public static int ONE_BILLION = 1000000000;

    public double memory = 0;

    public static void main(String[] args) throws IOException {
        SimpleTest_primitives st = new SimpleTest_primitives();
        st.calc();
        VariableReferenceLogger.checkBaseAndNestedObjects(st, "st", "public static void main(String[] args)", "SimpleTest_primitives", 61);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "nickname_for_a")
    @Track(var = "b", nickname = "nickname_for_b")
    @Track(var = "i", nickname = "nickname_for_i")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", "public Double calc()", "SimpleTest_primitives", 62);
        double b;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("b", "public Double calc()", "SimpleTest_primitives", 63);
        a = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 64);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 64);
        b = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 65);
        VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 65);
        if (a < 10) {
            b++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 67);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 67);
        } else {
            b--;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 66);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 66);
        }
        while (a < 20) {
            a++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 68);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 68);
            b *= b / a;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 69);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 69);
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 73);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 73);
            VariableReferenceLogger.evaluateForLoopVarDeclaration(i, "i", "public Double calc()", "SimpleTest_primitives", 72);
            a = b + 1;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 70);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 70);
            i += 2;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 71);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 71);
        }
        return a;
    }
}
