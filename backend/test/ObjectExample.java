import annotation.Track;

public class ObjectExample {

    public static void main(String[] args) {
        ObjectExample og = new ObjectExample();
        og.calc();
    }

//    Example object program, trying to figure out what this program does with its objects field, will you our analysis to
//    understand what is actually happening here without print statements. And fix the problems

    @Track(var = "BugA", nickname = "BugA")
    @Track(var = "a", nickname = "anotherA")
    @Track(var = "anotherA", nickname = "anotherA")
    @Track(var = "i", nickname = "i")
    public void calc() {
        A a = new A(5, new int[]{1, 2, 3});
        a.num = 1;
        a.arr = new int[]{3,4,5};

        a.setNum(2);
        a.setArr(new int[]{23,25,27});

        A anotherA;
        anotherA = null;
        anotherA = new A(-2, new int[]{5,6,7});

        BugA BugA = new BugA(0, new char[]{'y','n','y'});

        // Based on the char array swap another A's array with the a's array, so y means replace and n means don't replace
        int i;
        for (i = 1;  i <3; i++){
            if(BugA.ChaArr[i] == 'n'){
                anotherA.arr[i] = a.arr[i];
            }
        }




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

        public A(int num, int[] arr) {
            this.num = num;
            this.arr = arr;
        }
    }

    private class BugA {
        private int num;
        char[] ChaArr;

        public void setNum(int num) {
            num = num;
        }

        public void setArr(char[] arr) {
            this.ChaArr = arr;
        }

        public BugA(int num, char[] arr) {
            this.num = num;
            this.ChaArr = arr;
        }
    }
}
