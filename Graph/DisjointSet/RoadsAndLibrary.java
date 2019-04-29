package DisjointSet;

import java.util.HashMap;

public class RoadsAndLibrary {
    //variation : each road got diff cost-> minimum spanning tree

    //The Ruler of HackerLand believes that every citizen of the country should have access to a library.
    // Unfortunately, HackerLand was hit by a tornado that destroyed all of its libraries and obstructed its roads!
    // As you are the greatest programmer of HackerLand, the ruler wants your help to repair the roads and build some new libraries efficiently.
    //
    //HackerLand has n  cities numbered from 1 to n. The cities are connected by  bidirectional roads. A citizen has access to a library if:
    //
    //Their city contains a library.
    //They can travel by road from their city to a city containing a library.
    //return min cost


    // Complete the roadsAndLibraries function below.
    static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
        Dset dset = new Dset();
        long cost =0;
        for(int i=1;i<=n;i++){
            dset.makeSetIfAbsent(i);//important !! dont forget those isolated cities
        }
        for(int[] edge:cities){
            int src = edge[0];
            int dst = edge[1];

            // dset.makeSetIfAbsent(src);
            // dset.makeSetIfAbsent(dst);
            if(c_road<c_lib){//dont need to build road if cost of lib is smaller
                boolean isConnected = !dset.union(src,dst);
                if(!isConnected){
                    cost+=c_road;
                }
            }

        }
        cost+=(long)dset.count*c_lib;
        return cost;

    }
    static class Dset{
        HashMap<Integer,Integer> map;
        public long count;
        public Dset(){
            map = new HashMap<>();
            count=0;
        }
        public void makeSetIfAbsent(int  id){
            if(!map.containsKey(id)){
                count++;
            }
            map.putIfAbsent(id,id);
        }
        public Integer find(int id){
            if(!map.containsKey(id)) return null;
            if( map.get(id)!=id  ){
                int parent = find(map.get(id));
                map.put(id,parent);
            }
            return map.get(id);

        }
        public boolean union(int id1,int id2){
            int p1 = find(id1);
            int p2 = find(id2);

            if(p1==p2){
                return false;
            }
            count--;
            map.put(p2,p1);
            return true;
        }


    }
}
