import java.util.Arrays;

public class ConnectToRoof {

//    白人小哥。给个2维数据，如下，1 1 1 1 1
//0 0 1 0 1
//0 0 1 0 1. 牛人云集,一亩三分地
//0 0 1 0 0
//
//第一行作为roof。移除其中某一个‘1’，没有连接到roof的‘1’会掉落，需要置为‘0’。 input是matrix和要删掉的点，output删掉那一点后的matrix。我用的uf，一开始想用dfs，后来又转用uf，耽误了时间，做完后问了时间复杂度。估计是跪了，心情不好，求大米。

    static class State{
        int i,j;

        public State(int i , int j){
            this.i = i;
            this.j=j;
        }
    }

    public static int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
    public static int[][] solutiton(int[][] mat, int i, int j){
        int m = mat.length;
        int n = m==0? 0: mat[0].length;
        if(m==0) return mat;

        mat[i][j] =  0;

        for(int i1=0, j1=0;j1<n;j1++){
            dfs(mat,i1,j1,m,n);
        }


        for(int i1=0;i1<m;i1++){
            for(int j1=0;j1<n;j1++){
                if(mat[i1][j1]==2){
                    mat[i1][j1]=1;
                }else{
                    mat[i1][j1]=0;
                }

            }
        }

        return mat;
    }

    public static void dfs(int[][] mat , int i , int j, int m, int n){
        if(mat[i][j]!=1) return;

        mat[i][j] = 2; //marked

        for(int[] dir : dirs){
            int i2 = i+dir[0];
            int j2 = j+dir[1];
            if(i2>=0 && j2>=0 && i2<m && j2<n ){
                if(mat[i2][j2]==1 ){
                    dfs(mat,i2,j2,m,n);
                }
            }


        }

    }

    public static void main(String[] args){
        System.out.println(Arrays.deepToString( solutiton( new int[][]{ {1,1,1,1},{1,1,1,1},{1,1,1,1},{0,1,0,1}  }, 2,1 )  ));
    }


}
