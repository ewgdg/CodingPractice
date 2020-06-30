package design;
import java.util.HashMap;
import java.util.HashSet;

//similar to this : https://leetcode.com/problems/all-oone-data-structure/


public class OrderedFrequencyLinkedHashMap<K,V> {
    //elem is ordered based on their frequency
    class Node{
        V val;
        public int count;
        public Node(V val){
            this.count=1;
            this.val=val;
        }
    }

    class Bucket {

        HashSet<K> group;
        Bucket prev,next;
        public Bucket(){
            this.group=new HashSet<>();
        }
    }


    HashMap<K,Node> map;
    HashMap<Integer, Bucket> count_keys;

    Bucket head,tail;

    public OrderedFrequencyLinkedHashMap(){
        head = new Bucket();
        tail = new Bucket();

        head.next=tail;
        tail.prev=head;


        map = new HashMap<>();
        count_keys = new HashMap<>();
    }

    public V get(K key){
        if(!map.containsKey(key)) return null;

        Node node = map.get(key);
        int count = node.count;
        Bucket group = count_keys.get(count);
        group.group.remove(key);//need to delete bucket if it is empty!!!!!!!! fix latter

        count++;
        if(count_keys.containsKey(count)){
            count_keys.get(count).group.add(key);
        }else {
            Bucket update = new Bucket();
            update.group.add(key);

            update.next=group.next;
            group.next.prev=update;

            update.prev=group;
            group.next=update;
        }
        return node.val;


    }

    public void put(K key, V val){

        if(map.containsKey(key)) {

            get(key);//update freq
            map.get(key).val=val;
            return;
        }

        Node node = new Node(val);
        map.put(key,node);
        if(!count_keys.containsKey(1)){
            Bucket group = new Bucket();

            group.next=head.next;
            head.next.prev=group;

            group.prev=head;
            head.next=group;
            count_keys.put(1,group);

        }

        count_keys.get(1).group.add(key);



    }
    public void remove(K key){
        //to do
    }


}
