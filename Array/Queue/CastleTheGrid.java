package Queue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


//You are given a square grid with some cells open (.) and some blocked (X).
// Your playing piece can move along any row or column until it reaches the edge of the grid or a blocked cell.
// Given a grid, a start and an end position, determine the number of moves it will take to get to the end position.
// any move along the same dir will be count as 1 cost

public class CastleTheGrid {


    static class Node{
        int x,y;
        public Node(int x, int y){
            this.x=x;
            this.y=y;
        }
        public int hashCode(){
            return x*31+y;
        }
        public boolean equals(Object obj){
            if(obj==null) return false;
            if(getClass()!=obj.getClass()) return false;
            Node that = (Node)obj;
            return that.x==this.x && that.y==this.y;
        }
    }

    // Complete the minimumMoves function below.
    static int minimumMoves(String[] grid, int startX, int startY, int goalX, int goalY) {
        HashSet<Node> seen = new HashSet<>();
        Queue<Node> open = new LinkedList<>();
        int n = grid.length;

        int[][] dirs= new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        open.add(new Node(startX,startY));
        int cost =0;
        while(!open.isEmpty()){
            int size= open.size();
            for(int i =0;i<size;i++){
                Node cur = open.poll();
                if(seen.contains(cur) ) continue;
                if(cur.x==goalX && cur.y==goalY){
                    return cost;
                }
                seen.add(cur);

                for(int[] dir : dirs){
                    int nextx=cur.x;
                    int nexty=cur.y;
                    while( nextx<n && nexty<n &&nextx>=0 && nexty>=0  && grid[nextx].charAt(nexty)!='X'   ){
                        nextx+=dir[0];
                        nexty+=dir[1];
                        Node next= new Node(nextx,nexty);
                        open.add(next);
                    }
                    // nextx-=dir[0];
                    // nexty-=dir[1];

                    // Node next= new Node(nextx,nexty);
                    // open.add(next);
                }
            }
            cost++;

        }
        return -1;


    }
}
