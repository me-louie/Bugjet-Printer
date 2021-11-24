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
        VariableReferenceLogger.checkBaseAndNestedObjects(st, 0);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "nickname_for_a")
    @Track(var = "b", nickname = "nickname_for_b")
    @Track(var = "i", nickname = "nickname_for_i")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", 1);
        double b;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("b", 2);
        a = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", 3);
        VariableReferenceLogger.evaluateAssignment(a, "a", 3);
        b = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", 4);
        VariableReferenceLogger.evaluateAssignment(b, "b", 4);
        if (a < 10) {
            b++;
            VariableReferenceLogger.evaluateAssignment(this, "this", 6);
            VariableReferenceLogger.evaluateAssignment(b, "b", 6);
        } else {
            b--;
            VariableReferenceLogger.evaluateAssignment(this, "this", 5);
            VariableReferenceLogger.evaluateAssignment(b, "b", 5);
        }
        while (a < 20) {
            a++;
            VariableReferenceLogger.evaluateAssignment(this, "this", 7);
            VariableReferenceLogger.evaluateAssignment(a, "a", 7);
            b *= b / a;
            VariableReferenceLogger.evaluateAssignment(this, "this", 8);
            VariableReferenceLogger.evaluateAssignment(b, "b", 8);
            System.out.println("hello world");
            VariableReferenceLogger.checkBaseAndNestedObjects(System, 9);
        }
        for (int i = 0; i < 10; i++) {
            VariableReferenceLogger.evaluateAssignment(this, "this", 13);
            VariableReferenceLogger.evaluateAssignment(i, "i", 13);
            VariableReferenceLogger.evaluateForLoopVarDeclaration(i, "i", 12);
            a = b + 1;
            VariableReferenceLogger.evaluateAssignment(this, "this", 10);
            VariableReferenceLogger.evaluateAssignment(a, "a", 10);
            i += 2;
            VariableReferenceLogger.evaluateAssignment(this, "this", 11);
            VariableReferenceLogger.evaluateAssignment(i, "i", 11);
        }
        return a;
    }
}
