package gui;

import builtin.math.Epsilon;
import builtin.system.ChangeString;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Oliver Chu
 */
public class SmartList<E> extends AbstractList<E> {
    private ArrayList<E> list;
    private Subprogram sub;
    public boolean isRecursiveList;
    public boolean isFrozen;
    
    public boolean isInfinite() {
        return sub != null || size() > 2100000000;
    }
    
    public boolean isAllIntegers() {
        if (!isInfinite()) {
            return false;
        }
        return get(0).toString().equals("0") &&
               get(1).toString().equals("1") &&
               get(2).toString().equals("-1") &&
               get(3).toString().equals("2") &&
               get(4).toString().equals("-2");
    }
    
    public boolean isAllPositiveIntegersAndZero() {
        if (!isInfinite()) {
            return false;
        }
        for (int j = 0, i = 129; j < 10; ++j, i += 2563) {
            boolean cond = get(j).toString().equals("" + j);
            boolean cond2 = get(i).toString().equals("" + i);
            if (!(cond && cond2)) {
                return false;
            }
        }
        return true;
    }

    public SmartList() {
        list = new ArrayList<E>();
        sub = null;
        isRecursiveList = false;
        isFrozen = false;
    }
    
    public void freeze() {
        isFrozen = true;
    }
    
    public void thaw() {
        isFrozen = false;
    }
    
    public SmartList(Subprogram s) {
        list = new ArrayList<E>();
        sub = s;
    }
    
    public void changeToRecursiveList() {
        isRecursiveList = true;
    }
    
    public void changeToRegularList() {
        isRecursiveList = false;
        isFrozen = false;
    }
    
    // Note: this doesn't do anything anymore.
    private void checkBounds(int i) {
        // Note: do not use i >= list.size() or else you will get random
        // error messages that do not apply.
        /* if (i < -list.size() || i > list.size()) {
            System.err.println("List index out of bounds: Index " + i +
                               ", List: " + list);
            boolean choice = ShowQuestionBox.javaApply("Out of Bounds",
                "You are attempting to evaluate: " + list + "[" + i + "]\n" +
                "Normally this is out of bounds.\n\n" +
                "Do you want to consider all lists to be circular?");
            if (!choice) {
                throw new MintException("OUT OF BOUNDS");
            } else {
                FileIO.strToFile("lists-are-circular", "lists-are-circular");
            }
        } */
    }
    
    public SmartList(E[] array) {
        list = new ArrayList<E>();
        list.addAll(Arrays.asList(array));
    }
    
    public SmartList(List<E> otherList) {
        list = new ArrayList<E>();
        list.addAll(otherList);
    }
    
    public SmartList(Collection<E> collection) {
        list = new ArrayList<E>();
        list.addAll(collection);
    }
    
    @Override
    public E get(int i) {
        if (isRecursiveList) {
            int oldI = i;
            while (oldI < 0) {
                oldI += list.size();
            }
            oldI %= list.size();
            i = Math.abs(i);
            i &= 1;
            if (i == 0) {
                return list.get(0);
            } else {
                SmartList<E> cdr = new SmartList<E>();
                for (int j = 1; j < list.size(); ++j) {
                    cdr.add(list.get(j));
                }
                if (!cdr.isEmpty()) {
                    cdr.changeToRecursiveList();
                }
                try {
                    // Return the cdr as a list.
                    return (E) Heap.allocateList((SmartList<Pointer>) cdr);
                } catch (Throwable t) {
                    try {
                        // Return the cdr as a string.
                        return (E) Heap.allocateString(cdr.toString());
                    } catch (Throwable t2) {
                        try {
                            // This isn't cooperating, so just return
                            // the actual ith element.
                            return list.get(oldI);
                        } catch (Throwable t3) {
                            // This is ridiculous. We give up.
                            return null;
                        }
                    }
                }
            }
        }
        if (sub != null) {
            SmartList<Pointer> args = new SmartList<Pointer>();
            args.add(Heap.allocateInt(i));
            try {
                return (E) sub.execute(new Environment(),
                       new SmartList<String>(), args,
                       new Interpreter());
            } catch (MintException ex) {
                System.err.println(ex.getMessage());
            }
        }
        /* try {
            if (!FileIO.exists("lists-are-circular")) {
                checkBounds(i);
            }
        } catch (Throwable t) {
            System.err.println("" + t);
            Mint.printStackTrace(t.getStackTrace());
        } */
        if (i < 0) {
            i += list.size();
        }
        try {
            if (list.isEmpty()) {
                System.err.println("Error: List is empty!");
                return null;
            }
            while (i < 0) {
                i += list.size();
            }
            i %= list.size();
            try {
                return list.get(i);
            } catch (Throwable t) {
                return list.get(0);
            }
        } catch (Throwable t2) {}
        return list.get(i);
    }
    
    @Override
    public E set(int i, E value) {
        if (isFrozen) {
            try {
                return (E) ChangeString.ERROR;
            } catch (Throwable t) {
                return null;
            }
        }
        try {
            if (!FileIO.exists("lists-are-circular")) {
                checkBounds(i);
            }
        } catch (Throwable t) {
            System.err.println("" + t);
            Mint.printStackTrace(t.getStackTrace());
        }
        if (i < 0)
            i += list.size();
        return list.set(i, value);
    }
    
