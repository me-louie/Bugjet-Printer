package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MethodArgsTest {

    public static void main(String[] args) throws IOException {
        MethodArgsTest mat = new MethodArgsTest();
        mat.methodArgs(5, "test");
        VariableReferenceLogger.checkBaseAndNestedObjects(mat, 0);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "arg1", nickname = "arg1")
    @Track(var = "arg2", nickname = "arg2")
    public void methodArgs(int arg1, String arg2) {
        VariableReferenceLogger.evaluateVarDeclaration(arg2, "arg2", 5);
        VariableReferenceLogger.evaluateVarDeclaration(arg1, "arg1", 4);
        int aaa = 10;
        if (arg1 < 5) {
            arg1 = 4;
            VariableReferenceLogger.evaluateAssignment(this, "this", 1);
            VariableReferenceLogger.evaluateAssignment(arg1, "arg1", 1);
        }
        arg1 = 10;
        VariableReferenceLogger.evaluateAssignment(this, "this", 2);
        VariableReferenceLogger.evaluateAssignment(arg1, "arg1", 2);
        if (arg1 > 5) {
            arg1 = 33;
            VariableReferenceLogger.evaluateAssignment(this, "this", 3);
            VariableReferenceLogger.evaluateAssignment(arg1, "arg1", 3);
        }
    }
}
