package tree.lowestCommonAncestor;

import java.util.Map;

//check ref:
//https://www.topcoder.com/community/competitive-programming/tutorials/range-minimum-query-and-lowest-common-ancestor/
//https://www.hackerrank.com/topics/lowest-common-ancestor
public class LCA_SparseTable {
//    precomputation: NlogN
    // each query : logN


    //build a 2D table  P[][]
    //p[i][j] means 2^jth  ancestor of node i

    //using dp
    //p[i][j] =  p [p[i][j-1]] [j-1]
    Integer[][] p;
    Map<Integer,Integer> parents;
    public void preprocess(int n) {
//        int max_log = Integer.highestOneBit(n-1)+1;
        int max_log = 31- Integer.numberOfLeadingZeros(n-1); //n-1 bc the highest possible level is n-1
        //while( (1<<log) <=n-1){  log++ } ; log--;




        p = new Integer[n][max_log+1];

        //Every element in P[][] is -1 initially;
        for(int i = 1 ; i <= n ; ++i){
            for(int j = 0 ; (1<<j) < n ; ++i){
                p[i][j] = -1;
            }
        }

        //init for j=0
        for(int i =1; i<=n; i++){
            p[i][0] = parents.get(i);
        }


        //at most n-1 ancestor
        for(int j=1; (1<<j)<n ;j++  ){
            for(int i =1;i<=n;i++){
                if(p[i][j-1]!=-1){
                    p[i][j]=p[p[i][j-1]][j-1];
                }
            }
        }




    }
    Map<Integer,Integer> levels;

    public int LCA(int a, int b){
        if(levels.get(a) < levels.get(b)  ){
            //swap
            a= ((b=a)+b)-a;
        }

        //raise to the same level
        int dist = levels.get(a)-levels.get(b);
        while(dist>0){
//            int raised_by  = Integer.highestOneBit(dist); //log2()//wrong highest one bit returns the value of highest one bit instead of pos
            int raised_by = 31-Integer.numberOfLeadingZeros(dist);

            a=p[a][raised_by];
            dist -= (1<<raised_by);
        }

        if(a==b){
            return a;
        }

        //keep searching for lca
        //Untill each node has been raised up till its (k-1)th ancestor
        // Such that the (k)th ancestor is the lca.
        //binary raise / meta binary search

        int log = 0;
        while(  (1<<log)<= levels.get(a) ){
            log++;
        }
        log--;

        for(int j=log; j>=0;j--){

            //if p a== p b ,then the lca is below the cur p
            //else the lca is above the cur p, so we update a= p a , b = p b
            if( p[a][j]!=null && p[a][j] != p[b][j] ){
                a=p[a][j];
                b=p[b][j];
            }
        }

        return parents.get(a);



    }

}
