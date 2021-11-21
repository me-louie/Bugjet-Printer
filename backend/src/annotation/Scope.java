package annotation;

import java.util.Objects;

public class Scope {

    private String enclosingMethod;
    private String varName;

    public Scope(String methodDeclaration, String annotation) {
        this.enclosingMethod = methodDeclaration;
        this.varName = annotation;
    }

    public String getEnclosingMethod() {
        return enclosingMethod;
    }

    public String getVarName() {
        return varName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope that = (Scope) o;

        if (!Objects.equals(enclosingMethod, that.enclosingMethod))
            return false;
        return Objects.equals(varName, that.varName);
    }

    @Override
    public int hashCode() {
        int result = enclosingMethod != null ? enclosingMethod.hashCode() : 0;
        result = 31 * result + (varName != null ? varName.hashCode() : 0);
        return result;
    }
}
