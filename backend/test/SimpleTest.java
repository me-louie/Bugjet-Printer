import annotation.Track;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) {
        SimpleTest st = new SimpleTest();
        st.calc();
    }

    @Track(var="x", nickname ="x")
    @Track(var = "y", nickname = "y")
    public Double calc() {
        double a = -1;

        int[] x = {1, 2, 3};
        int[] y = x;
        y[0] = 200;
        x[1] = 300;
//        y[0] = 100;
//        x[1] = 101;
//        int[] z = new int[3];
//        y = z;

//        int[] arr;
//        arr = new int[3];
//        arr[1] = 123;
//
//        int[] arrCopy;
//        arrCopy = arr;
//        arrCopy[0] = 1000;

//        int[] m = {1, 2, 3};
//        m[0] = 10;
//        m[1] = 11;
        return a;
    }
}
