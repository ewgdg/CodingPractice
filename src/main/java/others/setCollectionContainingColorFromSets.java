package others;
import java.util.*;

public class setCollectionContainingColorFromSets {

    //有三个国家参加奥运会，国旗的颜色依次是<1,2,3>，<4,5>,<3>
    //如果给的set是<3,4>，则不能代表所有国旗；如果是<1,4>，则可以。
    //写完了问如果写代码能让别人易懂，如何定义输入等。然后问假设总共的颜色种类不多，要找出所有能代表全部国旗的颜色集合。lz说从1到n把可能的颜色组合列举出来，每种组合用之前的方法判断符不符合就可以了。然后写代码。当时时间不多有点慌，就没写完，就说了一下用backtracking找出所有permutations的思路。

    public boolean isValid(List<HashSet<Integer>> flags, HashSet<Integer>  set){

        for(HashSet<Integer> flag:flags){
            boolean valid =false;
            for(Integer c:flag){
                if(set.contains(c)){
                    valid =true;
                    break;
                }
            }

            if(!valid) return false;
        }
        return true;

    }

    //return all of the valid sets
    public List<HashSet<Integer> > solution( List<HashSet<Integer>> flags){
        HashSet<Integer> collections = new HashSet<>();
        for(HashSet<Integer> flag: flags){
            for(Integer c: flag){
                collections.add(c);
            }
        }

        //dfs memorization
        List<Integer> list= new ArrayList<>(collections);


        return dfs(list,flags,new HashSet<>(),0);

    }

    public List<HashSet<Integer> > solution2( List<HashSet<Integer>> flags){
        HashSet<Integer> collections = new HashSet<>();
        for(HashSet<Integer> flag: flags){
            for(Integer c: flag){
                collections.add(c);
            }
        }


        List<Integer> list= new ArrayList<>(collections);
        List<HashSet<Integer>> ans = new ArrayList<>();
        backtracking(list,flags,new HashSet<>(),0,ans);
        return ans;

    }

    static int read_mem =0;
    public List<HashSet<Integer> > dfs(List<Integer> collections, List<HashSet<Integer>> flags, HashSet<Integer> tempSet, int index){

        //mem no need to use mem, since each time the target flags are unique due to the uniqueness of set collections
//        if(mem.containsKey(key)){
//            read_mem++;
//            return mem.get(key);
//        }

        List<HashSet<Integer>> res =  new ArrayList<>();
        //terminating cond
        if(isValid(flags,tempSet)){
            res.add(new HashSet<>());
        }else {//if know early should be pruned

//            return res; //cannot return here bc there might be valid latter
        }

        if(index>=collections.size()){ //check index at last bc we are validate previous layer tempSet before it

            return res;
        }


        tempSet.add(collections.get(index));
        List<HashSet<Integer>> list1 = dfs(collections,flags,tempSet,index+1 );
        tempSet.remove(collections.get(index));

        for(HashSet<Integer> set:list1){
            HashSet<Integer> clone = new HashSet<>(set);
            clone.add(collections.get(index));
            res.add(clone);
        }

//        if(index==2){
//            System.out.println("4");;
//        }
        List<HashSet<Integer>> list2 = dfs(collections,flags,tempSet,index+1 );
        for(HashSet<Integer> set:list2){
            if(!set.isEmpty())//if we satisfy at this layer then all lower layer can keep satisfied by do nothing, this will result in repetitive arrays
                res.add(set); //neglect any empty set if we do not modify it
        }




        return res;



    }

    public void backtracking(List<Integer> collections, List<HashSet<Integer>> flags, HashSet<Integer> tempSet, int index, List<HashSet<Integer> > ans){
        if(isValid(flags,tempSet)){
            ans.add(new HashSet<>(tempSet));
        }

        for(int i=index;i<collections.size();i++){
            tempSet.add(collections.get(i));
            backtracking(collections,flags,tempSet,i+1,ans);
            tempSet.remove(collections.get(i));
        }



    }



    public static void main(String[] args){

        HashSet<Integer> set1 = new HashSet<>();
        set1.add(1); set1.add(2); set1.add(3);

        HashSet<Integer> set2 = new HashSet<>();
        set2.add(4); set2.add(5);

        HashSet<Integer> set3 = new HashSet<>();
        set3.add(3);

        HashSet<Integer> set4 = new HashSet<>();
        set4.add(1);set4.add(2);set4.add(3);//set4.add(4);set4.add(5);

        HashSet<Integer> set5 = new HashSet<>();
        set5.add(4);

        List<HashSet<Integer>> flags = new ArrayList<>();
        flags.add(set1); flags.add(set2); flags.add(set3);


        setCollectionContainingColorFromSets solver = new setCollectionContainingColorFromSets();

        System.out.println(solver.solution(flags));
        System.out.println(solver.solution2(flags));

//        System.out.println(solver.isValid(flags,set4));
//        System.out.println(read_mem);
//
//        System.out.println(solver.isValid(flags,set5));
    }


}
