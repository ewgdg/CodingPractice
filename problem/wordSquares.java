import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wordSquares {
    public List<List<String>> wordSquares(String[] words) {
        int n= words.length;
        int k = n==0?0:words[0].length();

        Trie tree= new Trie();
        for(String word: words){
            tree.insert(word);
        }

        List<List<String>> ans = new ArrayList<>();
        for(String word:words){
            backtrack(ans,new ArrayList<String>( Arrays.asList(word) ),k,words,tree);
        }
        return ans;

    }

    public void backtrack(List<List<String>> ans, List<String> list, int k, String[] words ,Trie tree){
        if(list.size()==k){
            ans.add(new ArrayList<>(list) ); //add a copy of new list
            return;
        }
        StringBuilder prefix = new StringBuilder();
        int index = list.size();
        for(String str : list){
            prefix.append(str.charAt(index));
        }

        List<String> candidates = tree.prefixSearch(prefix.toString());
        for(String candidate : candidates){

                list.add(candidate);
                backtrack(ans, list, k, words, tree);
                list.remove(list.size()-1);

        }

    }


    class TrieNode{
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;

    }

    class Trie{
        TrieNode root = new TrieNode();

        public void insert(String word){
            TrieNode cur= root;
            for(char c: word.toCharArray()){
                int index = c-'a';
                if(cur.children[index]==null){
                    cur.children[index] = new TrieNode();
                }
                cur = cur.children[index];
            }
            cur.isEnd = true;

        }

        public List<String> prefixSearch(String prefix){
            TrieNode cur = root;
            List<String> ans = new ArrayList<String>();
            for(char c: prefix.toCharArray()){
                int index= c-'a';
                if(cur.children[index]!=null){
                    cur = cur.children[index];
                }else{
                    return ans;
                }
            }



            if(cur.isEnd){
                ans.add(prefix);
            }
            ans.addAll(prefixSearch(prefix,cur));

            return ans;

        }

        public List<String> prefixSearch(String prefix, TrieNode cur){
            List<String> ans = new ArrayList<String>();
            for(char c='a';c<='z';c++ ){
                TrieNode next = cur.children[c-'a'];
                if(next!=null){
                    if(next.isEnd){
                        ans.add(prefix+c);
                    }
                    ans.addAll(prefixSearch(prefix+c,next));
                }
            }
            return ans;
        }

    }

    public static void main(String[] args){

        System.out.println(new wordSquares().wordSquares( new String[]{"abaa","aaab","baaa","aaba"}));
    }
}
