package gui;

import java.util.Iterator;

/**
 *
 * @author Oliver Chu
 */
public class Environment {
    private SmartList<MintObject> frames;
    private int thisPointer;
    private String thisPointerName;
    SmartList<Integer> thisPointers;
    SmartList<String> thisPointerNames;
    
    public Environment() {
        frames = new SmartList<MintObject>();
        frames.add(new MintObject());
        thisPointer = -1;
        thisPointerName = "";
        thisPointers = new SmartList<Integer>();
        thisPointerNames = new SmartList<String>();
        thisPointers.add(-1);
        thisPointerNames.add("");
    }
    
    public void eraseAll() {
        frames = new SmartList<MintObject>();
    }
    
    void addFrame(MintObject frame) {
        frames.add(frame);
    }
    
    MintObject pop() {
/*        thisPointer = -1;
        thisPointerName = ""; */
        if (!thisPointers.isEmpty()) {
            thisPointer = thisPointers.pop();
            thisPointerName = thisPointerNames.pop();
        }
        return frames.pop();
    }
    
    public Pointer getValue(String name) throws MintException {
        try {
            if (name == null) {
                return null;
            }
            if (thisPointer != -1) {
                try {
                    Pointer p = frames.get(thisPointer).get(thisPointerName);
                    if (p != null) {
                        //System.out.println("P: " + p);
                        MintObject obj = PointerTools.dereferenceObject(p);
                        if (obj != null && obj.containsName(name)) {
                            return obj.get(name);
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    
                }
            }
            for (int i = frames.size() - 1; i >= 0; i--) {
                MintObject currentFrame = frames.get(i);
                if (currentFrame.containsName(name)) {
                    return currentFrame.get(name);
                }
            }
            Pointer p = deepSearchGetValue(name, this);
            if (p != null) {
                return p;
            }
            p = forwardSearchGetValue(name, this);
            if (p != null) {
                return p;
            }
            throw new MintException(name + " is not defined.");
        } catch (NullPointerException ex) {
        }
        return Constants.MINT_NULL;
    }
    
    SmartList<MintObject> getFrames() {
        return frames;
    }
    
    public int getCurrentFrameIndex() {
        return frames.size() - 1;
    }
    
    public static Pointer forwardSearchGetValue(String name, Environment env) {
        SmartList<MintObject> fr = env.getFrames();
        for (int i = fr.size() - 3; i >= 0; i--) {
            MintObject currentFrame = fr.get(i);
            if (currentFrame.containsName(name))
                return currentFrame.get(name);
            for (Pointer p : currentFrame.values()) {
                if (p.type == Constants.OBJECT_TYPE) {
                    Environment newEnv = new Environment();
                    newEnv.addFrame(PointerTools.dereferenceObject(p));
                    Pointer result = deepSearchGetValue(name, newEnv);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
    
    public static Pointer deepSearchGetValue(String name, Environment env) {
        SmartList<MintObject> fr = env.getFrames();
        for (int i = fr.size() - 1; i >= 0; i--) {
            MintObject currentFrame = fr.get(i);
            if (currentFrame.containsName(name))
                return currentFrame.get(name);
            for (Pointer p : currentFrame.values()) {
                if (p.type == Constants.OBJECT_TYPE) {
                    Environment newEnv = new Environment();
                    newEnv.addFrame(PointerTools.dereferenceObject(p));
                    Pointer result = deepSearchGetValue(name, newEnv);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
    
    public void setThisPointer(int value) {
        thisPointers.add(thisPointer);
        thisPointer = value;
    }
    
    public void setThisPointerName(String value) {
        thisPointerNames.add(thisPointerName);
        thisPointerName = value;
    }
    
    void remove(String name) {
        frames.get(frames.size() - 1).remove(name);
    }
    
    void put(SmartList<Pointer> nameReference, Pointer value)
                                                          throws MintException {
        nameReference = ListTools.removePointers(nameReference,
                     new Pointer(Constants.KEYWORD_TYPE, Constants.OPEN_PAREN));
        nameReference = ListTools.removePointers(nameReference,
                    new Pointer(Constants.KEYWORD_TYPE, Constants.CLOSE_PAREN));
        SmartList<Pointer> objects = new SmartList<Pointer>();
        SmartList<Pointer> lists = new SmartList<Pointer>();
        SmartList<Integer> indexList = new SmartList<Integer>();
        //SmartList<Boolean> nextIsList = new SmartList<Boolean>();
        for (int i = 0; i < nameReference.size(); i++) {
            Pointer p = nameReference.get(i);
            Integer keyword = PointerTools.dereferenceKeyword(p);
            if (keyword != null) {
                switch (keyword) {
                    case Constants.DOT:
                        String objName = PointerTools.dereferenceName(
                                                      nameReference.get(i - 1));
                        MintObject obj = null;
                        SmartList<Pointer> list = null;
                        Iterator<Integer> indexIterator = indexList.iterator();
                        if (!lists.isEmpty() || !objects.isEmpty()) {
                            boolean gotFirst = false;
                            for (Pointer objN : objects) {
                                if (gotFirst) {
                                    Pointer newObj = null;
                                    if (objN.type == Constants.OBJECT_TYPE) {
                                        obj = PointerTools.dereferenceObject(
                                                           objN);
                                    } else if (obj != null) {
                                        newObj = obj.get(PointerTools.
                                                 dereferenceName(objN));
                                    }
                                    String newObjName = null;
                                    if (newObj != null)
                                        newObjName = PointerTools.
                                                     dereferenceName(newObj);
                                    if (newObj != null && (
                                        obj.containsName(newObjName) ||
                                        getValue(newObjName) != null)) {
                                        Pointer newObjPtr = obj.get(newObjName);
                                        if (newObjPtr.type ==
                                            Constants.OBJECT_TYPE) {
                                            obj =
                                                PointerTools.dereferenceObject(
                                                                     newObjPtr);
                                        } else if (newObjPtr.type ==
                                                   Constants.LIST_TYPE) {
                                            list = PointerTools.
                                                   dereferenceList(newObjPtr);
                                            do {
                                                Pointer next = list.get(
                                                          indexIterator.next());
                                                if (next.type ==
                                                    Constants.LIST_TYPE) {
                                                    list = PointerTools.
                                                          dereferenceList(next);
                                                    continue;
                                                } else if (next.type ==
                                                        Constants.OBJECT_TYPE) {
                                                    obj = PointerTools.
                                                        dereferenceObject(next);
                                                }
                                            } while (false);
                                            //lastWasList = true;
                                        }
                                    }
                                } else {
                                    if (objN != null) {
                                        if (objN.type ==
                                            Constants.OBJECT_TYPE) {
                                            obj = PointerTools.
                                                  dereferenceObject(objN);
                                        } else {
                                            obj = PointerTools.
                                                  dereferenceObject(getValue(
                                                  PointerTools.dereferenceName(
                                                  objN)));
                                        }
                                    }
                                }
                                gotFirst = true;
                            }
                        } else {
                            if (objName != null) {
                                obj = PointerTools.dereferenceObject(
                                                   getValue(objName));
                            }
                        }
                        String member = PointerTools.dereferenceName(
                                        nameReference.get(i + 1));
                        if (i + 1 == nameReference.size() - 1) {
                            obj.put(member, value);
                            return;
                        }
                        if (objName == null)
                            objects.add(nameReference.get(i - 1));
                        else
                            objects.add(Heap.allocateName(objName));
                        Pointer mem = Heap.allocateName(member);
                        try {
                            if (ListTools.findPointerValue(objects, mem) == -1)
                            {
                                objects.add(mem);
                            }
                        } catch (NullPointerException ex) {
                            
                        }
                        break;
                    default:
                        throw new MintException("Unknown keyword: " + keyword);
                }
            }
            if (p.type == Constants.LIST_TYPE) {
                SmartList<Pointer> indexL = PointerTools.dereferenceList(p);
                Integer index = PointerTools.dereferenceInt(indexL.get(0));
                SmartList<Pointer> list = null;
                String listName = PointerTools.dereferenceName(
                                  nameReference.get(i - 1));
                if (!lists.isEmpty() || !objects.isEmpty()) {
                    if (index != null)
                        indexList.add(index);
                    boolean gotFirst = false;
                    Iterator<Pointer> nameIterator = lists.iterator();
                    MintObject obj = null;
                    for (Integer _i : indexList) {
                        Iterator<Pointer> objNameIterator = objects.iterator();
                        if (gotFirst) {
                            Pointer newList = null;
                            if (list != null)
                                newList = list.get(_i);
                            if (newList != null) {
                                if (newList.type == Constants.LIST_TYPE) {
                                    list = PointerTools.dereferenceList(
                                                        newList);
                                } else if (newList.type ==
                                           Constants.OBJECT_TYPE) {
                                    do {
                                        obj = PointerTools.dereferenceObject(
                                                           newList);
                                        Pointer n = objNameIterator.next();
                                        Pointer next;
                                        if (n.type == Constants.OBJECT_TYPE) {
                                            next = n;
                                        } else {
                                            next = obj.get(PointerTools.
                                                           dereferenceName(n));
                                        }
                                        if (next.type ==
                                            Constants.LIST_TYPE) {
                                            list = PointerTools.
                                                   dereferenceList(next);
                                        } else if (next.type ==
                                                   Constants.OBJECT_TYPE) {
                                            obj = PointerTools.
                                                  dereferenceObject(next);
                                            continue;
                                        }
                                    } while (false);
                                }
                            }
                        } else {
                            Pointer nxt = nameIterator.next();
                            if (nxt.type == Constants.NAME_TYPE) {
                                list = PointerTools.dereferenceList(getValue(
                                       PointerTools.dereferenceName(nxt)));
                            } else {
                                list = PointerTools.dereferenceList(nxt);
                            }
                            list = PointerTools.dereferenceList(list.get(_i));
                        }
                        gotFirst = true;
                    }
                } else {
                    if (listName != null) {
                        list = PointerTools.dereferenceList(
                                            getValue(listName));
                    }
                }
                if (i == nameReference.size() - 1) {
                    list.set(index, value);
                    return;
                }
                if (listName == null)
                    lists.add(nameReference.get(i - 1));
                else
                    lists.add(Heap.allocateName(listName));
                indexList.add(index);
                try {
                    if (list.get(index).type == Constants.OBJECT_TYPE) {
                        objects.add(list.get(index));
                    }
                } catch (NullPointerException ex) {
                    
                }
            }
        }
    }
    
    void putAll(MintObject mo) {
        if (frames.isEmpty())
            frames.add(new MintObject());
        frames.get(frames.size() - 1).putAll(mo);
    }
    
    public void put(String name, Pointer value) {
        if (thisPointer != -1) {
            Pointer po = frames.get(thisPointer).get(thisPointerName);
            MintObject obj = null;
            if (po != null)
                obj = PointerTools.dereferenceObject(po);
            if (obj != null && obj.containsName(name)) {
                obj.put(name, value);
                frames.get(thisPointer).put(thisPointerName,
                                        Heap.allocateObject(obj));
            } else {
                frames.get(frames.size() - 1).put(name, value);
            }
        } else {
            if (frames.isEmpty())
                frames.add(new MintObject());
            frames.get(frames.size() - 1).put(name, value);
        }
    }
    
    @Override
    public String toString() {
        return frames.toString();
    }
    
    public int size() {
        return frames.size();
    }
}
