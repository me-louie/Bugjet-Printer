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
    public Double calc() {
        double a;
        // TODO: figure out how best way to handle different types
//        VariableLogger.log("a", null, 0);
        double b;
        a = 5;
        VariableLogger.log("a", a, 1);
        b = 6;
        if (a < 10) {
            b++;
        } else {
            b--;
        }
        while (a < 20) {
            a++;
            VariableLogger.log("a", a, 2);
            b *= b / a;
            System.out.println("hello world");
        }
        for (int i = 0; i < 10; i++) {
            a = b + 1;
            VariableLogger.log("a", a, 3);
        }
        return a;
    }
}
