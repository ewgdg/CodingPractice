package online;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import design.CircularByteBuffer;

class StreamCompressor {

  CircularByteBuffer buffer;
  OutputStream bufferOutputStream;
  InputStream bufferInputStream;

  public StreamCompressor(int size) {
    buffer = new CircularByteBuffer(size);
    bufferInputStream = buffer.getInputStream();// used as inputstream to consumer to consume the buffer
    bufferOutputStream = buffer.getOutputStream(); // used as outputstream from producer to fill the buffer

  }

  // compress and write
  public void write(String string) throws IOException {
    StringBuilder sb = new StringBuilder();
    int count = 0;
    Character prev = null;
    for (int i = 0; i < string.length(); i++) {

      if (prev != null && string.charAt(i) == prev) {
        count++;
      } else {
        if (prev != null && string.charAt(i) != prev) {
          sb.append(prev);
          if (count > 1)
            sb.append(count);
        }
        prev = string.charAt(i);
        count = 1;
      }

    }
    sb.append(prev);
    if (count > 1)
      sb.append(count);
    // send compressed string
    System.out.println(sb.toString());
    if (sb.length() > 0)
      bufferOutputStream.write(sb.toString().getBytes());

  }

  Character cur;
  Character next;
  int count;

  public Character readRawChar() throws IOException {
    int raw = bufferInputStream.read();

    Character c = null;
    if (raw >= 0) {
      c = (char) raw;
    }
    return c;

  }

  // read the next char
  public Character read() throws IOException {

    if (count <= 0) {
      cur = next == null ? readRawChar() : next;
      next = null;
      // read count
      Character c;
      boolean done = false;
      do {
        c = readRawChar();
        if (c == null) {
          break;
        }
        if (!Character.isDigit(c)) {
          next = c;
          done = true;
        } else {
          count = count * 10 + c - '0';
        }
      } while (!done);
    }

    if (count > 0)
      count--;
    return cur;

  }

  public static void main(String[] args) {

    StreamCompressor compressor = new StreamCompressor(80);
    try {
      compressor.write("LeetCode");
      compressor.write("LeettCode");
      Character c = null;
      do {
        c = compressor.read();
        if (c != null) {
          System.out.print(c);
        }
      } while (c != null);
      System.out.print('\n');

      compressor.write("a222"); // improvement : use ':' as special char to sepearate count and char when compress , use \: if want to print ':' , \\ if print \ in compressed stream(special char [: ,\])
      compressor.write("LeettCode");
      do {
        c = compressor.read();
        if (c != null) {
          System.out.print(c);
        }
      } while (c != null);
    } catch (IOException e) {

      e.printStackTrace();
    }

  }

}