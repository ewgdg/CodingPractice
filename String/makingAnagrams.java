import java.util.HashMap;

public class makingAnagrams {
    //min num of  deletion for our two strings to turn them into anagrams of each other
    //anagram->same pool of chars
    static int makeAnagram(String a, String b) {
        //test driven
        //abc
        //dddb

        //found shared chars , delete the rest
        HashMap<Character,Integer> pool = new HashMap<>();
        int count =0;
        for(char c: a.toCharArray()){
            pool.put(c, pool.getOrDefault(c,0 )+1);
        }
        for(char c:b.toCharArray()){
            if(pool.containsKey(c)){
                count+=2;//2 chars found to be equal to each other
                pool.put(c,pool.getOrDefault(c,0)-1  );
                if(pool.get(c)==0){
                    pool.remove(c);
                }
            }
        }

        return a.length()+b.length()-count;


    }

}
