package array;

import java.util.*;

public class TopKfreq {
    //min heap maintain k top elem so far, nlogk //https://www.geeksforgeeks.org/binary-heap/

    //quick select O n


    public List<Integer> solve(int[] nums,int k){
        HashMap<Integer, Node> counter = new HashMap<>();
        for(int num:nums){ //need node instead of count bc we need to sort count to find val, but count could be dup(so hashmap doesnt work)
            Node cur = counter.computeIfAbsent(num, (key)->new Node(key) );
            cur.count++;
        }

        List<Node> list = new ArrayList<>(counter.values());
        quickSelect(list,k,0,list.size()-1);

        List<Integer> res =new ArrayList<>();
        for(int i=0;i<k;i++){
            res.add(list.get(i).val);
        }
        return res;
    }

    public void quickSelect(List<Node> list, int k, int lo, int hi){


//recursive method
//        int index = partition(list,lo,hi);
//        if(index==k-1){
//            return;
//        }else if(index>k-1){
//            quickSelection(list,k,lo,index-1);
//        }else if(index<k-1){
//            quickSelection(list,k,index+1,hi);
//        }
        while(lo<=hi) {
            int index = partition(list, lo, hi);
            if (index == k - 1) {
                //got exact k elem
                return;
            } else if (index > k - 1) {
                hi = index - 1;
            } else if (index < k - 1) {
                lo = index + 1;
            }
        }



    }
    public int partition(List<Node> list, int lo, int hi){
        Random random = new Random();
        int pivotIndex = hi-lo==0?lo:random.nextInt((hi-lo))+lo;
        /**
         * Generates a random integer inside the lo and hi interval
         * @param lo
         * @param hi
         * @return the generated int
         */
        //public static int randomInteger(int lo, int hi) {
        //     java.util.Random rn = new java.util.Random();
        //     int n = hi - lo + 1;
        //     int i = rn.nextInt() % n;
        //     if (i < 0) i = -i;
        //     return lo + i;
        //   }

        swap(list,lo,pivotIndex);

        int pivot = list.get(lo).count;
        int j=lo+1;
        for(int i=lo+1;i<=hi;i++){
            if(list.get(i).count>=pivot){
                swap(list,j,i);
                j++;
            }
        }

        swap(list,lo,j-1);
        return j-1;


    }

    public void swap(List<Node> list, int i, int j){
        Node temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    class Node{
        int count;
        int val;

        public Node(int val){
            this.val=val;
            count=0;
        }

    }

    public static void main(String[] args){
        TopKfreq solver = new TopKfreq();
        List<Integer> res= solver.solve(new int[]{1,1,1,3,3,3,3,3,7,7,7,7,7,4,4,2,1,8,8,8,8,8,5,6,9,0},3);
        System.out.println(res);

    }

}
