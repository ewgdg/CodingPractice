package Array.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class SteadyGene {

//    A gene is represented as a string of length n (where n is divisible by 4), composed of the letters a,c ,g , and t.
    //It is considered to be steady if each of the four letters occurs exactly n/4  times.

    //find the minimum substring length we need to replace to make gene steady
    //gaaataaa
    //-> a : 6 > 8/4=2
    //so need to replace substring aaata , len=5



    //method1:
    //sliding window , find the excess gene count and
    //find a smallest window to capture the excess
    //2 pointer left and right, for each right  find the largest left
    //another way to find the smallest right index for each left;
    static int steadyGene(String gene) {
        int n = gene.length();
        int bound = n/4;
        HashMap<Character,Integer> counter = new HashMap<>();
        HashMap<Character,Integer> goal = new HashMap<>();//the char need to be modified
        for(char c: gene.toCharArray()){
            counter.put(c,counter.getOrDefault(c,0)+1);
        }
        for(Map.Entry<Character,Integer> entry:counter.entrySet()){
            if(entry.getValue()>bound){
                goal.put(entry.getKey(),entry.getValue()-bound);
            }
        }


        //notice the right-left+1 is at least 1 so there is a corner case when size should be 0;
        if(goal.size()==0){
            return 0;
        }

        //sliding window
        int left = 0;
        int right = 0;


        int res = Integer.MAX_VALUE;
        int goal_reached=0;
        while(right<n){
            char c = gene.charAt(right);
            if(goal.containsKey(c)){
                goal.put(c,goal.get(c)-1);
                if(goal.get(c)==0){
                    goal_reached++;
                }
            }
            while(left<=right){//while reached_goal == goal.size//shrink the sliding window as much as possible

                char toRemove = gene.charAt(left);
                if(goal.containsKey(toRemove)&&goal.get(toRemove)<0){
                    goal.put(toRemove,goal.get(toRemove)+1);
                }else if(goal.containsKey(toRemove)&&goal.get(toRemove)>=0){
                    break;
                }
                //if(goal_reached==goal.size()){
                //     res = Math.min(res,right-left+1);
                //}
                left++;
            }
            if(goal_reached==goal.size()){
                res = Math.min(res,right-left+1);
            }
            right++;

        }
        return res;


    }

//    method 2
//another sliding window with different goal focus on the region outside the window
    //the sliding window is capturing invalid, so the remaining should be all valid-->goal 2


    //Let's first think when Limak can choose some particular interval (substring). We should care about the remaining letters,
    // both in the prefix and the suffix. If there are more than  n/4
    // remaining letters of one type then we can't get a steady string.
    // Otherwise, we know exactly how many letters of each type are missing and we can fill the removed interval with these exact letters.
    // So, the interval can be chosen only if the remaining part doesn't contain more than  letters of some type.

}
