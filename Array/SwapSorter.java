import java.util.*;

public class SwapSorter {
    //Given an array of n distinct elements, find the minimum number of swaps required to sort the array.

    //Input : {4, 3, 2, 1}
    //Output : 2
    //Explanation : Swap index 0 with 3 and 1 with 2 to
    //              form the sorted array {1, 2, 3, 4}.



    //sort with position index , then swap based on sorted

    static class Node {
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
            return o1.val-o2.val;
        }

    }


    public static List<int[]> solution(int[] nums){
        Node[] sorted = new Node[nums.length];
        HashMap<Integer,Node> map = new HashMap<>();//to fast access the node from val

        for(int i =0; i<nums.length;i++){
            sorted[i] = new Node(nums[i],i);
            map.put(nums[i],sorted[i]);
        }

        HashMap<Integer,Integer> map2 = new HashMap<>();//fast access original index , can replace map1
        for(int i =0; i<nums.length;i++){
            map2.put(nums[i],i);
        }

        Arrays.sort(sorted,new NodeComparator());



        List<int[]> res = new ArrayList<>();

        for(int i=0;i<sorted.length;i++){
            if(nums[i]!=sorted[i].val){
                int temp = nums[i];
//                int pos = sorted[i].index;
                int pos = map2.get(sorted[i].val);
                nums[i] = nums[pos];
                nums[pos] = temp;
                //if swap, need to update the pos index in sorted list
//                map.get(temp).index=pos;
                map2.put(temp,pos);

                res.add(Arrays.copyOf(nums,nums.length));
                System.out.println(Arrays.toString(nums));
            }
        }
        return res;

    }


    public static void main(String[] args){
        System.out.println( solution(new int[]{1, 5, 4, 3, 2}) );
        System.out.println( solution(new int[]{4, 5, 2, 1, 3}) );
        System.out.println( solution(new int[]{2,3,1}) ); //wrong here, corrected
        System.out.println( solution(new int[]{3,4,2,1}) ); //wrong here, corrected with hashmap
    }



}
