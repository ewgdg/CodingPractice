package others;
//import org.apache.log4j.Priority;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class sortSquare {
    //given a sorted array , output sorted square of that array
    //[-3,-2,2] -> [4,4,9]

    //follow up : if square is very slow, how to optimize ? answer using cache abs(num)->square(num) to reduce half amount of square
    //follow up2 : else? multithreading -> (n/k) for each thread, then final merge takes n/k*logk ,multi-way merge

    //corner cases [] , [overflow?] [single]
    public static int[] solution(int[] nums){
        int left=0;
        int right = nums.length-1;

        int[] res = new int[nums.length];
        int n = nums.length;

        HashMap<Integer,Integer> mem = new HashMap<>();

        for(int i=n-1;i>=0;i--){


            int left_square;
            int num_left = Math.abs(nums[left]);

            if(mem.containsKey(num_left)){
                left_square=mem.get(num_left);
            }else{
                left_square= nums[left]*nums[left];
                mem.put(num_left,left_square);
            }


            int right_square = nums[right]*nums[right];

            if(left_square<right_square){
                right--;
                res[i]=right_square;
            }else{
                left++;
                res[i] = left_square;

            }

        }

        return res;


    }

    static class Worker extends Thread{
        public int[] nums;
        public Worker(int[] nums){
            this.nums=nums;
        }
        @Override
        public void run() {
            nums=solution(nums);
        }
    }

    static  class Node implements Comparable{
        public int val,index;
        public int listIndex;
        public Node(int v, int i, int j){
            val=v; index=i; listIndex=j;
        }
        @Override
        public int compareTo(Object b){
            return this.val-((Node)b).val;
        }


    }
    public static  int[] multithread_solution(int[] nums){
        //assume k=4 cores threads
        int k=4;

        int n = nums.length;
        if(n<k) k=n;
        int start=0;
        Worker[] workers = new Worker[k];
        for(int i=0;i<k;i++){
            int end = start+n/k;
            if(i==k-1) end = n;
            int[] subArray = Arrays.copyOfRange(nums,start,end);
            workers[i] = new Worker(subArray);
            workers[i].start();
            start+=n/k;
        }

        for(int i =0; i<k; i++){
            try {
                workers[i].join();
            }catch (Exception e){
                ;
            }
        }
        PriorityQueue<Node> heap  = new PriorityQueue<>();

        for(int i=0; i<k;i++){
            heap.add( new Node(workers[i].nums[0],0,i) );
        }

        int[] res =new int[n];
        int i =0;


        while(!heap.isEmpty()){
            Node node = heap.poll();
            res[i]=node.val;
            if(node.index+1<workers[node.listIndex].nums.length){
                heap.add( new Node(workers[node.listIndex].nums[node.index+1],node.index+1,node.listIndex  ));
            }
            i++;
        }

        return res;


    }
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            solution(new int[]{-8,-7,-6,-4,-3,-2,2,3,4,5,6,7,8,9,10,11,13,14,15,18,20,32,40,50,99,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115});
        }
        System.out.println(Arrays.toString( solution(new int[]{-8,-7,-6,-4,-3,-2,2,3,4,5,6,7,8,9,10,11,13,14,15,18,20,32,40,50,99,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115} )) );
        System.out.println(System.currentTimeMillis()-start);

        start = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            multithread_solution(new int[]{-8,-7,-6,-4,-3,-2,2,3,4,5,6,7,8,9,10,11,13,14,15,18,20,32,40,50,99,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115});
        }
        System.out.println( Arrays.toString( multithread_solution(new int[]{-8,-7,-6,-4,-3,-2,2,3,4,5,6,7,8,9,10,11,13,14,15,18,20,32,40,50,99,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115} )));
        System.out.println(System.currentTimeMillis()-start);
    }

}
