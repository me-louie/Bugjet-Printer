package ast;

import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Map;

public class VariableAnnotationCollector extends VoidVisitorAdapter<Map<String, String>> {

    @Override
    public void visit(NormalAnnotationExpr nae, Map<String, String> collector) {
        super.visit(nae, collector);
        if (nae.getNameAsString().equals("Track")) {
            for (MemberValuePair pair : nae.getPairs()) {
                collector.put(pair.getNameAsString(), pair.getValue().toString());
            }
        }
    }
}
