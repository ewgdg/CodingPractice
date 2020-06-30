package Tree;

public class BST<T extends Comparable<T>> {
    public class Node {
        public T data;
        public Node left;
        public Node right;
        public Node parent;

        public Node(T val) {
            data = val;
        }
    }

    public Node root;
    public int size;

    public void insert(T val) {
        root = insertHelper(val, root, null);
        size++;
    }

    public Node insertHelper(T val, Node node, Node parent) {
        if (node == null) {
            node = new Node(val);
            node.parent = parent;
            return node;
        }
        if (val.compareTo(node.data) >= 0) {
            node.right = insertHelper(val, node.right, node);
        } else {
            node.left = insertHelper(val, node.left, node);
        }
        return node;

    }

    public void insertFrom(T val, Node node) {
        if (node == null) {
            return;
        }
        size++;
        while (true) {
            if (val.compareTo(node.data) >= 0) {
                if (node.right == null) {
                    node.right = new Node(val);
                    node.right.parent = node;
                    return;
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    node.left = new Node(val);
                    node.left.parent = node;
                    return;
                } else {
                    node = node.left;
                }
            }
        }

    }

    public void delete(T val) {
        root = deleteHelper(val, root, null);

    }

    public void deleteNode(Node node) {
        if (node == null) return;
        root = deleteHelper(node, root, null);

    }

    public Node deleteHelper(T val, Node node, Node parent) {
        if (node == null) return null;

        if (node.data.compareTo(val) == 0) {
            size--;
            if (node.left == null) {
                if (node.right != null)
                    node.right.parent = parent;
                return node.right;
            } else if (node.right == null) {
                if (node.left != null)
                    node.left.parent = parent;
                return node.left;
            } else {
                Node pred = removePredecessor(node);
                //replace
                pred.left = node.left;
//                    if(pred!=node.right){
                pred.right = node.right;
//                    }
                node = pred;
                node.parent = parent;

            }
        } else if (val.compareTo(node.data) > 0) {
            node.right = deleteHelper(val, node.right, node);
        } else {
            node.left = deleteHelper(val, node.left, node);
        }
        return node;

    }

    public Node deleteHelper(Node toDelete, Node node, Node parent) {
        if (node == null) return null;

        if (node.equals(toDelete)) {
            size--;
            if (node.left == null) {
                if (node.right != null)
                    node.right.parent = parent;
                return node.right;
            } else if (node.right == null) {
                if (node.left != null)
                    node.left.parent = parent;
                return node.left;
            } else {
                Node pred = removePredecessor(node);
                //replace
                pred.left = node.left;
//                    if(pred!=node.right){
                pred.right = node.right;
//                    }
                node = pred;
                node.parent = parent;

            }
        } else if (toDelete.data.compareTo(node.data) >= 0) {
            node.right = deleteHelper(toDelete, node.right, node);
        } else {
            node.left = deleteHelper(toDelete, node.left, node);
        }
        return node;

    }

    public Node removePredecessor(Node node) {
        Node cur = node.left;
        Node parent = node;

        while (cur.right != null) {
            parent = cur;
            cur = cur.right;
        }
        if (parent == node) {//such that we dont need to check if(pred!=node.right)
            parent.right = cur.right; //we know cur.left is null as pred
            if (cur.right != null) cur.right.parent = parent;
        } else {
            parent.left = cur.right;
            if (cur.right != null) cur.right.parent = parent;
        }
        return cur;
    }

    public Node getPred(Node node) {
        Node cur = node.left;
        if (cur != null) {
            while (cur.right != null) {
                cur = cur.right;
            }
        } else {
            //search ancestor
            cur = node;
            while (cur.parent != null && cur.parent.left == cur) {
                cur = cur.parent;
            }
            //cur.parent.right==cur;
            cur = cur.parent;
        }
        return cur;
    }

    public Node getSucc(Node node) {
        Node cur = node.right;
        if (cur != null) {
            while (cur.left != null) {
                cur = cur.left;
            }
        } else {
            //search ancestor
            cur = node;
            while (cur.parent != null && cur.parent.right == cur) {
                cur = cur.parent;
            }
            //cur.parent.left==cur;
            cur = cur.parent;
        }

        return cur;
    }

    public boolean contains(T num) {

        Node cur = root;
        while (cur != null) {
            if (num.compareTo(num) == 0) {
                return true;
            } else if (num.compareTo(num) > 0) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return false;
    }

}
