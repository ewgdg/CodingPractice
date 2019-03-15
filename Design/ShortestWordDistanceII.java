import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortestWordDistanceII {

    //or with cache hashmap for better res;
    HashMap<String, List<Integer>> map;
    public ShortestWordDistanceII(String[] words) {
        map = new HashMap<>();
        int idx=0;
        for(String w:words){
            map.computeIfAbsent(w,k->new ArrayList<>()).add(idx);
            idx++;
        }

    }

    public int shortest(String word1, String word2) {
        List<Integer> list1= map.get(word1);
        List<Integer> list2 = map.get(word2);

        int i1=0;
        int i2=0;
        int res = Integer.MAX_VALUE;

        //bc the 2 list are sorted, we can iterate the list with sliding window method ot find min dist
        while(i1<list1.size() && i2<list2.size()){ // notice the complexity here is O(m+n) , instead of m*n if we use 2 for loop
            int index1=list1.get(i1);
            int index2 = list2.get(i2);

            res = Math.min(res,Math.abs(index1-index2));
            if(index1<index2){
                i1++;
            }else{
                i2++;
            }

        }

        return res;
    }


/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(words);
 * int param_1 = obj.shortest(word1,word2);
 */
}
