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

    @Track(var = "a", nickname = "nickname_for_a")
    @Track(var = "b", nickname = "nickname_for_b")
    @Track(var = "arr", nickname = "nickname_for_arr")
    @Track(var = "arrCopy", nickname = "nickname_for_arr_copy")
    @Track(var = "m", nickname = "nickname_for_m")
    public Double calc() {
        double a = -1;
        VariableLogger.log("a", -1, 0);
        double b;
        VariableLogger.log("b", "uninitialized", 1);
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
            i += 2;
        }
        int[] arr;
        VariableLogger.log("arr", "uninitialized", 9);
        arr = new int[3];
        VariableLogger.log("arr", arr, 10);
        arr[1] = 123;
        VariableLogger.log("arr", arr, 11);
        int[] arrCopy;
        VariableLogger.log("arrCopy", "uninitialized", 12);
        arrCopy = arr;
        VariableLogger.log("arr", arr, 14);
        VariableLogger.log("arrCopy", arrCopy, 13);
        arrCopy[0] = 1000;
        VariableLogger.log("arr", arr, 16);
        VariableLogger.log("arrCopy", arrCopy, 15);
        int[] m = { 1, 2, 3 };
        VariableLogger.log("m", m, 17);
        m[0] = 10;
        VariableLogger.log("m", m, 18);
        m[1] = 11;
        VariableLogger.log("m", m, 19);
        return a;
    }
}
