import annotation.Track;

public class ObjectTest {

        public static int ONE_BILLION = 1000000000;
        static int TWO_BILLION = 200000000;

        private double memory = 0;

        public static void main(String[] args) {
            ObjectTest ot = new ObjectTest();
            ot.calc();
        }

        @Track(var = "a", nickname = "a")
        @Track(var = "anotherA", nickname = "anotherA")
        @Track(var = "nestedB", nickname = "nestedB")
        public void calc() {
            A a = new A(5, new int[]{1, 2, 3}, new B());
            a.num = 1;
            a.arr = new int[]{3,4,5};
            a.arr[0] = 0;
            a.obj.bInt = 6;
            a.obj.setbInt(7);
            a.obj = null;
            a.setNum(2);
            a.setArr(new int[]{6,7,8});
            a.setObj(new B());
            nestedMethod(a);
            nestedStaticMethod(a);
            a = null;
            a = new A(-1, new int[]{-1}, new B());

            A anotherA;
            anotherA = null;
            anotherA = a;
            anotherA = new A(-2, new int[]{-2}, new B());

            A notTrackedA = a;
            notTrackedA.num = 5;
            notTrackedA.arr = new int[]{15,16,17};
            notTrackedA.arr[0] = 123;
            notTrackedA.obj.bInt = 10;
            notTrackedA.obj.setbInt(11);
            notTrackedA.obj = null;
            notTrackedA.setNum(6);
            notTrackedA.setArr(new int[]{18,19,20});
            notTrackedA.setObj(new B());
            nestedMethod(notTrackedA);

            B nestedB = new B();
            notTrackedA.setObj(nestedB);
            notTrackedA.obj.bInt = 6;
            notTrackedA.obj.setbInt(7);
        }

    private static void nestedStaticMethod(A a) {
            a.setNum(1);
    }

    private void nestedMethod(A alias) {
            alias.num = 3;
            alias.arr = new int[]{9,10,11};
            alias.arr[0] = 1;
            alias.obj = null;
            alias.setNum(4);
            alias.setArr(new int[]{12,13,14});
            alias.setObj(new B());
            alias.obj.bInt = 8;
            alias.obj.setbInt(9);
            // nothing after this point should be logged
            alias = new A(-100, new int[]{-100, 200, 300}, new B());
            alias.num = -10000000;
        }

    private class A {
            private int num;
            int[] arr;

        public void setNum(int num2) {
            num = num2;
        }

        public void setArr(int[] arr) {
            this.arr = arr;
        }

        public void setObj(B obj) {
            this.obj = obj;
        }

        private B obj;

            public A(int num, int[] arr, B obj) {
                this.num = num;
                this.arr = arr;
                this.obj = obj;
            }
    }

    private static class B {

        protected int bInt = 5;

        public void setbInt(int bInt) {
            this.bInt = bInt;
        }
    }
}

