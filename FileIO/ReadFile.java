import java.io.*;

public class ReadFile {

    public static void solve(String filename,String output){

        try(
            BufferedReader in = new BufferedReader(new FileReader(filename));
            BufferedWriter out =  new BufferedWriter(new FileWriter(output,false));
        ){
            String line = null;
            while( (line=in.readLine())!=null){
                int index = line.indexOf(' ');
                String first = index>-1?line.substring(0, index):line;
                out.write(first+"\n");
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args){
        solve( "S:\\GitHub\\Practice\\FileIO\\in.txt","S:\\GitHub\\Practice\\FileIO\\out.txt");
        System.out.println("  sss bb   bbc".split("\\s+")[0]);
    }

    //Automatic Resource Management in multiple resources
    //
    //Multiple resources can be used inside a try-with-resources block and have them all automatically closed. In this case, the resources will be closed in the reverse order in which they were created inside the brackets.
    //
    //filter_none
    //edit
    //play_arrow
    //
    //brightness_4
    //// Java program to illustrate
    //// Automatic Resource Management
    //// in Java with multiple resource
    //class Resource
    //{
    //    public static void main(String s[])
    //    {
    //        //note the order of opening the resources
    //        try(Demo d = new Demo(); Demo1 d1 = new Demo1())
    //        {
    //            int x = 10/0;
    //            d.show();
    //            d1.show1();
    //        }
    //        catch(ArithmeticException e)
    //        {
    //            System.out.println(e);
    //        }
    //    }
    //}
    //
    //// custom resource 1
    //class Demo implements AutoCloseable
    //{
    //    void show()
    //    {
    //        System.out.println("inside show");
    //    }
    //    public void close()
    //    {
    //        System.out.println("close from demo");
    //    }
    //}
    //
    ////custom resource 2
    //class Demo1 implements AutoCloseable
    //{
    //    void show1()
    //    {
    //        System.out.println("inside show1");
    //    }
    //    public void close()
    //    {
    //        System.out.println("close from demo1");
    //    }
    //}
}
