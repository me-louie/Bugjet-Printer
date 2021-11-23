package modifiedast;

import annotation.Track;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ObjectTest {

    public static int ONE_BILLION = 1000000000;

    public static int TWO_BILLION = 200000000;

    public double memory = 0;

    public static void main(String[] args) throws IOException {
        ObjectTest ot = new ObjectTest();
        ot.calc();
        VariableReferenceLogger.checkBaseAndNestedObjects(ot, "ot", "public static void main(String[] args)", "ObjectTest", 0);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "a")
    @Track(var = "anotherA", nickname = "anotherA")
    @Track(var = "nestedB", nickname = "nestedB")
    public void calc() {
        A a = new A(5, new int[] { 1, 2, 3 }, new B());
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", "public void calc()", "ObjectTest", 1);
        a.num = 1;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 2);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.num, "a.num", "public void calc()", "ObjectTest", 2);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 2);
        a.arr = new int[] { 3, 4, 5 };
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 3);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.arr, "a.arr", "public void calc()", "ObjectTest", 3);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 3);
        a.arr[0] = 0;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 4);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.arr, "a.arr", "public void calc()", "ObjectTest", 4);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 4);
        a.obj.bInt = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 5);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj.bInt, "a.obj.bInt", "public void calc()", "ObjectTest", 5);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj, "a.obj", "public void calc()", "ObjectTest", 5);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 5);
        a.obj.setbInt(7);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, "a", "public void calc()", "ObjectTest", 6);
        a.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 7);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj, "a.obj", "public void calc()", "ObjectTest", 7);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 7);
        a.setNum(2);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, "a", "public void calc()", "ObjectTest", 8);
        a.setArr(new int[] { 6, 7, 8 });
        VariableReferenceLogger.checkBaseAndNestedObjects(a, "a", "public void calc()", "ObjectTest", 9);
        a.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(a, "a", "public void calc()", "ObjectTest", 10);
        nestedMethod(a);
        nestedStaticMethod(a);
        a = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 11);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 11);
        a = new A(-1, new int[] { -1 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 12);
        VariableReferenceLogger.evaluateAssignment(a, "a", "public void calc()", "ObjectTest", 12);
        A anotherA;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("anotherA", "public void calc()", "ObjectTest", 13);
        anotherA = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 14);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", "public void calc()", "ObjectTest", 14);
        anotherA = a;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 15);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", "public void calc()", "ObjectTest", 15);
        anotherA = new A(-2, new int[] { -2 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 16);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", "public void calc()", "ObjectTest", 16);
        A notTrackedA = a;
        notTrackedA.num = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 17);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.num, "notTrackedA.num", "public void calc()", "ObjectTest", 17);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 17);
        notTrackedA.arr = new int[] { 15, 16, 17 };
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 18);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.arr, "notTrackedA.arr", "public void calc()", "ObjectTest", 18);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 18);
        notTrackedA.arr[0] = 123;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 19);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.arr, "notTrackedA.arr", "public void calc()", "ObjectTest", 19);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 19);
        notTrackedA.obj.bInt = 10;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 20);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj.bInt, "notTrackedA.obj.bInt", "public void calc()", "ObjectTest", 20);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, "notTrackedA.obj", "public void calc()", "ObjectTest", 20);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 20);
        notTrackedA.obj.setbInt(11);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 21);
        notTrackedA.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 22);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, "notTrackedA.obj", "public void calc()", "ObjectTest", 22);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 22);
        notTrackedA.setNum(6);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 23);
        notTrackedA.setArr(new int[] { 18, 19, 20 });
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 24);
        notTrackedA.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 25);
        nestedMethod(notTrackedA);
        B nestedB = new B();
        VariableReferenceLogger.evaluateVarDeclaration(nestedB, "nestedB", "public void calc()", "ObjectTest", 26);
        notTrackedA.setObj(nestedB);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 27);
        notTrackedA.obj.bInt = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", "public void calc()", "ObjectTest", 28);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj.bInt, "notTrackedA.obj.bInt", "public void calc()", "ObjectTest", 28);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, "notTrackedA.obj", "public void calc()", "ObjectTest", 28);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 28);
        notTrackedA.obj.setbInt(7);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, "notTrackedA", "public void calc()", "ObjectTest", 29);
    }

    private static void nestedStaticMethod(A a) {
        a.setNum(1);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, "a", "private static void nestedStaticMethod(A a)", "ObjectTest", 30);
    }

    private void nestedMethod(A alias) {
        alias.num = 3;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 31);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.num, "alias.num", "private void nestedMethod(A alias)", "ObjectTest", 31);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 31);
        alias.arr = new int[] { 9, 10, 11 };
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 32);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.arr, "alias.arr", "private void nestedMethod(A alias)", "ObjectTest", 32);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 32);
        alias.arr[0] = 1;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 33);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.arr, "alias.arr", "private void nestedMethod(A alias)", "ObjectTest", 33);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 33);
        alias.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 34);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj, "alias.obj", "private void nestedMethod(A alias)", "ObjectTest", 34);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 34);
        alias.setNum(4);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 35);
        alias.setArr(new int[] { 12, 13, 14 });
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 36);
        alias.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 37);
        alias.obj.bInt = 8;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 38);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj.bInt, "alias.obj.bInt", "private void nestedMethod(A alias)", "ObjectTest", 38);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj, "alias.obj", "private void nestedMethod(A alias)", "ObjectTest", 38);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 38);
        alias.obj.setbInt(9);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 39);
        alias = new A(-100, new int[] { -100, 200, 300 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 40);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 40);
        alias.num = -10000000;
        VariableReferenceLogger.evaluateAssignment(this, "this", "private void nestedMethod(A alias)", "ObjectTest", 41);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.num, "alias.num", "private void nestedMethod(A alias)", "ObjectTest", 41);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", "private void nestedMethod(A alias)", "ObjectTest", 41);
    }

    private class A {

        public int num;

        public int[] arr;

        public void setNum(int num2) {
            num = num2;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setNum(int num2)", "A", 42);
            VariableReferenceLogger.evaluateAssignment(num, "num", "public void setNum(int num2)", "A", 42);
        }

        public void setArr(int[] arr) {
            this.arr = arr;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setArr(int[] arr)", "A", 43);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.arr, "this.arr", "public void setArr(int[] arr)", "A", 43);
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setArr(int[] arr)", "A", 43);
        }

        public void setObj(B obj) {
            this.obj = obj;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setObj(B obj)", "A", 44);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.obj, "this.obj", "public void setObj(B obj)", "A", 44);
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setObj(B obj)", "A", 44);
        }

        public B obj;

        public A(int num, int[] arr, B obj) {
            this.num = num;
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 45);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.num, "this.num", "null", "A", 45);
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 45);
            this.arr = arr;
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 46);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.arr, "this.arr", "null", "A", 46);
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 46);
            this.obj = obj;
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 47);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.obj, "this.obj", "null", "A", 47);
            VariableReferenceLogger.evaluateAssignment(this, "this", "null", "A", 47);
        }
    }

    private static class B {

        public int bInt = 5;

        public void setbInt(int bInt) {
            this.bInt = bInt;
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setbInt(int bInt)", "B", 48);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.bInt, "this.bInt", "public void setbInt(int bInt)", "B", 48);
            VariableReferenceLogger.evaluateAssignment(this, "this", "public void setbInt(int bInt)", "B", 48);
        }
    }
}
