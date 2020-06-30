package array;
public class median2array {
    //2 sorted array
    public static int solution(int[] a, int[] b){

        int l1 = a.length;
        int l2 = b.length;
        int k = (l1+l2)/2; //cannot do l1/2 + l2/2 due to integer division truncate

        //we can recursively or iteratively solve


        int starta=0;
        int startb=0;

        //similar to quick selection idea ,O logk
        while(k>1 && starta<l1 && startb<l2){
            int indexa = Math.min(l1-1 , starta+k/2-1);
            int indexb = Math.min(l2-1, startb+k/2-1);

            if(a[indexa]<b[indexb]){
                //prune a[start] - a[start+k/2-1], more than m+n-k element greater than it , need k-1 smaller , can have at most k-2 smaller

                k -= indexa-starta+1;//notice the order
                starta=indexa+1;//update starta at last

            }else{
                k-= indexb-startb+1;
                startb=indexb+1;

            }


        }

        if(starta==l1){
            return b[startb+k-1];
        }else if(startb==l2){
            return a[starta+k-1];
        }
        else if(k==1 ){
            return Math.min(a[starta],b[startb]);
        }

        return -1;
    }
    public static void main(String[] args){
        System.out.println(solution(new int[]{1,5,10}, new int[]{3,8,9}));
    }

}
