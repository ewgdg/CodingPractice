package Design;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MaxStack {
    //the difficulty here is to support the pop max op
    //after pop max we might need the another max
    //e.g
    // add 0, 5 , 2, 1
    //pop max 5
    //pop max 2, here is the tricky point , need to re-add  2 ,1 to find the second max if we use 2 stack method
    //another method is to use treemap method see maxstack2

    //method1
    //2 stack
    //O n for popMax
    //O 1 for others

    Stack<Integer> stack ;
    Stack<Integer> maxStack;
    Queue<Integer> removed;

    public MaxStack(){
        stack = new Stack<>();
        maxStack = new Stack<>();
    }
    public void push(int x) {

        stack.add(x);
        if(maxStack.isEmpty()||maxStack.peek()<=x){
            maxStack.add(x);
        }
    }


    public int pop() {
//        clean();
        int res =stack.pop();
        if(maxStack.peek()==res){
            maxStack.pop();
        }
        return res;
    }

    public int top() {
//        clean();
        return stack.peek();
    }

    public int peekMax(){
        return maxStack.peek();
    }

    public int popMax(){
        int res= maxStack.pop();
        // removed.add(res);
        Stack<Integer> buffer = new Stack<>();
        int read;
        while( (read=stack.pop())!=res ){
            buffer.add(read);
        }
        while(!buffer.isEmpty()){
            push(buffer.pop());
        }

        return res;
    }





}
