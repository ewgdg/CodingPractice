import java.util.*;

public class SwapSorter {
    //Given an array of n distinct elements, find the minimum number of swaps required to sort the array.

    //Input : {4, 3, 2, 1}
    //Output : 2
    //Explanation : Swap index 0 with 3 and 1 with 2 to
    //              form the sorted array {1, 2, 3, 4}.



    //sort with position index , then swap based on sorted

    static class Node { // for dup val , to avoid using map??? no !!! doesnt really work for dup since the sorter might not sort by min cost,
        //// need in place sorter, inplace still dont work ,works if inplace sorting is required result
        int val;
        int index;
        public Node(int val, int index){
            this.val = val;
            this.index=index;
        }
    }

    static class NodeComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            int diff = o1.val-o2.val;
            return diff==0?o1.index-o2.index:diff;//for  inplace sorting, already in place sorting , no need to change
        }

    }

//wrong for dup cases
    public static List<int[]> solution(int[] nums){
        Node[] sorted = new Node[nums.length];
        Node[] unsorted = new Node[nums.length];


        for(int i =0; i<nums.length;i++){
            sorted[i] = new Node(nums[i],i);
            unsorted[i] = sorted[i];//same obj
        }



        Arrays.sort(sorted,new NodeComparator());



        List<int[]> res = new ArrayList<>();

        for(int i=0;i<sorted.length;i++){
            if(sorted[i].index!=i){
                int temp = nums[i];
                int pos = sorted[i].index;

                nums[i] = nums[pos];
                nums[pos] = temp;
                //if swap, need to update the pos index in sorted list. normally find the node through map but to avoid dup issue we use unsorted node array, Wrong for dup!!!!
                unsorted[pos]=unsorted[i];
                unsorted[pos].index=pos;

                res.add(Arrays.copyOf(nums,nums.length));
                System.out.println(Arrays.toString(nums));
            }
        }
        return res;

    }
    //only works for unique val
    public static List<int[]> solution2(int[] nums){
        int[] sorted = Arrays.copyOf(nums,nums.length);
//        HashMap<Integer,Node> map = new HashMap<>();//to fast access the node from val, dont need node , just another map can do it



        HashMap<Integer,Integer> map = new HashMap<>();//fast access original index , can replace map1
        for(int i =0; i<nums.length;i++){
            map.put(nums[i],i);
        }

        Arrays.sort(sorted);
        List<int[]> res = new ArrayList<>();

        for(int i=0;i<sorted.length;i++){
            if(nums[i]!=sorted[i]){
                int temp = nums[i];
                int pos = map.get(sorted[i]);
                nums[i] = nums[pos];
                nums[pos] = temp;
                //if swap, need to update the pos index in sorted list
                map.put(temp,pos);
                map.put(nums[i],i);//need for dup, but taking extra step

                res.add(Arrays.copyOf(nums,nums.length));
                System.out.println(Arrays.toString(nums));
            }
        }
        return res;

    }


    public static void main(String[] args){
        System.out.println( solution(new int[]{1, 5, 4, 3, 2}) );
        System.out.println( solution2(new int[]{1, 5, 4, 3, 2}) );
        System.out.println( solution(new int[]{4, 5, 2, 1, 3}) );
        System.out.println( solution(new int[]{2,3,1}) ); //wrong here, corrected
        System.out.println( solution(new int[]{3,4,2,1}) ); //wrong here, corrected with hashmap
        System.out.println( solution2(new int[]{3,4,2,1}) ); //wrong here, corrected with hashmap


        System.out.println( solution(new int[]{1, 5, 4, 3, 2,2}) );//wrong!!!  dont work, but it is the best we can do , nothing else work, works if inplace sorting is required
        System.out.println( solution2(new int[]{1, 5, 4, 3, 2,2}) );
    }



}
