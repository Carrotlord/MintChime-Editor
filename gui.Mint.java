package gui;

import builtin.system.GetArgs;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This is the Interpreter for the Mint Programming Language.
 * @author Oliver Chu / Jiangcheng Chu, UC Berkeley EECS Major.
 */
public class Mint {
    public static final BufferedReader KEYBOARD =
         new BufferedReader(new InputStreamReader(System.in));
    public static boolean seduced = false;
    private static Environment overloadEnvironment = new Environment();
    // TODO implement more of import graphics
    // TODO Anywhere String can be replaced with Stringbuilder for numerous
    // append statements instead of += is a really good place to optimize.
    
    // [Apparently Fixed] for each loops sometimes (?) do not execute the
    // last statement
    // right before the end statement. I'm not talking about the increment.
    
    // TODO Using curly braces with if... else if... else sometimes results
    // in NullPointerExceptions. [Fixed]
    
    // TODO lists and strings of length 1 are not indexable. [Fixed]
    
    // TODO for each <item> of <iterable> does not work right when <iterable>
    // is an expression such as dict.keys.something rather than a simple var.
    
    //<editor-fold defaultstate="collapsed" desc="Sample programs">
    // IRC program :
    //"import web; sub commands(chan, sender, login, hostname, msg); import time;" +
    //"if msg == \"time\"; d = date(); sendMessage(chan, d); else if msg == \"hi\"; " +
    //"sendMessage(chan, \"Hello\"); end; end; connectToIRC(\"aperture.esper.net\", " +
    //"\"MintBot3\", [\"#radishbot\"], true, commands)";
    
    // Dot and index assignment:
    //"sub Obj(); return this; end; x = [1,2,3,Obj()]; x[3].z = 15; print x; " +
    //"z = [[1, 2], [3, 4]]; z[0][1] = 500; print z;";
    
    // Inheritance:
    //"sub Human(); name = \"Missy\"; sub talk(); print \"My name " +
    //"is \" + name; end; return this; end; m = Human(); m.talk(); sub " +
    //"CatGirl(); inherit Human(); return this; end; x = CatGirl(); x.talk()";
    
    // Imports:
    //"import operator; print inc(14); print mod(14, 5);";
    
    // Import file:
    //"import file; b = Bytes([0, -1, 2]); print b; b.append(200); print b; b.set(0, 100); print b;";
    
    // Comments and import into:
    //"import type into t; print t/*x = input; print t.type(x);*/;print 2000";
    
    // For each:
    //"for each x of \"Hello\"; print x; end; print 2000";
    
    // In operator:
    //"sub Obj(); a = 5; b = 10; return this; end; x = Obj(); print \"a\" in x; print \"c\" in x;";
    
    // Basic graphics:
    //"import graphics; x = Window(); x.setTitle(\"Hello world\");" +
    //"x.display(); x.setSize(300, 400); i = showQuestionBox(\"Hi\", \"Sex?\"); print i;";
    
    // More graphics:
    //"import graphics; x = Window(); x.display(); x.setSize(300, 300);" +
    //"b = Button(); x.add(b); b.setSize(100, 50); b.setText(\"Hi\");" +
    //" b.setPosition(50, 50); sub hello(); import graphics; " +
    //"showMessageBox(\"Hi\", \"Hello\"); end; b.setSubprogram(hello);";
    
    // Compound assignment:
    //"a = 5; a += 6; print a; a *= 7; print a; a -= 50; print a; a /= 2;" +
    //" print a; a %= 5; print a; a ^= 10; print a;";
    
    // Threads (working well!):
    //"import time; import thread; sub T1(); import time; print 1; wait(1500); print 3; end;" + 
    //"sub T2(); import time; wait(750); print 2; end; startThread(T1); startThread(T2);";
    
    // Graphics (drawing of a rectangle and circle on a black bg):
    //"import graphics; win = Window(); win.display(); win.setSize(300, 300);" +
    //" kuro = [0, 0, 0]; win.setBgColor(kuro); r = Rectangle(50, 50, 100, 100);" + 
    //" o = Oval(75, 75, 100, 100); white = [255, 255, 255]; red = [255, 0, 0];" +
    //" win.setDrawingColor(white); win.drawShape(r); win.setDrawingColor(red); win.drawShape(o);";
    
    // Infinite fibonacci list:
    //"import type; sub f(i); sub g(i); when i <= 1; return i; " +
    //"return g(i - 1) + g(i - 2); end; return g(i); end; x = list(f); print x";
    //</editor-fold>
    
    // Switch statement:
    //"x = 0; switch x; case 0; print \"Zero\"; case 1, 2; print \"One, Two\";
    //default; print \"DERPFAULT\"; end;"
    /**
     * @param args the command line arguments
     */
    public static void mux(String... args) throws MintException {
        // Instantiating the heap must always be the first Java statement
        // in this program. By convention, only 1 heap should be alive at
        // a time.
        Heap h = new Heap();
        SmartList<String> argsList = new SmartList<String>(Arrays.asList(args));
        if (argsList.size() > 1) {
            GetArgs.setArgs(argsList.subList(1));
        }
        Interpreter interpreter = new Interpreter();
        Interpreter overloadInterpreter = new Interpreter();
        String code;
        final String ol = "overload.mint";
        final String startup = "startup.mint";
        if (args.length == 0) {
            code = "program.mint";
        } else {
            code = args[0];
        }
        if (code.contains("--execute:")) {
            interpreter.run(StrTools2.slice(code, 10), true);
        }
        //try {
            if (FileIO.exists(ol)) {
                overloadInterpreter.runFromFile(ol);
                overloadEnvironment = overloadInterpreter.getEnv();
            } else {
                System.err.println(
                    "Warning: The file " + ol + " does not exist!");
            }
            /* if (FileIO.exists(startup)) {
                interpreter.runFromFile(startup);
            } */
            final String STARTUP_COMMANDS =
                "import graphics;import file;import web;import matcher;" +
                "sub alert(x);showMessageBox(\"Message\", x);end;";
            interpreter.run(STARTUP_COMMANDS + code, true);
        /*} catch (MintException ex) {
            System.err.println(ex.getMessage());
        } catch (Throwable t) {
            System.err.println("Error: Raw Java Exception Thrown...\n\n" +
                "Info: " + t.toString()); */
    }
    
    public static void printStackTrace(StackTraceElement[] stElements) {
        for (StackTraceElement ste : stElements) {
            System.err.println(ste.toString().replace(".java:", " @ "));
        }
    }
    
    public static Environment getOverloadEnv() {
        return overloadEnvironment;
    }
}
