package ast;

import annotation.VariableScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VariableReferenceLogger {

    // maps variable name -> the obj reference that the variable points to
    private static HashMap<String, String> varToRefMap = new HashMap<>();
    // maps obj reference -> set of names of variables that point to this reference
    private static HashMap<String, Set<String>> refToVarMap = new HashMap<>();

    public static void evaluateVarDeclaration(Object var, String varName, int lineInfoNum) {
        if (var == null) {
            varToRefMap.put(varName, null);
            VariableLogger.log(varName, null, lineInfoNum);
            return;
        }
        // check if this reference already has an entry, it may if another tracked variable also references it
        if (!isTrackedReference(var.toString())) {
            refToVarMap.put(var.toString(), new HashSet<>());
            // TODO: Should populate refToScopeMap here? Use lineinfo map to get scope info? Maybe delegate this to
            //  VariableLogger?
        }
        refToVarMap.get(var.toString()).add(varName); // add this var to the list of vars that point to its reference
        varToRefMap.put(varName, var.toString());     // add an entry for this variable
        VariableLogger.log(varName, var, lineInfoNum);
    }

    public static void evaluateVarDeclarationWithoutInitializer(String varName) {
        varToRefMap.put(varName, null);
    }

    public static void evaluateAssignment(Object var, String varName, int lineInfoNum) {
        if (var == null) {
            evaluateNullAssignment(varName, lineInfoNum);
            return;
        }
        if (trackedVarReferenceHasChanged(var, varName)) {
            updateMapsWithNewReference(var, varName);
            // TODO: update reference in refToScopeMap?
        }
        if (isTrackedReference(var.toString())) { // the obj that this var references is referenced by one or more tracked vars
            for (String trackedVariableName : refToVarMap.get(var.toString())) {
                VariableLogger.log(trackedVariableName, var, lineInfoNum);
            }
        }
    }

    private static boolean trackedVarReferenceHasChanged(Object var, String varName) {
        return varToRefMap.containsKey(varName) && !var.toString().equals(varToRefMap.get(varName));
    }

    private static void evaluateNullAssignment(String varName, int lineInfoNum) {
        if (varToRefMap.containsKey(varName)) {
            // we don't update the refToVarMap here because this will lead to every variable declared as null getting
            // a history entry every time a tracked variable is set to null
            varToRefMap.put(varName, null);
            VariableLogger.log(varName, null, lineInfoNum);
        }
    }

    private static boolean isTrackedReference(String s) {
        return refToVarMap.containsKey(s);
    }

    private static void updateMapsWithNewReference(Object var, String varName) {
        String oldRef = varToRefMap.get(varName);             // grab a copy of varName's old obj ref
        String newRef = var.toString();
        if (oldRef != null) {
            refToVarMap.get(oldRef).remove(varName);          // remove varName from the list of variables that point to oldRef
            if (refToVarMap.get(oldRef).isEmpty()) {          // if no other variables we care about point to oldRef
                refToVarMap.remove(oldRef);                   // remove oldRef from the map
            }
        }
        varToRefMap.put(varName, newRef);                     // replace varName's old entry with the new obj ref
        if (!isTrackedReference(newRef)) {                    // if this var ref doesn't have an entry
            refToVarMap.put(newRef, new HashSet<>());         // add an entry for the new ref
        }
        refToVarMap.get(newRef).add(varName);                 // add varName to the list of variables that point to newRef
    }
}
