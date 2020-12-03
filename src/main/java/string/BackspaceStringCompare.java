package string;


// Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.

// Note that after backspacing an empty text, the text will continue empty.

// Input: S = "ab#c", T = "ad#c"
// Output: true
// Explanation: Both S and T become "ac".
public class BackspaceStringCompare {

  class Solution {
    class BackwardIterator{
        String s;
        int i;
        int n;
        int skips;
        public BackwardIterator(String s){
            this.s=s;
            n=s.length();
            i=n-1;
            skips=0;
        }
        
        
        //skip to next valid char
        public boolean hasNext(){
          
            while(i>=0){
                if(s.charAt(i)=='#'){
                    skips++;
                  
                }
                else if(skips>0){
                    skips--;
                   
                }else{
                    //found
                  
                    break;
                   
                   
                }
                i--;
            
            }
            return i>=0;
        }
        public Character next(){
            Character res = null;
            if(hasNext()){
                res=s.charAt(i);
                i--;
            }
            
            
            return res;
            
        }
        
    }
    public boolean backspaceCompare(String S, String T) {
        BackwardIterator iter1 = new BackwardIterator(S);
        BackwardIterator iter2 = new BackwardIterator(T);
        
        
        while(iter1.hasNext() && iter2.hasNext()){
            if(iter1.next()!=iter2.next()){
                return false;
            }    
        }
        if(iter1.hasNext()||iter2.hasNext()){
            return false;
        }
        return true;
        
        
    }
}
}
