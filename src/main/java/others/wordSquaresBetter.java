package others;
import java.util.ArrayList;
import java.util.List;

public class wordSquaresBetter {
    public List<List<String>> wordSquares(String[] words) {
        int n= words.length;
        int k = n==0?0:words[0].length();

        Trie tree= new Trie();
        for(String word: words){
            tree.insert(word);
        }


        List<List<String>> ans = new ArrayList<>();

        TrieNode[] rows = new TrieNode[k];

        for(int i =0;i<k;i++){
            rows[i]=tree.root;
        }

        backtrack(ans,rows,k,0,0);

        return ans;

    }

    public void backtrack(List<List<String>> ans, TrieNode[] rows, int k,  int i, int j){ //i = cur row, j = cur col, k is the size of word
        if(j==k){
            i = i+1;
            j=i;
        }


        if(i==k && j==k){
            List<String> list = new ArrayList<>();
            for(TrieNode node : rows){
                list.add(node.word);
            }
            ans.add( list );
            return;
        }

        TrieNode cur = rows[i]; //store for revert during backtracking
        TrieNode corresponding = rows[j];
        for( int c =0 ; c<26 ;c++){
            if(rows[i].children[c]!=null){ //current cell is valid

                //exam the correspongind cell
                if(rows[j].children[c]!=null){

                    rows[i]=rows[i].children[c];
                    if(i!=j) {
                        rows[j] = rows[j].children[c]; //if i==j , we have made that change, no need to change again
                    }

                    //next layer
                    backtrack(ans,rows,k,i,j+1);
                    //revert change
                    rows[i]=cur;
                    rows[j]=corresponding;
                }
            }
        }



    }


    class TrieNode{
        TrieNode[] children = new TrieNode[26];
        String word = null;

    }

    class Trie{
        public TrieNode root = new TrieNode();

        public void insert(String word){
            TrieNode cur= root;
            for(char c: word.toCharArray()){
                int index = c-'a';
                if(cur.children[index]==null){
                    cur.children[index] = new TrieNode();
                }
                cur = cur.children[index];
            }
            cur.word = word;

        }

    }
    public static void main(String[] args){

        System.out.println(new wordSquaresBetter().wordSquares( new String[]{"area","lead","wall","lady","ball"}));
    }
}
