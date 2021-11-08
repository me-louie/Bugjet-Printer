package ast;

import ast.VariableHistory;
import ast.VariableHistoryCollector;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String FILE_PATH = "wherever/frontend/puts/input/file.java";

    public static void main(String[] args) throws FileNotFoundException {
        // todo: assuming frontend pass in args for which variables we should analyze, then we can just grab those instead
        //       of every variable, as this currently does
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        VoidVisitor<Map<String, VariableHistory>> variableHistoryCollector = new VariableHistoryCollector();
        Map<String, VariableHistory> variableHistories = new HashMap<>(); // map of variable names -> their histories (note that this assumes we couldn't have duplicate names, which we could have if we have multiple functions/classes that reuse the same name. Will likely need a more complex way of keying variables)
        variableHistoryCollector.visit(cu, variableHistories);
        // todo: Put variableHistories in a format that frontend can read and pass it back.
        //       Will have to have discussion with whoever is implementing visualizer about how they'd like the data
        //       to be represented--maybe outputting variableHistories as a json file or something
    }
}
