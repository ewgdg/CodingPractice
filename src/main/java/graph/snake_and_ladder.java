package graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class snake_and_ladder {
    public static int snakesAndLadders(int[][] board) {
        int n = board.length;
        if(n<=1) return 0;
        int goal = n*n;
        //bfs
        Queue<Integer> open = new LinkedList<>();
        HashSet<Integer> seen = new HashSet<>();
        seen.add(1);
        open.add(1);
        int cost=0;
        while(!open.isEmpty()){
            int size = open.size();
            for(int i=0;i<size;i++){

                int cur = open.poll();
                if(cur==goal) return cost;


                for(int j=1;j<=6;j++){
                    int next = cur+j;
                    //notice !!!  that we cannot !!!!check!!!! visited or add visited here, because we might jump to new place once
                    //this 'next' might be a visited node , but the jumped val can be not visited
                    //we follow such a rule for jumped game , checked jumped node and seen jumped node only
                    if(next>goal) continue;


                    int[] coord = getCoord(next,n);
                    int snake= board[coord[0]][coord[1]];
                    if(snake!=-1 ){
                        //reset next
                        next= snake;
                    }

                    if(!seen.contains(next)){
                        seen.add(next);
                        open.add(next);
                    }




                }
            }
            cost++;
        }
        return cost;

    }

    public static int[] getCoord(int num, int n){
        num--;//change to 0 based;
        int row = num/n;
        boolean forward= ((row&1)==0) ;
        row = n-1-row;

        int col = num%n;
        if(!forward) col=n-1-col;

        return new int[]{row,col};

    }

    public static void main(String[] args){

        int[][] board = new int[][]{{-1,-1,-1,46,47,-1,-1,-1},{51,-1,-1,63,-1,31,21,-1},{-1,-1,26,-1,-1,38,-1,-1},{-1,-1,11,-1,14,23,56,57},{11,-1,-1,-1,49,36,-1,48},{-1,-1,-1,33,56,-1,57,21},{-1,-1,-1,-1,-1,-1,2,-1},{-1,-1,-1,8,3,-1,6,56}};


        System.out.println(snakesAndLadders(board));
    }
}
