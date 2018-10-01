import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class visitingAll {
    static class State{//state should include visited nodes to differentiate itself
        public boolean[] visited; //use list so that we can use equals to compare
        public int node;
        public int d;
        public State(boolean[] v, int val){
            visited = v;
            node = val;
        }

        @Override
        public boolean equals(Object another){
            State that = (State)another;
            if(Arrays.equals( that.visited, this.visited  ) && that.node==this.node ){
                return true;
            }
            return false;
        }

        @Override
        public int hashCode(){
            int code =0;
            for(boolean v:visited){
                code= code<<1;
                if(v) code =  code+1;
            }
            code+=node*3;
            return code;
        }

    }
    public static int shortestPathLength(int[][] graph) {
        Queue<State> queue = new LinkedList<>();
        int n = graph.length;

        HashSet<State> set = new HashSet<>();

        //init
        for(int i =0;i<n;i++ ){
            boolean[] visited = new boolean[n];
            visited[i]=true;
            State state = new State(visited, i);
            state.d= 0;

            queue.add(state);
            set.add(state);
        }

        // int dist[][] = new int[n][n];
        // Arrays.fill(dist, n*n);

        boolean[] target = new boolean[n];
        Arrays.fill(target,true);


        //bfs
        while(!queue.isEmpty()){
            State cur = queue.poll();
            if(Arrays.equals(target,cur.visited)){
                return cur.d;
            }
            for(int child: graph[cur.node]){
                boolean[] visited = Arrays.copyOf(cur.visited,n);
                visited[child]=true;
                State state = new State(visited,child);
                state.d = cur.d+1;
                if(!set.contains(state)) {
                    queue.add(state);
                    set.add(state);
                }


            }
        }


        throw null;
    }

    public static void main(String[] args){
        boolean[] v1 = new boolean[5];
        v1[1]=true;

        boolean[] v2 = new boolean[5];
        v2[1]=true;

        State s1 = new State(v1,2);
        State s2 = new State(v2,2);
        System.out.println( s1.equals(s2) );
        System.out.println(shortestPathLength(new int[][]{{1,2,3,4,5,6,7,8,9,10,11},{0,2,3,4,5,6,7,8,9,10,11},{0,1,3,4,5,6,7,8,9,10,11},{0,1,2,4,5,6,7,8,9,10,11},{0,1,2,3,5,6,7,8,9,10,11},{0,1,2,3,4,6,7,8,9,10,11},{0,1,2,3,4,5,7,8,9,10,11},{0,1,2,3,4,5,6,8,9,10,11},{0,1,2,3,4,5,6,7,9,10,11},{0,1,2,3,4,5,6,7,8,10,11},{0,1,2,3,4,5,6,7,8,9,11},{0,1,2,3,4,5,6,7,8,9,10}}));
    }
}
