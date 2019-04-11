import java.util.Arrays;
import java.util.Stack;

public class Cap_salary {

//    salary list and target, 要求一個cap使得調整過後的薪水總合為target. cap的定義是：當前薪水大於cap, 則用cap當作新的薪水，否則保持原本的薪水 (cap = min(cap, curr_salary)).
    public static double solution(int target, int[] salaries){
        int n = salaries.length;
        Arrays.sort(salaries);
        int sum =0;
        Stack<Integer> valid_salaries = new Stack<>();
        int max =0;
        for(int i =0; i<n; i++ ){
            int salary = salaries[i];
            max = Math.max(max,salary);
            if(sum+salary<target){
                sum+=salary;
                valid_salaries.push(salary);
            }
            else{
                double cap = (double)(target-sum)/(n-i);

                while( valid_salaries.peek()  > cap  ){  //dont need the stack just simply start from the very end
                    sum-=valid_salaries.pop(); i--;
                    cap = (double)(target-sum)/(n-i);
                }
                return cap;
            }
        }

        return max;

    }

    public static void main(String[] args){
        System.out.println(solution(27,new int[]{8,2,3,4,6,7}));
    }


}
