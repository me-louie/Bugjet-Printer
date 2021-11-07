import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    private static final String FILE_PATH = "test/ReversePolishNotation.java";

    public static void main(String[] args) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
        List<String> methodNames = new ArrayList<>();
        methodNameCollector.visit(cu, methodNames);
        methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));
    }

    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }
}

