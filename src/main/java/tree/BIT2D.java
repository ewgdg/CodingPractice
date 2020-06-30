package tree;

public class BIT2D {
    int[][] nums;
    int[][] tree;

    int m,n;

    public BIT2D(int m , int n){
        this.m=m;
        this.n=n;
        nums= new int[m][n];
        tree = new int[m+1][n+1];
    }

    public BIT2D(int[][] vals){
        this(vals.length, vals.length==0?0:vals[0].length);

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                update(i,j,vals[i][j]);
            }
        }

    }

    public void update(int i, int j, int val){
        int diff = val-nums[i][j];
        nums[i][j] = val;
        i++; j++;
        while(i<=m){

            int k=j;
            while(k<=n){

                tree[i][k]+=diff;
                k+=(-k&k);
            }
            i+= -i&i;
        }



    }

    public int getSum(int i, int j){
        i++; j++;

        int sum=0;
        while(i>0){

            int j2=j;
            while(j2>0){
                sum+=tree[i][j2];

                j2-=-j2&j2;
            }
            i-=-i&i;
        }
        return sum;
    }


    public static void main(String[] args){
        BIT2D tree = new BIT2D(new int[][]{{1,2,3},{3,4,5},{22,22,22}});

        System.out.println(tree.getSum(0,0));
        System.out.println(tree.getSum(1,1));
        tree.update(0,1,102);
        System.out.println(tree.getSum(0,0));
        System.out.println(tree.getSum(1,1));
        System.out.println(tree.getSum(2,1));
    }
}
