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

    @Track(var = "a", alias = "alias_for_a")
    @Track(var = "b", alias = "alias_for_b")
    public Double calc() {
        double a;
//        VariableLogger.log("a", null, 0);
        double b;
//        VariableLogger.log("b", null, 1);
        a = 5;
        VariableLogger.log("a", a, 2);
        b = 6;
        VariableLogger.log("b", b, 3);
        if (a < 10) {
            b++;
            VariableLogger.log("b", b, 5);
        } else {
            b--;
            VariableLogger.log("b", b, 4);
        }
        while (a < 20) {
            a++;
            VariableLogger.log("a", a, 6);
            b *= b / a;
            VariableLogger.log("b", b, 7);
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            a = b + 1;
            VariableLogger.log("a", a, 8);
        }
        return a;
    }
}
