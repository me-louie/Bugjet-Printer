package loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class CodeLoader {
    private static final String MODIFIED_AST_FILE_PATH = "backend/test/modifiedast/";
    private static final String CLASS_FILE_PATH = "backend/test/modifiedast/modifiedast/";

    public static void run(String classname) throws Exception {

        File sourceFile = new File(MODIFIED_AST_FILE_PATH + classname + ".java");
        // compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        // load the compiled class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { parentDirectory.toURI().toURL() });
        Class<?> modifiedClass = classLoader.loadClass(classname);

        // call main on the loaded class
        Method method = modifiedClass.getDeclaredMethod("main", String[].class);
        method.invoke(modifiedClass.getDeclaredConstructor().newInstance(), (Object) null);

        // delete .class artifact after running the code
        new File(CLASS_FILE_PATH + classname + ".class").delete();
    }
}
