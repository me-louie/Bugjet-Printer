package modifiedast;

import annotation.Track;
import java.io.IOException;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) throws IOException {
        SimpleTest st = new SimpleTest();
        st.calc();
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "x", nickname = "nickname_for_arr")
    @Track(var = "y", nickname = "nickname_for_arr_copy")
    public Double calc() {
        double a = -1;
        double b;
        a = 5;
        b = 6;
        if (a < 10) {
            b++;
        } else {
            b--;
        }
        while (a < 20) {
            a++;
            b *= b / a;
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            a = b + 1;
            i += 2;
        }
        int[] x = { 1, 2, 3 };
        VariableReferenceLogger.refToVarMap.put(x, x.toString());
        VariableLogger.log("x", x, 0);
        int[] y = x;
        VariableReferenceLogger.refToVarMap.put(y, y.toString());
        VariableLogger.log("y", x, 1);
        y[0] = 100;
        VariableLogger.log("y", y, 2);
        x[1] = 101;
        VariableLogger.log("x", x, 3);
        int[] z = new int[3];
        y = z;
        VariableLogger.log("y", y, 4);
        return a;
    }
}
