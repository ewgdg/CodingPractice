import org.slf4j.impl.StaticLoggerBinder;

import java.util.*;
import java.util.stream.Collector;

public class convertToReversedPolishNotation {

    //A B C D - * + E F * -  == A+B*(C-D)-E*F
    // how to solve post polish ?  use a stack if we see an op(and the stack size>=2), then pop 2 number from stack , calc the res to push back the res into stack, continue

    //中缀表达式转换成前缀表达式和中缀表达式转换成后缀表达式十分类似，只需要将扫描方向由前往后变成由后往前，
    //将'('改为')',')'改为'(',注意其中一个判断优先级的地方需要由>=变成>.

    // local highest prio is always calc first. low high low , using monotone stack
    public static String solve(String in){

//        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        Stack<Integer> prios = new Stack<>();

        int prio = 0;
        int n  = in.length();

        StringBuilder res =new StringBuilder();
        for(int i=0;i<n;i++){

            char c = in.charAt(i);

            if(Character.isDigit(c)){
                int cur =0;
                while(i<n && Character.isDigit( (  c=in.charAt(i)  )  )){
                    cur = cur*10+(c-'0');
                    i++;
                }
                res.append(cur);
                res.append(" ");
            }

            if(c=='('){
                prio+=2;
            }else if(c==')'){
                prio-=2;
            }else if(c=='+' || c=='-' || c=='*'||c=='/' ){//in case space or other invalid char

                int curPrio = prio;
                if(c=='*'||c=='/') curPrio++;

                //if the cur prio is less than or equal to those in stack, we should calc higher or equal prio in stack first
                while(!prios.isEmpty() && curPrio<= prios.peek()){
                    res.append(ops.pop()).append(" ");
                    prios.pop();
                }

                prios.add(curPrio);
                ops.add(c);
            }




        }

        while(!prios.isEmpty()){
            res.append(ops.pop()).append(" ");
            prios.pop();
        }
        return res.toString();





    }
    //normal polish notation
    public static String toPolishNotation(String exp){

        Deque<Character> charSequence = new LinkedList<>(); //just stack should be fine
        StringBuilder res = new StringBuilder();

        Stack<Character> ops = new Stack<>();
        Stack<Integer> prios = new Stack<>();

        //if exp starts from - sign , insert 0 before - ?
        int n = exp.length();
        int prio=0;
        //read from end
        for(int i= n -1 ;i>=0;i--){
            char c = exp.charAt(i);
            if(Character.isDigit(c)){

                while(i>=0  &&  Character.isDigit(c=exp.charAt(i))  ){
                    charSequence.addFirst(c);//dont really need to make it int , just make a char seq
                    i--;
                }
                charSequence.addFirst(' ');
                i++;
            }else if(c == ')'){
                prio+=2;
            }else if(c=='('){
                prio-=2;
            }else if(c=='+' || c=='-' || c=='*'||c=='/' ){
                //operations
                int curP = prio;
                if(c == '*'|| c== '/'){
                    curP++;
                }

                while(!prios.isEmpty() && prios.peek()>curP){
                    prios.pop();
                    charSequence.addFirst(ops.pop());
                    charSequence.addFirst(' ');
                }
                prios.add(curP);
                ops.add(c);
            }
        }

        while(!prios.isEmpty()){
            prios.pop();
            charSequence.addFirst(ops.pop());
            charSequence.addFirst(' ');
        }
//        System.out.println(charSequence.toString());
        charSequence.removeFirst();//remove first space
        while(!charSequence.isEmpty()){
            res.append(charSequence.removeFirst());
        }
        
        return res.toString();


    }



    public static void main(String[] args){

        String test = " 10+9+6+21*(31-42)-35*7";  //A B C D - * + E F * -
        System.out.println(solve(test));
        System.out.println(toPolishNotation(test));

    }

}
