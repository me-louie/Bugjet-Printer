package modifiedast;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UserStudyTest {

    public static void main(String[] args) throws IOException {
        UserStudyTest ust = new UserStudyTest();
        ust.swap();
        VariableReferenceLogger.checkBaseAndNestedObjects(ust, "ust", "public static void main(String[] args)", "UserStudyTest", 75);
        VariableLogger.writeOutputToDisk();
    }

    private void swap() {
        int[] arr1 = { 10, 11, 12, 13, 14 };
        int[] arr2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int j = arr2.length - arr1.length - 1;
        for (int i = 1; i < arr1.length; i++) {
            int temp = arr1[i];
            arr1[i] = arr2[j];
            VariableReferenceLogger.evaluateAssignment(this, "this", "private void swap()", "UserStudyTest", 76);
            VariableReferenceLogger.evaluateAssignment(arr1, "arr1", "private void swap()", "UserStudyTest", 76);
            arr2[j] = temp;
            VariableReferenceLogger.evaluateAssignment(this, "this", "private void swap()", "UserStudyTest", 77);
            VariableReferenceLogger.evaluateAssignment(arr2, "arr2", "private void swap()", "UserStudyTest", 77);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("arr1: \n");
        VariableReferenceLogger.checkBaseAndNestedObjects(sb, "sb", "private void swap()", "UserStudyTest", 78);
        for (int a = 0; a < arr1.length; a++) {
            sb.append(arr1[a] + ", ");
            VariableReferenceLogger.checkBaseAndNestedObjects(sb, "sb", "private void swap()", "UserStudyTest", 79);
        }
        sb.append("\n arr2: \n");
        VariableReferenceLogger.checkBaseAndNestedObjects(sb, "sb", "private void swap()", "UserStudyTest", 80);
        for (int b = 0; b < arr2.length; b++) {
            sb.append(arr2[b] + ", ");
            VariableReferenceLogger.checkBaseAndNestedObjects(sb, "sb", "private void swap()", "UserStudyTest", 81);
        }
        System.out.println(sb);
    }
}
