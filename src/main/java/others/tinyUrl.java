package others;

public class tinyUrl {
    //counter based --> best scalable no collision, issue run out of space soon if integer type, cannot map to same ,
    // use inverse map + map


    //hashcode ==> issue collision
    //random num=> issue collision cannot map to same

    // idToUrl
    static String codes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String idToUrl(int n){
        StringBuilder sb= new StringBuilder();

        while(n>0){
            int digit =  Math.floorMod(n,62);//n%62; Math.floorMod(n,62);
            sb.append(codes.charAt(digit));
            n=n/62;
        }
        while(sb.length()<6){
            sb.append('0');
        }
        return sb.reverse().toString();

    }

    public static void main(String[] args){
        System.out.println(idToUrl(100232));
    }




}
