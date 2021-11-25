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
        VariableReferenceLogger.checkBaseAndNestedObjects(st, "st", "public static void main(String[] args)", "SimpleTest_primitives", 144);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "nickname_for_a")
    @Track(var = "b", nickname = "nickname_for_b")
    @Track(var = "i", nickname = "nickname_for_i")
    public Double calc() {
        double a = -1;
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", "public Double calc()", "SimpleTest_primitives", 145);
        double b;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("b", "public Double calc()", "SimpleTest_primitives", 146);
        a = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 147);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 147);
        b = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 148);
        VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 148);
        if (a < 10) {
            b++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 150);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 150);
        } else {
            b--;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 149);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 149);
        }
        while (a < 20) {
            a++;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 151);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 151);
            b *= b / a;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 152);
            VariableReferenceLogger.evaluateAssignment(b, "b", "public Double calc()", "SimpleTest_primitives", 152);
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 156);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 156);
            VariableReferenceLogger.evaluateForLoopVarDeclaration(i, "i", "public Double calc()", "SimpleTest_primitives", 155);
            a = b + 1;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 153);
            VariableReferenceLogger.evaluateAssignment(a, "a", "public Double calc()", "SimpleTest_primitives", 153);
            i += 2;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public Double calc()", "SimpleTest_primitives", 154);
            VariableReferenceLogger.evaluateAssignment(i, "i", "public Double calc()", "SimpleTest_primitives", 154);
        }
        return a;
    }
}
