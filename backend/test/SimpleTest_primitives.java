import annotation.Track;

public class SimpleTest_primitives {

    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    public static void main(String[] args) {
        SimpleTest_primitives st = new SimpleTest_primitives();
        st.calc();
    }

    @Track(var="a", nickname = "nickname_for_a")
    @Track(var="b", nickname ="nickname_for_b")
    @Track(var="i", nickname="nickname_for_i")

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
            i+=2;
        }

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
        return a;
    }
}
