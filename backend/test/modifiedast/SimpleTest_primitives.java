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
        VariableReferenceLogger.checkBaseAndNestedObjects(st, "st", "public static void main(String[] args)", "SimpleTest_primitives", 76);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "nickname_for_a")
    @Track(var = "b", nickname = "nickname_for_b")
    @Track(var = "i", nickname = "nickname_for_i")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", "public Double calc()", "SimpleTest_primitives", 77);
        double b;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("b", "public Double calc()", "SimpleTest_primitives", 78);
        a = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 79);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 79);
        b = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 80);
        VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 80);
        if (a < 10) {
            b++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 82);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 82);
        } else {
            b--;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 81);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 81);
        }
        while (a < 20) {
            a++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 83);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 83);
            b *= b / a;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 84);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 84);
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 88);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 88);
            VariableReferenceLogger.evaluateForLoopVarDeclaration(i, "i", "public Double calc()", "SimpleTest_primitives", 87);
            a = b + 1;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 85);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 85);
            i += 2;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 86);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 86);
        }
        return a;
    }
}
