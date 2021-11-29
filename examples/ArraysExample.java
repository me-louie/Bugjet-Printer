import annotation.Track;
import java.util.Arrays;

public class ArraysExample {

    @Track(var = "a", nickname = "a")
    @Track(var = "b", nickname = "b")
    @Track(var = "c", nickname = "c")
    @Track(var = "d", nickname = "d")
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        int[] b = {-1, -2, -3, -4};
        swap(a, b);
        System.out.println("a should equal [-1, -2, -3, -4, 5, 6, 7], is actually equal to: " + Arrays.toString(a));
        System.out.println("b should equal [1, 2, 3, 4], is actually equal to: " + Arrays.toString(b));

        int[][] c = { {1, 2, 3}, {4, 5, 6} };
        int[][] d = { {7, 8, 9, 10}, {11, 12}, {13, 14, 15, 16}};
        twoDSwap(c, d);
        System.out.println("c[0] should equal [7, 8, 9], is actually equal to: " + Arrays.toString(c[0]));
        System.out.println("c[1] should equal [11, 12, 6], is actually equal to: " + Arrays.toString(c[1]));
        System.out.println("d[0] should equal [1, 2, 3, 10], is actually equal to: " + Arrays.toString(d[0]));
        System.out.println("d[1] should equal [4, 5], is actually equal to: " + Arrays.toString(d[1]));
        System.out.println("d[2] should equal [13, 14, 15, 16], is actually equal to: " + Arrays.toString(d[2]));
    }

    // swaps the values of a[i] and b[i] up until the end of one of the arrays is reached
    // then leaves the remaining indexes of the longer array untouched
    public static void swap(int[] a, int[] b) {
        for (int i = 0; i < a.length && i < b.length; i++) {
            int[] temp = a;
            a[i] = b[i];
            b[i] = temp[i];
        }
    }

    // swaps the values of a[i] and b[i] up until the end of one of the arrays is reached
    // then leaves the remaining indexes of the longer array untouched
    public static void twoDSwap(int[][] a, int[][] b) {
        for (int i = 0; i < a[0].length && i < b[0].length; i++) {
            for (int j = 0; j < b.length && j < a.length; j++) {
                int[] temp = Arrays.copyOf(a[j], a[j].length);
                a[j] = new int[b[j].length];
                for (int k = 0; k < a[j].length; k++) {
                    a[j][k] = b[j][k];
                }
                b[i] = temp;
            }
        }
    }
}
