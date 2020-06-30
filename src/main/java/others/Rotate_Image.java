package others;
public class Rotate_Image {
    //in place
    public void rotate(int[][] matrix) {
        int size=matrix.length;
        for(int i=0;i<size/2;i++){
            for(int j=i;j<size-i-1;j++){
                int val = matrix[i][j];
                int i2=i,j2=j;

                do{

                    int temp=i2;
                    i2=j2;
                    j2=size-temp-1;

                    temp=matrix[i2][j2];
                    matrix[i2][j2]=val;
                    val=temp;



                }while(i2!=i||j2!=j);



            }
        }


    }
}
