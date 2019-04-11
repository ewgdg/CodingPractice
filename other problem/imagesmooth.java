public class imagesmooth {
    public int[][] imageSmoother(int[][] M) {
        int m = M.length;
        int n = m==0?0:M[0].length;

        int[][] ans = new int[m][n];

        int[] col = new int[3];
        int[] colc = new int[3];

        for(int i =0 ;i < m ;i++){
            col = new int[3];
            colc = new int[3];
            for(int j =0 ; j< n;j++){

                for(int k = 1;k<3;k++){
                    if(colc[k]==0){
                        colc[k-1]=0 ; col[k-1]=0;
                        int j2=j-1+k-1;
                        for(int i2=i-1; i2<=i+1; i2++){
                            if(isValid(i2,j2,m,n)){
                                col[k-1]+=M[i2][j2];
                                colc[k-1]++;
                            }
                        }

                    }else{
                        colc[k-1] = colc[k];
                        col[k-1] = col[k];
                    }

                }


                //last col
                col[2]=0; colc[2]=0;
                int j2=j+1;
                for(int i2=i-1; i2<=i+1; i2++){
                    if(isValid(i2,j2,m,n)){
                        col[2]+=M[i2][j2];
                        colc[2]++;
                    }
                }

                ans[i][j] = (col[0] +col[1]+col[2])/(colc[0]+colc[1]+colc[2]);

            }

        }
        return ans;
    }

    public boolean isValid(int i, int j ,int m, int n){
        return i>=0&&i<m&&j>=0&&j<n;
    }

    public static void main(String[] args){

        System.out.println(new imagesmooth().imageSmoother(new int[][]{{1,1,1},{1,1,1},{1,1,1}} ) );
    }


}
