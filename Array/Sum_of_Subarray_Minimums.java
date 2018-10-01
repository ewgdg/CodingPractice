import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class Sum_of_Subarray_Minimums {

    public static int solution(int[] nums){

        Deque<Node> stack = new LinkedList<>();
        long res=0;
        long curSum=0;
        int totSize=0;
        // long fill =0;
        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            while(!stack.isEmpty()&& cur<stack.peekLast().num){
                Node node = stack.removeLast();
                curSum-=node.num*node.size;
                totSize-=node.size;
            }


            // stack.addLast(cur);
            // curSum+=cur;

            int size = i+1-totSize;
            stack.addLast(new Node(cur,size));
            curSum+=cur*size;
            totSize+=size;


            // if(!stack.isEmpty() && (stack.size()+fill)<i+1 ){
            //     long extra_fill = i+1-stack.size()-fill;
            //     int filler = stack.getLast();
            //     curSum +=  extra_fill*filler;
            //     fill+=extra_fill;
            // }
            res += curSum;
        }
        return (int)(res%(1e9+7));

    }
    static class Node{
        int num;
        int size;
        public Node(int num, int size){
            this.num=num;
            this.size =size;
        }
    }



    public static void main(String[] args){
        System.out.println(solution(new int[]{
                85,93,93,90
        }));
    }

}
