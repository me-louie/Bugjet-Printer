package ast;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.Map;

public class VariableHistoryCollector extends VoidVisitorAdapter<Map<String, VariableHistory>> {

    @Override
    public void visit(VariableDeclarator vd, Map<String, VariableHistory> collector) {
        super.visit(vd, collector);
        collector.put(vd.getNameAsString(), new VariableHistory(vd));
    }

    @Override
    public void visit(AssignExpr ae, Map<String, VariableHistory> collector) {
        super.visit(ae, collector);
        VariableHistory vh = collector.get(ae.getTarget().toString());
        vh.addMutation(ae);
    }
}
