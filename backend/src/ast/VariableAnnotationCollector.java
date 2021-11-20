package ast;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Map;

public class VariableAnnotationCollector extends VoidVisitorAdapter<Map<String, String>> {

    @Override
    public void visit(MethodDeclaration md, Map<String, String> collector) {
        super.visit(md, collector);
        System.out.println("here");
        System.out.println(md.getDeclarationAsString(true, true, true));
        System.out.println("here");
    }
    @Override
    public void visit(NormalAnnotationExpr nae, Map<String, String> collector) {
        super.visit(nae, collector);
        if (nae.getNameAsString().equals("Track")) {
            // TODO: figure out where these "" are coming from and fix this string hack
            String var = nae.getPairs().getFirst().get().getValue().toString().replace("\"", "");
            String alias = nae.getPairs().getLast().get().getValue().toString().replace("\"", "");
            collector.put(var, alias);
        }
    }
}
