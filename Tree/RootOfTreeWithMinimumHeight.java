import java.util.ArrayList;
import java.util.List;

public class RootOfTreeWithMinimumHeight {
    //  Method to return roots which gives minimum height to tree

    //Start pointers from all leaf nodes and move one step inside each time, keep combining pointers which overlap while moving,
    // at the end only one pointer will remain on some vertex or two pointers will remain at one distance away.
    // Those node represent the root of the vertex which will minimize the height of the tree.

    //bfs + count of neighbour
    //define leaf as node with countOfneighbour==1
    List<Integer> rootForMinimumHeight(){

//        queue<int> q;
//
//        //  first enqueue all leaf nodes in queue
//        for (int i = 0; i < V; i++)
//            if (degree[i] == 1)
//                q.push(i);
//
//        //  loop untill total vertex remains less than 2
//        while (V > 2)
//        {
//            for (int i = 0; i < q.size(); i++)
//            {
//                int t = q.front();
//                q.pop();
//                V--;
//
//                // for each neighbour, decrease its degree and
//                // if it become leaf, insert into queue
//                for (auto j = adj[t].begin(); j != adj[t].end(); j++)
//                {
//                    degree[*j]--;
//                    if (degree[*j] == 1)
//                    q.push(*j);
//                }
//            }
//        }
//
//        //  copying the result from queue to result vector
//        vector<int> res;
//        while (!q.empty())
//        {
//            res.push_back(q.front());
//            q.pop();
//        }
//        return res;
        return null;
    }
}
