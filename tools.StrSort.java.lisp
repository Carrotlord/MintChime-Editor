package tools;

import java.util.ArrayList;
/**
 * @author Oliver Chu, UC Berkeley EECS Major.
 */
public class StrSort {
    
    public StrSort() {
        super();
    }

    public static ArrayList<String> performQuickSort(ArrayList<String>
                                                     unsortedStrList) {
        QuickSort quickSortObj = new QuickSort(unsortedStrList);
        return quickSortObj.getSortedStrList();
    }

    public static ArrayList<String> performCountingSort(ArrayList<String>
                                                        unsortedList) {
        CountingSort countingSortObj = new CountingSort(unsortedList);
        return countingSortObj.getSortedStrList();
    }

/**
 * Please see
 * [http://en.wikipedia.org/wiki/Quicksort] for the quicksort algorithm.
 * <br />
 * Use the in-place sort for less memory consumption (already implemented
 * in this case)
 * You may also wish to look up "bucket sort" and "pigeonhole sort"<br /><br />
 *
 * SPEED TESTS (using a defines block of about 3500 lines)<br />
 * 5.37 sec, 5.26 sec, 5.19 sec
 *
 * (using 10700 lines)
 * 56.58 sec,
 */
private static class QuickSort {
    /** The list to be sorted */
    ArrayList<String> strList;

    /** Class constructor. Simply stores the list */
    QuickSort(ArrayList<String> inputStrList) {
        strList = inputStrList;
    }

    public ArrayList<String> getSortedStrList() {
        sort(0,strList.size()-1);
        return strList;
    }

    private void sort(int left,int right) {
        //The if statement avoids lists that are size 0 or 1, which
        //are already sorted.
        if (right > left) {
            int pivotIndex = choose(left,right);
            int newPivotIndex = partition(left,right,pivotIndex);
            sort(left,newPivotIndex - 1);
            sort(newPivotIndex + 1,right);
        }
    }

    /**
     * right is the rightmost index in the array, left is the leftmost index
     * in the array, inclusive. The total number of elements in the subarray
     * is right-left+1.
     * @param strList
     * @param left
     * @param right
     * @param pivotIndex
     * @return
     */
    private int partition(int left,int right,int pivotIndex) {
        String pivotString = strList.get(pivotIndex);
        int pivotStrLength = pivotString.length();
        String rightMostString = strList.get(right);
        //Swap the pivot slot with the rightmost slot so the pivot doesn't get
        //in the way.
        strList.set(right,pivotString);
        strList.set(pivotIndex,rightMostString);
        int storedIndex = left;
        for (int i=left; i<right; i++) {
            String currentString = strList.get(i);
            //To reverse the ordering of the list, change the greater than
            //sign (>) to a lesser than sign (<).
            if (currentString.length() > pivotStrLength) {
                String storedString = strList.get(storedIndex);
                //Swap strList[i] and strList[storedIndex]
                strList.set(i,storedString);
                strList.set(storedIndex,currentString);
                storedIndex++;
            }
        }
        //We exchange strList[right] and strList[storedIndex]
        //to indicate that the pivot has been moved to its final location.
        String finalStoredString = strList.get(storedIndex);
        strList.set(right,finalStoredString);
        strList.set(storedIndex,pivotString);
        return storedIndex;
    }

    private int choose(int lower,int upper) {
        int result = (lower + upper)/2;
        if (result < lower || result > upper)
            return lower;
        return result;
    }
}//END CLASS QuickSort

/**
 * SPEED TESTS (using a defines block of about 3500 lines)<br />
 * 5.30 sec
 *
 * (using 10700 lines)
 * 58.45 sec,56.13
 */
private static class CountingSort {
    ArrayList<String> strList;

    CountingSort(ArrayList<String> inputStrList) {
        strList = inputStrList;
    }

    public ArrayList<String> getSortedStrList() {
        ArrayList<Integer> countingList = new ArrayList<Integer>();
        ArrayList<String> finalList = new ArrayList<String>();
        int maxStrLength = 0;
        for (String eachString : strList) {
            int currentLength = eachString.length();
            if (!countingList.contains(currentLength)) {
                countingList.add(currentLength);
                if (currentLength > maxStrLength)
                    maxStrLength = currentLength;
            }
        }
        //Strings of length 0 are rather pointless to put in the final list.
        for (int i=maxStrLength; i > 0; i--) {
            if (countingList.contains(i)) {
                for (String eachString : strList) {
                    if (eachString.length() == i)
                        finalList.add(eachString);
                }
            }
        }
        return finalList;
    }
}//END CLASS CountingSort

}//END MAIN CLASS
