package String;

import java.util.HashMap;

public class Trie {
    public static class TrieNode{

        HashMap<Character,TrieNode> children;
        int count;
        boolean isWord;

        public TrieNode(){
            children= new HashMap<>();
            count=0;
            isWord=false;
        }
    }

    public TrieNode root = new TrieNode();
    public void insert(String word){
        TrieNode cur = root;
        for(char c:word.toCharArray()){
            cur = cur.children.computeIfAbsent(c,(k)->new TrieNode());
        }
        cur.isWord=true;
        cur.count++;

    }

    public boolean search(String str){//both prefix and whole
        TrieNode cur = root;
        for(char c:str.toCharArray()){
            if(!cur.children.containsKey(c)){
                return false;
            }
            cur= cur.children.get(c);
        }

        return true;
    }


}

