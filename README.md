# Cool and Fun Java Variable Tracker for Friends

A recent poll found that 100% of developers write code that doesn't work. This tool aims to help mitigate the effects of that problem by providing an easy way to track a variable's value across the life of a program. The tool can be used in place of the less elegant solutions developers often resort to, like inserting thousands of print statements into their already appalling code. 

## How it works
1. Write some code that doesn't work
2. For each variable you wish to track add the `@Track` annotation above the method where the variable is first seen. For example, if you wanted to track the variable `myObject`:
   ```aidl
   @Track(var = "myObject", nickname = "myObject")
   void myMethod() {
       MyObject myObject = new MyObject();
       myObject.doStuffThatChangesMyObject();
   }
   ```
   
   `@Track` takes in two String arguments: `var`—the name of the variable you want to track—and `nickname`—the name you want the variable to be represented as in the tool's visualization. The `nickname` argument can be anything, including the actual name of the variable. It is intended to be used to differentiate two variables with the same name. e.g.
      ```aidl
      @Track(var = "i", nickname = "myMethodI")
      void myMethod(int i) {
        ...
      }
   
      @Track(var = "i", nickname = "otherMethodI")
      void otherMethod() {
         int i = 5;
         ...
      }
      ```
      The tool automatically limits tracking to the scope of a method, thus it's not necessary for users to give unique names to variables or nicknames for the program to function properly. However, you may wish to do so to avoid confusion when looking at the visualization. 
3. TODO: Run the tool 
4. Add your code to the tool's editor. Run it to render variable histories. Step through the visualization to see how and where your variable changes.

See the `examples` folder for some example code snippets that you can try out.

## What we support
1. Tracking primitive, array, and user-defined local variables. e.g.
   ```aidl
   @Track(var = "prim", nickname = "prim")
   @Track(var = "arr", nickname = "arr")
   @Track(var = "userDefObj", nickname = "userDefObj")
   void myMethod() {
        int[] arr = new int[10];
        UserDefinedObject userDefObj = new UserDefinedObject();
        userDefObj.setNum(10);
        for (int prim = 0; prim < arr.length; prim++) {
            arr[prim] = obj.getNum();
        }
   }
   ```
2. Tracking of primitive, array, and user-defined method arguments 
      ```aidl
   @Track(var = "prim", nickname = "prim")
   @Track(var = "arr", nickname = "arr")
   @Track(var = "userDefObj", nickname = "userDefObj")
   void myMethod(int i, int prim, int[] arr, UserDefinedObj userDefObj) {
        UserDefinedObject userDefObj = new UserDefinedObject();
        userDefObj.setNum(10);
        for (i = 0; i < arr.length; i++) {
            arr[i] = prim;
        }
   }
   ```
3. Tracking of aliases for objects (including arrays)
   ```aidl
   @Track(var = "userDefObj", nickname = "userDefObj"
   void myMethod() {
        UserDefinedObject userDefObj = new UserDefinedObject();
        UserDefinedObject alias = userDefObj;
        alias.setNum(11);         // will be tracked
        doSomething(userDefObj);
   }
   
   void doSomething(UserDefObj anotherAlias) {
        anotherAlias.setNum(12);   // will be tracked
   }
   ```

## What we don't support
1. Tracking of individual fields. e.g. 
   ```aidl
   @Track(var = "a.size", nickname = "a.size")
   void myMethod() {
        A a = new A();
        a.size = 5;
   }
   ```
   Individual fields can be indirectly tracked by tracking their enclosing object. In the example above the history of changes to `a.size` can be collected by tracking `a`. Note, however, that changes to `a`'s other fields will also be captured in this history.
2. Tracking of non-user defined objects (with the exception of arrays). e.g.
    ```aidl
      @Track(var = "myList", nickname = "myList")
      void myMethod() {
           List<String> list = new ArrayList<>(); 
           list.add("a"); // we do not guarantee that this will be tracked properly
      }
      ```
3. Separate tracking of multiple local variables that have the same name within a single method. e.g.
   ```aidl
   @Track(var = "i", nickname = "i")
   void myMethod() {
      for (int i = 0; i < 5; i++) {
         ...
      }
   
      if (conditionalMethod()) {
         int i = 6;
      } else {
         int i = 7;
      }
   ```
   Mutations to `i` in both the for loop and the if/else block will be logged as part of a single history for `i`.

## Program requirements
Code fed to our tool must 
1. include a `main` method with the signature `public static void main(String[] args)`. The signature can also include any exceptions the method throws.
   ```aidl
   public static void main(String[] args) {
      // this is valid 
   }
   
   @Track(var = "i", nickname = "i")
   void myMethod(int i) {
      ...
   }
   ```

   ```aidl
   public static void main(String[] args) throws FileNotFoundException {
      // this is also valid 
   }
   
   @Track(var = "i", nickname = "i")
   void myMethod(int i) {
      ...
   }
   ```

   ```aidl
   public static void main() {
      // this is not valid
   }
   
   @Track(var = "i", nickname = "i")
   void myMethod(int i) {
      ...
   }
   ```
2. follow Java naming conventions for variables. Local variables, method arguments, and non-final fields must be named using camelcase e.g.
   ```aidl
   @Track(var = "userDefObj", nickname = "validlyNamedVariable")
   void myMethod() {
        UserDefinedObject userDefObj = new UserDefinedObject();
        userDefObj.setNum(10);            // userDefObj is named in camelcase, this is valid
        int i = userDefObj.FINAL_FIELD;   // FINAL_FIELD is not camelcase, this is valid since the field is final
        System.out.println("hello world") // System is not camelcase, this is valid since System is not a local variable/field/method arg
   }
   ```

   ```aidl
   @Track(var = "UserDefObj", nickname = "InvalidlyNamedVariable")
   void myMethod() {
        UserDefinedObject UserDefObj = new UserDefinedObject(); // UserDefObj is local and not camelcase, this is not valid
   ```
3. use curly braces (i.e. `{}`) for any control flow blocks. This includes loops and if/else statements. For example, if the user wanted to track variable `i`:

    ```aidl
    for (int i = 0; i < size; i++)   // this is not valid
        doSomething();
    ```
    
    ```aidl
    for (int i = 0; i < size; i++) { // this is valid
        doSomething();
    }
   ```
