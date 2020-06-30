package others;
public class rotatedDigit {
    public static int solution(int n){
        int len = String.valueOf(n).length(); //get number of digit
        String digits = String.valueOf(n);

        int num= n;
        int[][][] dp = new int[len+1][2][2];
        dp[len][0][0]=1;
        dp[len][1][0]=1;

        for(int i=len-1;i>=0;i--){
            int curDigit = num%10; num/=10;

            for(int bounded = 0; bounded<2; bounded++){

                for(int valid = 0; valid < 2; valid++){ //valid =0 for neutral number, =1 for containing valid digit
                    //start from last digit 0-9

                    int bound = bounded==0? 9: curDigit;

                    int count=0;
                    for(int digit = 0; digit <=bound; digit++ ){
                        int prevBounded = digit==curDigit? bounded:0;
                        if(digit==6||digit==5||digit==9||digit==2){
                            if(valid==1)
                                count+=dp[i+1][prevBounded][0] + dp[i+1][prevBounded][1];
                        }else if(digit==0||digit==1||digit==8){

                            count+=dp[i+1][prevBounded][valid==1?1:0];
                        }else{
                            continue;//invalid number
                        }
                    }
                    dp[i][bounded][valid]=count;


                }
            }
        }
        return dp[0][1][1];


    }
    public static int solution2(int n ){
        int[] dp = new int[n+1];//1 means neutral ,2 means valid
        int count =0;
        for(int i =1;i<=n;i++ ){
            if(i<10){
                if(i==2||i==5||i==6||i==9) {
                    dp[i]=2; count++;
                }
                else if(i==0||i==1||i==8) dp[i]=1;
            }else{
                int a = dp[i/10]; int b = dp[i%10];
                if(a==1&&b==1) dp[i]=1;
                else if(a>=1 && b>=1){
                    count++;dp[i]=2;
                }
            }
        }
        return count;

    }

    public static void main(String[] args){
        System.out.println(solution(10));
        System.out.println(solution2(10));
    }


}
