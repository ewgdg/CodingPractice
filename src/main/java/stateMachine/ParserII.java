package StateMachine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserII {
    //simplified method
    //有一个实例来辅助分析话会更方便，这里使用的实例是：
    // 编程找出文件中符合正则表达式 "alpha[alpha|digit]*" 和 "digit+" 的字符串。alpha 就是字母啦：[a-zA-Z]；digit就是数字：[0-9]。

    public enum InputType{
        Letter,Digit,Other;
    }
    public interface Operation {
        public void dispatch(char c);
    }

    StringBuilder token;
    List<String> res;


    HashMap<Integer, Operation> ops;
    public ParserII(){
        res=new ArrayList<>();
        token= new StringBuilder();
        m_state=0;

        ops = new HashMap<>();
        ops.put(0,(c)->{
            if(token.length()>0){
                res.add(token.toString());
            }
            token=new StringBuilder();
        });
        ops.put(1,(c)->token.append(c));
        ops.put(2,(c)->token.append(c));
        //or use the same func pointer for both 1 and 2
        //process = (c)->token.append(c);
        //ops.put(1,process);

    }

    public static int[][] transition= new int[][]{
            {1,2,0},
            {1,1,0},
            {0,2,0}
    };
    int m_state;//master state id

    public InputType getType(char c){
        if(Character.isLetter(c)){
            return InputType.Letter;
        }else if(Character.isDigit(c)){
            return InputType.Digit;
        }else{
            return InputType.Other;
        }
    }
    public List<String> parse(String s){
        //reset ops
        m_state=0;
        res =new ArrayList<>();
        token = new StringBuilder();

        for(char c: s.toCharArray()){
            InputType type = getType(c);
            m_state=transition[m_state][type.ordinal()];
            ops.get(m_state).dispatch(c);
        }
        if(token.length()>0){
            res.add(token.toString());
        }
        return res;
    }

    public static void main(String[] args){
        ParserII parser = new ParserII();
        System.out.println( parser.parse("asf_*fdfdf123*12312333") );

    }



}
