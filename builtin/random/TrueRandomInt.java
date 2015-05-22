package builtin.random;

import builtin.BuiltinSub;
import builtin.web.GetWebsiteContents;
import java.util.Random;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 * Returns a random integer based off of information gathered from the
 * Internet.
 * An Internet connection is not required, but helps randomness
 * considerably.
 * This is a very slow built-in subprogram. Expect around 1 second of
 * delay before it returns. That's the price for true randomness.
 * @author Oliver Chu
 */
public class TrueRandomInt extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        RandomInt.setRandomNumberGenerator();
        String reddit =
            GetWebsiteContents.getUrlSource("http://www.reddit.com", 0);
        String nsfw =
            GetWebsiteContents.getUrlSource(
                "http://gelbooru.com/index.php?page=post&s=list", 0);
        String husky =
            GetWebsiteContents.getUrlSource(
                "http://www.youtube.com/user/HuskyStarcraft/videos?view=0",
                0);
        int trulyRandomInteger = husky.length() +
                                 nsfw.length() + reddit.length();
        trulyRandomInteger = ((Long) System.nanoTime()).hashCode() -
                             trulyRandomInteger;
        trulyRandomInteger |= husky.hashCode();
        trulyRandomInteger *= Math.abs(reddit.hashCode()) % 256;
        trulyRandomInteger ^= nsfw.hashCode();
        Random numGenerator = new Random();
        numGenerator.setSeed(System.nanoTime());
        double gaussian = Math.abs(numGenerator.nextGaussian());
        double someNumber = numGenerator.nextInt(nsfw.length()) * gaussian;
        int someNum = (int) someNumber;
        trulyRandomInteger += someNum;
        trulyRandomInteger ^= (int) (numGenerator.nextLong() +
            System.currentTimeMillis() &
            ((Double) numGenerator.nextDouble()).toString().hashCode());
        return Heap.allocateInt(trulyRandomInteger);
    }
    
}
