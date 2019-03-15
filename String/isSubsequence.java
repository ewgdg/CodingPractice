import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//Example 1:
//s = "abc", t = "ahbgdc"
//
//Return true.
//
//Example 2:
//s = "axc", t = "ahbgdc"
//
//Return false.
//
//Follow up:
//If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B,
// and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?
public class isSubsequence {
    //for the follow up we want to pre-process the t string first
    //The binary search is proportional to the number of occurrences of the specific character. In the worst case this is N,
    // if all characters are the same. However for most strings, characters would be distributed so the binary search is O(logX),
    // where X is likely to be significantly smaller than N. For a particular string, we are comparing O(MlogX) to O(N),
    // where X is at most N. So for small x this is almost definitely better,
    // since N is also much larger than m by the problem given. But for some strings it will be longer time.
    //not very efficient although
    //check number of matching subsequence with string iterator method for better method
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
            if(cur<=target){//skip == , bc need to find larger
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
