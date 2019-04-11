import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class Subsequence {
    static class Node{
        public int min,max ;
        public Node(int min, int max){
            this.min=min;
            this.max = max;

        }

    }

    public static boolean seq123(int[] nums){
        Stack<Node> stack = new Stack<>();
        for(int i=0;i<nums.length;i++){
            Node cur = new Node(nums[i],nums[i]);
            while(!stack.isEmpty() && nums[i] > stack.peek().min){
                Node prev = stack.pop(); //merge
                if( prev.max> prev.min && nums[i]>prev.max ){
                    return true;
                }else {
                    cur.min=Math.min(cur.min, prev.min);


                }

            }
            stack.push(cur);

        }
        return false;

    }

    public static void main(String[] args){
        System.out.println(  seq123( new int[]{1,5,2,1,2,3}) );
    }


}
