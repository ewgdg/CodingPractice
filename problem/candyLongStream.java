import java.util.Stack;

public class candyLongStream {
    //lc 135 distribute candies , follow up what if the input is a long stream see solution3 the best so far
    public static long solution(int[] ratings){
        if(ratings.length==0) return 0;
        //since long stream , we can only record count candy number without candy for each student
        long count = 0;
        int up=0;
        int down =0;
        int prev_slope=0;
        int cur_slope=0;

        int peak=1;
        count +=peak;

        for(int i=1;i<ratings.length;i++){
            cur_slope = ratings[i]>ratings[i-1]? 1:ratings[i]<ratings[i-1]?-1:0;

            if(cur_slope!=prev_slope){
                if(up>0){
                    count+=up*(up+1)/2+up+1-1;
                }
                if(down>0){
                    count+=down*(down+1)/2;
                    if(down>=peak) count += down-peak+1;
                    peak=1;
                }
                up=0;
                down=0;
            }
//            if( (cur_slope==0 && prev_slope>0)  || (prev_slope<0 && cur_slope>=0)  ){
//                count += up*(up+1)/2 + down*(down+1)/2 + Math.max(down,up)+1-1;
//                up=0;
//                down=0;
//
//            }

            if(cur_slope>0){
                up++; peak=up+1;
            }else if(cur_slope<0){
                down++;
            }else{
                count++;
                peak=1;
            }

            prev_slope=cur_slope;
        }
//        count += up*(up+1)/2 + down*(down+1)/2 + Math.max(down,up)+1;//+1 for first child extra one

        if(up>0){
            count+=up*(up+1)/2+up;
        }
        if(down>0){
            count+=down*(down+1)/2;
            if(down>=peak) count += down-peak+1;
            peak=1;
        }

        return count;

    }

    //simple accumulating method, best method
    public static int solution3(int[] ratings){
        if(ratings.length==0) return 0;

        int count=0;
        int peakIndex=0;

        int prev =1;
        count+=prev;
        for(int i=1;i<ratings.length;i++){
            if(ratings[i]>ratings[i-1]){
                prev++;
                count+=prev;
                peakIndex=i;
            }else if(ratings[i]<ratings[i-1]){
                prev--;
                count+=prev;
                //if i is last element of decreasing
                if(i+1>=ratings.length || ratings[i+1]>=ratings[i] ) {
                    int diff = 1 - prev;
                    int len = i - peakIndex;
                    if (prev < 1) {
                        len+=1;
                    }
                    count += len * diff;
                    prev = 1;

                }
            }else{
                prev=1;
                count+=prev;
                peakIndex=i;
            }


        }
        return count;
    }




    //normal method
    public static int solution2(int[] ratings) {
        if(ratings.length==0) return 0;
        int[] candies = new int[ratings.length];
        candies[0]=1;
        Stack<Integer> stack = new Stack<>(); // dont need stack , just reverse search decreasing rating

        for(int i =1 ;i<ratings.length; i++){
            if(ratings[i]<ratings[i-1]){
                stack.push(i);
                candies[i]=1;
            }else if(ratings[i]>ratings[i-1]){
                candies[i] = candies[i-1]+1;
            }else{
                candies[i]=1;
            }

        }

        for(int i= ratings.length-1; i>0 ; i--){
            if(ratings[i-1] > ratings[i]){
                if(candies[i-1] <= candies[i]){
                    candies[i-1] = candies[i]+1;
                }
            }
        }

//         while(!stack.isEmpty()){
//             int index = stack.pop();
//             if(candies[index-1] <= candies[index]){
//                 candies[index-1] = candies[index]+1;
//             }

//         }

        int res=0;
        for(int num: candies){
            res+=num;
        }


        return res;
    }


    public static void main(String[] args){
        System.out.println(solution(new int[]{1,2,3,1,2,2,2,2,1,3,4,3,3,42,2,1,2,2,3,4,5,1,2,3,4,5,3,2,1,5,5,5,4,3,2,1,1,1}));
        System.out.println(solution2(new int[]{1,2,3,1,2,2,2,2,1,3,4,3,3,42,2,1,2,2,3,4,5,1,2,3,4,5,3,2,1,5,5,5,4,3,2,1,1,1}));
        System.out.println(solution3(new int[]{1,2,3,1,2,2,2,2,1,3,4,3,3,42,2,1,2,2,3,4,5,1,2,3,4,5,3,2,1,5,5,5,4,3,2,1,1,1}));
    }

}
