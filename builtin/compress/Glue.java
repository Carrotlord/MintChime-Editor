package builtin.compress;

import builtin.BuiltinSub;
import java.util.Random;
import gui.Constants;
import gui.Pointer;
import gui.SmartList;
import gui.PointerTools;
import gui.FileIO;
import gui.Heap;
import gui.MintException;

/**
 *
 * @author Oliver Chu
 */
public class Glue extends BuiltinSub {
    private static Random rng = null;
    private static byte randomByte() {
        if (rng == null) {
            long seed = new Object().hashCode() + System.nanoTime();
            rng = new Random();
            rng.setSeed(seed);
            return (byte) rng.nextInt(256);
        }
        Float f = rng.nextFloat();
        return (byte) (Math.abs(f.toString().hashCode()) % 256);
    }
    
    private static byte[] randomFiveBytes() {
        byte[] bytes = new byte[5];
        for (int i = 0; i < 5; i++) {
            bytes[i] = randomByte();
        }
        return bytes;
    }
    
    private static SmartList<Byte> addToList(SmartList<Byte> bytes,
        byte[] bs) {
        for (byte b : bs) {
            bytes.add(b);
        }
        return bytes;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        SmartList<Pointer> fileNames =
            PointerTools.dereferenceList(args.get(0));
        String gluedFile = PointerTools.dereferenceString(args.get(1));
        SmartList<Byte> gluedBytes = new SmartList<Byte>();
        byte[] separator = randomFiveBytes();
        /** Use a 5-byte separator to separate files. */
        gluedBytes = addToList(gluedBytes, separator);
        for (Pointer pntr : fileNames) {
            String file = PointerTools.dereferenceString(pntr);
            int fileSize = FileIO.getFileSize(file);
            gluedBytes.addAll(FileIO.readBytes(file, 0, fileSize));
            gluedBytes = addToList(gluedBytes, separator);
        }
        FileIO.strToFile("", gluedFile);
        FileIO.writeBytes(gluedFile, gluedBytes, 0);
        if (!gluedFile.endsWith(".glue")) {
            return Heap.allocateString(
                "Glue files should preferably end with .glue");
        }
        return Constants.MINT_NULL;
    }
}
