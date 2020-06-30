package string;

import java.util.*;

public class checkIfBuildable {
    //given a list of char and a list of string
    // check if each of string is buildable from the list of char

    //method 1: n chars , m Strings
    //naively find out all of the possible  string from chars
    //permute method
    //permutation with n extra empty choice 2n!
    //or build permutation for size= 1 to n , sum of i=1->n i! ~= n*n!
    //or better!!! remove size limit , the for loop bt will terminate automatically, add res for every single temp String ,O n!

    //check the dict of strs
    //O m+n+ (n+n)! , ~= n! , could be very large if n is big , extra space O n! or test the res within permute directly

    //stupid method
    //build a trie from strs, and iterate the tree with char pool backtrack after consumed
    //wrong bc take extra space from building the tree
    //O m*n

    //method2 better if n is big
    //for each string in strs  check if the string consists of chars from the char pool
    //O m*n
    public static List<String> check(String[] strs, char[] chars){
        List<String> res = new ArrayList<>();

        HashMap<Character,Integer> counter = new HashMap<>();
        for(char c:chars){
            counter.put(c,counter.getOrDefault(c,0)+1);
        }

        for(String str: strs){
            if(buildable(str,new HashMap<Character,Integer>(counter) )){
                res.add(str);
            }
        }

        return res;
    }

    public static boolean buildable(String str, HashMap<Character,Integer> counter){

        for(char c: str.toCharArray()){
            int count = counter.getOrDefault(c,0);
            if(count<=0){
                return false;
            }
            counter.put(c,count-1);
        }
        return true;
    }

    public static List<String> method1(String[] strs, char[] chars){
        List<String> candidates = new ArrayList<>();
//        for(int i =1 ; i< chars.length;i++){
            permute(chars,0,candidates,new StringBuilder(),chars.length); //or test the res within permute directly to reduce space
//        }
        System.out.println(candidates);

        HashSet<String> dict = new HashSet<>();
        for(String str: strs){
            dict.add(str);
        }

        List<String> res= new ArrayList<>();
        for(String cand: candidates){
            if(dict.contains(cand)){
                res.add(cand);
            }
        }
        return res;

    }
    //issue!!!!, dup char result in dup res, need to fix, if hashSet extra cost
    // O n!
    public static void permute(char[] chars, int index, List<String> res ,StringBuilder temp, int size){//index represent the size of tempString
//        if(index== size) {
//            //res.add(temp.toString()); limited size permute
//            return;
//        }
        res.add(temp.toString()); //full size permute

        Set<Character> seen = new HashSet<>();
        for(int i = index; i<chars.length;i++){
            char cur = chars[i];
            if(seen.contains(cur)) continue;//skip dup for the same loop/subprob
            seen.add(cur);

            swap(chars,i,index);
            temp.append(cur);
            permute(chars, index+1 ,res, temp,size);


            //backtrack
            //revert change
            temp.deleteCharAt(temp.length()-1);
            swap(chars,i, index);
        }
        //extra empty option for full permute of all size, no need , just remove limit size


    }
    public static void swap(char[] chars, int i , int j ){
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;

    }

    public static void main(String[] args){

        String[] strs = new String[]{"acb","bbb","str","ccc","cc","c"};
        char[] chars = new char[]{'a','b','c','s','t','r','c'};
//        char[] chars = new char[]{'s','t','r'};

        System.out.println(check(strs,chars));
        System.out.println(method1(strs,chars));



    }
}
