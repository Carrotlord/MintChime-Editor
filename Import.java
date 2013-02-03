package gui;

/* Because Java packages are not hierarchical, you cannot
 * substitute the following imports with "import builtin.*;"
 */
import builtin.compress.*;
import builtin.file.*;
import builtin.game.*;
import builtin.graphics.*;
import builtin.math.*;
import builtin.mint.*;
import builtin.operator.*;
import builtin.programming.*;
import builtin.random.*;
import builtin.system.*;
import builtin.thread.*;
import builtin.time.*;
import builtin.type.*;
import builtin.web.*;
import builtin.matcher.*;

/**
 * @author Oliver Chu
 */
public class Import {
    public static MintObject importGame() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        Subprogram _Window = new Subprogram("GraphicsGame", zeroArgs,
                             new Window());
        Subprogram txtGame = new Subprogram("TextBasedGame", zeroArgs,
                             new TextBasedGame());
        mo.put("GraphicsGame", Heap.allocateSub(_Window));
        mo.put("TextBasedGame", Heap.allocateSub(txtGame));
        return mo;
    }
    
    public static MintObject importRandom() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("f"));
        Subprogram coin = new Subprogram("coinToss", oneArg, new Coin());
        Subprogram randomInt = new Subprogram("randomInt", twoArgs,
                                              new RandomInt());
        Subprogram randomReal = new Subprogram("randomReal", zeroArgs,
                                               new RandomReal());
        Subprogram tRandomInt = new Subprogram("trueRandomInt", zeroArgs,
                                               new TrueRandomInt());
        Subprogram tRandomReal = new Subprogram("trueRandomReal", zeroArgs,
                                               new TrueRandomReal());
        mo.put("randomInt", Heap.allocateSub(randomInt));
        mo.put("randomReal", Heap.allocateSub(randomReal));
        mo.put("trueRandomInt", Heap.allocateSub(tRandomInt));
        mo.put("trueRandomReal", Heap.allocateSub(tRandomReal));
        mo.put("coinToss", Heap.allocateSub(coin));
        return mo;
    }
    
    public static MintObject importThread() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("sub"));
        Subprogram startThread = new Subprogram("startThread", oneArg,
                                 new StartThread());
        mo.put("startThread", Heap.allocateSub(startThread));
        return mo;
    }
    
    public static MintObject importGraphics() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        //SmartList<Pointer> oneArg = new SmartList<Pointer>();
        //oneArg.add(Heap.allocateName("a"));
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        SmartList<Pointer> sixArgs = new SmartList<Pointer>();
        sixArgs.add(Heap.allocateName("a"));
        sixArgs.add(Heap.allocateName("b"));
        sixArgs.add(Heap.allocateName("c"));
        sixArgs.add(Heap.allocateName("d"));
        sixArgs.add(Heap.allocateName("e"));
        sixArgs.add(Heap.allocateName("f"));
        SmartList<Pointer> fourArgs = new SmartList<Pointer>();
        fourArgs.add(Heap.allocateName("a"));
        fourArgs.add(Heap.allocateName("b"));
        fourArgs.add(Heap.allocateName("c"));
        fourArgs.add(Heap.allocateName("d"));
        SmartList<Pointer> anyArgs = new SmartList<Pointer>();
        anyArgs.add(Heap.allocateName("a"));
        anyArgs.add(new Pointer(Constants.KEYWORD_TYPE, Constants.DOUBLE_DOT));
        Subprogram scrnshot = new Subprogram("screenshot", zeroArgs,
                              new Screenshot());
        Subprogram _Window = new Subprogram("Window", zeroArgs, new Window());
        Subprogram showMessageBox = new Subprogram("showMessageBox", twoArgs,
                                                   new ShowMessageBox());
        Subprogram showWarningBox = new Subprogram("showWarningBox", twoArgs,
                                                   new ShowWarningBox());
        Subprogram showErrorBox = new Subprogram("showErrorBox", twoArgs,
                                                   new ShowErrorBox());
        Subprogram showPlainBox = new Subprogram("showPlainBox", twoArgs,
                                                   new ShowPlainBox());
        Subprogram showQuestionBox = new Subprogram("showQuestionBox", twoArgs,
                                                    new ShowQuestionBox());
        Subprogram _Button = new Subprogram("Button", zeroArgs, new Button());
        Subprogram _Arc = new Subprogram("Arc", sixArgs, new Arc());
        Subprogram _Line = new Subprogram("Line", fourArgs, new Line());
        Subprogram _Oval = new Subprogram("Oval", fourArgs, new Oval());
        Subprogram _Polygon = new Subprogram("Polygon", anyArgs, new Polygon());
        Subprogram _Rectangle = new Subprogram("Rectangle", fourArgs,
                                               new Rectangle_());
        mo.put("Window", Heap.allocateSub(_Window));
        mo.put("Button", Heap.allocateSub(_Button));
        mo.put("showMessageBox", Heap.allocateSub(showMessageBox));
        mo.put("showWarningBox", Heap.allocateSub(showWarningBox));
        mo.put("showErrorBox", Heap.allocateSub(showErrorBox));
        mo.put("showPlainBox", Heap.allocateSub(showPlainBox));
        mo.put("showQuestionBox", Heap.allocateSub(showQuestionBox));
        mo.put("Arc", Heap.allocateSub(_Arc));
        mo.put("Line", Heap.allocateSub(_Line));
        mo.put("Oval", Heap.allocateSub(_Oval));
        mo.put("Polygon", Heap.allocateSub(_Polygon));
        mo.put("Rectangle", Heap.allocateSub(_Rectangle));
        mo.put("screenshot", Heap.allocateSub(scrnshot));
        return mo;
    }
    
    public static MintObject importMint() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("code"));
        Subprogram eval = new Subprogram("eval", oneArg, new Eval());
        Subprogram exec = new Subprogram("exec", oneArg, new Exec());
        mo.put("eval", Heap.allocateSub(eval));
        mo.put("exec", Heap.allocateSub(exec));
        return mo;
    }
    
    public static MintObject importWeb() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        Subprogram getWebsiteContents = new Subprogram("getWebsiteContents",
                                        oneArg, new GetWebsiteContents());
        Subprogram downloadFile = new Subprogram("downloadFile", twoArgs,
                                                 new DownloadFile());
        SmartList<Pointer> ircArgs = new SmartList<Pointer>();
        ircArgs.add(Heap.allocateName("server"));
        ircArgs.add(Heap.allocateName("botName"));
        ircArgs.add(Heap.allocateName("channels"));
        ircArgs.add(Heap.allocateName("showDebug"));
        ircArgs.add(Heap.allocateName("commandsSub"));
        Subprogram connectToIRC = new Subprogram("connectToIRC", ircArgs,
                                   new ConnectToIRC());
        Subprogram openWebBrowser = new Subprogram("openWebBrowser", oneArg,
                                    new OpenWebBrowser());
        Subprogram getIPAddress = new Subprogram("getIPAddress", zeroArgs,
                                                 new GetIPAddress());
        mo.put("downloadFile", Heap.allocateSub(downloadFile));
        mo.put("getWebsiteContents", Heap.allocateSub(getWebsiteContents));
        mo.put("connectToIRC", Heap.allocateSub(connectToIRC));
        mo.put("openWebBrowser", Heap.allocateSub(openWebBrowser));
        mo.put("getIPAddress", Heap.allocateSub(getIPAddress));
        return mo;
    }
    
    public static MintObject importFile() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        SmartList<Pointer> threeArgs = new SmartList<Pointer>();
        threeArgs.add(Heap.allocateName("a"));
        threeArgs.add(Heap.allocateName("b"));
        threeArgs.add(Heap.allocateName("c"));
        Subprogram Bytes = new Subprogram("Bytes", oneArg, new Bytes());
        Subprogram appendStrToFile =
              new Subprogram("appendStrToFile", twoArgs, new AppendStrToFile());
        Subprogram bytesToHex = new Subprogram("bytesToHex", oneArg,
                                               new BytesToHex());
        Subprogram canReadAndWrite = new Subprogram("canReadAndWrite", oneArg,
                                                    new CanReadAndWrite());
        Subprogram copyFile = new Subprogram("copyFile", twoArgs,
                                             new CopyFile());
        Subprogram delete = new Subprogram("delete", oneArg, new Delete());
        Subprogram exists = new Subprogram("exists", oneArg, new Exists());
        Subprogram fileToStr = new Subprogram("fileToStr", oneArg,
                                              new FileToStr());
        Subprogram fullPathToRelPath = new Subprogram("fullPathToRelPath",
                                       oneArg, new FullPathToRelPath());
        Subprogram getCurrentFolder = new Subprogram("getCurrentFolder",
                                      zeroArgs, new GetCurrentFolder());
        Subprogram getFileSize = new Subprogram("getFileSize", oneArg,
                                                new GetFileSize());
        Subprogram getFilesInFolder = new Subprogram("getFilesInFolder",
                                      oneArg, new GetFilesInFolder());
        Subprogram hexToBytes = new Subprogram("hexToBytes", oneArg,
                                               new HexToBytes());
        Subprogram makeFolder = new Subprogram("makeFolder", oneArg,
                                               new MakeFolder());
        Subprogram readBytes = new Subprogram("readBytes", threeArgs,
                                              new ReadBytes());
        Subprogram readHex = new Subprogram("readHex", threeArgs,
                                            new ReadHex());
        Subprogram removeFileExtension = new Subprogram("removeFileExtension",
                                         oneArg, new RemoveFileExtension());
        Subprogram strToFile = new Subprogram("strToFile", twoArgs,
                                              new StrToFile());
        Subprogram writeBytes = new Subprogram("writeBytes", threeArgs,
                                               new WriteBytes());
        Subprogram writeHex = new Subprogram("writeHex", threeArgs,
                                             new WriteHex());
        Subprogram isFolder = new Subprogram("isFolder", oneArg,
                                             new IsFolder());
        Subprogram fileLength = new Subprogram("fileLength", oneArg,
                                               new FileLength());
        mo.put("Bytes", Heap.allocateSub(Bytes));
        mo.put("appendStrToFile", Heap.allocateSub(appendStrToFile));
        mo.put("bytesToHex", Heap.allocateSub(bytesToHex));
        mo.put("canReadAndWrite", Heap.allocateSub(canReadAndWrite));
        mo.put("copyFile", Heap.allocateSub(copyFile));
        mo.put("delete", Heap.allocateSub(delete));
        mo.put("exists", Heap.allocateSub(exists));
        mo.put("fileToStr", Heap.allocateSub(fileToStr));
        mo.put("fullPathToRelPath", Heap.allocateSub(fullPathToRelPath));
        mo.put("getCurrentFolder", Heap.allocateSub(getCurrentFolder));
        mo.put("getFileSize", Heap.allocateSub(getFileSize));
        mo.put("getFilesInFolder", Heap.allocateSub(getFilesInFolder));
        mo.put("hexToBytes", Heap.allocateSub(hexToBytes));
        mo.put("makeFolder", Heap.allocateSub(makeFolder));
        mo.put("readBytes", Heap.allocateSub(readBytes));
        mo.put("readHex", Heap.allocateSub(readHex));
        mo.put("removeFileExtension", Heap.allocateSub(removeFileExtension));
        mo.put("strToFile", Heap.allocateSub(strToFile));
        mo.put("writeBytes", Heap.allocateSub(writeBytes));
        mo.put("writeHex", Heap.allocateSub(writeHex));
        mo.put("isFolder", Heap.allocateSub(isFolder));
        mo.put("fileLength", Heap.allocateSub(fileLength));
        return mo;
    }
    
    public static MintObject importMatcher() {
        MintObject mo = new MintObject();
        try {
            SmartList<Pointer> twoArgs = new SmartList<Pointer>();
            twoArgs.add(Heap.allocateName("strHaystack"));
            twoArgs.add(Heap.allocateName("listNeedles"));
            Subprogram matches = new Subprogram("matches", twoArgs,
                new Matches());
            mo.put("matches", Heap.allocateSub(matches));
            mo.put("zeroOrMoreOfNext", Matches.ZERO_OR_MORE_OF_NEXT);
            mo.put("anyCharacter", Matches.ANY_CHAR_MATCH);
            return mo;
        } catch (Throwable t) {
            System.err.println(t.getMessage() + " ... " +
                t.getStackTrace().toString());
            return mo;
        }
    }
    
    public static MintObject importSystem(Interpreter i) {
        MintObject mo = new MintObject();
        SmartList<Pointer> noArgs = new SmartList<Pointer>();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("toggler"));
        twoArgs.add(Heap.allocateName("firstReal"));
        twoArgs.add(Heap.allocateName("secondReal"));
        Subprogram systemScan = new Subprogram("systemScan", noArgs,
            new SystemScan());
        Subprogram flourish = new Subprogram("flourish",
            oneArg, new Flourish());
        Subprogram getArgs = new Subprogram("getArgs", noArgs, new GetArgs());
        Subprogram sizeInBytes = new Subprogram("sizeInBytes", oneArg,
                                                new SizeInBytes());
        Subprogram halt = new Subprogram("halt", noArgs, new Halt());
        Subprogram swap = new Subprogram("swap", twoArgs, new Swap());
        Subprogram clear = new Subprogram("clear", noArgs, new Clear());
        Subprogram inveigle = new Subprogram("inveigle", oneArg,
                                             new Inveigle());
        Subprogram extract = new Subprogram("extract", oneArg, new Extract(i));
        Subprogram inject = new Subprogram("inject", oneArg, new Inject());
        Subprogram changeString = new Subprogram("changeString", twoArgs,
                                                 new ChangeString());
        Subprogram address = new Subprogram("address", oneArg, new Address());
        Subprogram star = new Subprogram("star", twoArgs, new Star());
        Subprogram viewStrings =
            new Subprogram("viewStrings", noArgs, new ViewStrings());
        Subprogram viewHeap =
            new Subprogram("viewHeap", noArgs, new ViewHeap(i));
        Subprogram caliber =
            new Subprogram("caliber", oneArg, new Caliber());
        mo.put("exit", Heap.allocateSub(
            new Subprogram("exit", noArgs, new Exit())));
        mo.put("sizeInBytes", Heap.allocateSub(sizeInBytes));
        mo.put("getArgs", Heap.allocateSub(getArgs));
        mo.put("halt", Heap.allocateSub(halt));
        mo.put("swap", Heap.allocateSub(swap));
        mo.put("clear", Heap.allocateSub(clear));
        mo.put("inveigle", Heap.allocateSub(inveigle));
        mo.put("extract", Heap.allocateSub(extract));
        mo.put("inject", Heap.allocateSub(inject));
        mo.put("changeString", Heap.allocateSub(changeString));
        mo.put("address", Heap.allocateSub(address));
        mo.put("viewStrings", Heap.allocateSub(viewStrings));
        mo.put("viewHeap", Heap.allocateSub(viewHeap));
        mo.put("caliber", Heap.allocateSub(caliber));
        mo.put("star", Heap.allocateSub(star));
        mo.put("flourish", Heap.allocateSub(flourish));
        mo.put("systemScan", Heap.allocateSub(systemScan));
        return mo;
    }
    
    public static MintObject importCompress() {
        MintObject mo = new MintObject();
        SmartList<Pointer> args = new SmartList<Pointer>();
        args.add(Heap.allocateName("listOfFiles"));
        args.add(Heap.allocateName("gluedFile"));
        Subprogram glue = new Subprogram("glue", args,
                                         new Glue());
        mo.put("glue", Heap.allocateSub(glue));
        return mo;
    }
    
    public static MintObject importProgramming() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("programPath"));
        Subprogram py = new Subprogram("executePython",
            oneArg, new ExecutePython());
        Subprogram jv = new Subprogram("executeJava",
            oneArg, new ExecuteJava());
        mo.put("executePython", Heap.allocateSub(py));
        mo.put("executeJava", Heap.allocateSub(jv));
        return mo;
    }
    
    public static MintObject importTime() {
        MintObject mo = new MintObject();
        SmartList<Pointer> zeroArgs = new SmartList<Pointer>();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        Subprogram wait = new Subprogram("wait", oneArg, new Wait());
        Subprogram year = new Subprogram("year", zeroArgs, new Year());
        Subprogram amPm = new Subprogram("amPm", zeroArgs, new AmPm());
        Subprogram date = new Subprogram("date", zeroArgs, new _Date());
        Subprogram hour = new Subprogram("hour", zeroArgs, new Hour());
        Subprogram hour24HourFormat = new Subprogram("hour24HourFormat",
                                      zeroArgs, new Hour24HourFormat());
        Subprogram millisecond = new Subprogram("millisecond", zeroArgs,
                                 new Millisecond());
        Subprogram minute = new Subprogram("minute", zeroArgs, new Minute());
        Subprogram month = new Subprogram("month", zeroArgs, new Month());
        Subprogram second = new Subprogram("second", zeroArgs, new Second());
        Subprogram timeZone = new Subprogram("timeZone", zeroArgs,
                                             new TimeZone());
        Subprogram weekday = new Subprogram("weekday", zeroArgs, new Weekday());
        Subprogram weekdayNumber = new Subprogram("weekdayNumber", zeroArgs,
                                   new WeekdayNumber());
        mo.put("wait", Heap.allocateSub(wait));
        mo.put("year", Heap.allocateSub(year));
        mo.put("amPm", Heap.allocateSub(amPm));
        mo.put("date", Heap.allocateSub(date));
        mo.put("hour", Heap.allocateSub(hour));
        mo.put("hour24HourFormat", Heap.allocateSub(hour24HourFormat));
        mo.put("millisecond", Heap.allocateSub(millisecond));
        mo.put("minute", Heap.allocateSub(minute));
        mo.put("month", Heap.allocateSub(month));
        mo.put("second", Heap.allocateSub(second));
        mo.put("timeZone", Heap.allocateSub(timeZone));
        mo.put("weekday", Heap.allocateSub(weekday));
        mo.put("weekdayNumber", Heap.allocateSub(weekdayNumber));
        return mo;
    }
    
    public static MintObject importType() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        Subprogram string = new Subprogram("string", oneArg, new _String());
        Subprogram _int = new Subprogram("int", oneArg, new Int());
        Subprogram real = new Subprogram("real", oneArg, new Real());
        Subprogram truth = new Subprogram("truth", oneArg, new Truth());
        Subprogram list = new Subprogram("list", oneArg, new List());
        Subprogram type = new Subprogram("type", oneArg, new Type());
        Subprogram _char = new Subprogram("char", oneArg, new Char());
        mo.put("string", Heap.allocateSub(string));
        mo.put("int", Heap.allocateSub(_int));
        mo.put("real", Heap.allocateSub(real));
        mo.put("truth", Heap.allocateSub(truth));
        mo.put("list", Heap.allocateSub(list));
        mo.put("type", Heap.allocateSub(type));
        mo.put("char", Heap.allocateSub(_char));
        return mo;
    }
    
    public static MintObject importMath() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        Subprogram abs = new Subprogram("abs", oneArg, new Abs());
        Subprogram sin = new Subprogram("sin", oneArg, new Sin());
        Subprogram cos = new Subprogram("cos", oneArg, new Cos());
        Subprogram tan = new Subprogram("tan", oneArg, new Tan());
        Subprogram asin = new Subprogram("asin", oneArg, new Asin());
        Subprogram acos = new Subprogram("acos", oneArg, new Acos());
        Subprogram atan = new Subprogram("atan", oneArg, new Atan());
        Subprogram sinh = new Subprogram("sinh", oneArg, new Sinh());
        Subprogram cosh = new Subprogram("cosh", oneArg, new Cosh());
        Subprogram tanh = new Subprogram("tanh", oneArg, new Tanh());
        Subprogram asinh = new Subprogram("asinh", oneArg, new Asinh());
        Subprogram acosh = new Subprogram("acosh", oneArg, new Acosh());
        Subprogram atanh = new Subprogram("atanh", oneArg, new Atanh());
        Subprogram ln = new Subprogram("ln", oneArg, new Ln());
        Subprogram log10 = new Subprogram("log10", oneArg, new Log10());
        Subprogram sqrt = new Subprogram("sqrt", oneArg, new Sqrt());
        Subprogram cbrt = new Subprogram("cbrt", oneArg, new Cbrt());
        Subprogram factorial = new Subprogram("factorial", oneArg,
                               new Factorial());
        Subprogram log = new Subprogram("log", twoArgs, new Log());
        Subprogram min = new Subprogram("min", twoArgs, new Min());
        Subprogram max = new Subprogram("max", twoArgs, new Max());
        Subprogram BigInt = new Subprogram("BigInt", oneArg, new BigInt());
        Subprogram PreciseReal = new Subprogram("PreciseReal", oneArg,
                                                new PreciseReal());
        Subprogram findRoot = new Subprogram("findRoot", twoArgs,
            new FindRoot());
        Subprogram diff = new Subprogram("differentiate",
            oneArg, new Differentiate());
        Subprogram binom = new Subprogram("binomialChoose", twoArgs,
            new BinomialChoose());
        Subprogram fib = new Subprogram("fib", oneArg, new Fib());
        mo.put("e", Heap.allocateReal(Math.E));
        mo.put("pi", Heap.allocateReal(Math.PI));
        mo.put("phi", Heap.allocateReal((1 + Math.sqrt(5)) / 2));
        mo.put("UNDEFINED", Heap.allocateReal(Double.NaN));
        mo.put("INFINITY", Heap.allocateReal(Double.POSITIVE_INFINITY));
        mo.put("NEGATIVE_INFINITY", Heap.allocateReal(
            Double.NEGATIVE_INFINITY));
        mo.put("findRoot", Heap.allocateSub(findRoot));
        mo.put("differentiate", Heap.allocateSub(diff));
        mo.put("binomialChoose", Heap.allocateSub(binom));
        mo.put("fib", Heap.allocateSub(fib));
        mo.put("abs", Heap.allocateSub(abs));
        mo.put("sin", Heap.allocateSub(sin));
        mo.put("cos", Heap.allocateSub(cos));
        mo.put("tan", Heap.allocateSub(tan));
        mo.put("asin", Heap.allocateSub(asin));
        mo.put("acos", Heap.allocateSub(acos));
        mo.put("atan", Heap.allocateSub(atan));
        mo.put("sinh", Heap.allocateSub(sinh));
        mo.put("cosh", Heap.allocateSub(cosh));
        mo.put("tanh", Heap.allocateSub(tanh));
        mo.put("asinh", Heap.allocateSub(asinh));
        mo.put("acosh", Heap.allocateSub(acosh));
        mo.put("atanh", Heap.allocateSub(atanh));
        mo.put("ln", Heap.allocateSub(ln));
        mo.put("log10", Heap.allocateSub(log10));
        mo.put("sqrt", Heap.allocateSub(sqrt));
        mo.put("cbrt", Heap.allocateSub(cbrt));
        mo.put("factorial", Heap.allocateSub(factorial));
        mo.put("log", Heap.allocateSub(log));
        mo.put("min", Heap.allocateSub(min));
        mo.put("max", Heap.allocateSub(max));
        mo.put("BigInt", Heap.allocateSub(BigInt));
        mo.put("PreciseReal", Heap.allocateSub(PreciseReal));
        return mo;
    }
    
    public static MintObject importOperator() {
        MintObject mo = new MintObject();
        SmartList<Pointer> oneArg = new SmartList<Pointer>();
        oneArg.add(Heap.allocateName("a"));
        SmartList<Pointer> twoArgs = new SmartList<Pointer>();
        twoArgs.add(Heap.allocateName("a"));
        twoArgs.add(Heap.allocateName("b"));
        Subprogram bitNot = new Subprogram("bitNot", oneArg, new BitNot());
        Subprogram neg = new Subprogram("neg", oneArg, new Neg());
        Subprogram bitAnd = new Subprogram("bitAnd", twoArgs, new BitAnd());
        Subprogram bitOr = new Subprogram("bitOr", twoArgs, new BitOr());
        Subprogram bitXor = new Subprogram("bitXor", twoArgs, new BitXor());
        Subprogram pow = new Subprogram("pow", twoArgs, new Pow());
        Subprogram shl = new Subprogram("shl", twoArgs, new Shl());
        Subprogram shr = new Subprogram("shr", twoArgs, new Shr());
        Subprogram sar = new Subprogram("sar", twoArgs, new Sar());
        Subprogram plus = new Subprogram("plus", twoArgs, new Plus());
        Subprogram minus = new Subprogram("minus", twoArgs, new Minus());
        Subprogram mul = new Subprogram("mul", twoArgs, new Mul());
        Subprogram div = new Subprogram("div", twoArgs, new Div());
        Subprogram intDiv = new Subprogram("intDiv", twoArgs, new IntDiv());
        Subprogram mod = new Subprogram("mod", twoArgs, new Mod());
        Subprogram inc = new Subprogram("inc", oneArg, new Inc());
        Subprogram dec = new Subprogram("dec", oneArg, new Dec());
        Subprogram logicAnd = new Subprogram("logicAnd", twoArgs,
                              new LogicAnd());
        Subprogram logicOr = new Subprogram("logicOr", twoArgs, new LogicOr());
        Subprogram logicXor = new Subprogram("logicXor", twoArgs,
                              new LogicXor());
        Subprogram logicNot = new Subprogram("logicNot", oneArg,
                              new LogicNot());
        Subprogram eq = new Subprogram("eq", twoArgs, new Eq());
        Subprogram notEq = new Subprogram("notEq", twoArgs, new NotEq());
        Subprogram greater = new Subprogram("greater", twoArgs, new Greater());
        Subprogram lesser = new Subprogram("lesser", twoArgs, new Lesser());
        Subprogram greaterEq = new Subprogram("greaterEq", twoArgs,
                               new GreaterEq());
        Subprogram lesserEq = new Subprogram("lesserEq", twoArgs,
                                             new LesserEq());
        mo.put("bitNot", Heap.allocateSub(bitNot));
        mo.put("neg", Heap.allocateSub(neg));
        mo.put("bitAnd", Heap.allocateSub(bitAnd));
        mo.put("bitOr", Heap.allocateSub(bitOr));
        mo.put("bitXor", Heap.allocateSub(bitXor));
        mo.put("pow", Heap.allocateSub(pow));
        mo.put("shl", Heap.allocateSub(shl));
        mo.put("shr", Heap.allocateSub(shr));
        mo.put("sar", Heap.allocateSub(sar));
        mo.put("mul", Heap.allocateSub(mul));
        mo.put("plus", Heap.allocateSub(plus));
        mo.put("minus", Heap.allocateSub(minus));
        mo.put("div", Heap.allocateSub(div));
        mo.put("intDiv", Heap.allocateSub(intDiv));
        mo.put("floorDiv", Heap.allocateSub(intDiv));
        mo.put("mod", Heap.allocateSub(mod));
        mo.put("dec", Heap.allocateSub(dec));
        mo.put("inc", Heap.allocateSub(inc));
        mo.put("logicAnd", Heap.allocateSub(logicAnd));
        mo.put("logicOr", Heap.allocateSub(logicOr));
        mo.put("logicXor", Heap.allocateSub(logicXor));
        mo.put("logicNot", Heap.allocateSub(logicNot));
        mo.put("eq", Heap.allocateSub(eq));
        mo.put("notEq", Heap.allocateSub(notEq));
        mo.put("greater", Heap.allocateSub(greater));
        mo.put("lesser", Heap.allocateSub(lesser));
        mo.put("greaterEq", Heap.allocateSub(greaterEq));
        mo.put("lesserEq", Heap.allocateSub(lesserEq));
        return mo;
    }
}
