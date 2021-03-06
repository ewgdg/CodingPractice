package design;

// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashSet {
    int capacity;
    double threshold;
    ArrayList<LinkedList<Integer>> table;
    final double factor = 0.8;
    int size;

    /** Initialize your data structure here. */
    public MyHashSet() {
        capacity=100;
        threshold = (capacity*factor);
        table=  new ArrayList<LinkedList<Integer>>(Collections.nCopies(capacity, null));// new LinkedList[capacity];
        // while(table.size()<capacity) table.add(null);
        size=0;
        // for(int i =0;i<capacity; i++){
        //     table[i] = new LinkedList<>();
        // }

    }

    public void add(int key) {
        if(size>=threshold){
            resize(capacity*2);
        }

        int hash = hashCode(key);
        LinkedList<Integer> bucket = table.get(hash);//[hash];
        if(bucket==null) {
            bucket = new LinkedList<>();
            table.set(hash, bucket);
        }

        if(bucket.contains(key)){
            return;
        }
        size++;
        bucket.addLast(key);


    }

    public void remove(int key) {
        int hash = hashCode(key);
        LinkedList<Integer> bucket = table.get(hash);
        if(bucket==null) return;

        if(bucket.remove( Integer.valueOf(key) )){//not remove index
            size--;
        }
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int hash= hashCode(key);
        LinkedList<Integer> bucket= table.get(hash);

        if(bucket==null) return false;
        return bucket.contains(key);
    }


    public int hashCode(int key){
        return (key)%capacity;
    }

    public void resize(int newSize){
        capacity = newSize;
        threshold = capacity*factor;

        ArrayList<LinkedList<Integer>> newTable = new ArrayList<LinkedList<Integer>>(Collections.nCopies(capacity, null));
        // while(newTable.size()<capacity) newTable.add(null);
        transfer(newTable);
        table = newTable;
    }
    public void transfer(ArrayList<LinkedList<Integer>> newTable){
        for(LinkedList<Integer> bucket :  table){
            if(bucket==null) continue;
            Iterator<Integer> iter = bucket.iterator();
            while(iter.hasNext()){
                int key= iter.next();
                int hash = hashCode(key);
                LinkedList<Integer> newBucket = newTable.get(hash);
                if(newBucket==null) {
                    newBucket= new LinkedList<>();
                    newTable.set(hash,newBucket);
                }

                newBucket.addLast(key);
            }

        }

    }
}
