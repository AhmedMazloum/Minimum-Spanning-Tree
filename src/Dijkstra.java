import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {
    private Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    /*
      Description: Constructor set
      Pre-condition: none
       Post-condition: none
        */
    public Dijkstra() {}
    /*
      Description: Adds edge to the shortest path
      Pre-condition: src,dest,and weight needs to set
      Post-condition: nodes are added to hash map
          */
    public void addEdge(int src, int dest, int weight) {
        Node node1 = nodes.get(src);
        if (node1 == null) {
            node1 = new Node(src);
        }

        Node node2 = nodes.get(dest);
        if (node2 == null) {
            node2 = new Node(dest);
        }

        node1.addNeighbor(node2, weight);
        node2.addNeighbor(node1, weight);

        nodes.put(src, node1);
        nodes.put(dest, node2);
    }

    /*
      Description: Finds the shortest path
      Pre-condition: needs a starting point and endpoint
      Post-condition: Finds the shortest path between the two points
          */
    public List<Integer> shortestPath(int startNodeName, int endNodeName) {
        // key node, value parent
        Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        PriorityQueue<PathNode> temp = new PriorityQueue<PathNode>();

        PathNode start = new PathNode(startNodeName, 0, 0);
        temp.add(start);

        while (temp.size() > 0) {
            PathNode currentPathNode = temp.remove();

            if (!visited.contains(currentPathNode.name)) {
                Node currentNode = nodes.get(currentPathNode.name);
                parents.put(currentPathNode.name, currentPathNode.parent);
                visited.add(currentPathNode.name);

                // return the shortest path if end node is reached
                if (currentPathNode.name == (endNodeName)) {
                    return getPath(parents, endNodeName);
                }

                Node[] neighbors = nodes.get(currentPathNode.name).getNeighbors();
                for (int i = 0; i < neighbors.length; i++) {
                    Node neighbor = neighbors[i];

                    int distance2root = currentPathNode.distance2root + currentNode.getNeighborDistance(neighbor);
                    // PriorityQueue ensure that the node with shortest distance to the root is put at the
                    // head of the queue
                    temp.add(new PathNode(neighbor.name, currentPathNode.name, distance2root));

                }

            }
        }
        return null;
    }
    /*
    Description: Prints the path that was taken
    Pre-condition: hash map and int of the endNodeName are passed in
    Post-condition: Displays the path taken for the shortest path
          */
    private List<Integer> getPath(Map<Integer, Integer> parents, int endNodeName) {
        List<Integer> path = new ArrayList<Integer>();
        int node = endNodeName;
        while (node != 0) {
            path.add(0, node);
            int parent = parents.get(node);
            node = parent;
        }
        return path;
    }


}

class Node {
    int name;
    // key: neighbor; value: distance from this node to the neighbor
    Map<Node, Integer> neighbors = new HashMap<Node, Integer>();
    /*
     Description: Sets the name for the node
     Pre-condition: name is passed in
     Post-condition: Sets the name for the node
          */
    public Node(int name) {
        this.name = name;
    }
    /*
    Description: Gets the name for the node
    Pre-condition: none
    Post-condition: Gets the name for the node
     */
    public int getName() {
        return this.name;
    }
    /*
     Description: Adds neighbor the nodes
     Pre-condition: node and int needs to be passed in
     Post-condition: adds the value to neighbor for the nodes
      */
    public void addNeighbor(Node neighbor, int distance) {
        neighbors.put(neighbor, distance);
    }
    /*
        Description: Stores node neighbors size in result
        Pre-condition: none
        Post-condition: Stores node neighbors size in result
         */
    public Node[] getNeighbors() {
        Node[] result = new Node[neighbors.size()];
        neighbors.keySet().toArray(result);
        return result;
    }

    /*
        Description: gets neighbors at the node position
        Pre-condition: node is passed in
        Post-condition: gets neighbors at the node position
         */
    public int getNeighborDistance(Node node) {
        return neighbors.get(node);
    }

    /*
Description: checks if name is equal to node another name is true or false
Pre-condition: Passes in object called another
Post-condition: returns true or false
        */
    @Override
    public boolean equals(Object another) {
        return name == (((Node) another).name);
    }
}
/*
Description: Class path node
Pre-condition: none
Post-condition: none
        */
class PathNode implements Comparable<PathNode> {
    int name;
    int parent;
    // distance to the root
    int distance2root;
    /*
 Description: Sets the values for name, parent, and distance2root
 Pre-condition: Passes in object called another
 Post-condition:  name, parent, abd distance2root is set
            */
    public PathNode(int name, int parent, int distance2root) {
        this.name = name;
        this.parent = parent;
        this.distance2root = distance2root;

    }
    /*
 Description: CompareTo find the difference between distance2root from another distance2root
 Pre-condition: PathNode another is passed in
 Post-condition: returns difference between distance2root from another distance2root
            */
    @Override
    public int compareTo(PathNode another) {
        return distance2root - another.distance2root;
    }
    /*
 Description: Returns the this.name, this.parent, and this.distance2root as a message
 Pre-condition: none
 Post-condition: Returns the this.name, this.parent, and this.distance2root as a message
                */
    @Override
    public String toString() {
        return "(" + this.name + "," + this.parent + "," + this.distance2root +")";
    }
}

