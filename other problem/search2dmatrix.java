public class search2dmatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        //binary search


        int m = matrix.length;
        int n = m==0?0:matrix[0].length;

        if(m==0||n==0) return false;

        int left =0;
        int right = m-1;
        while (left<right){
            int mid = left + (right-left)/2;
            if(target  > matrix[mid][0] ){
                left = mid+1;
            }else if(target < matrix[mid][0]){
                right = mid;
            }else{
                return true;
            }

        }
        int row =0;
        if(target < matrix[left][0])
            row= left-1 ;
        else{
            row= left;
        }

        if(row<0) return false;


        left = 0; right = n-1;
        while(left<right){
            int mid = left + (right-left)/2;
            if(matrix[row][mid]  < target ){
                left = mid+1;
            }else if(matrix[row][mid]  > target ){
                right = mid-1;
            }else{
                return true;
            }
        }
        return false;
        // return matrix[row][left] == target;

    }

    public static void main(String[] args){

        boolean a = new search2dmatrix().searchMatrix(new int[][]{{1}},1);
        System.out.println(a);
    }
}
