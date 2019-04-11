public class pickFruit {
    public static int solution(int[] A) {
        // write your code in Java SE 8

        Integer fruitA = null;
        Integer fruitB = null;

        int countA =0;
        int countB =0;

        int left = 0;//boundary for sliding window

        int max =0;

        for(int i =0 ; i<A.length ; i++){


            // max =Math.max(max,countA+countB);

            if(fruitA!=null && fruitA==A[i]){
                countA++;
            }else if(fruitB!=null && fruitB==A[i]){
                countB++;
            }else if(fruitA==null){
                fruitA=A[i];
                countA++;
            }else if(fruitB==null){
                fruitB=A[i];
                countB++;
            }else{// found the third type of fruit

                int sum = countA+countB;
                max =Math.max(max,sum);

                //get rid of one type of fruit
                while(fruitA!=null && fruitB!=null ){
                    if(fruitA==A[left] ){
                        countA--;
                        if(countA==0) fruitA=null;
                    }else if(fruitB==A[left]){
                        countB--;
                        if(countB==0) fruitB=null;
                    }
                    left ++;
                }

                //set the new fruit
                i--;
            }




        }
        max = Math.max(max,countA+countB);
        return max;

        //naive method to verify


//        int max =0;
//        for(int i =0 ; i< A.length; i++){
//
//            Integer fruitA = null;
//            Integer fruitB = null;
//
//            int count = 0;
//            for(int j = i ; j< A.length; j++ ){
//                if(fruitA==null){
//                    fruitA=A[j];
//                }else if(fruitB==null){ //wrong //the fruitB will set to same as fruitA, need to check equality first
//                    fruitB=A[j];
//                }
//
//                if(fruitA==A[j] || fruitB==A[j] ){
//                    count++;
//                }else{
//                    break;
//                }
//            }
//
//            max =Math.max(max,count);
//        }
//        return max;

    }

    public static void main(String[] args){

        int[] l = new int[]{1,2,3,4,5,5,6,6,6,6,6};
        System.out.println(solution(l) );
    }
}
