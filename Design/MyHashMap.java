public class MyHashMap {

    //if resize we need to change hashcode so we might want to use consistent hashing
    //or use transfer
    Node[] table;
    int capacity;
    double threshold;
    final double factor = 0.8;
    int size;

    /** Initialize your data structure here. */
    public MyHashMap() {

        capacity = 2;//init size
        table = new Node[capacity];//can use java LinkedList for  myHashSet but not good for hashMap, since java.linkedlist cannot find entry given entry.key
        //but it is better to create a MyLinkedList/Bucket for better abstraction

        threshold = capacity*factor;
        size=0;
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {

        checkCapacity();

        int hash = hashCode(key);

        Node found = find(key);
        if(found==null){//insert as the first node
            Node _new = new Node(new Entry(key,value));
            Node old = table[hash];
            table[hash]=_new;
            _new.next=old;
            size++;
        }else{
            found.entry.value=value;
        }
    }
    public Node find(int key){
        int hash= hashCode(key);
        Node node = table[hash];
        while(node!=null){
            if(node.entry.key == key){
                return node;
            }
            node=node.next;
        }
        return node;

    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {

        Node found = find(key);
        if(found!=null){
            return found.entry.value;
        }
        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int hash = hashCode(key);
        Node node = table[hash];
        Node prev= null;
        while(node!=null){
            if(node.entry.key == key){
                if(prev!=null){
                    prev.next=node.next;
                }else{
                    table[hash]=node.next;
                }
            }
            prev=node;
            node=node.next;
        }

    }

    public void checkCapacity(){

        if(size<threshold){
            return;
        }
        capacity = 2* capacity;
        threshold = capacity*0.8;
        Node[] newTable = new Node[capacity];
        transfer(newTable);
        table = newTable;


    }

    public void transfer(Node[] newTable){
        for(int i = 0 ; i< table.length; i++){
            Node node = table[i];
            while(node!=null){
                Node next= node.next;

                int hash = hashCode(node.entry.key);
                insert(hash,node,newTable);

                node = next;
            }


        }

    }
    public void insert(int hash, Node node,Node[] table){
        Node old = table[hash];
        table[hash]=node;
        node.next=old;
    }

    public int hashCode(int key){
        return Integer.hashCode(key)%capacity;
    }
    class Entry{
        int value;
        int key;
        public Entry(int key, int value){
            this.key= key;
            this.value=value;
        }
    }
    class Node{
        Entry entry;
        Node next;
        public Node(Entry e){
            entry =e;
        }
    }

    public static void main(String[] args){
        MyHashMap map = new MyHashMap();
        map.put(54,35);
        map.put(36,39);
        map.put(63,9);
        map.put(72,28);
        map.remove(78);
        map.put(84,88);
        map.put(56,42);
        map.put(69,55);
        map.put(4,47);
        map.remove(56);
        map.put(25,46);
        map.put(5,18);
        map.put(35,94);
        map.put(10,4);
        map.put(50,67);
        map.put(77,16);
        map.put(75,48);
        map.put(7,80);


        map.get(65);
        map.put(61,5);
        map.put(52,32);
        map.put(68,84);
        map.put(54,18);
        map.put(44,41);
        map.put(17,60);
        map.put(43,30);
        map.put(30,49);


        map.remove(56);
        map.remove(54);
        map.put(20,52);
        map.put(4,0);
        map.get(56);
        map.put(98,51);
        map.put(66,73);

        System.out.println(map.get(23));
//        map.remove(7);
        System.out.println(map.get(72));
        System.out.println(map.get(83));
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
