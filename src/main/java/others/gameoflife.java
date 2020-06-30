package others;

public class gameoflife {
    public enum state {
        //can use binary
        //first bit prev
        //second bit as cur state
        //&1 to get first
        //use >> to at last to proceed to next ops
    }

    public static void gameOfLife(int[][] board) {
        int m = board.length;
        int n = m == 0 ?   0:board[0].length;

        for (int i = 1; i < m - 1; ++i) {
            int right = Math.min(n-1,1);
            int prev_right = (board[i - 1][right]&1) + (board[i][right]&1) + (board[i + 1][right]&1);
            int prev_middle = (board[i - 1][0]&1) + (board[i][0]&1) + (board[i + 1][0]&1);
            for (int j = 1; j < n - 1; ++j) {
                int count = 0;
                int cur_right = 0;


                for (int i2 = i - 1; i2 < i + 2; ++i2) {
                    cur_right += (board[i2][j + 1]&1);
                }

                count = prev_right + prev_middle + cur_right;
                if (((count - board[i][j]) | (board[i][j]&1) ) == 3) {
                    board[i][j] |= 0b010;
                }

                prev_middle = prev_right;
                prev_right = cur_right;
            }
        }

        //handle border
        for (int i = 1; i < m - 1; ++i) {
            int j = 0;
            int count = 0;
            for (int i2 = Math.max(i - 1, 0); i2 < Math.min(i + 2, m); ++i2) {
                for (int j2 = Math.max(j - 1, 0); j2 < Math.min(j + 2, n); ++j2) {
                    count += (board[i2][j2]&1);
                }
            }
            if (((count - board[i][j]) | (board[i][j]&1) ) == 3) {
                board[i][j] |= 0b010;
            }

            if(j!=n-1) {
                j = n - 1;
                count = 0;
                for (int i2 = Math.max(i - 1, 0); i2 < Math.min(i + 2, m); ++i2) {
                    for (int j2 = Math.max(j - 1, 0); j2 < Math.min(j + 2, n); ++j2) {
                        count += (board[i2][j2]&1);
                    }
                }
                if (((count - board[i][j]) | (board[i][j]&1) ) == 3) {
                    board[i][j] |= 0b010;

                }
            }


        }


        for(int j=0;j<n;++j) {
            int i = 0;
            int count = 0;
            for (int i2 = Math.max(i - 1, 0); i2 < Math.min(i + 2, m); ++i2) {
                for (int j2 = Math.max(j - 1, 0); j2 < Math.min(j + 2, n); ++j2) {
                    count += (board[i2][j2]&1);
                }
            }
            if (((count - board[i][j]) | (board[i][j]&1) ) == 3) {
                board[i][j] |= 0b010;
            }

            if(i!=m-1) {
                i = m - 1;
                count = 0;
                for (int i2 = Math.max(i - 1, 0); i2 < Math.min(i + 2, m); ++i2) {
                    for (int j2 = Math.max(j - 1, 0); j2 < Math.min(j + 2, n); ++j2) {
                        count += (board[i2][j2]&1);
                    }
                }
                if (((count - board[i][j]) | (board[i][j]&1) ) == 3) {
                    board[i][j] |= 0b010;
                }
            }
        }

        //restore current state
        for(int i = 0 ;i<m;++i){
            for(int j=0;j<n;++j){
                board[i][j]>>=1;
            }
        }


    }


    //simple method
    // int m = board.size(), n = m ? board[0].size() : 0;
    //    for (int i=0; i<m; ++i) {
    //        for (int j=0; j<n; ++j) {
    //            int count = 0;
    //            for (int I=max(i-1, 0); I<min(i+2, m); ++I)
    //                for (int J=max(j-1, 0); J<min(j+2, n); ++J)
    //                    count += board[I][J] & 1;
    //            if (count == 3 || count - board[i][j] == 3)
    //                board[i][j] |= 2;
    //        }
    //    }
    //    for (int i=0; i<m; ++i)
    //        for (int j=0; j<n; ++j)
    //            board[i][j] >>= 1;
}
