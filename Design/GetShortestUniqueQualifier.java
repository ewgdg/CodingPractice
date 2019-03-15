import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class GetShortestUniqueQualifier {
    public static class CustomNode
    {
        public String Title;
        public CustomNode Parent;
        public ArrayList<CustomNode> Children;

        public CustomNode(String title, CustomNode parent)
        {
            Title = title;
            Parent = parent;
            Children = new ArrayList<CustomNode>();

            if(Parent!=null)
                Parent.Children.add(this);
        }

        public CustomNode Find(String path)
        {
            if (path.equals(Title))
                return this;

            String[] pieces = path.split("/");

            for (CustomNode child : Children) {
                if (child.Title.equals(pieces[1]))
                    return child.Find(path.substring(Title.length() + 1));
            }

            return null;
        }
    }

    public static void main1 (String[] args) throws java.lang.Exception
    {
//        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        //Build a test tree (matches the example)
        CustomNode root = new CustomNode("Root", null);
        CustomNode userData = new CustomNode("UserData", root);
        CustomNode ud_browser = new CustomNode("Browser", userData);
        CustomNode ud_word = new CustomNode("Word", userData);
        CustomNode priv = new CustomNode("Private", userData);
        CustomNode priv_word = new CustomNode("Word", priv);

        CustomNode windows = new CustomNode("Windows", root);
        CustomNode programs = new CustomNode("Programs", root);
        CustomNode notepad = new CustomNode("Notepad", programs);
        CustomNode prog_word = new CustomNode("Word", programs);
        CustomNode prog_browser = new CustomNode("Browser", programs);

        CustomNode custom1 = new CustomNode("Root", root);
        CustomNode custom2 = new CustomNode("Root", custom1);
        CustomNode custom3 = new CustomNode("Programs", custom2);
        CustomNode target = root.Find("Root/Programs");

        System.out.println(GetShortestUniqueQualifier(root, target));
    }

    public static String GetShortestUniqueQualifier(CustomNode root, CustomNode target)
    {
        if(!root.Title.equals("Root")) return "exception";

        Queue<Deque<String>> buffer = new LinkedList<>();

        Deque<String> temp = new LinkedList<>();
        temp.addLast(root.Title);
        dfs(buffer,root,target,temp);


        CustomNode cur = target;
        Deque<String> res = new LinkedList<>();


//not very efficient as it take much space
        // we can check one by one while iterating
        while(!buffer.isEmpty() && cur!=null){

            boolean found = false;
            int size=buffer.size();
            for(int i =0;i<size;i++) {
                Deque<String> path = buffer.poll();
                String title = path.isEmpty()? "" : path.removeLast();
                if(title.equals(cur.Title)){
                    found=true;
                    if(!path.isEmpty())
                        buffer.add(path);
                }

            }

            if(found) { //only need parent if we found dup
                res.addFirst(cur.Title);
                cur = cur.Parent;
            }

        }
        if(cur!=null) {//add unique name
            res.addFirst(cur.Title);
        }//or res.add( cur == null? "" : cur.Title)


        StringBuilder sb=  new StringBuilder();
        if(cur==null){
            sb.append("/");
        }

        sb.append(res.removeFirst());



        for(String name: res){
            sb.append("/");
            sb.append(name);
        }


        return sb.toString();
    }

    public static void dfs(Queue<Deque<String>> buffer, CustomNode cur, CustomNode target, Deque<String> temp){

        if(cur.Title.equals(target.Title) && cur!=target){
            buffer.add(new LinkedList<>(temp));
        }

        for(CustomNode child :cur.Children){
            temp.addLast(child.Title);
            dfs(buffer, child, target, temp);
            temp.removeLast();
        }

    }
    public static void main (String[] args) throws java.lang.Exception
    {
        //Build a test tree (matches the example)
        CustomNode root = new CustomNode("Root", null);
        CustomNode userData = new CustomNode("UserData", root);
        CustomNode ud_browser = new CustomNode("Browser", userData);
        CustomNode ud_word = new CustomNode("Word", userData);
        CustomNode priv = new CustomNode("Private", userData);
        CustomNode priv_word = new CustomNode("Word", priv);

        CustomNode windows = new CustomNode("Windows", root);
        CustomNode programs = new CustomNode("Programs", root);
        CustomNode notepad = new CustomNode("Notepad", programs);
        CustomNode prog_word = new CustomNode("Word", programs);
        CustomNode prog_browser = new CustomNode("Browser", programs);

        CustomNode custom1 = new CustomNode("Custom1", root);
        CustomNode custom2 = new CustomNode("Custom2", custom1);
        CustomNode custom3 = new CustomNode("Custom3", custom2);

        RunTests(root, custom1, custom2, custom3);
    }

    public static void TestGetShortestUniqueQualifier(CustomNode root, String targetAbsPath, String expected)
    {
        String output;
        try {
            output = GetShortestUniqueQualifier(root, root.Find(targetAbsPath));
        } catch (Exception e) {
            output = "exception";
        }

        if((output != null && output.equals(expected)) || (output == null && expected == null) || (expected==null && "".equals(output)))
            System.out.println("Succeeded for " + targetAbsPath);
        else
            System.out.println("Failed for " + targetAbsPath + ": Failed with [" + output + "] rather than [" + expected + "]");
    }

    public static void RunTests(CustomNode root, CustomNode custom1, CustomNode custom2, CustomNode custom3)
    {
        //They are unique
        TestGetShortestUniqueQualifier(root, "Root", "Root");
        TestGetShortestUniqueQualifier(root, "Root/Programs", "Programs");
        TestGetShortestUniqueQualifier(root, "Root/Programs/Notepad", "Notepad");

        //They have duplicate names
        TestGetShortestUniqueQualifier(root, "Root/Programs/Browser", "Programs/Browser");
        TestGetShortestUniqueQualifier(root, "Root/UserData/Browser", "UserData/Browser");

        //Root has a duplicate name
        custom1.Title = "a";
        custom2.Title = "b";
        custom3.Title = "Root";
        TestGetShortestUniqueQualifier(root, "Root/a/b/Root", "b/Root");

        //Edge cases
        custom1.Title = "Root";
        custom2.Title = "b";
        custom3.Title = "c";
        TestGetShortestUniqueQualifier(root, "Root/Root", "Root/Root");

        custom1.Title = "a";
        custom2.Title = "a";
        custom3.Title = "a";
        TestGetShortestUniqueQualifier(root, "Root/a", "Root/a");
        TestGetShortestUniqueQualifier(root, "Root/a/a", "Root/a/a");
        TestGetShortestUniqueQualifier(root, "Root/a/a/a", "a/a/a");

        custom1.Title = "Root";
        custom2.Title = "UserData";
        custom3.Title = "Word";
        TestGetShortestUniqueQualifier(root, "Root/Root/UserData/Word", "Root/Root/UserData/Word");

        TestGetShortestUniqueQualifier(root, "Root", "/Root");
        TestGetShortestUniqueQualifier(root, "Root/UserData/Word", "/Root/UserData/Word");
    }
}
