package others;

public class gcd {
    public static int solution(int a, int b){
        return b==0? a: solution(b,a%b);
    }

    public  static  void main(String[] args){
        System.out.println(solution(-1,6));
    }
}
