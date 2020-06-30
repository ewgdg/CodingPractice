package tree.LCA;

import java.util.*;

public class LCA_SquareRootDecomposition {
    // for a large amount of queries

    // Time Complexity for Preprocessing: O n
    //
    // Time Complexity for answering each query: SquareRoot of N

    // https://www.hackerrank.com/topics/lowest-common-ancestor
    // In this method we would be dividing our tree into equal-sized parts to make
    // the process of answering queries faster

    // each part is a section of height = square(h)
    // Now, in the preprocessing step for each node , we store a node
    // (critical_ancestor)
    // such that it is an ancestor of the current node and it is located at the
    // bottom-most level of the previous/upper section of the current node
    Map<Integer, Integer> critical_ancestors = new HashMap<>();
    int h;

    public void preprocess(Map<Integer, Set<Integer>> tree) {
        Map<Integer, Integer> parents = new HashMap<>();
        // Map<Integer, Set<Integer>> tree;
        Map<Integer, Integer> levels = new HashMap<>();
        h = 0;// set h to max height;
        // find min height root or any root;

        int cur_level = 0;
        traverse(0, 0, 1, cur_level, tree, parents, levels, critical_ancestors);

    }

    // helper to find cri_ancestor for each node
    public void traverse(int cur, int section_head, int prev_section, int cur_level, Map<Integer, Set<Integer>> tree,
            Map<Integer, Integer> parents, Map<Integer, Integer> levels, Map<Integer, Integer> critical_ancestors) {
        // section_head is the top ancestor of cur that belongs to the same section

        // int current_section = (int) Math.sqrt( levels.get(cur) ); //another way to
        // split the sections

        // to find the correct ancestor node, we need to find when first time the
        // section is changed
        // we can record the current section and compare to prev section
        // or we can check level%sqrt(n)==0 to know it

        // if cur row is the new section head
        // we can set ancestor to cur.parent
        // otherwise
        // we can set ancestor to ancestor of cur.parent. // cur and cur.parent share
        // the same ancestor
        // or set ancestor to head.parent

        levels.put(cur, cur_level);
        int current_section = levels.get(cur) / (int) Math.sqrt(h);
        if (current_section == prev_section + 1) {
            // find a new section
            // record head
            section_head = cur;

        }
        // get ancestor of upper section
        int cri_ancestor=0;
        if(current_section==0) cri_ancestor=0;
        else cri_ancestor = parents.get(section_head);
        critical_ancestors.put(cur, cri_ancestor);

        // dfs
        for (int child : tree.getOrDefault(cur, new HashSet<>())) {
            parents.put(child, cur);
            if (child != parents.get(cur)) {
                traverse(child, section_head, current_section, cur_level + 1, tree, parents, levels,
                        critical_ancestors);
            }
        }

    }

    public int getLCA(int a, int b, Map<Integer, Integer> parents, Map<Integer, Integer> levels) {

        // lifting candidate a and b to same section
        while (critical_ancestors.get(a) != critical_ancestors.get(b)) {
            if (levels.get(a) > levels.get(b)) {
                a = critical_ancestors.get(a);
            } else {
                b = critical_ancestors.get(b);
            }
        }

        // traverse the section from a or b to find lca
        while (a != b) {
            if (levels.get(a) > levels.get(b)) {
                a = parents.get(a);
            } else {
                b = parents.get(b);
            }

        }

        return a;

    }

}
