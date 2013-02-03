package tools;

//<editor-fold defaultstate="collapsed" desc="Import statements">
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import gui.Dialog;
//</editor-fold>

/**
 * This class is used to simplify file input/output operations.
 * @author Oliver Chu, UC Berkeley EECS Major.
 */
public class FileIO {
    public static String addFileExtensionIfNeeded(String filePath,
                                                  String extension) {
        //The user has already specified a file extension. Do not add a new
        //one.
        if (filePath.contains("."))
            return filePath;
        if (!extension.startsWith("."))
            extension = "." + extension;
        return filePath + extension;
    }
    
    public static String removeFileExtension(String fileNameWithExtension) {
        int maxLength = fileNameWithExtension.length();
        for (int i = maxLength - 1; i >= 0; i--) {
            if (fileNameWithExtension.charAt(i) == '.')
                return StrTools.slice(fileNameWithExtension,0,i);
        }
        //We could not find the file extension, so return the fileName as is.
        return fileNameWithExtension;
    }

    static int getFileSize(String fileName) {
        File fileObj = new File(fileName);
        if (!fileObj.canRead())
            return 0;
        long fileSize = fileObj.length();
        if (fileSize >= 2147483648L)
            return Integer.MAX_VALUE;
        else
            return (int)fileSize;
    }
    
    /**
     * Checks if a file (or folder) exists or not.
     * @return true if file exists, false otherwise.
     */
    public static boolean exists(String fileName)
    {
        File fileObj = new File(fileName);
        return fileObj.exists();
    }
    
    /**
     * Checks if a file can be read and written.
     * @return true if file can be read and written, false otherwise.
     */
    public static boolean canReadAndWrite(String fileName)
    {
        File fileObj = new File(fileName);
        return (fileObj.canRead() && fileObj.canWrite());
    }

    /**
     * Takes the contents of a file and returns a string with those contents.
     * <br /><br />Do NOT make this method private. It needs to be used by some
     * classes that work with the binary tables.
     * @param fileName
     * @return Contents of file (as string)
     */
    public static String fileToStr(String fileName)
    {
        String outString="";
        try
        {
            InputStream fileObj = new FileInputStream(fileName);
            int maxReadSize = fileObj.available();
            for (int i=0;i < maxReadSize;i++)
                outString += (char)fileObj.read();
            fileObj.close();
        }
        catch(IOException exceptionInfo)
        {
            Dialog.errorBox("File reading error\n"+exceptionInfo,true);
        }
        return outString;
    }

    /**
     * Stores the string fileContents into file with path fileName.
     * @param fileContents
     * @param fileName
     */
    private static void strToFile(String fileContents,String fileName)
    {
        int maxLen = fileContents.length();
        try
        {
            OutputStream fileObj = new FileOutputStream(fileName);
            for (int index=0;index < maxLen;index++)
                fileObj.write(fileContents.charAt(index));
            fileObj.close();
        }
        catch(IOException exceptionInfo)
        {
            Dialog.errorBox("File writing error\n"+exceptionInfo,true);
        }
    }

    /**
     * Converts file contents into a string, but the file must be a text-file
     * with line breaks. System newlines (\r\n) get converted to \n.
     * @param fileName
     * @return
     */
    public static String textFileToStr(String fileName)
    {
        return StrTools.unixifyNewlines(fileToStr(fileName));
    }

    public static void strToTextFile(String fileContents,String fileName)
    {
        strToFile(StrTools.convertNewlines(fileContents),fileName);
    }

    /**
     * Appends the string 'append' to the specified file.
     * @param append
     * @param fileName
     */
    private static void appendStrToFile(String append,String fileName)
    {
        try
        {
            BufferedWriter fileObj =
                             new BufferedWriter(new FileWriter(fileName, true));
            fileObj.write(append);
            fileObj.close();
        }
        catch (IOException exceptionInfo)
        {
            Dialog.errorBox("File appending error: "+exceptionInfo, true);
        }
    }

    /**
     * Appends the string 'append' to the specified file. The 'append' has
     * all unix-style newlines (\n) converted to native newlines first.
     * @param append
     * @param fileName
     */
    public static void appendTextStrToFile(String append, String fileName)
    {
        appendStrToFile(StrTools.convertNewlines(append),fileName);
    }

