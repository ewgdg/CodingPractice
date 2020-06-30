package others;
import javax.management.ImmutableDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingPuzzle {
    static class State {
        public String board;
        public int indexZero;

        public State(String board, int index){
            this.board =board;
            this.indexZero=index;
        }
        @Override
        public boolean equals(Object that){
            State another = (State)that;
            return another.board.equals(this.board);
        }
        @Override
        public int hashCode(){
            return board.hashCode();
        }

    }

    public static boolean isGoal(State state){
        return state.board.equals("123450");
    }

    public static int slidingPuzzle(int[][] board) {
        int index =0;
        int m = board.length;
        int n = m==0? 0:board[0].length;
        StringBuilder b = new StringBuilder();


        for(int i=0; i<board.length;i++){
            for(int j = 0;j<board[i].length;j++){
                int num = board[i][j];
                if(num==0) index= i*n+j;
                b.append(num);
            }
        }

        int[] dirs = new int[]{1,-1,3,-3};
        Queue<State> open = new LinkedList<>();
        open.add(new State(b.toString(),index));
        HashSet<State> seen = new HashSet<>();

        int cost =0;
        while(!open.isEmpty()){

            int size = open.size();

            for(int d=0;d<size;d++){
                State cur = open.poll();
                if(isGoal(cur)) return cost;
                int idx0 = cur.indexZero;
                char[] curboard = cur.board.toCharArray();
//                System.out.println(curboard);
                for(int dir : dirs){
                    int nextidx = idx0+dir;
                    if( nextidx <6 && nextidx>=0 && !(idx0==2&&nextidx==3) && !(idx0==3&&nextidx==2) ){
                        curboard[idx0] = curboard[nextidx];
                        curboard[nextidx] = '0';
                        State newState = new State(new String(curboard),nextidx);
                        if(!seen.contains(newState)){
                            seen.add(newState);
                            open.add(newState);
                        }

                        //swap back
                        curboard[nextidx] = curboard[idx0];
                        curboard[idx0] = '0';
                    }


                }


            }
            cost++;
        }
        return -1;

    }

    public static void main(String[] args){
        System.out.println(slidingPuzzle(new int[][]{{4,1,2},{5,0,3}}));

        System.out.println(Arrays.deepEquals( new int[][]{{4,1,2},{5,0,3}}, new int[][]{{4,1,2},{5,0,3}}  ));

        System.out.println(Integer.MAX_VALUE + Integer.MAX_VALUE+100);
    }
}
