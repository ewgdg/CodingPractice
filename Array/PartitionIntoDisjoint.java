import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class PartitionIntoDisjoint {

//Given an array A, partition it into two (contiguous) subarrays left and right so that:
//
//Every element in left is less than or equal to every element in right.
//left and right are non-empty.
//left has the smallest possible size.
//Return the length of left after such a partitioning.  It is guaranteed that such a partitioning exists.

    public static int partitionDisjoint(int[] A) {
        Monotone mono  = new Monotone();
        for(int i = A.length-1;i>=0;i--){
            mono.add(A[i]);
        }

        int n  = A.length-1;
        int res = A.length-1;
        int min=Integer.MAX_VALUE;
        for(int i = A.length-1;i>0;i--){
            int cur = A[i];
            min  = Math.min(cur,min);
            System.out.println("min "+min);
            mono.poll();
            if(mono.max() <= min ){
                //reach a pos follow the rules
                System.out.println("found "+i);
                res = i;
            }

        }

        return res;

    }
    static class Monotone{
        Queue<Integer> queue;
        Deque<Integer> stack;
        public Monotone(){
            queue = new LinkedList<>();
            stack =  new LinkedList<>();
        }
        public void add(int val){
            queue.add(val);
            while(!stack.isEmpty() && val>stack.peekLast() ){
                stack.removeLast();
            }
            stack.addLast(val);
        }

        public int poll(){
            int res = queue.poll();
            if(res==stack.peekFirst()){
                stack.removeFirst();
            }
            return res;
        }

        public int max(){
            System.out.println(stack.peekFirst());
            return stack.peekFirst();
        }

    }


    public static void main(String[] args){
        System.out.println(partitionDisjoint(new int[]{6,0,8,30,37,6,75,98,39,90,63,74,52,92,64}));

    }
}
