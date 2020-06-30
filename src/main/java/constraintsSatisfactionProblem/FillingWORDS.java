package constraintsSatisfactionProblem;

public class FillingWORDS {

    //A  10X10 Crossword grid is provided to you,
    // along with a set of words (or names of places) which need to be filled into the grid.
    // Cells are marked either + or -. Cells marked with a - are to be filled with the word list.
    //


    //assign each room with word ??? not easy to do
    //assign word with correct pos and dir??


    public static boolean plainPropagator(String word, char[][] board, int i, int j, int[] dir){

        if(dir[1]==1) {
            //hori dir
            int k=0;
            for ( k = 0; j + k < board[i].length && k < word.length(); k++) {
                if (board[i][j + k] != '-' && board[i][j + k] != word.charAt(k)) {

                    break;
                }
            }

            if (k==word.length() ) {//fill the board
                for ( k = 0; j + k < board[i].length && k < word.length(); k++) {
                    board[i][j + k] = word.charAt(k);
                }
                return true;
            }
        }else {
            //test veriti
            int k=0;
            for ( k = 0; i + k < board.length && k < word.length(); k++) {
                if (board[i + k][j] != '-' && board[i + k][j] != word.charAt(k)) {

                    break;
                }
            }
            if (k==word.length()) {//fill the board
                for ( k = 0; i + k < board.length && k < word.length(); k++) {
                    board[i + k][j] = word.charAt(k);
                }
                return true;
            }
        }
        return false;

    }
    public void unassign(int i,int j, char[][] board, int[] dir, int len){

        for(int k=0;k<len;k++){
            board[i+dir[0]*k][j+dir[1]*k] = '-';
        }

    }
    public boolean bt(int index, String[] words, char[][] board){
        if(index==words.length){
            //found solution
            return true;
        }
        String word = words[index];

        int[][] dirs = new int[][]{{0,1},{1,0}};
        for(int i =0; i<board.length ;i++){  //for each of the possible val in scope
            for(int j=0;j<board[0].length;j++){
                char old = board[i][j];
                for(int[] dir: dirs) {
                    if (plainPropagator(word, board, i, j, dir)) {
                        if (bt(index + 1, words, board)) {
                            return true;
                        } else {
                            //continue search sol;
                            //unassign var

                            unassign(i,j,board,dir,word.length());
                            board[i][j]=old;
                        }
                    }
                }



            }
        }






        return false;





    }

    public void solve(char[][] board, String[] words){
        bt(0,words,board);
    }


}
