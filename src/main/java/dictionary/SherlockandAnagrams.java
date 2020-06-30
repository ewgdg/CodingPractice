package dictionary;
import java.util.HashMap;

public class SherlockandAnagrams {
    //Two strings are anagrams of each other if the letters of one string can be rearranged to form the other string.
    // Given a string, find the number of pairs of substrings of the string that are anagrams of each other.

    //e.g mom -> 2
    //mo om , m m


    static int sherlockAndAnagrams(String s) {
        //naive sol:
        //find all substring encode them into signature , store into hashmap
        //n^2*n(encoding，create copy)
        HashMap<HashMap<Character,Integer>,Integer> counter = new HashMap<>();
        int n = s.length();
        for(int i=0;i<n;i++ ){
            HashMap<Character,Integer> chars = new HashMap<>();
            for(int j = i; j<n;j++){
                //substring i,j
                char c = s.charAt(j);
                chars.put(c,chars.getOrDefault(c,0)+1);
                HashMap<Character,Integer> signature = new HashMap<>(chars);
                counter.put(signature,counter.getOrDefault(signature,0)+1);
            }
        }
        int res=0;
        for(Integer count: counter.values()){
            int numOfpair = (count-1+1)*(count-1)/2;
            res+=numOfpair;
        }
        return res;


    }

}
