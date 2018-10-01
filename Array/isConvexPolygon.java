import java.util.Arrays;
import java.util.List;

public class isConvexPolygon {
    public static boolean isConvex(int[][] points) {
        //vector |cross product| = |A||B|sin(theta) , if theta >180 , sin<0 , theta is based on right hand rule a to b
        //if ccw / cw we will get differnt sign for the cross product , but the sign should be consistent
        //if seeing any inconsistent sign non-convex is found

        int sign = 0;

        for(int i=0;i<points.length;i++){
            int[] pointa = points[i];
            int[] pointb = points[ (i+1)%points.length ];
            int[] pointc = points[(i+2)%points.length  ];

            int[] vector_ba = new int[]{pointa[0]-pointb[0] , pointa[1]-pointb[1] };
            int[] vector_bc = new int[]{pointc[0]-pointb[0] , pointc[1]-pointb[1] };

            int res = crossProduct(vector_ba,vector_bc);
            if(sign==0) sign=res;

            if(res!=0&&sign!=res) return false;
        }
        return true;



    }
    public static int crossProduct(int[] a, int[] b){
        int res = (a[0]*b[1] - a[1]*b[0] );
        return res==0?0:res>0?1:-1;
    }

    public static void main(String[] args){
        System.out.println(isConvex( new int[][]{{0,0},{0,10},{5,5},{10,10},{10,0}}));
    }

}
