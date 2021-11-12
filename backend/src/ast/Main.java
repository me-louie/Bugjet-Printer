package ast;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.*;
import java.util.*;

public class Main {

    private static final String INPUT_FILE_PATH = "backend/test/SimpleTest.java";
    private static final String MODIFIED_AST_FILE_PATH = "backend/test/modifiedast/SimpleTest.java";
    private static final String MODIFIED_VARIABLE_LOGGER_FILE_PATH = "backend/test/modifiedast/VariableLogger.java";
    private static final String MODIFIED_LINE_INFO_FILE_PATH = "backend/test/modifiedast/LineInfo.java";
    private static final String VARIABLE_LOGGER_FILE_PATH = "backend/src/ast/VariableLogger.java";
    private static final String LINE_INFO_FILE_PATH = "backend/src/ast/LineInfo.java";
    private static final String MODIFIED_FILES_PACKAGE_NAME = "modifiedast";

    public static void main(String[] args) throws IOException {
        // get ast
        CompilationUnit cu = StaticJavaParser.parse(new File(INPUT_FILE_PATH));
        // collect names/aliases of variables to track
        VoidVisitor<Map<String, String>> variableAnnotationCollector = new VariableAnnotationCollector();
        Map<String, String> variablesToTrack = new HashMap<>(); // map of variable names -> the aliases we'll track them under
        variableAnnotationCollector.visit(cu, variablesToTrack);
        // add logging code to ast
        ModifierVisitor<Map<String, List<LineInfo>>> variableHistoryModifier = new VariableHistoryModifier();
        Map<String, List<LineInfo>> lineInfoMap = new HashMap<>(); // map of variable names -> list of LineInfo for each line a mutation occurs
        // we add an entry for the first declaration of a variable to pass in the alias
        variablesToTrack.keySet().forEach(var ->
                lineInfoMap.put(var, new ArrayList<>(List.of(new LineInfo(var, variablesToTrack.get(var))))));
        variableHistoryModifier.visit(cu, lineInfoMap);
        // this is super hacky, in order to get the alias info to the visit methods the first item in the list is a
        // LineInfo with only the name and alias. Since it's not a real LineInfo we delete it here. I'll fix this at a later date
        lineInfoMap.values().forEach(statementList -> statementList.remove(0));
        // add a call to VariableLogger.writeOutputToDisk() to write output after execution is complete
        try {
            MethodDeclaration mainMethod = cu.findFirst(MethodDeclaration.class, methodDeclaration ->
                    methodDeclaration.getDeclarationAsString().contains("public static void main(String[] args)")).get();
            IOException e = new IOException();
            mainMethod.addThrownException(e.getClass());
            mainMethod.getBody().get().addStatement("VariableLogger.writeOutputToDisk();");
        } catch (NoSuchElementException e) {
            System.out.println("File does not contain a main method");
            // todo: we'll probably want to return this error to the frontend to display to user
            System.exit(1);
        }
        writeModifiedProgram(cu);
        writeModifiedVariableLogger(lineInfoMap);
        writeModifiedLineInfo();
        // todo: send output.json to frontend
    }

    private static String populateLineInfoMap(Map<String, List<LineInfo>> lineInfoMap) {
        StringBuilder putStatements = new StringBuilder();
        for (List<LineInfo> lineInfos : lineInfoMap.values()) {
            for (LineInfo lineInfo : lineInfos) {
                putStatements.append("\t\tput(" + lineInfo.getUniqueIdentifier() + ", new LineInfo(\""
                        + lineInfo.getName() + "\", \"" + lineInfo.getAlias() + "\", \"" + lineInfo.getType() + "\","
                        + lineInfo.getLineNum() + ", \"" + lineInfo.getStatement() + "\", \"" + lineInfo.getEnclosingClass()
                        + "\", \"" + lineInfo.getEnclosingMethod() + "\", " + lineInfo.getUniqueIdentifier() + "));" + "\n");
            }
        }
        return putStatements.toString();
    }

    private static void writeModifiedProgram(CompilationUnit cu) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(MODIFIED_AST_FILE_PATH));
        cu.setPackageDeclaration(MODIFIED_FILES_PACKAGE_NAME);
        writer.write(cu.toString());
        writer.close();
    }

    private static void writeModifiedVariableLogger(Map<String, List<LineInfo>> lineInfoMap) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(MODIFIED_VARIABLE_LOGGER_FILE_PATH));
        BufferedReader reader = new BufferedReader(new FileReader(VARIABLE_LOGGER_FILE_PATH));
        String line;
        StringBuilder variableLoggerString = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            // TODO: fix this hacky way to chance the package name
            if (line.contains("package ast")){
                line = "package " + MODIFIED_FILES_PACKAGE_NAME + ";";
            }
            variableLoggerString.append(line).append("\n");
            if (line.contains("private static Map<Integer, LineInfo> lineInfoMap = new HashMap<>() {{")) {
                // take lineInfoMap from above and stick it into lineInfoMap in VariableLogger
                variableLoggerString.append(populateLineInfoMap(lineInfoMap));
            }
        }
        writer.write(variableLoggerString.toString());
        writer.close();
    }

    private static void writeModifiedLineInfo() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(MODIFIED_LINE_INFO_FILE_PATH));
        CompilationUnit lineInfoCU = StaticJavaParser.parse(new File(LINE_INFO_FILE_PATH));
        lineInfoCU.setPackageDeclaration(MODIFIED_FILES_PACKAGE_NAME);
        writer.write(lineInfoCU.toString());
        writer.close();
    }
}
