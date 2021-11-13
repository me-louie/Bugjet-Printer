import annotation.Track;

public class SimpleTest {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) {
        SimpleTest st = new SimpleTest();
        st.calc();
    }

    @Track(var="a", nickname = "nickname_for_a")
    @Track(var="b", nickname ="nickname_for_b")
    @Track(var="arr", nickname ="nickname_for_arr")
    @Track(var = "arrCopy", nickname = "nickname_for_arr_copy")
    public Double calc() {
        double a;
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
        }

        int[] arr;
        arr = new int[3];
        arr[1] = 123;

        int[] arrCopy;
        arrCopy = arr;
        arrCopy[0] = 1000;

        // cases we don't currently handle:
//        @Track(a=a)
//        while (a++ < 30) {
//            System.out.println("do nothing");
//        }
//
//        @Track(a=a)
//        for (int i = 0; i < 10; i++)
//            a = b >> 1;
//
//        @Track(a=a)
//        if (true)
//            a = 5;
//        else b = 6;

//        @Track(i=i)
//        for (int i = 0; i < 10; i++) {
//            a = b >> 1;
//        }
        return a;
    }
}
