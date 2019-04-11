import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

public class CircularByteBuffer {
    //implement based on document from//https://ostermiller.org/utils/doc/com/Ostermiller/util/CircularByteBuffer.html

    static int INFINITE_SIZE = -1; //A buffer that will grow as things are added.
    int capacity;

    int startIdx;
    int endIdx;

    List<Byte> buffer;
    public CircularByteBuffer(int size){
        this.capacity=size;
        startIdx=0;
        endIdx=0;
        inputStream = new _InputStream();//or we can make it a singleton?
        outputStream = new _OutputStream();

    }

    public int increaseIdx(int idx,int amount){
        if(capacity<0) return idx+amount;
        return (idx+amount)%capacity;
    }


    //CircularByteBuffer(boolean blockingWrite)
    //          Create a new buffer with a default capacity and given blocking behavior.






    //getOutputStream
    //public OutputStream getOutputStream()
    //Retrieve a OutputStream that can be used to fill this buffer.
    //Write methods may throw a BufferOverflowException!!!!!!!!!!
    //if the buffer is not large enough.
    // A large enough buffer size must be chosen so that this does not happen or the caller must be prepared to catch the exception
    // and try again once part of the buffer has been consumed.
    //
    //Returns:
    //the producer for this buffer.
    public OutputStream getOutputStream(){
        return outputStream;
    }
    _OutputStream outputStream;
    class _OutputStream extends OutputStream{

        @Override
        public synchronized void write(int b) throws IOException {
            if(getSpaceLeft()<4){//another choice is blocking here
                throw new IOException("out of space.");
            }

            buffer.set(endIdx,(byte)((b>>24)&0xFF));
            buffer.set(endIdx+1,(byte)((b>>16)&0xFF));
            buffer.set(endIdx+2,(byte)((b>>8)&0xFF));
            buffer.set(endIdx+3,(byte)((b)&0xFF));
            endIdx=increaseIdx(endIdx,4);
        }
    }

    //public InputStream getInputStream()
    //Retrieve a InputStream that can be used to empty this buffer.
    //This InputStream supports marks at the expense of the buffer size.
    //
    //Returns:
    //the consumer for this buffer.
    public InputStream getInputStream(){
        return inputStream;
    }
    _InputStream inputStream;
    class _InputStream extends InputStream{

        @Override
        public synchronized int read() throws IOException {//need to lock access of buffer to prevent race cond
            if(getAvailable()<4) throw new IOException("no enough data");

            int res = buffer.get(startIdx+3) & 0xFF |
                    (buffer.get(startIdx+2) & 0xFF) << 8 |
                    (buffer.get(startIdx+1) & 0xFF) << 16 |
                    (buffer.get(startIdx) & 0xFF) << 24;
            startIdx=increaseIdx(startIdx,4);
            return res;
        }
    }


    //Get number of bytes that are available to be read.
    //Note that the number of bytes available plus the number of bytes free may not add up to the capacity of this buffer, as the buffer may reserve some space for other purposes.
    public synchronized int getAvailable(){
        int count=0;
        if(startIdx<=endIdx){
            count = endIdx-startIdx;
        }else{
            count+= capacity-startIdx;
            count+= endIdx-0;
        }

        return count;

    }
    //Get the number of bytes this buffer has free for writing.
    //Note that the number of bytes available plus the number of bytes free may not add up to the capacity of this buffer, as the buffer may reserve some space for other purposes.
    public synchronized int getSpaceLeft(){
        if(capacity<0) return Integer.MAX_VALUE;
        return capacity-getAvailable();
    }




    public static void main(String[] args){

//        CircularByteBuffer cbb = new CircularByteBuffer();
//        new Thread(
//                new Runnable(){
//                    public void run(){
//                        class1.putDataOnOutputStream(cbb.getOutputStream());
//                    }
//                }
//        ).start();
//        class2.processDataFromInputStream(cbb.getInputStream());
    }
}
