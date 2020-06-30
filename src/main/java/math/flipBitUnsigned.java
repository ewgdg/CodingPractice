package Math;


public class flipBitUnsigned {

    //there is no unsigned int , to store val for unsigned we need larger container which is long
    //the first 32 bit of long will be 0 as if it is an unsigned int
    //0xffffffffl has 32 bit 0 following by 32 bit 1.
    static long flippingBits(long n) {
        // return (~n )& 0xffffffffl;
        return (0xffffffffl - n);

    }
}
