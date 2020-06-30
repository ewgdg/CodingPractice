package DynamicProgramming;

import java.util.Arrays;

public class robHouseII {

    //circular list
//Input: [1,2,3,1]
//Output: 4
//Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
//             Total amount you can rob = 1 + 3 = 4.

    public static int solution(int[] nums){

        int[] nums_rob_first = Arrays.copyOfRange(nums,0,nums.length-1);
        int[] nums_rob_last = Arrays.copyOfRange(nums,1,nums.length);

        robHouseI solver = new robHouseI();
        return Math.max(solver.solution(nums_rob_first),solver.solution(nums_rob_last));
    }

    public static void main(String[] args){
        System.out.println(solution(new int[]{1,2,3,1}));
    }



}
