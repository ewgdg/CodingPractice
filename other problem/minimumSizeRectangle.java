import java.util.Arrays;

public class minimumSizeRectangle {


    //assume the points are in a given order ABCD
    // A  B
    // D  C

    public static boolean isRect(double[][] points){
        double[] a = points[0];
        double[] b= points[1];
        double[] c = points[2];
        double[] d = points[3];


        //vector addition
        double[] ac = new double[]{c[0]-a[0] , c[1]-a[1]};
        double[] ad = new double[]{d[0]-a[0], d[1]-a[1]  };
        double[] ab = new double[]{b[0]-a[0], b[1]-a[1]};

        boolean parallel =  ab[0]+ad[0]==ac[0] && ab[1]+ad[1]==ac[1]   ;
        boolean angle90 = ad[0]*ab[0] + ad[1]*ab[1]  ==0;  //dot product = cos angle



        double slope_ad = (ad[1]/ad[0]);
        double slope_ab = (ab[1]/ab[0]);

        //issue of division by zero
//        System.out.println(   slope_ad*slope_ab );

        //without division by zero
//        System.out.println(   ab[1]* ad[1] == -1 * (ad[0]*ab[0]) );

        return parallel&&angle90;
    }


    public static double calculateArea(double[][] points){
        double[] a = points[0];
        double[] b= points[1];
        double[] c = points[2];
        double[] d = points[3];

        double len_ab = Math.pow((b[0]-a[0]),2) + Math.pow( (b[1]-a[1]),2) ;
        double len_ad = Math.pow((d[0]-a[0]),2) + Math.pow( (d[1]-a[1]),2) ;

        return len_ab*len_ad;

    }

    public static double solution(double[][] points){
        double min=-1;
        for(double[] a : points){
            for(double[] b : points){
                if(b==a) continue;
                for(double[] c : points){
                    if(c==b||c==a) continue;
                    for(double[] d : points){
                        if(d==a||d==b||d==c) continue;

                        double[][] rect = new double[][]{a,b,c,d};
                        if(isRect( rect)){
                            if(min==-1) min = calculateArea(rect);
                            else min = Math.min(min, calculateArea(rect));
                            System.out.println(Arrays.deepToString( rect ));
                        }
                    }
                }
            }
        }

        return min;
    }

    public static void main(String[] args){

        //Question to ask ? precision issue?

        System.out.println(isRect(new double[][]{ {0.0,0.1}, {1,1.1}, {2,0.1}, {1,-0.9}  }));

        System.out.println(isRect(new double[][]{ {0.2,0}, {1.3,0}, {1.3,-0.5}, {0.2,-0.5}  }));

        System.out.println(calculateArea(new double[][]{ {0.2,0}, {1.3,0}, {1.3,-0.5}, {0.2,-0.5} }));
        System.out.println(calculateArea(new double[][]{ {0.0,0.1}, {1,1.1}, {2,0.1}, {1,-0.9} }));

        System.out.println( solution(new double[][]{ {0.2,0}, {1.1,0}, {1.3,-0.5},{1.3,0}, {0.2,-0.5},{0.0,0.2}, {1,1.1}, {2,0.1}, {1,-0.9},{1.0,0.1},  } ) );
    }

}
