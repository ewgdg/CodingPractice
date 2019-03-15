import java.util.Stack;

public class NewYearChaos {
    //It's New Year's Day and everyone's in line for the Wonderland rollercoaster ride! There are a number of people queued up, and each person wears a sticker indicating their initial position in the queue. Initial positions increment by  from  at the front of the line to  at the back.
    //
    //Any person in the queue can bribe the person directly in front of them to swap positions. If two people swap positions, they still wear the same sticker denoting their original places in line. One person can bribe at most two others. For example, if and  bribes , the queue will look like this: .
    //
    //Fascinated by this chaotic queue, you decide you must know the minimum number of bribes that took place to get the queue into its current state!


    //2 1 5 3 4 -> 3 bribes
    //2 5 1 3 4 -> invalid

    //observation:
    //The number of people the  person (for ) has bribed is equal to the number of people on the right of that person with a value less than  (where array  represents the given array or final state of the people).
    //To get to the current position, each person has to bribe all the people who are behind them and have a smaller number. This is the same as counting inversions of an array.
    //So, if that number is greater than  for any index  we print Too chaotic else print the total sum of bribes.
    //O n2
//    improved
    //use monotone stack?? wrong, monotone keeps only increasing order smaller number , but we need all
    //BIT for  smaller : nlogn




    //another  O kn , k=number of bribes allowed :
    //reverse simulation
    //start from very end,thus the swapping direction is fixed!!!(important), find the desired num from prev K pos, swap backward until it get correct position , add count of swapping, proceed to next


    public int solve(int[] q){
        int n = q.length;

        int count=0;
        for(int i=n-1;i>=0;i-- ){
            int order= i+1;//the desired num
            if(q[i]==order) continue;
            if(i-1>=0 && q[i-1]==order ){
//                int temp = q[i-1];
                q[i-1] = q[i];
                q[i] = order;

                count+=1;

            }else if(i-2>=0 && q[i-2]==order){
//                int temp = q[i-2];
                q[i-2]=q[i-1];
                q[i-1] = q[i];
                q[i] = order;
                count+=2;

            }else{
                return -1;//invalid
            }

        }
        return count;

    }


}
