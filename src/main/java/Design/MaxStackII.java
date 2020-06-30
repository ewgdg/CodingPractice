package Design;
import java.util.Stack;
import java.util.TreeMap;

public class MaxStackII {
    //method 2 with treeMap(sorted map) and doubly linked list
    //logn push, pop max,remove,pop,peek max

    TreeMap<Integer, Stack<Node>> treeMap;
    DoublyLinkedList dll;

    public MaxStackII(){
        treeMap=new TreeMap<>();
        dll = new DoublyLinkedList();
    }

    public void push(int x) {
        Node cur = new Node(x);
        treeMap.computeIfAbsent(x,k->new Stack<>()).add(cur);
        dll.addLast(cur);
    }


    public int pop() {
        Node res =dll.removeLast();
        Stack stack = treeMap.get(res.val);
        stack.pop();
        //clean empty stack
        if(stack.isEmpty()){
            treeMap.remove(res.val);
        }
        return res.val;
    }

    public int top() {
        return dll.peekLast().val;
    }

    public int peekMax(){
        return treeMap.lastEntry().getKey();
    }

    public int popMax(){
        Node node = treeMap.lastEntry().getValue().pop();
        if(treeMap.lastEntry().getValue().isEmpty()){
            treeMap.pollLastEntry();
        }
        dll.remove(node);
        return node.val;
    }




    class Node{
        Node prev,next;
        int val;
        public Node(int v){
            this.val=v;
        }
    }
    class DoublyLinkedList{
        Node head,tail;
        public DoublyLinkedList(){
            head= new Node(0);
            tail = new Node(0);

            head.next=tail;
            tail.prev=head;

        }

        public void addLast(Node node){
            tail.prev.next=node;
            node.prev=tail.prev;

            node.next=tail;
            tail.prev=node;
        }

        public Node removeLast(){
            if(head.next==tail) return null;
            Node res = tail.prev;
            res.prev.next=tail;
            tail.prev=res.prev;
            return res;
        }

        public void remove(Node node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
        }
        public Node peekLast(){
            return tail.prev;
        }
    }

}
