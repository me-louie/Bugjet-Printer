package ast;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Map;

public class VariableAnnotationCollector extends VoidVisitorAdapter<Map<String, String>> {

    @Override
    public void visit(NormalAnnotationExpr nae, Map<String, String> collector) {
        super.visit(nae, collector);
        if (nae.getNameAsString().equals("Track")) {
            // TODO: fix this string hack
            String var = nae.getPairs().getFirst().get().getValue().toString().replace("\""
                    , "");
            String alias = nae.getPairs().getLast().get().getValue().toString().replace("\""
                    , "");
            collector.put(var, alias);
        }
    }
}