    /**
     * Converts a full file path to a relative file path.
     * If you give this a path such as<br />
     * C:\Users\Javagrams\MYPROGRAM.jar\yes\hello.txt<br />
     * It will return "yes/hello.txt"<br />
     * And yes, if you're on Windows, the backslashes will be converted to
     * forward slashes.<br />
     * Does NOT support "up one level" folders. For example, ../../image.bmp
     * will never be returned.
     * @param filePath
     * @return Relative path
     */
    public static String fullPathToRelPath(String filePath)
    {
        String currentWorkingDir = System.getProperty("user.dir");
        currentWorkingDir = currentWorkingDir.replace('\\', '/');
        filePath = filePath.replace('\\','/');
        if (!filePath.startsWith(currentWorkingDir))
        {
            Dialog.errorBox("Can't get a relative path using:\n"+
                            "Filepath = "+filePath+
                            "\nProgrampath = "+currentWorkingDir,true);
        }
        //Remove the current working dir prefix from the file path.
        String relativePath = StrTools.slice(filePath,currentWorkingDir.length());
        if (relativePath.startsWith("/"))
            relativePath = StrTools.slice(relativePath,1);
        return relativePath;
    }

    /**
     * Converts a full path such as "C:/Users/Documents/hello.txt" to a
     * simple filename such as "hello.txt".
     * @param filePath
     * @return Simple Filename
     */
    public static String fullPathToFileName(String filePath) {
        int maxLength = filePath.length();
        String fileNameBuffer = "";
        for (int x = maxLength-1; x >= 0; x--) {
            char currentChar = filePath.charAt(x);
            if (currentChar == '/' || currentChar == '\\')
                return fileNameBuffer;
            fileNameBuffer = currentChar + fileNameBuffer;
        }
        return fileNameBuffer;
    }

    /**
     * Creates a single folder.
     * If the folder already exists, this does not recreate it.
     */
    public static void makeFolder(String folderName) {
        File folderObj = new File(folderName);
        if (folderObj.exists())
            return;
        boolean isCreated = folderObj.mkdir();
        if (!isCreated)
            Dialog.errorBox("Could not create folder: "+folderName,true);
        return;
    }

    /**
     * Deletes the file or directory determined by path.
     * @param path
     */
    public static void delete(String path)
    {
    /* Thanks a lot to
     * <http://www.java2s.com/Code/
     *  Java/File-Input-Output/DeletefileusingJavaIOAPI.htm>
     * for their useful information.
     */
        File deleteFileObj = new File(path);
        if (!deleteFileObj.exists())
            Dialog.errorBox("Cannot delete file "+path+" because that file\n"+
                            "doesn't exist!", true);
        if (!deleteFileObj.canWrite())
            Dialog.errorBox("Cannot delete file "+path+" because that file\n"+
                            "is write-protected.", true);
        if (deleteFileObj.isDirectory())
        {//Check to make sure the folder has no files in it.
            int filesInDirectory = deleteFileObj.list().length;
            if (filesInDirectory > 0)
                Dialog.errorBox("Cannot delete folder "+path+" because there\n"+
                                "are still files in the folder.",true);
        }
        boolean deleteStatus = deleteFileObj.delete();
        if (!deleteStatus)
        //Our delete failed, even after all that checking!
            Dialog.errorBox("Cannot delete file "+path+" for unknown reason.",
                            true);
    }
    
    public static boolean deleteWithoutErrors(String path)
    {
        File deleteFileObj = new File(path);
        if (!deleteFileObj.exists() || !deleteFileObj.canWrite())
            return false;
        if (deleteFileObj.isDirectory())
        {//Check to make sure the folder has no files in it.
            int filesInDirectory = deleteFileObj.list().length;
            if (filesInDirectory > 0)
                return false;
        }
        return deleteFileObj.delete();
    }

