package others;
public class closesttime {

    String ans = "";

    public static String nextClosestTime(String time) {
        int[] digits = new int[time.length()-1];
        boolean[] visiting = new boolean[time.length()-1];

        int idx =0 ;
        for(char c: time.toCharArray()){
            if(c!=':'){
                digits[idx]=c-'0';
                idx++;
            }
        }

        int start = (digits[0]*10+digits[1])*60 + digits[2]*10+digits[3];
        int min = 24*60;

        String ans=new String(time);

        for(int i=0;i<4;i++){
            int hour = digits[i];
            visiting[i]=true;
            for(int i2=0;i2<4  ;i2++){
                if(visiting[i2]) continue;

                hour = hour*10+digits[i2];
                if(hour>=24 ) continue;
                visiting[i2]=true;
//                if(hour<24 ) { //if continue then visiting[] should be fixed

                    for (int i3 = 0; i3 < 4 ; i3++) {
                        if(visiting[i3]) continue;
                        visiting[i3] = true;
                        int minute = digits[i3];

                        for (int i4 = 0; i4 < 4 ; i4++) {
                            if(visiting[i4]) continue;
                            minute = minute * 10 + digits[i4];
                            if (minute >= 60) continue;
                            int curTime = hour * 60 + minute;
                            int diff = Math.floorMod((curTime - start), 24 * 60);
                            if (diff > 0 && diff < min) { //==0 means diff is 24*60 the max value ,so ans remains unchanged

                                ans = new String(String.valueOf(hour) + ":" + String.valueOf(minute));
                                min = diff;
                            }

                        }
                        visiting[i3] = false;

                    }
//                }
                visiting[i2]=false;
            }
            visiting[i]=false;
        }
        return ans;
    }

    public static void main(String[] args){
        System.out.println(nextClosestTime("23:51"));
    }


}
