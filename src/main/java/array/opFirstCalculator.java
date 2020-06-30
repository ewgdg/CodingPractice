package array;

// import org.apache.log4j.Priority;

import java.util.HashMap;
import java.util.Stack;

public class opFirstCalculator {
    // * (+ + 2 4 1) 3 =( 2 + 4 + 1 ) * 3//类似波兰式
    //注意 波兰式 优先级已经按照先后顺序确定好了， 但这个没有??，必须自己定义优先级别根据括号，乘法，除法
    //A*B+C == * A + B C?? or + * A B C???


    //A+B*(C-D)-E*F为例
//前缀表达式又叫做波兰式。同样的道理，表达式的前缀表达式是由相应的语法树的前序遍历的结果得到的。
//如上图的前缀表达式为- + A * B - C D * E F
//由前缀表达式求出结果有下面两种思路：
//　　1.从左至右扫描表达式，如果一个操作符后面跟着两个操作数时，则计算，然后将结果作为操作数替换(这个操作符和两个操作数)，
//重复此步骤，直至所有操作符处理完毕。如-+A*B-CD*EF，扫描到-CD时，会计算C-D=C',表达式变成：-+A*BC'*EF
//继续扫描到*BC',计算B*C'=B',表达式变成:-+AB'*EF,继续+AB'，依此类推。
//---------------------
//作者：Raise
//来源：CSDN
//原文：https://blog.csdn.net/linraise/article/details/20459751?utm_source=copy
//版权声明：本文为博主原创文章，转载请附上博文链接！

    public int solve(String equa){

        Stack<Integer> nums = new Stack<>();//assume polish notation



        int n=equa.length();

        for(int i =n-1; i>=0;i--){
            char c =equa.charAt(i);
            if(Character.isDigit(c)) {
                int cur = 0;
                int rank=1;
                while (i>=0 && Character.isDigit( (c=equa.charAt(i) ) )) {
                    cur = cur  + (c - '0')*rank;
                    rank*=10;
                    i--;
                }
                nums.add(cur);
            }

            if(c=='+'||c=='-'||c=='*'||c=='/') {
               if(nums.size()>=2){
                   int a= nums.pop();
                   int b = nums.pop();

                   int r = calc(a,b,c);
                   nums.add(r);
               }

            }

        }

        return nums.pop();

    }

    public int calc(int a, int b, char op){
        return ops.get(op).calc(a,b);
    }
    static HashMap<Character, Operation> ops;
    static {
        ops = new HashMap<>();
        ops.put('+',(a,b)->a+b);
        ops.put('-',new Substruct());
        ops.put('*',new Multiplication());
        ops.put('/', (a,b)->a/b);//syntax sugar!!!!!!!!!!!!!!!!!!!!!!!!
    }
    static interface Operation{
        public int calc(int a, int b);
    }


    static class Substruct implements  Operation{
        public int calc(int a, int b){
            return a-b;
        }
    }

    static class Multiplication implements  Operation{
        public int calc(int a, int b){
            return a*b;
        }
    }



    public static void main(String[] args){
        String test = "* (+ + 2 41 1) 3"; // ( 2 + 41 + 1 ) * 3 = 132

        opFirstCalculator solver =new opFirstCalculator();

        System.out.println(solver.solve(test));

    }

}
