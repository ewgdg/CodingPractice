package graph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class MazeII {

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {

        HashSet<Node> seen =  new HashSet<>(); //can use hashmap to store the cost as well to compare

        Queue<State> queue = new PriorityQueue<State>();
        int m= maze.length;
        int n = m==0?0:maze[0].length;
        if(m==0 || n==0) return -1;

        //init
        State s = new State(start[0],start[1],0);
        // seen.add(s.node);
        queue.add(s);
        while(!queue.isEmpty()){
            State cur = queue.poll();
            if(!seen.contains(cur.node)){ //check visited after poll bc we need to sort it first
                seen.add(cur.node);
            }else{
                continue;
            }

            if(isGoal(cur,destination)){
                return cur.cost;
            }


            for(int[] dir : dirs){
                int i= cur.node.i;//+dir[0];
                int j = cur.node.j;//+dir[1];
                // System.out.println(i+":"+j);
                int dist = 0;
                while(i>=0 && i< m && j>=0 && j<n && maze[i][j]!=1 ){
                    i+=dir[0];
                    j+=dir[1];
                    dist++;
                }
                i-=dir[0];
                j-=dir[1];
                dist--;

                State next = new State(i,j,cur.cost+dist);
                // if(!seen.contains(next.node)){
                queue.add(next);
                // seen.add(next.node);
                // }

            }
        }
        return -1;




    }
    static int[][] dirs = new int[][]{{0,1},{1,0},{-1,0},{0,-1}};

    public boolean isGoal(State state,int[] destination){
        return state.node.i==destination[0] && state.node.j==destination[1];
    }

    class Node{
        int i,j;
        public Node(int i, int j){
            this.i=i;this.j=j;
        }

        public boolean equals(Object obj){
            if(obj==null) return false;
            if(obj.getClass() != getClass()) return false;

            Node that  = (Node)obj;
            return this.i==that.i && this.j==that.j;

        }

        public int hashCode(){
            return i*31+j;
        }
    }
    class State implements Comparable<State>{
        public Node node;
        int cost;
        public State(int i , int j , int cost){
            this.node = new Node(i,j);
            this.cost=cost;
        }
        public int compareTo(State that){
            return this.cost-that.cost;
        }
    }


}
