package uned.dlr.pfc.util;



import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import uned.dlr.pfc.util.ExecutorIF;

public class ClosureExecutor implements ExecutorIF{

  public static String compile(String code) {
    Compiler compiler = new Compiler();

    CompilerOptions options = new CompilerOptions();
    // Advanced mode is used here, but additional options could be set, too.
   // CompilationLevel.ADVANCED_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);


    // To get the complete set of externs, the logic in
    // CompilerRunner.getDefaultExterns() should be used here.
    SourceFile extern = SourceFile.fromCode("externs.js",
        "function alert(x) {}");

    // The dummy input name "input.js" is used here so that any warnings or
    // errors will cite line numbers in terms of input.js.
    SourceFile input = SourceFile.fromCode("input.js", code);

    // compile() returns a Result, but it is not needed here.
    compiler.compile(extern, input, options);
   
    // The compiler is responsible for generating the compiled code; it is not
    // accessible via the Result.
    return compiler.toSource();
  }

  public static void main(String[] args) {
    String compiledCode = compile(
        "function hello(name) {" +
          "alert('Hello, ' + name);" +
        "}" +
        "hello('New user');");
    System.out.println(compiledCode);
  }

public String execute(String code, String name) {
	return compile(code);
}


public String execute(String code) {
	return compile(code);
}

}
