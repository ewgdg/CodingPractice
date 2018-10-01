import java.util.ArrayList;
import java.util.List;

public class Parser {
    //有一个实例来辅助分析话会更方便，这里使用的实例是：
    // 编程找出文件中符合正则表达式 "alpha[alpha|digit]*" 和 "digit+" 的字符串。alpha 就是字母啦：[a-zA-Z]；digit就是数字：[0-9]。

    public enum InputType{
        Letter,Digit,Other;
    }

    StringBuilder token;
    List<String> res;
    public interface State{
        public State transit(InputType type);
        public void dispatch(char c);
    }
    State m_state;

    State1 state1 = new State1();
    State2 state2 = new State2();
    State0 state0 = new State0();

    State[][] transition = new State[][]{
            {state1,state2,state0},
            {state1,state1,state0},
            {state0,state2,state0}
    };

    public class State1 implements State{

//        State[] transition ;//= new State[]{state1,state1,state0}; //doesnt work bc other state are not yet initialized, need to set transition after instantiation

        int id = 1;
        @Override
        public State transit(InputType type) {
            return transition[id][type.ordinal()];
        }

        @Override
        public void dispatch(char c) {
            token.append(c);
        }
    }
    public class State2 implements State{

//        State[] transition = new State[]{state0,state2,state0};
        int id = 2;
        @Override
        public State transit(InputType type) {
            return transition[id][type.ordinal()];
        }
        public void dispatch(char c) {
            token.append(c);
        }
    }

    public class State0 implements State{

//        State[] transition = new State[]{state1,state2,state0};
        int id = 0;
        @Override
        public State transit(InputType type) {
            return transition[id][type.ordinal()];
        }
        public void dispatch(char c) {
//            token.append(c);
            //reset everything
            res.add(token.toString());
            token=new StringBuilder();

        }
    }

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
        m_state=state0;
        res =new ArrayList<>();
        token = new StringBuilder();
        for(char c: s.toCharArray()){
            InputType type = getType(c);
            m_state=m_state.transit(type);
            m_state.dispatch(c);
        }
        if(token.length()>0){
            res.add(token.toString());
        }
        return res;
    }


    public static void main(String[] args){
        Parser parser = new Parser();
        System.out.println( parser.parse("asf_fdfdf123*12312333") );

    }
}

