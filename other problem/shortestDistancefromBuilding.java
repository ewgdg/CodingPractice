import java.util.LinkedList;
import java.util.Queue;

public class shortestDistancefromBuilding {
    public int shortestDistance(int[][] grid) {
        int m =  grid.length;
        int n = m ==0? 0: grid[0].length;
        if(m==0||n==0) return -1;

        int house_count=0;
        int[][] dist = new int[m][n];
        int[][] reach_count = new  int[m][n];



        for(int i=0 ; i< m; i++){
            for(int j =0 ; j < n ; j++){
                if(grid[i][j] ==1 )
                    house_count++;
            }
        }


        for(int i=0 ; i< m; i++){
            for(int j =0 ; j < n ; j++){
                if(grid[i][j] ==1 ){
                    if(!bfs(grid,reach_count,dist,house_count,i,j,m,n)){
                        return -1;
                    }
                }

            }
        }

        int min = -1;

        for(int i=0 ; i< m; i++){
            for(int j =0 ; j < n ; j++){
                if(reach_count[i][j] == house_count){
                    if(min==-1) min=dist[i][j];
                    else min = Math.min(min, dist[i][j]);
                }
            }
        }

        return min;


    }
    public int[][] dirs = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

    public boolean bfs(int[][] grid, int[][] reach_count, int[][] dist, int house_count ,int i1, int j1 , int m , int n ){


        boolean[][] visited = new boolean[m][n];
        Queue<int[]> open = new LinkedList<>();

        visited[i1][j1] =true;

        for(int[] dir : dirs){
            int[] next= new int[]{ i1 + dir[0] , j1+dir[1] } ;
            if(next[0]>=0 && next[0] < m && next[1] >=0 && next[1] < n && !visited[next[0]][next[1]]){
                open.add(next);
            }
        }

        int count =1;
        int level =1;
        while(!open.isEmpty()){

            int size = open.size();
            //level by level
            for(int i=0; i< size; i++){
                int[] cur = open.poll();
//                visited[cur[0]][cur[1]]=true;

                int cell =grid[cur[0]][cur[1]];
                if(cell==1 ){
                    count++;

                }
                else if (cell==0 ){
                    dist[cur[0]][cur[1]] += level; //accumulate
                    reach_count[cur[0]][cur[1]] ++;

                    //add next level
                    for(int[] dir : dirs){
                        int[] next= new int[]{ cur[0] + dir[0] , cur[1]+dir[1] } ;
                        if(next[0]>=0 && next[0] < m && next[1] >=0 && next[1] < n && !visited[next[0]][next[1]]){
                            visited[next[0]][next[1]]=true;
                            open.add(next);
                        }
                    }

                }



            }
            level++;

        }



        return count==house_count;



    }

    public static void main(String[] args){
        System.out.println( new shortestDistancefromBuilding().shortestDistance(new int[][]{{1,0,2,0,1},{0,0,0,0,0},{0,0,1,0,0}}));


    }
}
