package Design;
// import java.util.Comparator;
import java.util.TreeMap;
// import java.util.TreeSet;

public class MultiSet<K extends Comparable<K>> { // or named as bag // another way for compare is to cast K into Comparable<? super K>

    TreeMap<K,DLL<K>> map;
    public MultiSet(){
        map = new TreeMap<>();

    }

    public Node<K> add(K data){

        DLL<K> dll = map.computeIfAbsent(data, k->new DLL<>());
        dll.addLast(data);
        return dll.peekLast();

    }

    public boolean contains(K data){
        return map.containsKey(data);
    }

    public void remove(K data){
        if(contains(data)){
            DLL<K> dll = map.get(data);
            dll.removeFirst();
            if(dll.head==dll.tail){
                map.remove(data);
            }
        }
    }
    public void remove(Node<K> node){
        if(!contains(node.data)) return;

        DLL<K> dll = map.get(node.data);
        dll.remove(node);
        if(dll.head==dll.tail){
            map.remove(node.data);
        }

    }
    public Node<K> next(Node<K> node){
        K data = node.data;
        DLL<K> dll = map.get(data);
        Node<K> res;
        if(dll.isTail(node)){ //or check if the next node is dummy ,add boolean isDummy for Node
            dll = map.higherEntry(data).getValue();
            res = dll.peekFirst();
        }else{
            res = node.next;
        }
        return res;
    }

    public Node<K> prev(Node<K> node){
        K data = node.data;
        DLL<K> dll = map.get(data);
        Node<K> res;
        if(dll.isHead(node)){
            dll = map.lowerEntry(data).getValue();
            res = dll.peekLast();
        }else{
            res = node.prev;
        }
        return res;
    }





    class DLL<T>{//no need to use generic T for a inner class , just use K ==> class DLL{ K data }
        Node<T> head;
        Node<T> tail;

        public DLL(){
            head = new Node<>(null);
            tail = new Node<>(null);
            head.next=tail;
            tail.prev=head;
        }

        public void addLast(T data){
            Node<T> node = new Node<T>(data);
            tail.prev.next=  node;
            node.prev =tail.prev;

            tail.prev= node;
            node.next=tail;
        }

        public void remove(Node<T> node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
        }

        public void removeFirst(){
            head.next= head.next.next;
            head.next.prev= head;//head.next now is head.next.next
        }
        public Node<T> peekFirst(){
            if(head.next!=tail)
                return head.next;
            return null;
        }
        public Node<T> peekLast(){
            if(head.next==tail)
                return null;
            return tail.prev;
        }

        public boolean isTail(Node<T> node){
            return node.next==tail;
        }
        public boolean isHead(Node<T> node){
            return node.prev==head;
        }

    }
    public class Node<T>{ //or simply Node and use K from parent
        Node<T> prev,next;
        T data;
        public Node(T val){
            data= val;
        }
    }



    public static void main(String[] args){
//        TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1<o2?-1:1;
//            }
//        });
//
//        set.add(1);
//        System.out.println(set.contains(1)); //doesnt work



    }


}
