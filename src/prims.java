import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class prims {

    static class Edge {
        int src;
        int dest;
        int weight;
        /*
            Description: Edge values being set
            Pre-condition: none
            Post-condition: Edge has values from main
             */
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }
    /*
        Description: Heap node is created
        Pre-condition: none
        Post-condition: Heap node has two values
         */
    static class HeapNode {
        int vertex;
        int key;
    }
    /*
        Description: Result set is created
        Pre-condition: none
        Post-condition: Result set has two values
         */
    static class ResultSet {
        int parent;
        int weight;
    }

    static class Graph {
        int V;
        LinkedList<Edge>[] adjacencylist;
        /*
       Description: Graph has a link list and link list is set
       Pre-condition: Vertices need to be passed in
       Post-condition: link list is set based on vertices
        */
        Graph(int V) {
            this.V = V;
            adjacencylist = new LinkedList[V];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < V; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }
        /*
            Description: Adds edge to prims
            Pre-condition: Needs a src, dest, and weight from the file
            Post-condition: Edge is added to prims
             */
        public void addEdge(int src, int dest, int weight, ObjectOutputStream output) throws IOException {
            Edge edge = new Edge(src, dest, weight);
            adjacencylist[src].addFirst(edge);

            edge = new Edge(dest, src, weight);
            adjacencylist[dest].addFirst(edge); //for undirected graph
            System.out.println("add edge " + src + " " + dest + " with weight " + weight);
            output.writeObject("add edge " + src + " " + dest + " with weight " + weight + "\n");
        }

        /*
            Description: Prims graph is created
            Pre-condition: none
            Post-condition: Prims graph is created using the link list and a priory queue
             */
        public void primMST() {

            boolean[] inPriorityQueue = new boolean[V];
            ResultSet[] resultSet = new ResultSet[V];
            int[] key = new int[V];  //keys used to store the key to know whether priority queue update is required

         //create heapNode for all the vertices
            HeapNode[] heapNodes = new HeapNode[V];
            for (int i = 0; i < V; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
                resultSet[i].parent = -1;
                inPriorityQueue[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            //decrease the key for the first index
            heapNodes[0].key = 0;

            //add all the vertices to the priority queue
            PriorityQueue<HeapNode> pq = new PriorityQueue<>(V,
                    new Comparator<HeapNode>() {
                        @Override
                        public int compare(HeapNode o1, HeapNode o2) {
                            return o1.key - o2.key;
                        }
                    });
            //add all the vertices to priority queue
            for (int i = 0; i < V; i++) {
                pq.offer(heapNodes[i]);
            }

            //while priority queue is not empty
            while (!pq.isEmpty()) {
                //extract the min
                HeapNode extractedNode = pq.poll();

                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                inPriorityQueue[extractedVertex] = false;

                //iterate through all the adjacent vertices
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is present in heap
                    if (inPriorityQueue[edge.dest]) {
                        int dest = edge.dest;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update if
                        if (key[dest] > newKey) {
                            decreaseKey(pq, newKey, dest);
                            //update the parent node for destination
                            resultSet[dest].parent = extractedVertex;
                            resultSet[dest].weight = newKey;
                            key[dest] = newKey;
                        }
                    }
                }
            }
            //print mst
            printMST(resultSet);
        }
        /*
            Description: Decreases the key for the primMST
            Pre-condition: Priority Queue, newKey, and vertex are passed in
            Post-condition: Decreases the key for the primMST
             */
        public void decreaseKey(PriorityQueue<HeapNode> pq, int newKey, int vertex) {

            //iterate through nodes in priority queue and update the key for the vertex
            Iterator it = pq.iterator();

            while (it.hasNext()) {
                HeapNode heapNode = (HeapNode) it.next();
                if (heapNode.vertex == vertex) {
                    pq.remove(heapNode);
                    heapNode.key = newKey;
                    pq.offer(heapNode);
                    break;
                }
            }
        }
        /*
           Description: Prints the MST graph
           Pre-condition: Result is passed in from primMST
           Post-condition: Prints graph when primsMST is called
     */
        public void printMST(ResultSet[] resultSet) {
            int total_min_weight = 0;
            System.out.println("Minimum Spanning Tree: ");
            for (int i = 1; i < V; i++) {
                System.out.println("Edge: " + i + " - " + resultSet[i].parent +
                        " weight: " + resultSet[i].weight);
                total_min_weight += resultSet[i].weight;
            }
            System.out.println("Shortest Path: " + total_min_weight);
        }
        /*
          Description: Increments a weight from a vertex to vertex either from the file or user
          Pre-condition: src, dest, and weight needs to have a value entered by the user or file
          Post-condition: Increments a weight from vertex to vertex either from the file or user
    */
        public void increase(int src, int dest, int weight, ObjectOutputStream output) throws IOException {
            boolean found;
            if(src < V) {
                for (int i = 0; i < adjacencylist[src].size(); i++) {
                    Edge edge = adjacencylist[src].get(i);
                    edge.weight += weight;
                    found = true;

                }
            }
            else {
                System.out.println("Error invalid vertex");
               output.writeObject("Error invalid vertex"+ "\n");
            }

        }
        /*
          Description: Decrements a weight from a vertex to vertex either from the file or user
          Pre-condition: src, dest, and weight needs to have a value entered by the user or file
          Post-condition: Decrements a weight from vertex to vertex either from the file or user
    */
        public void decrease(int src, int dest, int weight){
            boolean found1;
            for(int i =0; i<  adjacencylist[src].size(); i++){
                Edge edge1 = adjacencylist[src].get(i);
                edge1.weight -= weight;
                found1 = true;
                break;
            }

        }
    }

}






