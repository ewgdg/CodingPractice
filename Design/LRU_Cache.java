import java.util.HashMap;

public class LRU_Cache {
    //Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
    //
    //get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
    //put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

    //Least recently, not least frequently
    //move  the most recently used to front


//    Follow up:
//    Could you do both operations in O(1) time complexity?


    class Node{ //we can use template<K,V> for the better design
        int val,key;
        Node prev,next;
        public Node(int val,int key){
            this.val = val;
            this.key = key;
        }
        public Node(){
            this(0,0);
        }
    }



    int capacity;
    Node head,tail;
    HashMap<Integer,Node> map;

    public LRU_Cache(int capacity) {
        this.capacity = capacity;
        this.head = new Node();
        this.tail = new Node();

        head.next=tail;
        tail.prev=head;

        this.map = new HashMap<>(capacity);
    }

    public int get(int key){
        if(!map.containsKey(key)) return -1;

        Node node = map.get(key);
        update(node);
        return node.val;

    }
    public void put(int key, int val){
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val=val;
            update(node);
        }else{
            Node node = new Node(key,val);

            while(map.size()>=capacity){
                deleteLast();
            }
            map.put(key,node);
            insertHead(node);

        }


    }

    public void update(Node node){
        node.prev.next= node.next;
        node.next.prev= node.prev;

        insertHead(node);
    }

    public void insertHead(Node node){
        head.next.prev = node;
        node.next= head.next;

        head.next=node;
        node.prev= head;
    }

    public void deleteLast(){
        Node node = tail.prev;
        if(node==head){
            return;
        }

        node.prev.next=tail;
        tail.prev= node.prev;

        map.remove(node.key);

    }



}
