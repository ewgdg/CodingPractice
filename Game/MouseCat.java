import java.util.HashMap;
import java.util.HashSet;

public class MouseCat {
//A game on an undirected graph is played by two players, Mouse and Cat, who alternate turns.
//
//The graph is given as follows: graph[a] is a list of all nodes b such that ab is an edge of the graph.
//
//Mouse starts at node 1 and goes first, Cat starts at node 2 and goes second, and there is a Hole at node 0.
//
//During each player's turn, they must travel along one edge of the graph that meets where they are.  For example, if the Mouse is at node 1, it must travel to any node in graph[1].
//
//Additionally, it is not allowed for the Cat to travel to the Hole (node 0.)
//
//Then, the game can end in 3 ways:
//
//If ever the Cat occupies the same node as the Mouse, the Cat wins.
//If ever the Mouse reaches the Hole, the Mouse wins.
//If ever a position is repeated (ie. the players are in the same position as a previous turn, and it is the same player's turn to move), the game is a draw.
//Given a graph, and assuming both players play optimally, return 1 if the game is won by Mouse, 2 if the game is won by Cat, and 0 if the game is a draw.


    public int catMouseGame(int[][] graph) {

        int n =0;
        for(int[] children : graph){
            n+= children.length;
        }

//        System.out.println("------------");
//        System.out.println(n);
//        System.out.println(graph.length);
//        System.out.println("------------");

        HashSet<State> seen = new HashSet<State>();
        HashMap<String,Integer> mem = new HashMap<>();

        State start = new State(1,2,0);
        seen.add(start);

        int res = minMax(0,graph,start,seen, Integer.MIN_VALUE, Integer.MAX_VALUE,0,graph.length, mem);
        //notice that mem must record level because for different level the seen path will changed


//        System.out.println(res);
        if(res>0) return 1;
        if(res<0) return 2;
        return 0;

    }

    public int minMax(int player, int[][] graph, State state,HashSet<State> seen, int alpha, int beta, int level, int n, HashMap<String,Integer> mem ){


        //terminating

        if(level>n) return 0; //wrong cond if n = n+1??? ,  will trigger state.mousePos==0
        if(state.mousePos == state.catPos){ //evaluate from player 0's perspective , mouse is max player.
            return -1;//cat wins , min player win
        }else if( state.mousePos==0  ){ //0 is mouse
            return 1;
        }

//        if(state.mousePos==5 && state.catPos==2&&player==1){
//            System.out.println("here");
//        }
        // else if(prevPos.contains(pos)){
        //     return 0;
        // }

        String key = level+":"+state;
//        System.out.println(key);
//        assert(player == state.player);
        if(mem.containsKey(key)){
            return mem.get(key);
        }

        //int res = 0;
        if(player == 0){
            for(int child : graph[state.mousePos]  ){
                State next =new State(child,state.catPos,state.player^1);

                if(!seen.contains(next)){

//                    seen.add(next);
                    alpha = Math.max(alpha, minMax(player^1,graph,next,seen,alpha,beta,level+1,n,mem) );
//                    if(level==0)
//                        System.out.println("alpha: "+alpha +" state: "+state +" move: "+ child);

                    //reset prevPos
                    seen.remove(next);
                }else{
                    alpha = Math.max(alpha,0);
                }

//                if(alpha>=beta) break;

            }

            mem.put(key,alpha);
            return alpha;
        }else{
            //min player
            for(int child : graph[state.catPos]  ){
                if(child==0) continue; //cat cannot travel hole
                State next =new State(state.mousePos,child,state.player^1);
                if(!seen.contains(next)){
//                    seen.add(next);
                    beta = Math.min(beta, minMax(player^1,graph,next,seen,alpha,beta,level+1,n,mem) );

                    //reset prevPos
                    seen.remove(next);
                }else{//evaluate 0
                    beta = Math.min(beta,0);
                }
//                if(alpha>=beta) break;
            }
//            if(level==1)
//                System.out.println("beta: "+beta +" state: "+state);
            mem.put(key,beta);
            return beta;
        }



    }

