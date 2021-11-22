import annotation.Track;

public class PrimitiveTest {

        public static int ONE_BILLION = 1000000000;

        private double memory = 0;

        public static void main(String[] args) {
            PrimitiveTest st = new PrimitiveTest();
            st.calc();
        }

        @Track(var = "a", nickname = "a")
        public Double calc() {
            double a = -1;
            a = 4;
            double b = a;
            b = 5;
            a++;
            a--;
            a *= 2;
            a = b;
            nestedMethod(a);
            return a;
        }

        private void nestedMethod(double alias) {
            alias = -1;
        }
}
