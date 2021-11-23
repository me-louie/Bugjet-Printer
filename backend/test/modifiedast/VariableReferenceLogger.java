package modifiedast;

import annotation.VariableScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VariableReferenceLogger {

    // maps variable name -> the obj reference that the variable points to
    private static HashMap<VariableScope, String> varToRefMap = new HashMap<>();

    // maps obj reference -> set of variable scopes that point to this reference
    private static HashMap<String, Set<VariableScope>> refToVarMap = new HashMap<>();

    // maps obj reference -> json representation of object after last time it was mutated
    private static HashMap<String, String> refToJsonMap = new HashMap<>();

    // maps obj reference -> set of variable scopes in which the reference should be tracked
    public static HashMap<String, Set<VariableScope>> refToScopeMap = new HashMap<>();

    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void evaluateVarDeclaration(Object var, String varName, String enclosingMethod, String enclosingClass, int lineInfoNum) {
        VariableScope scope = new VariableScope(varName, enclosingMethod, enclosingClass);
        if (var == null) {
            varToRefMap.put(scope, null);
            VariableLogger.log(null, varName, enclosingMethod, enclosingClass, lineInfoNum);
            return;
        }
        // check if this reference already has an entry, it may if another tracked variable also references it
        if (!isTrackedReference(var.toString())) {
            refToVarMap.put(var.toString(), new HashSet<>());
            // TODO: Should populate refToScopeMap here?
            refToScopeMap.put(var.toString(), new HashSet<>());
        }
        // add this var to the list of vars that point to its reference
        refToVarMap.get(var.toString()).add(scope);
        refToScopeMap.get(var.toString()).add(scope);
        // add an entry for this variable
        varToRefMap.put(scope, var.toString());
        refToJsonMap.put(var.toString(), gson.toJson(var));
        VariableLogger.log(var, varName, enclosingMethod, enclosingClass, lineInfoNum);
    }

    public static void evaluateVarDeclarationWithoutInitializer(String varName, String enclosingMethod, String enclosingClass, int lineInfoNum) {
        VariableScope scope = new VariableScope(varName, enclosingMethod, enclosingClass);
        varToRefMap.put(scope, null);
        VariableLogger.log("uninitialized", varName, enclosingMethod, enclosingClass, lineInfoNum);
    }

    public static void evaluateForLoopVarDeclaration(Object var, String varName, String enclosingMethod, String enclosingClass, int lineInfoNum) {
        VariableScope scope = new VariableScope(varName, enclosingMethod, enclosingClass);
        // only add for loop variable to VarMap if it isn't already there
        if (!isTrackedReference(var.toString())) {
            refToVarMap.put(var.toString(), new HashSet<>());
            // add this var to the list of vars that point to its
            refToVarMap.get(var.toString()).add(scope);
            // reference
            // add an entry for this variable
            varToRefMap.put(scope, var.toString());
            VariableLogger.log(var, varName, enclosingMethod, enclosingClass, lineInfoNum);
        }
    }

    public static void evaluateAssignment(Object var, String varName, String enclosingMethod, String enclosingClass, int lineInfoNum) {
        VariableScope scope = new VariableScope(varName, enclosingMethod, enclosingClass);
        if (var == null) {
            evaluateNullAssignment(scope, lineInfoNum);
            return;
        }
        if (trackedVarReferenceHasChanged(var, varName)) {
            // if a tracked var now points to a different ref
            // update the map to reflect that
            updateMapsWithNewReference(var, scope);
            // log for this one tracked var
            VariableLogger.log(var, varName, enclosingMethod, enclosingClass, lineInfoNum);
            // TODO: update reference in refToScopeMap?
        }
        // if the above if block ran then checkBaseAndNestedObjects won't detect any changes since the
        // refToJsonMap entry for this ref was just updated to be this exact version of the obj
        checkBaseAndNestedObjects(var, varName, enclosingMethod, enclosingClass, lineInfoNum);
    }

    public static void checkBaseAndNestedObjects(Object baseObject, String varName, String enclosingMethod, String enclosingClass, int lineInfoNum) {
        VariableScope scope = new VariableScope(varName, enclosingMethod, enclosingClass);
        if (baseObject == null) {
            return;
        }
        checkObject(baseObject, lineInfoNum, baseObject, scope);
        for (Field field : baseObject.getClass().getFields()) {
            try {
                Object nestedObject = field.get(baseObject);
                if (nestedObject == null) {
                    continue;
                }
                checkObject(baseObject, lineInfoNum, nestedObject, scope);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkObject(Object var, int lineInfoNum, Object nestedObject, VariableScope scope) {
        if (isTrackedReference(nestedObject.toString()) && isObjectModified(nestedObject)) {
            // update the refToJsonMap with the modified object
            refToJsonMap.put(var.toString(), gson.toJson(var));
            logForAllVariablesThatPointToReference(var, lineInfoNum, scope);
        }
    }

    private static void logForAllVariablesThatPointToReference(Object var, int lineInfoNum, VariableScope scope) {
        for (VariableScope trackedVariableScope : refToVarMap.get(var.toString())) {
            VariableLogger.log(var, trackedVariableScope.getVarName(), scope.getEnclosingMethod(), scope.getEnclosingClass(), lineInfoNum);
        }
    }

    private static boolean isObjectModified(Object var) {
        return !refToJsonMap.get(var.toString()).equals(gson.toJson(var));
    }

    private static boolean trackedVarReferenceHasChanged(Object var, String varName) {
        return varToRefMap.containsKey(varName) && !var.toString().equals(varToRefMap.get(varName));
    }

    private static void evaluateNullAssignment(VariableScope scope, int lineInfoNum) {
        if (varToRefMap.containsKey(scope)) {
            String oldRef = varToRefMap.get(scope);
            removeOldRefEntriesFromMap(scope, oldRef);
            varToRefMap.put(scope, null);
            // we don't add a null entry for refToVarMap here because this will lead to every variable declared as null
            // getting a history entry every time a tracked variable is set to null
            VariableLogger.log(scope.getVarName(), scope.getEnclosingMethod(), scope.getEnclosingMethod(), null, lineInfoNum);
        }
    }

    private static boolean isTrackedReference(String s) {
        return refToVarMap.containsKey(s);
    }

    private static void updateMapsWithNewReference(Object var, VariableScope scope) {
        // grab a copy of varName's old obj ref
        String oldRef = varToRefMap.get(scope.getVarName());
        String newRef = var.toString();
        removeOldRefEntriesFromMap(scope, oldRef);
        // replace varName's old entry with the new obj ref
        varToRefMap.put(scope, newRef);
        if (!isTrackedReference(newRef)) {
            // if this var ref doesn't have an entry
            // add an entry for the new ref
            refToVarMap.put(newRef, new HashSet<>());
            refToScopeMap.put(newRef, new HashSet<>());
            refToJsonMap.put(var.toString(), gson.toJson(var));
        }
        // add varName to the list of variables that point to newRef
        refToVarMap.get(newRef).add(scope);
        refToScopeMap.get(newRef).add(scope);
    }

    private static void removeOldRefEntriesFromMap(VariableScope scope, String oldRef) {
        if (oldRef != null) {
            // remove varName from the list of variables that point
            refToVarMap.get(oldRef).remove(scope);
            // to oldRef
            if (refToVarMap.get(oldRef).isEmpty()) {
                // if no other variables we care about point to oldRef
                // remove oldRef from both maps that key by reference
                refToVarMap.remove(oldRef);
                refToScopeMap.remove(oldRef);
                refToJsonMap.remove(oldRef);
                // we don't delete the varToRefMap entry because it just gets overwritten
            }
        }
    }
}
