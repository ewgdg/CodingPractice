package string;
import java.util.*;
//Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:
//Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.
//
//Example:
//
//Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
//Output:
//[
//  ["abc","bcd","xyz"],
//  ["az","ba"],
//  ["acef"],
//  ["a","z"]
//]

//idea is to find a common encoding for the group

public class GroupShiftedString {

    public static List<List<String>> groupStrings(String[] strings) {
        HashMap<String,List<String>> map = new HashMap<>();
        for(String s:strings){
            String encoded = encode(s);

//             map.computeIfAbsent(encoded, key->new ArrayList<>()).add(s);
            //notice computeIfAbsent is very slow ,100 times slower ,!!!wrong, that is only for small amount of data set, due to java environment warm up??.
            map.putIfAbsent(encoded,new ArrayList<>());
            map.get(encoded).add(s);
        }

        List<List<String>>  res = new ArrayList<>();
        for(List<String> group:map.values() ){
            Collections.sort(group);
            res.add(group);
        }
        return res;
    }

    //encode based on the first char
    //set first char as offset, encode as char-offset
    // digit might be confusing so we map 0 -> 'a';
    public static String encode(String s){
        StringBuilder encoded = new StringBuilder();
//        encoded.append('a');

        int offset = s.charAt(0)-'a';
        int n = s.length();
        char[] array = s.toCharArray();
        for(int i=0; i<n;i++){
            char c = array[i];
            char code = (char)(c-offset);
            if(code<'a') code+=26;//ensure no negative code, shift circularly

            encoded.append(code);
        }

        return encoded.toString();

    }

    public static void main(String[] args){
        long startTime = System.nanoTime();
        System.out.println( groupStrings(new String[]{"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"})  );

        HashMap<Integer,List<Integer> > map = new HashMap<>();

        for(int i=0;i<1000;i++){
            int key=9;
//            map.computeIfAbsent(key,k->new ArrayList<>()).add(i);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(i);
        }


        long endTime   = System.nanoTime();

        long totalTime = endTime - startTime;
        System.out.println(totalTime);

    }
}
