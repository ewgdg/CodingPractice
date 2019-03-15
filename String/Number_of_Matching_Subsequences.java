import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class Number_of_Matching_Subsequences {
    //Input:
    //S = "abcde"
    //words = ["a", "bb", "acd", "ace"]
    //Output: 3
    //Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".


    //All words in words and S will only consists of lowercase letters.
    //The length of S will be in the range of [1, 50000]. S  -->>  S>>>>L this is the key point
    //The length of words will be in the range of [1, 5000]. W
    //The length of words[i] will be in the range of [1, 50].L !!!!!!!!!!!!!!!!!!!!! notice the len << than S


//O( (S+L)*W  )
    public int NaiveMethod_numMatchingSubseq(String S, String[] words) {
        int count=0;
        HashMap<String , Boolean> cache = new HashMap<>();
        for(String word: words){
            boolean res = false;
            if(!cache.containsKey(word)){
                res = isSubseq(S,word);
                cache.put(word,res);
            }else{
                res = cache.get(word);
            }
            if(res){
                count++;
            }
        }
        return count;

    }
    public boolean isSubseq(String a, String b){ // can be improved by hashmap binary search
        int i =0;
        int j=0;

        int m = a.length();
        int n = b.length();
        if(n>m) return false;
        while(i<m && j<n){
            if(a.charAt(i) == b.charAt(j)){
                j++;
            }
            i++;
        }

        return j==n;
    }

    //String iterator bucket method, similar to idea of KMP non-backward of parent string
    //O (S+W*L) very fast in case of S is large

    public int solution(String S, String[] words){
        HashMap<Character, Deque<StringIterator>> buckets = new HashMap<>();
        for(String word:words){
            StringIterator iter = new StringIterator(word);
            buckets.computeIfAbsent(iter.next(),(k)->new ArrayDeque<StringIterator>()).addLast(iter);
        }
        int n = S.length();
        int res=0;
        for(int i=0;i<n;i++){
            char c= S.charAt(i);
            Deque<StringIterator> bucket = buckets.getOrDefault(c,new ArrayDeque<>());
            int size = bucket.size();
            for(int j =0 ;j<size;j++){
                StringIterator iter = bucket.removeFirst();
                if(iter.hasNext()){
                    buckets.computeIfAbsent(iter.next(),(k)->new ArrayDeque<>()).addLast(iter);
                }else{
                    //done
                    res++;
                }

            }

        }
        return res;
    }


    class StringIterator{
        String str;
        int index;
        int len ;
        public StringIterator(String str){
            this.str =str;
            index=0;
            len = str.length();
        }

        public boolean hasNext(){
            return index<len;
        }
        public Character next(){
            if(hasNext()){
                return str.charAt(index++);
            }
            return null;
        }
    }

//    public int Trie_solution(String S, String[] words){
//        Trie tree =new Trie();
//        tree.insert(S);
//
//        for(String word: words){
//            Trie.TrieNode cur = tree.root;
//            for(char c: word.toCharArray()){
//                if(!cur.children.containsKey(c)){
//                    break;//not found
//                }
//            }
//        }
//    }



    public static void main(String[] args){

        Number_of_Matching_Subsequences solver = new Number_of_Matching_Subsequences();
        System.out.println(solver.solution("abcde", new String[]{"a","bb","acd","ace"}));
    }


}
