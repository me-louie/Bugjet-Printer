import annotation.Track;

public class MethodArgsTest {

    public static void main(String[] args) {
        MethodArgsTest mat = new MethodArgsTest();
        mat.methodArgs(5, "test");
    }

    @Track(var = "arg1", nickname = "arg1")
    @Track(var = "arg2", nickname = "arg2")
    public void methodArgs(int arg1, String arg2) {
        int aaa = 10;
        if (arg1 < 5) {
            arg1 = 4;
        }
        arg1 = 10;
        if (arg1 > 5) {
            arg1 = 33;
        }
    }

}
