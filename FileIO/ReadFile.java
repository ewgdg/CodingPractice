import java.io.*;

public class ReadFile {

    public static void solve(String filename,String output){

        try(
            BufferedReader in = new BufferedReader(new FileReader(filename));
            BufferedWriter out =  new BufferedWriter(new FileWriter(output));
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
}