    class State{
        int player;
        int mousePos;
        int catPos;
        public State(int m , int c, int p){
            mousePos=m; catPos=c;player=p;
        }
        public boolean equals(Object obj){
            State that = (State)obj;
            return this.mousePos==that.mousePos && this.catPos==that.catPos && this.player==that.player;
        }
        public int hashCode(){
            return mousePos*31+catPos*17+player;
        }
        public String toString(){
            return mousePos+":"+catPos+":"+player;
        }
    }
    public static void main(String[] args){
        MouseCat solver = new MouseCat();

        System.out.println( solver.catMouseGame(new int[][]{{3,4,6,7,9,15,16,18},{4,5,8,19},{4,5,6,7,9,18},{0,10,11,15},{0,1,2,6,10,12,14,16},{1,2,7,9,15,17,18},{0,2,4,7,9,10,11,12,13,14,15,17,19},{0,2,5,6,9,16,17},{1,9,14,15,16,19},{0,2,5,6,7,8,10,11,13,15,16,17,18},{3,4,6,9,17,18},{3,6,9,12,19},{4,6,11,15,17,19},{6,9,15,17,18,19},{4,6,8,15,19},{0,3,5,6,8,9,12,13,14,16,19},{0,4,7,8,9,15,17,18,19},{5,6,7,9,10,12,13,16},{0,2,5,9,10,13,16},{1,6,8,11,12,13,14,15,16}}));
        System.out.println(solver.catMouseGame(new int[][]
                {{1,6,7,8,17,19},{0,6,9,16},{4,10,14,17,18},{5,8,10,11,14,19},{2,5,9,12,15,17,18,19},{3,4,6,7,18},{0,1,5,12,13,14,15,18,19},{0,5,11},{0,3,11,12,13},{1,4,10,16},{2,3,9,11},{3,7,8,10,12,13},{4,6,8,11,13,18},{6,8,11,12,15,17,18},{2,3,6},{4,6,13,18},{1,9},{0,2,4,13},{2,4,5,6,12,13,15,19},{0,3,4,6,18}}));

    //expect 2
        System.out.println(solver.catMouseGame(new int[][]{{2,4,7,8},{2,3,4,5,7,8,9},{0,1,3,4,5,7,8,9},{1,2,5},{0,1,2,5,6,7,9},{1,2,3,4,6,7},{4,5,8,9},{0,1,2,4,5,9},{0,1,2,6,9},{1,2,4,6,7,8}}));



    //0
        System.out.println(solver.catMouseGame(new int[][]{{2,5},{3},{0,4,5},{1,4,5},{2,3},{0,2,3}}));

//        System.out.println(solver.catMouseGame(new int[][]
//                {{9,16,17,29,32,43},{12,16,20,32,48},{8,12,16,21,24,43,45,46},{8,10,16,27},{5,8,10,13,21,25,26,37,48},{4,7,12,23,28,33,35,48},{10,20,22,25,26,31,45,46,47},{5,10},{2,3,4,30,41},{0,17,29},{3,4,6,7,21,23,25,29,38},{22,35,41},{1,2,5,17,19,22,28,31,44},{4,15,16,23,33,39,40,44,49},{19,23,24,26,27,34,45,46,47},{13,26,31,32,37,44,45},{0,1,2,3,13,32,34,49},{0,9,12,19,20,32,36},{38},{12,14,17,21,30,42},{1,6,17,21,23,36,38,45},{2,4,10,19,20,31,45,47},{6,11,12,25,26,27,48},{5,10,13,14,20,44,48},{2,14,26,29,34},{4,6,10,22,30,40,47},{4,6,14,15,22,24,30,43,49},{3,14,22,34,35,43,44},{5,12,43,48},{0,9,10,24,33,47},{8,19,25,26,37,40,42,48},{6,12,15,21,37,39,42,44,49},{0,1,15,16,17,49},{5,13,29,35,43,47},{14,16,24,27,36,38},{5,11,27,33,42,48},{17,20,34,40},{4,15,30,31,45,46},{10,18,20,34},{13,31,40},{13,25,30,36,39,41,42,46,47},{8,11,40,43,45},{19,30,31,35,40},{0,2,26,27,28,33,41},{12,13,15,23,27,31,49},{2,6,14,15,20,21,37,41,46,49},{2,6,14,37,40,45},{6,14,21,25,29,33,40},{1,4,5,22,23,28,30,35},{13,16,26,31,32,44,45}}));
    }
}
