package others;
import java.util.HashSet;

public class robotRoomCleaner {

    public static class coord {
        public int x, y;

        public coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object that) {
            coord c = (coord)that;
            return this.x==c.x && this.y==c.y;
        }

        @Override
        public int hashCode() {
            return x * 37 + y;
        }
    }

//    public void cleanRoom(Robot robot) {
//        //set init pos as 0,0
//        HashSet<coord> visited = new HashSet<>();
//
//        //dfs until all directions
//        dfs(robot,new coord(0,0), visited, new Dir(0));
//
//    }
//
//    public void dfs(Robot robot, coord cur, HashSet<coord> visited, Dir curDir){
//        if(visited.contains(cur) ){
//            return;
//        }
//
//        robot.clean();
//        visited.add(cur);
//
//
//        for(Dir dir: dirs){
//
//            coord next = new coord(cur.x+dir.change.x,cur.y + dir.change.y);
//            if(!visited.contains(next) ){
//                rotate(robot,curDir,dir);
//                boolean action = robot.move();
//                if(action){
//                    dfs(robot,next,visited,curDir);
//                    rotate(robot,curDir,dir.opposite());
//                    robot.move();
//                }
//
//            }
//
//
//        }
//
//
//
//
//
//    }
//    public Dir[] dirs = new Dir[]{ new Dir(0),new Dir(1), new Dir(2), new Dir(3)  };
//    //up:0 , right:1, down:2 , left:3
//    class Dir{
//        public int val;
//        public coord change;
//
//        public Dir(int val){
//            this.val = val%4;
//            if(this.val==0){
//                change = new coord(1,0);
//            }else if(this.val==1){
//                change = new coord(0,1);
//            }else if(this.val==2){
//                change = new coord(-1,0);
//            }else if(this.val==3){
//                change = new coord(0,-1);
//            }else{
//                change = new coord(10000000,10000000);
//            }
//        }
//        public Dir opposite(){
//            int ans = (this.val+2)%4;
//            return new Dir(ans);
//        }
//
//        public void turnLeft(){
//            int ans = (this.val-1)%4;
//            if(ans<0) ans+=4; //be careful, the modulo will return negative number
//            this.val=ans;
//        }
//        public void turnRight(){
//            int ans = (this.val+1)%4;
//            this.val=ans;
//        }
//
//
//    }
//    public void rotate(Robot robot, Dir from , Dir to){
//        int change = to.val-from.val;
//
//
//        if(change==3){
//            change = -1;
//        }
//        if(change == -3){
//            change = 1;
//        }
//        switch(change){
//            case 1:
//                robot.turnRight();from.turnRight();
//                break;
//            case -1:
//                robot.turnLeft();from.turnLeft();
//                break;
//            case 2:
//                robot.turnRight();from.turnRight();
//                robot.turnRight();from.turnRight();
//                break;
//            case -2:
//                robot.turnLeft();from.turnLeft();
//                robot.turnLeft();from.turnLeft();
//                break;
//        }
//
//    }


    public static void  main(String[] args){
        HashSet<coord> visited = new HashSet<>();
        visited.add(new coord(1,1));
        visited.add(new coord(13,112));
        System.out.println(new coord(1,1).equals(new coord(1,1)));
        System.out.println(visited.contains(new coord(13,112)));
        System.out.println(visited.contains(new coord(112,13)));
        System.out.println(visited.contains(new coord(0,38)));
        System.out.println( (-2)%4);
    }



}
