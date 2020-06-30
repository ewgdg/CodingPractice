package array.stack;

import java.util.Stack;

public class PoisonousPlant {

    // Complete the poisonousPlants function below.
    //There are a number of plants in a garden. Each of these plants has been treated with some amount of pesticide.
    // After each day, if any plant has more pesticide than the plant on its left, being weaker than the left one, it dies.
    //
    //You are given the initial values of the pesticide in each of the plants.
    // Print the number of days after which no plant dies, i.e. the time after which there are no plants with more pesticide content than the plant to their left.
    static int poisonousPlants(int[] p) {
        //5 3 2 1 : 0
        //3 6 2 8 9 10 : 1
        // 1 2 3
        // 1 5 8 6 9 7 10  8 11  9

        // 1 5 4 3 2 // each day die one
        // naive method , bfs, simulation // n^2

        //observation,
        //monotone, if there is elem in stack greater than it, means the cur should at least wait until the prev disappear


        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack_date = new Stack<>();//after which day it will disappear
        //0 means wont be disappear

        int res=0;
        for(int num: p){
            if(stack.isEmpty()){
                stack.add(num);
                stack_date.add(0);
            }else if(stack.peek() < num  ){
                stack.add(num);
                stack_date.add(1);
                res=Math.max(res,stack_date.peek());
            }else{
                int temp=0;
                while(!stack.isEmpty() &&  stack.peek()>= num ){
                    stack.pop();
                    temp=Math.max(temp,stack_date.pop() ) ;
                }


                if(stack.isEmpty()){
                    stack_date.add(0);
                }else{
                    stack_date.add(temp+1);
                }
                stack.add(num);
                res = Math.max(res,stack_date.peek());
            }

        }


        return res;




    }
}
