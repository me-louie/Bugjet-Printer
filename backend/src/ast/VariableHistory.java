package ast;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.type.Type;

import java.util.ArrayList;
import java.util.List;

/** stores information about the history of a variable throughout execution */

public class VariableHistory {

    private String name;
    private Object initialValue;
    private Type type;
    private List<Object> mutations;

    public VariableHistory(VariableDeclarator vd) {
        this.name = vd.getNameAsString();
        this.initialValue = vd.getInitializer().isPresent() ? vd.getInitializer().get() : null;
        this.type = vd.getType();
        mutations = new ArrayList<>();
    }

    public void addMutation(AssignExpr ae) {
        mutations.add(ae.getValue());
        // todo: ae.getValue() might be a method call or another variable, need to parse it accordingly
    }
}
