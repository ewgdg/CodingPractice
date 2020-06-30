package others;
import java.util.ArrayList;
import java.util.List;

public class subset {

    public static List<List<Integer>> solution(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());

//        dfs(nums,ans, new ArrayList<>(), 0);
        iterative(ans,nums);
        return ans;
    }

    //brach 2
    public static void dfs(int[] nums, List<List<Integer>> ans, List<Integer> tempList, int index){

        if(index>=nums.length) return;



        //branch 1
        tempList.add(nums[index]);  ans.add( new ArrayList<>(tempList));
        dfs(nums,ans,tempList,index+1);

        //branch 2
        tempList.remove(tempList.size()-1);
        dfs(nums,ans,tempList,index+1);

        //restore tempList

        return;
    }

    //another way to solve
    public static void backtrack(List<List<Integer>> ans, List<Integer> tempList, int[] nums, int start  ){
        ans.add(tempList);
        for(int i=start;i<nums.length;i++){
            List<Integer> newList = new ArrayList<>(tempList);
            newList.add(nums[i]);
            backtrack(ans,newList,nums,i+1);

        }

    }

    //another way to solve
    public static void iterative(List<List<Integer>> ans, int[] nums ){

        for(int i =0; i<nums.length;i++ ) {
            for (int j = 0, size = ans.size(); j < size; j++) { //keep updating ans.size()
                List<Integer> list =  new ArrayList<>(ans.get(j)); //clone a copy
                list.add(nums[i]);
                ans.add(list);

            }
        }

    }

    //iterative bfs




    public static void main(String[] args){
        System.out.println(solution(new int[]{1,2,3}));
    }


}
