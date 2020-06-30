package Array;
import java.util.Arrays;

public class maxSumfromLeftAndMainView {
    //用一个二维矩阵表示一个城市， 每个cell代表的是一栋楼的高度
    //例如
    //2 1 1
    //1 1 1
    //1 1 1

    //然后给两个数列 一个代表这个城市的主视图 一个代表这个城市的左视图
    //比如这个题的例子就是[2,1,1]和[2,1,1]
    //Q：现在只知道这个城市的主视图和左视图两个数列， 求这个城市的!!!最大!!!!体积.

    //brutal force, 每个pos i,j 比较 left[i] main[j],取min， 最后sum整个matrix
    //nlogn， sort main view left view. 不影响最后sum
    //每个row 找到对应的 j such that main[j] > left[i]

    //row0   j1
    //row1      j2

    //sum each row based on prefix sum and left[i]

    public static int solution(int[] leftView, int[] mainView){
        int m = leftView.length;
        int n = mainView.length;

        Arrays.sort(leftView);
        Arrays.sort(mainView);

        int j =0;
        int prefixSum=0;
        int res=0;
        for(int i =0;i<m;i++){
            int leftVal = leftView[i];
            while(j<n && mainView[j] <leftVal ){
                prefixSum+=mainView[j];
                j++;
            }
            int count = n-j;
            res+=prefixSum+count*leftVal;
        }

        return res;


    }


    public static void main(String[] args){
        //1 1 1
        //1 3 4
        //1 3 5
        System.out.println(solution(new int[]{1,3,5}, new int[]{1,4,5}));

    }







}