    /**
     * Copies a file from fileName1 to fileName2.
     * @param fileName1
     * @param fileName2
     */
    public static void copyFile(String fileName1,String fileName2) {
        try {
            File fileObj1 = new File(fileName1);
            File fileObj2 = new File(fileName2);
            InputStream inputObj = new FileInputStream(fileObj1);
            OutputStream outputObj = new FileOutputStream(fileObj2);
            byte[] byteBuffer = new byte[1024];
            int currentLength;
            while ((currentLength = inputObj.read(byteBuffer)) > 0) {
                outputObj.write(byteBuffer,0,currentLength);
            }
            inputObj.close();
            outputObj.close();
        } catch (FileNotFoundException exceptionInfo) {
            showCopyError(fileName1,fileName2);
        } catch (IOException exceptionInfo) {
            showCopyError(fileName1,fileName2);
        }
    }

    private static void showCopyError(String fileName1,String fileName2) {
        Dialog.errorBox("Could not copy file "+fileName1+" to "+
                        fileName2, true);
    }

    public static String readHex(String fileName,int begin,int end) {
        byte[] bytesRead = {};
        try {
            File fileObj = new File(fileName);
            RandomAccessFile RAFObj = new RandomAccessFile(fileObj,"r");
            if (end > fileObj.length())
                //Yeah, it's a long to int conversion. But an int cannot be
                //bigger than a long unless the long fits into an int, so this
                //is fine.
                end = (int)fileObj.length();
            if (begin < 0)
                begin = 0;
            if (begin > end)
                begin = end;
            bytesRead = new byte[end - begin];
            RAFObj.seek(begin);
            RAFObj.read(bytesRead);
            RAFObj.close();
        } catch (IOException exceptionInfo) {
            Dialog.errorBox("There was an error while attempting to read the\n"+
                            "file: "+fileName,true);
        }
        return NumberTools.bytesToHex(bytesRead);
    }

    public static void writeHex(String fileName,String hexString,int begin,
                                boolean isGameExec) {
        try {
            File fileObj = new File(fileName);
            RandomAccessFile RAFObj = new RandomAccessFile(fileObj,"rw");
            if (begin > fileObj.length())
                return;
            if (begin < 0)
                begin = 0;
            byte[] bytesToWrite = NumberTools.hexToBytes(hexString);
            RAFObj.seek(begin);
            //It is okay to write past the end of the file. The .write()
            //method will simply append the extra bytes.
            RAFObj.write(bytesToWrite);
            RAFObj.close();
        } catch (IOException exceptionInfo) {
            if (isGameExec) {
                Dialog.errorBox("There was an error while attempting to write"+
                                "\ndata to the game exe: "+fileName+"\n\n"+
                                "Make sure the exe is not read-only.\n"+
                                "Also, close OllyDbg if it's open.",true);
            } else {
                Dialog.errorBox("There was an error while attempting to write"+
                                "\ndata to the file: "+fileName+"\n\n"+
                                "Make sure the file is not read-only.\n"+
                                "Also, close other programs that might be\n"+
                                "editing the file.",true);
            }
        }
    }
    
    static void saveErrorToLogFile(String errorMessage) {
        File checkFileObj = new File("error.log");
        boolean fileExists = checkFileObj.exists();
        if (fileExists && (!checkFileObj.canRead() ||
            !checkFileObj.canWrite())) {
            //Since an error already occurred, we can't show more errors.
            //Just return without a trace.
            return;
        }
        //<editor-fold defaultstate="collapsed" desc="File to str without errors">
        String outString = "";
        if (fileExists) {
            try {
                InputStream errorFileObj = new FileInputStream("error.log");
                int maxReadSize = errorFileObj.available();
                for (int i=0;i < maxReadSize;i++)
                    outString += (char)errorFileObj.read();
                errorFileObj.close();
            } catch(IOException exceptionInfo) {
                return;
            }
            outString = StrTools.unixifyNewlines(outString);
            outString = errorMessage + "\n\n====================\n" +
                        outString;
        } else {
            outString = errorMessage;
        }
        //</editor-fold>
        outString = StrTools.convertNewlines(outString);
        //<editor-fold defaultstate="collapsed" desc="Str to File without errors">
        int maxLen = outString.length();
        try {
            OutputStream fileObj = new FileOutputStream("error.log");
            for (int index=0;index < maxLen;index++)
                fileObj.write(outString.charAt(index));
            fileObj.close();
        } catch(IOException exceptionInfo) {
            return;
        }
        //</editor-fold>
    }

}//END MAIN CLASS
