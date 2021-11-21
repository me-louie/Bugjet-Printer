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
        VariableReferenceLogger.checkBaseAndNestedObjects(ot, 0);
        VariableLogger.writeOutputToDisk();
    }

    @Track(var = "a", nickname = "a")
    @Track(var = "anotherA", nickname = "anotherA")
    @Track(var = "nestedB", nickname = "nestedB")
    public void calc() {
        A a = new A(5, new int[] { 1, 2, 3 }, new B());
        VariableReferenceLogger.evaluateVarDeclaration(a, "a", 1);
        a.num = 1;
        VariableReferenceLogger.evaluateAssignment(this, "this", 2);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.num, 2);
        VariableReferenceLogger.evaluateAssignment(a, "a", 2);
        a.arr = new int[] { 3, 4, 5 };
        VariableReferenceLogger.evaluateAssignment(this, "this", 3);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.arr, 3);
        VariableReferenceLogger.evaluateAssignment(a, "a", 3);
        a.arr[0] = 0;
        VariableReferenceLogger.evaluateAssignment(this, "this", 4);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.arr, 4);
        VariableReferenceLogger.evaluateAssignment(a, "a", 4);
        a.obj.bInt = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", 5);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj.bInt, 5);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj, 5);
        VariableReferenceLogger.evaluateAssignment(a, "a", 5);
        a.obj.setbInt(7);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, 6);
        a.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", 7);
        VariableReferenceLogger.checkBaseAndNestedObjects(a.obj, 7);
        VariableReferenceLogger.evaluateAssignment(a, "a", 7);
        a.setNum(2);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, 8);
        a.setArr(new int[] { 6, 7, 8 });
        VariableReferenceLogger.checkBaseAndNestedObjects(a, 9);
        a.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(a, 10);
        nestedMethod(a);
        nestedStaticMethod(a);
        a = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", 11);
        VariableReferenceLogger.evaluateAssignment(a, "a", 11);
        a = new A(-1, new int[] { -1 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", 12);
        VariableReferenceLogger.evaluateAssignment(a, "a", 12);
        A anotherA;
        VariableReferenceLogger.evaluateVarDeclarationWithoutInitializer("anotherA", 13);
        anotherA = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", 14);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", 14);
        anotherA = a;
        VariableReferenceLogger.evaluateAssignment(this, "this", 15);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", 15);
        anotherA = new A(-2, new int[] { -2 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", 16);
        VariableReferenceLogger.evaluateAssignment(anotherA, "anotherA", 16);
        A notTrackedA = a;
        notTrackedA.num = 5;
        VariableReferenceLogger.evaluateAssignment(this, "this", 17);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.num, 17);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 17);
        notTrackedA.arr = new int[] { 15, 16, 17 };
        VariableReferenceLogger.evaluateAssignment(this, "this", 18);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.arr, 18);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 18);
        notTrackedA.arr[0] = 123;
        VariableReferenceLogger.evaluateAssignment(this, "this", 19);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.arr, 19);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 19);
        notTrackedA.obj.bInt = 10;
        VariableReferenceLogger.evaluateAssignment(this, "this", 20);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj.bInt, 20);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, 20);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 20);
        notTrackedA.obj.setbInt(11);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 21);
        notTrackedA.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", 22);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, 22);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 22);
        notTrackedA.setNum(6);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 23);
        notTrackedA.setArr(new int[] { 18, 19, 20 });
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 24);
        notTrackedA.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 25);
        nestedMethod(notTrackedA);
        B nestedB = new B();
        VariableReferenceLogger.evaluateVarDeclaration(nestedB, "nestedB", 26);
        notTrackedA.setObj(nestedB);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 27);
        notTrackedA.obj.bInt = 6;
        VariableReferenceLogger.evaluateAssignment(this, "this", 28);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj.bInt, 28);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA.obj, 28);
        VariableReferenceLogger.evaluateAssignment(notTrackedA, "notTrackedA", 28);
        notTrackedA.obj.setbInt(7);
        VariableReferenceLogger.checkBaseAndNestedObjects(notTrackedA, 29);
    }

    private static void nestedStaticMethod(A a) {
        a.setNum(1);
        VariableReferenceLogger.checkBaseAndNestedObjects(a, 30);
    }

    private void nestedMethod(A alias) {
        alias.num = 3;
        VariableReferenceLogger.evaluateAssignment(this, "this", 31);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.num, 31);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 31);
        alias.arr = new int[] { 9, 10, 11 };
        VariableReferenceLogger.evaluateAssignment(this, "this", 32);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.arr, 32);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 32);
        alias.arr[0] = 1;
        VariableReferenceLogger.evaluateAssignment(this, "this", 33);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.arr, 33);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 33);
        alias.obj = null;
        VariableReferenceLogger.evaluateAssignment(this, "this", 34);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj, 34);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 34);
        alias.setNum(4);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, 35);
        alias.setArr(new int[] { 12, 13, 14 });
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, 36);
        alias.setObj(new B());
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, 37);
        alias.obj.bInt = 8;
        VariableReferenceLogger.evaluateAssignment(this, "this", 38);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj.bInt, 38);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.obj, 38);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 38);
        alias.obj.setbInt(9);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias, 39);
        alias = new A(-100, new int[] { -100, 200, 300 }, new B());
        VariableReferenceLogger.evaluateAssignment(this, "this", 40);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 40);
        alias.num = -10000000;
        VariableReferenceLogger.evaluateAssignment(this, "this", 41);
        VariableReferenceLogger.checkBaseAndNestedObjects(alias.num, 41);
        VariableReferenceLogger.evaluateAssignment(alias, "alias", 41);
    }

    private class A {

        public int num;

        public int[] arr;

        public void setNum(int num2) {
            num = num2;
            VariableReferenceLogger.evaluateAssignment(this, "this", 42);
            VariableReferenceLogger.evaluateAssignment(num, "num", 42);
        }

        public void setArr(int[] arr) {
            this.arr = arr;
            VariableReferenceLogger.evaluateAssignment(this, "this", 43);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.arr, 43);
            VariableReferenceLogger.evaluateAssignment(this, "this", 43);
        }

        public void setObj(B obj) {
            this.obj = obj;
            VariableReferenceLogger.evaluateAssignment(this, "this", 44);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.obj, 44);
            VariableReferenceLogger.evaluateAssignment(this, "this", 44);
        }

        public B obj;

        public A(int num, int[] arr, B obj) {
            this.num = num;
            VariableReferenceLogger.evaluateAssignment(this, "this", 45);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.num, 45);
            VariableReferenceLogger.evaluateAssignment(this, "this", 45);
            this.arr = arr;
            VariableReferenceLogger.evaluateAssignment(this, "this", 46);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.arr, 46);
            VariableReferenceLogger.evaluateAssignment(this, "this", 46);
            this.obj = obj;
            VariableReferenceLogger.evaluateAssignment(this, "this", 47);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.obj, 47);
            VariableReferenceLogger.evaluateAssignment(this, "this", 47);
        }
    }

    private static class B {

        public int bInt = 5;

        public void setbInt(int bInt) {
            this.bInt = bInt;
            VariableReferenceLogger.evaluateAssignment(this, "this", 48);
            VariableReferenceLogger.checkBaseAndNestedObjects(this.bInt, 48);
            VariableReferenceLogger.evaluateAssignment(this, "this", 48);
        }
    }
}
