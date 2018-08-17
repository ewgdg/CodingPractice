import java.util.HashMap;

public class isNumber {
    //define input
    public enum InputType{
        DIGIT,DOT,E,SIGN,SPACE,OTHERS
    }
    //define transition matrix
    static int[][] transition;
    static {
        transition = new int[][]{
                {1,2,-1,3,0,-1},
                {1,6,4,-1,5,-1},
                {6,-1,-1,-1,-1,-1},
                {1,2,-1,-1,-1,-1},
                {8,-1,-1,7,-1,-1},
                {-1,-1,-1,-1,5,-1},
                {6,-1,4,-1,5,-1},
                {8,-1,-1,-1,-1,-1},
                {8,-1,-1,-1,5,-1}
        };

    }

    public boolean isNumber(String s) {
        int state =0 ;
        for(char c:s.toCharArray()){
            InputType input ;
            if(c<='9' && c>='0'){
                input=InputType.DIGIT;
            }else if(c=='.'){
                input=InputType.DOT;
            }else if(c=='+' || c=='-'){
                input=InputType.SIGN;
            }else if(c=='e'){
                input=InputType.E;
            }else if(c==' '){
                input=InputType.SPACE;
            }else{
                input=InputType.OTHERS;
            }
            state=transition[state][input.ordinal()];
            if(state==-1) return false;


        }
        if(state == 5|| state == 1|| state == 8|| state==6 ) return true;
        else return  false;
    }

    public static void main(String[] args){

        System.out.println(new isNumber().isNumber("-1.e49046 "));
    }


}
