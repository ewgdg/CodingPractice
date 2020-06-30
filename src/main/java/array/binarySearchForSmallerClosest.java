package array;

public class binarySearchForSmallerClosest {

    public static int solution(int[] nums, int target){
        //to search for smaller than target , we can compare mid prefer right mid when even size

        int lo=0;
        int hi = nums.length-1;

        while(lo<hi){
            int mid = lo+(hi+1-lo)/2;//offset mid by 1 when even size, not mid+1 when odd size

            if(  nums[mid]<=target){//flip op of lo and hi
                lo=mid;
            }else{
                hi=mid-1;
            }


        }

        return lo;


    }

    public static int solution2(int[] nums, int target){
        //to search for smaller than target , we can set hi=mid-1 and return hi , doesnt work bc we might start mid from lo to skip it???? might be fixed by lo<=hi

        int lo=0;
        int hi = nums.length-1;

        while(lo<=hi){//need <= to fix
            int mid = lo+(hi-lo)/2;

            if(  nums[mid]<target){
                lo=mid+1;
            }else if(nums[mid]==target){
                return mid;//or mid-1 //if equal return index
            }
            else{
                hi=mid-1; //hi=mid then find the next index both lo and hi, hi=mid-1 to find the smaller index hi only
                //right=mid might cause infinite loop when left==right!!!!!!!!!!!!!!!!!!!!!
            }


        }

        return hi;//hi represent the index lower than the cur , might lower than lo


    }
    public static int solution3(int[] nums, int target){
        //3 cases < , = , >

        int lo=0;
        int hi = nums.length-1;

        while(lo<hi){
            int mid = lo+(hi-lo)/2;

            if(  nums[mid]<target){
                lo=mid+1;
            }else if(nums[mid]==target){
                return mid;//or mid-1 //if equal return index
            }
            else{
                hi=mid-1; //hi=mid then find the next index both lo and hi, hi=mid-1 to find the smaller index hi only
            }


        }
        if(hi!=-1 && nums[hi]>target) hi--;
        return hi;//hi represent the index lower than the cur , might lower than lo


    }

    //right=mid might cause infinite loop when left==right!!!!!!!!!!!!!!!!!!!!!
    //find larger cloest
    //while(left<=right){
    //            long mid = left+(right-left)/2;
    //
    //            long prod = 0;
    //            for(long p:machines){
    //                prod+= mid/p;
    //            }
    //            if(prod>goal){
    //                right = mid-1;//return right for smaller  //right=mid might cause infinite loop when left==right!!!!!!!!!!!!!!!!!!!!!
    //            }else if(prod<goal){
    //                left = mid+1;//return left for larger prod
    //            }else{
    //                return mid;
    //            }
    //        }
    //        return left;
    public static void main(String[] args){
        System.out.println(solution(new int[]{1,2,5,6,7},0));
        System.out.println(solution2(new int[]{1,2,5,6,7},0));
        //3,14,1,7
        System.out.println(solution(new int[]{3,17,18,25},23));
        System.out.println(solution2(new int[]{3,17,18,25},23));



        System.out.println(solution(new int[]{0,3,17,18},0));
        System.out.println(solution(new int[]{0,3,17,18},1));
        System.out.println(solution(new int[]{0,3,17,18},2));
        for(int i =3;i<14+3;i++)
            System.out.println(solution(new int[]{0,3,17,18},i));


        for(int i =17;i<17+1;i++)
            System.out.println(solution(new int[]{0,3,17,18},i));


        for(int i =18;i<18+7;i++)
            System.out.println(solution(new int[]{0,3,17,18},i));
    }


}
