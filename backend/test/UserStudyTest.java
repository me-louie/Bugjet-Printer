import java.util.Arrays;

public class UserStudyTest {

    public static void main(String[] args) {
        UserStudyTest ust = new UserStudyTest();
        ust.swap();
    }
    // Given 2 arrays:
    // arr1 = {10, 11, 12, 13, 14};
    // arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Goal: swap array values such that the end states are the following:
    // arr1 = {10, 9, 8, 7, 6}
    // arr2 = {1, 2, 3, 4, 5, 14, 13, 12, 11, 10}

    private void swap() {
        int[] arr1 = {10, 11, 12, 13, 14};
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int j = arr2.length - arr1.length -1;
        for (int i = 1; i < arr1.length; i++) {
            int temp = arr1[i];
            arr1[i] = arr2[j];
            arr2[j] = temp;
        }
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }
}


