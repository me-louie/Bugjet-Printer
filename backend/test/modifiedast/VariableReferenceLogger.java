package modifiedast;

import java.util.HashMap;
import java.util.Set;

public class VariableReferenceLogger {

    // maps variable name/Object?? -> the reference that the variable points to
    public static HashMap<Object, String> varToRefMap = new HashMap<>();

    // maps reference -> list of variables which point to that reference
    public static HashMap<String, Set<String>> refToVarMap = new HashMap<>();
}
