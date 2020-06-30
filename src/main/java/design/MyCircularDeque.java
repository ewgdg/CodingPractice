package design;
public class MyCircularDeque {
    //use circular buffer with rear and front pointer
    //another simple method is linkedlist
    int capacity;
    int size;
    //2 array
    //or one array with 2 pointer
    //left pointer for rear point , right pointer for front point
    //close at middle
    int front;
    int rear;

    int[] buffer;
    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        capacity=k;
        size=0;
        front= k-1;
        rear = 0;
        buffer = new int[k];
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if(size>=capacity) return false;

        buffer[front]=value;
        front=updateIdx(front,-1);
        size++;
        return true;
    }
    public int updateIdx(int idx, int change){
        return ((idx+change)%capacity+capacity)%capacity;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if(size>=capacity) return false;
        buffer[rear]=value;
        rear=updateIdx(rear,+1);
        size++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if(size==0) return false;
        front=updateIdx(front,1);
        size--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if(size==0) return false;
        rear=updateIdx(rear,-1);
        size--;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if(size==0) return -1;
        return buffer[updateIdx(front,1)];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if(size==0) return -1;
        return buffer[updateIdx(rear,-1)];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return size==0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return size==capacity;

    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */
