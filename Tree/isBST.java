public class isBST {

    public static boolean solution(Node root){
        return check_helper(root,null,null);
    }

    public static boolean check_helper(Node root,Integer min, Integer max ){ //cannot directly check sub tree bc we need to check the range from upper layer
        if(root==null){
            return true;
        }

        if(min!=null&& root.val<min ){
            return false;
        }
        if(max!=null&& root.val>max ){
            return false;
        }


        return check_helper(root.left,min,root.val) && check_helper(root.right,root.val,max);





    }

    static class Reference{
        Node prev;
        public Reference(Node node){
            prev=node;
        }
    }

    //in order with static prev //wrong method ,cannot check right child's prev
    public static boolean  check(Node root,Reference prev){

        if(root==null){
            prev.prev=null;
            return true;
        }
//        if(prev.prev!=null&& prev.prev.val> root.val){
//            return false;
//        }
        check(root.left,prev);
        if(prev.prev!=null && prev.prev.val > root.val){
            return false;
        }

        check(root.right,prev);

        prev.prev=root.right;
        return true;
    }



    public static void main( String[] args){






        Node root =  new Node(1);
        root.left = new Node(0);
        root.left.right = new Node(1);//wrong node
        root.right = new Node(3);
        root.right.left = new Node(0);
        root.right.right = new Node(4);


        System.out.println(solution(root));
        System.out.println(check(root,new Reference(null)));

    }

}
