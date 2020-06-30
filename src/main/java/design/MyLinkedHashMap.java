package Design;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyLinkedHashMap<K,V>{//polymorphism is important for oo design

    class Node implements Map.Entry<K, V> {
        Node prev;
        Node next;
        V val;
        K key;
        public Node(){
            prev=null;
            next=null;
            val=null;
            key=null;
        }
        public Node(K k, V v){
            prev=null;
            next=null;
            val=v;
            key=k;
        }

        public K getKey(){
            return key;
        }
        public V getValue(){
            return val;
        }
        public V setValue(V v){
            V old =val;
            val=v;
            return old;
        }
    }

    //class State

    Node head;
    Node tail;
    HashMap<K,Node> map;
    public MyLinkedHashMap(){
        map =  new HashMap<>();
        head = new Node();
        tail = new Node();

        head.next=tail;
        tail.prev=head;
    }


    public void put(K key, V val ){
        Node cur = new Node(key,val);
        map.put(key,cur);
        tail.prev.next=cur;
        cur.prev= tail.prev;

        tail.prev=cur;
        cur.next=tail;
    }


    public V get(K key){
        return map.get(key).val;
    }

    public Set<Map.Entry<K,V>> entrySet(){
        Node cur = head.next;
        Set<Map.Entry<K,V>> set = new HashSet<>();
        while(cur!=tail){
            set.add(cur);
        }
        return set;
    }

    public void remove(K key){
        if(!map.containsKey(key)) return;

        Node cur = map.get(key);
        cur.prev.next=cur.next;
        cur.next.prev= cur.prev;

        cur.prev=null;
        cur.next=null;
        map.remove(key);

    }





}
