package tree;

public class MorisTraversal {

    public void inOrder(Node root){
        Node cur = root;
        while(cur!=null){

            if(cur.left==null){
                System.out.println(cur.val);
                cur=cur.right;
            }else{
                Node predecessor = findPredecessor(cur);
                if(predecessor.right==null){
                    predecessor.right=cur;
                    cur=cur.left;
                }else{
                    //predecessor pointed to cur node already, all left subtree is done
                    predecessor.right=null;
                    System.out.println(cur.val);
                    cur=cur.right;
                }

            }

        }


    }

    public Node findPredecessor(Node node){
        Node cur = node.left;
        while(cur.right!=null && cur.right!=node){
            cur=cur.right;
        }
        return cur;
    }


    public void preOrder(Node root){
        Node cur= root;

        while(cur!=null){
            if(cur.left==null){
                System.out.println(cur.val);
                cur=cur.right;
            }else{
                Node predecessor = findPredecessor(cur);
                if(predecessor.right==null){//havent traverse left sub yet
                    System.out.println(cur.val);
                    predecessor.right=cur;
                    cur=cur.left;
                }else{
                    predecessor.right=null;
                    cur=cur.right;
                }

            }

        }

    }
    public void postorderMorrisTraversal(Node root) {
        Node cur = root;
        while (cur != null) {
            if (cur.left == null) {
                cur = cur.right;
            } else {
                Node predecessor = findPredecessor(cur);
                if (predecessor.right == null) {
                    predecessor.right = cur;
                    cur = cur.left;
                } else {
                    //printReverse(cur.left,predecessor);//print reversely from cur.left to pred by using the algo the as if reverse a linkedlist
                    //keep append cur to the head of the linked list
                    //next=cur.next cur.next=prev cur=next
                    predecessor.right = null;
                    cur = cur.right;

                }

            }
        }
        //root , to right most ??? dummy head??
       
    }

    
    //another version of post order in C found from  stack overflow
    //This is Post Order :children before node( L ,R , N) 
// void morrisPostorderTraversal(Node *root){

//     // Making our tree left subtree of a dummy Node
//     Node *dummyRoot = new Node(0);
//     dummyRoot->left = root;
    
//     //Think of P as the current node 
//     Node *p = dummyRoot, *pred, *first, *middle, *last;
//     while(p!=NULL){        
    
//         if(p->left == NULL){
//             p = p->right;
//         } else{
//             /* p has a left child => it also has a predeccessor
//                make p as right child predeccessor of p    
//             */
//             pred = p->left;
//             while(pred->right!=NULL && pred->right != p){
//                 pred = pred->right;
//             }
    
//             if(pred->right == NULL){ 
    
//                 // predeccessor found for first time
//                 // modify the tree
    
//                 pred->right = p;    
//                 p = p->left;
    
//             }else {                          
    
//                // predeccessor found second time
//                // reverse the right references in chain from pred to p
//                 first = p;
//                 middle = p->left;              
//                 while(middle!=p){            
//                     last = middle->right;
//                     middle->right = first;
//                     first = middle;
//                     middle = last;
//                 }
    
//                 // visit the nodes from pred to p
//                 // again reverse the right references from pred to p    
//                 first = p;
//                 middle = pred;
//                 while(middle!=p){
    
//                     cout<<" "<<middle->data;  
//                     last = middle->right;          
//                     middle->right = first;
//                     first = middle;
//                     middle = last;
//                 }
    
//                 // remove the pred to node reference to restore the tree structure
//                 pred->right = NULL;    
//                 p = p-> right;
//             }
//         }
//     }    
//     }

}
