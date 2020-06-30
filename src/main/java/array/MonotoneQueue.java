package array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MonotoneQueue {
    //maintain the max

    public Queue<Integer> queue;
    public Deque<Integer> candidates;

    public MonotoneQueue(){
        queue= new LinkedList<>();
        candidates = new LinkedList<>();
    }
    public void add(int val){
        queue.add(val);
        while(!candidates.isEmpty() && val>=candidates.peekLast()){
            candidates.removeLast();
        }
        candidates.addLast(val);
    }
    public int poll(){
        int res = queue.poll();
        if(res==candidates.peekFirst()){
            candidates.removeFirst();
        }
        System.out.println(res);
        return res;
    }

    public int max(){
        System.out.println(candidates.peekFirst());
        return candidates.peekFirst();
    }

    public static void main(String[] args){
        MonotoneQueue queue = new MonotoneQueue();
        queue.add(1);
        queue.add(2);
        queue.max(); // 1, 2

        queue.poll(); // 2
        queue.add(5); // 2 5
        queue.add(3); // 2 5 3
        queue.add(0); // 2 5 3 0
        queue.max(); // 2 5 3 0
        queue.poll(); // 5 3 0
        queue.max();
        queue.poll();// 3 0
        queue.max();

        // 2 1 5 2 5 5 3
    }

}
