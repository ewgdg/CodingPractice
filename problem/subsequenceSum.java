import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.slf4j.impl.StaticLoggerBinder;

public class subsequenceSum  {


    public static List<List<Integer>> solution(int[] nums, int target){
        HashMap<String,List<List<Integer>>> mem = new HashMap<>();
        List<List<Integer>> res = dp(nums,target,0,mem);
        return res;

    }
    public static List<List<Integer>> solution2(int[] nums, int target){
        HashMap<String,List<List<Integer>>> mem = new HashMap<>();
        List<List<Integer>> res = dp2(nums,target,0,mem);
        return res;

    }
    static int call1=0;
    //complexity O(target * n); //2^n without mem because the binary tree expansion
    public static List<List<Integer>> dp(int[] nums, int target, int index ,HashMap<String,List<List<Integer>>> mem){//the index is starting index, or we can use ending index and reduce it to 0 subproblem range(0...end_index)
        String key = target+":"+index;

        call1++;
        if(mem.containsKey(key)) return mem.get(key);


        List<List<Integer>> res = new ArrayList<>();

        if(target<0) return res;
        else if(target==0){
            res.add(new ArrayList<>());
//            return res;//dont need to return , might have 0 after it
        }

        if(index>=nums.length) return res; // check index lastly cannot use else,


        List<List<Integer>> ans1 = dp( nums, target, index+1, mem );
        List<List<Integer>> ans2 = dp(nums, target-nums[index], index+1, mem );





        for(List<Integer> list  : ans2  ){
            List<Integer> clone = new ArrayList<>(list); //need a new array res to put into mem
            clone.add(nums[index]);//ans2 include cur num
            res.add(clone);
        }

//        res.addAll(ans1);
        for(List<Integer> list  : ans1 ){//clear any empty array
            if(!list.isEmpty()){
                res.add(list);
            }
        }

        mem.put(key,res);
        return res;





    }
    static int call2=0;
    //another similar method backtracking // not very ideal in terms of calling time
    public static List<List<Integer>> dp2(int[] nums, int target, int index ,HashMap<String,List<List<Integer>>> mem){
        call2++;
        String key = target+":"+index;
        if(mem.containsKey(key)) return mem.get(key);


        List<List<Integer>> res = new ArrayList<>();

        if(target<0) return res;
        else if(target==0){
            res.add(new ArrayList<>());
//            return res;
        }
        if(index>=nums.length) return res;



        for(int i=index;i<nums.length;i++){



            List<List<Integer>> ans = dp2(nums, target-nums[i], i+1, mem );

//            List<List<Integer>> res2 = new ArrayList<>();
            for(List<Integer> list  : ans  ){
                List<Integer> clone = new ArrayList<>(list); //need a new array res to put into mem
                clone.add(nums[i]);//ans2 include cur num
                res.add(clone);
//                res2.add(clone);
            }

//            //should store res here // need to clone new list, no need
//            int temp=(target-nums[i]);
//            String key2 = temp +":"+i;
//            mem.put(key2,res2);
        }


        mem.put(key,res);



        return res;





    }




    public static void main(String[] args){
        System.out.println(solution(new int[]{2,4,6,10,43,7,8,3,5,0},16 ));
        System.out.println(solution2(new int[]{2,4,6,10,43,7,8,3,5,0},16 ));

        System.out.println(call1);
        System.out.println(call2);

    }







}
