package others;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Substring {

    public static List<Integer> findSubstring(String s, String[] words) {
        HashMap<String,Integer> map = new HashMap<>();

        int wordlength = 0;
        for(String word : words){
            map.put(word,map.getOrDefault(word,0)+1);
            wordlength = word.length();
        }

        int left =0;
        int right =0 ;


        int goal = words.length;
        int curGoal=goal;


        HashMap<String,Integer> curMap = new HashMap<>(map);//need to reuse map , cannot modify it
        List<Integer> ans = new ArrayList<>();

        if(goal==0) return ans;


        for(int i =0 ; i<wordlength; i++ ){ //diff starting point
            left = i;
            curGoal = goal; //reset goal
            curMap=new HashMap<>(map);
            right =left;
            while(right+wordlength<=s.length()){
                String cur = s.substring(right,right+wordlength);
                if(curMap.containsKey(cur)){
                    curMap.put(cur,curMap.get(cur)-1);
                    if(curMap.get(cur)>=0){ // valid goal
                        curGoal--;
                    }
                    //move left p
                    while(curMap.get(cur)<0){
                        String tmp = s.substring(left,left+wordlength);
                        left = left+wordlength;
                        curMap.put(tmp, curMap.get(tmp)+1 );

                        if(curMap.get(tmp) >0) curGoal++;

                    }

                    if(curGoal==0){
                        ans.add(left);

                        String tmp = s.substring(left,left+wordlength);
                        left = left+wordlength;//only update by one word length

                        curMap.put(tmp, curMap.get(tmp)+1 );
                        curGoal++;


                    }

                }
                else{
                    //reset everything
                    left = right+wordlength;
                    curGoal=goal;
                    curMap=new HashMap<>(map);

                }

                right +=wordlength;


            }
        }
        return ans;
    }


    public static void main(String[] args){
        String s= "aaaaaaaa";
        String[] w = new String[]{"aa","aa","aa"};
        System.out.println(findSubstring(s,w));
    }
}
