package array.stack;

import java.util.HashMap;
import java.util.Stack;

public class Balancedbrackets {
    // Complete the isBalanced function below.
    static String isBalanced(String s) {
        //{[()]} yes
        // {[(])} no
        // {{[[(())]]}} yes
        //{(([])[])[]} yes

        //naively , recursive , too complicated see test case 4
        //or using stack to trace back
        //or iteratively 2 pointer left and right, wrong !!!, see test case 4

        HashMap<Character,Character> matcher = new HashMap<>();
        matcher.put('}','{');
        matcher.put(']','[');
        matcher.put(')','(');

        int n = s.length();
        if(n%2==1) return "NO";
        Stack<Character> stack = new Stack<>();
        for(int i =0;i<n;i++){
            char c = s.charAt(i);
            if(matcher.get(c)!=null){
                if(stack.isEmpty() ||  stack.pop()!=matcher.get(c)  ){
                    return "NO";
                }
            }else{
                stack.add(c);
            }
        }
        if(stack.isEmpty())
            return "YES";
        else
            return "NO";


    }
}
