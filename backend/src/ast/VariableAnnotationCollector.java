package ast;

import annotation.Scope;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Map;

public class VariableAnnotationCollector extends VoidVisitorAdapter<Map<Scope, String>> {

    @Override
    public void visit(MethodDeclaration md, Map<Scope, String> collector) {
        super.visit(md, collector);
        String scope = md.getDeclarationAsString(true, true, true);
        for (AnnotationExpr nae : md.getAnnotations()) {
            String name = nae.asNormalAnnotationExpr().getPairs().getFirst().get().getValue().toString().replace(
                    "\"", "");
            String nickname =
                    nae.asNormalAnnotationExpr().getPairs().getLast().get().getValue().toString().replace(
                            "\"", "");
            collector.put(new Scope(scope, name), nickname);
        }
    }
}
