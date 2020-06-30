package Design;
import java.util.*;

class FileSystem {
    Dir root;

    public FileSystem() {
        root = new Dir("root");
    }

    public List<String> ls(String path) {
        String[] dirNames = path.split("/");
        // System.out.println(path);
        List<String> res = new ArrayList<>();
        Dir cur = root;
        int index=0;
        for(int i=0;i<dirNames.length;i++){
            String name = dirNames[i];
            if(name.equals("") ) continue;//skip first empty , remember to update index even skipping

            if(cur.isDir(name)){
                cur = cur.getDir(name);
            }else if(i==dirNames.length-1 && cur.isFile(name)){
                res.add(name);
                return res;
            }else{
                return res;
            }


        }
        //last = dirNames[dirNames.length-1];//dirNames could be empty if "/"!!!!! , so error, check if len>0

        // res.addAll(cur.getChildren());//create a copy, already a copy
        // System.out.println(cur.subDirs.keySet());
        return cur.getChildren();


    }

    public void mkdir(String path) {
        String[] dirNames = path.split("/");
        Dir cur = root;
        // System.out.println(Arrays.toString(dirNames) );
        for(String name:dirNames){
            if(name.equals("") ) continue;//skip first empty
            if(!cur.isDir(name)){
                cur.mkdir(name);
            }
            cur= cur.getDir(name);
        }
    }

    public void addContentToFile(String filePath, String content) {
        String[] dirNames = filePath.split("/");
        Dir cur = root;

        for(int i =0; i< dirNames.length-1; i++){
            String name = dirNames[i];
            if(name.equals("") ) continue;//skip first empty
            if(!cur.isDir(name)){
                cur.mkdir(name);
            }
            cur= cur.getDir(name);
        }
        cur.addFile(dirNames[dirNames.length-1],content);
    }

    public String readContentFromFile(String filePath) {
        String[] dirNames = filePath.split("/");
        Dir cur = root;

        for(int i =0; i< dirNames.length-1; i++){
            String name = dirNames[i];
            if(name.equals("") ) continue;//skip first empty
            if(!cur.isDir(name)){
                return "";
            }
            cur= cur.getDir(name);
        }
        return cur.getFile(dirNames[dirNames.length-1]).getContent();
    }

    class Dir{
        HashMap<String,Dir> subDirs;
        HashMap<String,File> files;
        Set<String> sorted_names; //sorted
        String name;
        public Dir(String name){
            this.name = name;
            subDirs = new HashMap<>();
            files = new HashMap<>();
            sorted_names = new TreeSet<>();//sorted in lexicographic order
        }
        public void mkdir(String dirName){
            if(subDirs.containsKey(dirName)||files.containsKey(dirName)){
                return ; //invalid name
            }
            Dir newDir = new Dir(dirName);
            subDirs.put(dirName,newDir);
            sorted_names.add(dirName);
        }
        public void addFile(String fileName, String content){
            if(subDirs.containsKey(fileName)){
                return;
            }
            File file = files.computeIfAbsent(fileName, k->{
                sorted_names.add(k);
                return new File(k);
            });
            file.append(content);

        }
        public boolean isDir(String name){
            return subDirs.containsKey(name);
        }
        public boolean isFile(String name){
            return files.containsKey(name);
        }
        public Dir getDir(String name){
            return subDirs.get(name);
        }
        public List<String> getChildren(){

            return new ArrayList<String>(sorted_names);
        }
        public File getFile(String name){
            return files.get(name);
        }
    }
    class File{
        String name;
        StringBuilder content;
        public File(String name){
            content = new StringBuilder();
            this.name=name;
        }
        public void append(String c){
            content.append(c);
        }
        public String getContent(){
            return content.toString();
        }
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */
