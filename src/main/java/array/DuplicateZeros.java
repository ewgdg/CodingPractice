package array;

/* Given a fixed length array arr of integers, duplicate each occurrence of zero, shifting the remaining elements to the right.

Note that elements beyond the length of the original array are not written.

Do the above modifications to the input array in place, do not return anything from your function.

Input: [1,0,2,3,0,4,5,0]
Output: null
Explanation: After calling your function, the input array is modified to: [1,0,0,2,3,0,0,4]


Note:

1 <= arr.length <= 10000
0 <= arr[i] <= 9
*/

//the difficulty of this prob is to avoid using extra mem.
class DeplicateZeros {

  // sol1, use extra bits to store info
  public void sol1(int[] arr) {
    // [0,0,0]
    // [0,0,1]
    // [1,1,0]
    // [1,0,3,0,4,0]

    int n = arr.length;
    // to avoid use extra mem, use the first 4 bits for original val, latter 3 bits
    // to store the new val

    int shift = 0;
    int mask = 0b01111;

    for (int i = 0; i < n; i++) {

      if (shift + i < n) {

        int num = arr[i] & mask;
        // shift first

        arr[i + shift] = arr[i + shift] | (num << 4);

        // double zero

        if (num == 0) {
          // arr[i+shift+1] = arr[i+shift+1] | 0 ;
          shift += 1;

        }

      }
      arr[i] = (arr[i] >> 4) & mask;// set the new val

    }

  }

  // calculate the possible extra elems that are out of space,
  // discard all extraneous elements = possible dups
  // save the space for the rest
  // copy elem from the last non-extra elements to the proper position to avoid
  // overiding
  public void sol2(int[] arr) {

    // [0,1,7,6,0,2,0,7]
    // [0]
    int n = arr.length;

    int possible_dups = 0;

    int right = n - 1;
    // calculate num of extra;
    for (int i = 0; i < n; i++) {
      if (i + possible_dups >= n)
        break;

      if (arr[i] == 0) {

        if (i + possible_dups == n - 1) {
          // edge case
          // the last zero cannot be dup
          arr[n - 1] = 0;
          right--;
          possible_dups++;
          break;
        } else {
          possible_dups++;
        }

      }

    }

    // copy the rest to proper pos
    for (int i = n - possible_dups - 1; i >= 0; i--) {
      arr[right] = arr[i];
      if (arr[i] == 0) {

        arr[--right] = 0;
      }

      right--;
    }

  }

}
