package others;
import java.util.HashMap;
import java.util.HashSet;

public class Valid_Sudoku {

    class Info{
        public char val;
        public HashSet<Integer> rows;
        public HashSet<Integer> cols;
        public HashSet<Integer> subboxs;

        public Info(){
            this.rows=new HashSet<>();
            this.cols=new HashSet<>();
            this.subboxs=new HashSet<>();
        }

    }
    public boolean isValidSudoku(char[][] board) {
        HashMap<Character,Info> infos=new HashMap<>();

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]!='.'){
                    Info info;
                    if(infos.containsKey(Character.valueOf(board[i][j]))){
                        info=infos.get(Character.valueOf(board[i][j]));

                    }else{
                        info = new Info();
                        infos.put(Character.valueOf(board[i][j]),info);
                    }
                    if(!info.rows.add(i)||!info.cols.add(j)||!info.subboxs.add((j/3)+(i/3)*3)){
                        return false;
                    }
                }

            }
        }

        return true;
    }
}