    public void prependAll(SmartList<E> lst) {
        ArrayList<E> oldList = new ArrayList<E>();
        for (int i = 0; i < list.size(); ++i) {
            oldList.add(list.get(i));
        }
        list = new ArrayList<E>();
        list.addAll(lst);
        list.addAll(oldList);
    }
    
    @Override
    public boolean add(E value) {
        if (isFrozen) {
            return false;
        }
        return list.add(value);
    }
    
    @Override
    public void add(int i, E value) {
        if (isFrozen) {
            return;
        }
        list.add(i, value);
    }
    
    @Override
    public boolean isEmpty() {
        if (isRecursiveList) {
            return false;
        }
        return list.isEmpty();
    }
    
    public SmartList<E> makeAllReals() {
        sub = new Subprogram("epsilon",
              new SmartList<Pointer>(new Pointer[]{Heap.allocateName("x")}),
              new Epsilon());
        return this;
    }
    
    @Override
    public int size() {
        if (isRecursiveList) {
            return 2;
        }
        if (sub != null) {
            // System.out.println("This list is actually infinitely long.");
            return Integer.MAX_VALUE;
        }
        return list.size();
    }
    
    @Override
    public boolean contains(Object value) {
        return list.contains(value);
    }
    
    @Override
    public E remove(int i) {
        if (isFrozen) {
            try {
                return (E) ChangeString.ERROR;
            } catch (Throwable t) {
                return null;
            }
        }
        return list.remove(i);
    }
    
    public SmartList<E> reverse() {
        // If this list is frozen, then nothing happens!
        if (isFrozen) {
            return this;
        }
        ArrayList<E> oldList = list;
        list = new ArrayList<E>();
        for (int i = oldList.size() - 1; i >= 0; i--) {
            list.add(oldList.get(i));
        }
        return this;
    }
    
    public int find(E e) {
        int i = 0;
        for (E item : list) {
            if (item.equals(e))
                return i;
            i++;
        }
        return -1;
    }
    
    /** Removes the section of this list from index start to index end - 1,
     * and replaces it with a new section 'sublist'.
     */
    public void assignSublist(int start, int end, SmartList<E> sublist) {
        if (isFrozen) {
            return;
        }
        ArrayList<E> oldList = list;
        list = new ArrayList<E>();
        for (int i = 0; i < start; i++) {
            list.add(oldList.get(i));
        }
        for (E element : sublist) {
            list.add(element);
        }
        for (int i = end; i < oldList.size(); i++) {
            list.add(oldList.get(i));
        }
    }
    
    public void assignSublist(int start, int end, E value) {
        if (isFrozen) {
            return;
        }
        SmartList<E> sublist = new SmartList<E>();
        sublist.add(value);
        assignSublist(start, end, sublist);
    }
    
    public E pop() {
        if (isFrozen) {
            return list.get(list.size() - 1);
        }
        return list.remove(list.size() - 1);
    }
    
    public SmartList<E> subList(int start) {
        return new SmartList<E>(subList(start, size()));
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0)
            fromIndex += list.size();
        if (toIndex < 0)
            toIndex += list.size();
        return list.subList(fromIndex, toIndex);
    }
    
    @Override
    public String toString() {
        int elementsLeft = list.size();
        if (isRecursiveList) {
            if (list.size() == 0) {
                if (isFrozen) {
                    return "()";
                }
                return "[]";
            } else if (list.size() == 1) {
                if (isFrozen) {
                    return "(" + list.get(0) + " [])";
                }
                return "[" + list.get(0) + ", []]";
            }
            String result = "";
            char counter;
            for (counter = 'A'; counter <= 'Z' && elementsLeft > 0;
                 ++counter, --elementsLeft) {
                if (counter == 'A') {
                    if (isFrozen) {
                        result += "(" + list.get(counter - 'A') + ", " +
                            counter + ")\n";
                    } else {
                        result += "[" + list.get(counter - 'A') + ", " +
                            counter + "]\n";
                    }
                } else {
                    if (isFrozen) {
                        result += (char) (counter - 1) +
                              " --> (" + list.get(counter - 'A') +
                              ", " + counter + ")\n";
                    } else {
                        result += (char) (counter - 1) +
                              " --> [" + list.get(counter - 'A') +
                              ", " + counter + "]\n";
                    }
                }
            }
            --counter;
            if (elementsLeft > 0) {
                if (isFrozen) {
                    result += counter + " --> (...)";
                } else {
                    result += counter + " --> [...]";
                }
            } else {
                result += counter + " --> []";
            }
            return result;
        }
        if (sub != null) {
            ArrayList<E> lst = new ArrayList<E>();
            for (int i = 0; i < 10; i++) {
                lst.add(get(i));
            }
            String x = lst.toString();
            x = StrTools2.slice(x, 0, -1) + ", ...]";
            return x;
        }
        if (isFrozen) {
            String result = "(";
            for (E e : list) {
                result += e.toString() + " ";
            }
            return StrTools2.slice(result, 0, -1) + ")";
        }
        return list.toString();
    }
}
