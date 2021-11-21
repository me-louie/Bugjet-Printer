package modifiedast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VariableReferenceLogger {

    // maps variable name -> the obj reference that the variable points to
    private static HashMap<String, String> varToRefMap = new HashMap<>();

    // maps obj reference -> list of names of variables that point to this reference
    private static HashMap<String, Set<String>> refToVarMap = new HashMap<>();

    // maps obj reference -> json representation of object after last time it was mutated
    private static HashMap<String, String> refToJsonMap = new HashMap<>();

    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void evaluateVarDeclaration(Object var, String varName, int lineInfoNum) {
        if (var == null) {
            varToRefMap.put(varName, null);
            VariableLogger.log(varName, null, lineInfoNum);
            return;
        }
        // check if this reference already has an entry, it may if another tracked variable also references it
        if (!isTrackedReference(var.toString())) {
            refToVarMap.put(var.toString(), new HashSet<>());
        }
        // add this var to the list of vars that point to its reference
        refToVarMap.get(var.toString()).add(varName);
        // add an entry for this variable
        varToRefMap.put(varName, var.toString());
        refToJsonMap.put(var.toString(), gson.toJson(var));
        VariableLogger.log(varName, var, lineInfoNum);
    }

    public static void evaluateVarDeclarationWithoutInitializer(String varName, int lineInfoNum) {
        varToRefMap.put(varName, null);
        VariableLogger.log(varName, "uninitialized", lineInfoNum);
    }

    public static void evaluateAssignment(Object var, String varName, int lineInfoNum) {
        if (var == null) {
            evaluateNullAssignment(varName, lineInfoNum);
            return;
        }
        if (trackedVarReferenceHasChanged(var, varName)) {
            // if a tracked var now points to a different ref
            // update the map to reflect that
            updateMapsWithNewReference(var, varName);
            // log for this one tracked var
            VariableLogger.log(varName, var, lineInfoNum);
        }
        // if the above if block ran then checkBaseAndNestedObjects won't detect any changes since the
        // refToJsonMap entry for this ref was just updated to be this exact version of the obj
        checkBaseAndNestedObjects(var, lineInfoNum);
    }

    public static void checkBaseAndNestedObjects(Object baseObject, int lineInfoNum) {
        if (baseObject == null) {
            return;
        }
        checkObject(baseObject, lineInfoNum, baseObject);
        for (Field field : baseObject.getClass().getFields()) {
            try {
                Object nestedObject = field.get(baseObject);
                if (nestedObject == null) {
                    continue;
                }
                checkObject(baseObject, lineInfoNum, nestedObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkObject(Object var, int lineInfoNum, Object nestedObject) {
        if (isTrackedReference(nestedObject.toString()) && isObjectModified(nestedObject)) {
            // update the refToJsonMap with the modified object
            refToJsonMap.put(var.toString(), gson.toJson(var));
            logForAllVariablesThatPointToReference(var, lineInfoNum);
        }
    }

    private static void logForAllVariablesThatPointToReference(Object var, int lineInfoNum) {
        for (String trackedVariableName : refToVarMap.get(var.toString())) {
            VariableLogger.log(trackedVariableName, var, lineInfoNum);
        }
    }

    private static boolean isObjectModified(Object var) {
        return !refToJsonMap.get(var.toString()).equals(gson.toJson(var));
    }

    private static boolean trackedVarReferenceHasChanged(Object var, String varName) {
        return varToRefMap.containsKey(varName) && !var.toString().equals(varToRefMap.get(varName));
    }

    private static void evaluateNullAssignment(String varName, int lineInfoNum) {
        if (varToRefMap.containsKey(varName)) {
            String oldRef = varToRefMap.get(varName);
            removeOldRefEntriesFromMap(varName, oldRef);
            varToRefMap.put(varName, null);
            // we don't add a null entry for refToVarMap here because this will lead to every variable declared as null
            // getting a history entry every time a tracked variable is set to null
            VariableLogger.log(varName, null, lineInfoNum);
        }
    }

    private static boolean isTrackedReference(String s) {
        return refToVarMap.containsKey(s);
    }

    private static void updateMapsWithNewReference(Object var, String varName) {
        // grab a copy of varName's old obj ref
        String oldRef = varToRefMap.get(varName);
        String newRef = var.toString();
        removeOldRefEntriesFromMap(varName, oldRef);
        // replace varName's old entry with the new obj ref
        varToRefMap.put(varName, newRef);
        if (!isTrackedReference(newRef)) {
            // if this var ref doesn't have an entry
            // add an entry for the new ref
            refToVarMap.put(newRef, new HashSet<>());
            refToJsonMap.put(var.toString(), gson.toJson(var));
        }
        // add varName to the list of variables that point to newRef
        refToVarMap.get(newRef).add(varName);
    }

    private static void removeOldRefEntriesFromMap(String varName, String oldRef) {
        if (oldRef != null) {
            // remove varName from the list of variables that point to oldRef
            refToVarMap.get(oldRef).remove(varName);
            if (refToVarMap.get(oldRef).isEmpty()) {
                // if no other variables we care about point to oldRef
                // remove oldRef from both maps that key by reference
                refToVarMap.remove(oldRef);
                refToJsonMap.remove(oldRef);
                // we don't delete the varToRefMap entry because it just gets overwritten
            }
        }
    }
}
