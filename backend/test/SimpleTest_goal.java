//import annotation.Track;
//import modifiedast.VariableLogger;
//import modifiedast.VariableReferenceLogger;
//
//import java.io.IOException;
//import java.util.HashSet;
//
//public class SimpleTest_goal {
//
//    public static int ONE_BILLION = 1000000000;
//
//    private double memory = 0;
//
//    public static void main(String[] args) throws IOException {
//        SimpleTest_goal st = new SimpleTest_goal();
//        st.calc();
//        VariableLogger.writeOutputToDisk();
//    }
//
//    @Track(var = "x", nickname = "x")
//    public Double calc() {
//        double a = -1;
//        int[] x = { 1, 2, 3 };
//        if (!VariableReferenceLogger.refToVarMap.containsKey(x.toString()))
//            VariableReferenceLogger.refToVarMap.put(x.toString(), new HashSet<>());
//        else
//            ;
//        VariableReferenceLogger.refToVarMap.get(x.toString()).add("x");
//        VariableReferenceLogger.varToRefMap.put(x, x.toString());
//        VariableLogger.log("x", x, 0);
//        int[] y = x;
//        // since x is tracked, add y to x's refToVarMap entry
//            VariableReferenceLogger.refToVarMap.get(x.toString()).add("y");
//            VariableReferenceLogger.varToRefMap.put(y, y.toString());

//        y[0] = 200;
//        if (VariableReferenceLogger.varToRefMap.containsKey(y)){
//            System.out.println("here");
//            VariableLogger.log("x", y, 00); // todo fix id , add entry to put map
//        }
//        x[1] = 300;
//        if (!x.toString().equals(VariableReferenceLogger.varToRefMap.get(x)))
//            System.out.println("todo update references in both maps");
//        else
//            ;
//        if (VariableReferenceLogger.refToVarMap.containsKey(x.toString()))
//            for (String var : VariableReferenceLogger.refToVarMap.get(x.toString())) {
//                VariableLogger.log(var, x, 2);
//            }
//        else
//            ;
//        return a;
//    }
//}
