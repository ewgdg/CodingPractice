package Design;
import java.util.*;
//Design a data structure that supports all following operations in average O(1) time.
//
//Note: Duplicate elements are allowed.
//insert(val): Inserts an item val to the collection.
//remove(val): Removes an item val from the collection if present.
//getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.

//for a list to support remove in O(1) and we dont care order
//we can swap the removed to last pos
//optional to delete the last elem

class RandomizedCollection {
    //list with swap method!!!!!!!!!!!!!!

    List<Integer> buffer;
    HashMap<Integer,HashSet<Integer>> map; //for quick find val , use linkedHashSet is better for large amount of dup , dup will affect iterator perf of hashSet
    int size;
    Random rand;
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        buffer= new ArrayList<>();
        map = new HashMap<>();
        size=0;
        rand=new Random();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        if(buffer.size()>size){
            buffer.set(size,val);//use size as index to insert
        }
        else{
            buffer.add(val);
        }
        boolean res = !map.containsKey(val);
        map.computeIfAbsent(val,k->new HashSet<>()).add(size);
        size++;
        return res;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        size--;
        int index = map.get(val).iterator().next();
        swapRemove(buffer,map,index,size);
        return true;
    }

    public void swapRemove(List<Integer> buffer, HashMap<Integer,HashSet<Integer>> map, int idx1, int idx2){
        int val1 = buffer.get(idx1);
        int val2 = buffer.get(idx2);

        HashSet<Integer> set1 = map.get(val1);
        set1.remove(idx1);
        if(set1.isEmpty()) map.remove(val1);
        if(idx1!=idx2){
            map.get(val2).remove(idx2);
            map.get(val2).add(idx1);
        }

        buffer.set(idx1,val2);
        // buffer.remove(index2);//here ww can remove the last elem with O(1) to save some space
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        int idx = rand.nextInt(size);
        return buffer.get(idx);
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
