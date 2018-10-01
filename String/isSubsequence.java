import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class isSubsequence {
    public static boolean solution(String s, String t) {

//         int i1 =0;
//         if(i1==s.length()) return true;
//         for(int i2=0;i2<t.length(); i2++){
//             if(t.charAt(i2) == s.charAt(i1)){
//                 i1++;
//                 if(i1==s.length()) break;
//             }

//         }

//         return i1==s.length();

        //preprocess t with hashmap
        HashMap<Character, List<Integer>> map = new HashMap<>();
        for(int i =0 ; i< t.length(); i++){
            char c = t.charAt(i);
            List<Integer> list = map.computeIfAbsent(c,(k)->new ArrayList<>());
            list.add(i); // list is sorted due to sequential access

        }

        int prev = -1;
        for(char c: s.toCharArray()){
            if(map.containsKey(c)){
                List<Integer> list = map.get(c);
                prev = binarySearch(list,0,list.size()-1, prev ) ;
                if(prev==-1) return false;
            }else {
                return false;
            }
        }
        return true;


    }

    public static int binarySearch(List<Integer> list, int left , int right, int target ){

        while(left<right){
            int mid = (left+right)/2;

            int cur = list.get(mid);
            if(cur<=target){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        if(list.get(left) <= target){
            //not found
            return -1;
        }
        return list.get(left);


    }

    public  static  void main(String[] args){
        System.out.println(solution("leetcode","yyylyyyeyyyeyyyytyycyyyyyycyyyyoyyyydyyyyye"));
    }
}
