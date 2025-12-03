import java.util.*;

//create a input storage, where the vertex starts
//The connected vertex
//and how much each edge cost
class Edge {
    int startVertex;
    int connectedVertex;
    int weight;

    public Edge(int startVertex, int connectedVertex, int weight) {
        this.startVertex = startVertex;
        this.connectedVertex = connectedVertex;
        this.weight = weight;
    }
}

// create a vertex that collects the minimum weight key
// one that stores which vertex we're talking about and
// the current minumum weight to connect that vertex to the MST (D[v]
// value)
class pQEntry implements Comparable<pQEntry> {
    int vertex;
    int key;

    public pQEntry(int vertex, int key) {
        this.vertex = vertex;
        this.key = key;
    }

    // create a comparison between to keys and select the smallest
    public int compareTo(pQEntry other) {
        return Integer.compare(this.key, other.key);
    }
}

// create the current graph with the amount of vertices we want to have
class Graph {
    int numVertices;
    List<List<Edge>> edgeList;

    // create a constructor to access the numVertices and connectedList
    // create a array list that holds all the edge list
    public Graph(int numVertices) {
        this.numVertices = numVertices;
        edgeList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            edgeList.add(new ArrayList<>());
        }
    }

    // create a collector that allows you to add startverted,connectedVertex and
    // weight
    // allow that collector to be able to be called on in the imp class
    public void addEdge(int startVertex, int connectedVertex, int weight) {
        edgeList.get(startVertex).add(new Edge(startVertex, connectedVertex, weight));
        edgeList.get(connectedVertex).add(new Edge(connectedVertex, startVertex, weight));
    }

    // Make the starting vertex = 0
    // when we create the graph we will have it start with 0
    // Also track which vertices are in the tree
    public List<Edge> primJarnik() {
        int startV = 0;
        int[] D = new int[numVertices];
        Edge[] connect = new Edge[numVertices];

        boolean[] inMST = new boolean[numVertices];
        // create a starting vertices with a distance of 0
        D[startV] = 0;
        for (int v = 0; v < numVertices; v++) {
            if (v != startV) {
                D[v] = Integer.MAX_VALUE;
            }
        }

        // Initialize a priority queue Q with an entry (D[v],v) for each vertex v.
        PriorityQueue<pQEntry> Q = new PriorityQueue<>();
        Map<Integer, pQEntry> vertexToEntry = new HashMap<>();
        // add all created vertices to the prioprity queue
        for (int v = 0; v < numVertices; v++) {
            pQEntry entry = new pQEntry(v, D[v]);
            Q.add(entry);
            vertexToEntry.put(v, entry);
        }

        List<Edge> T = new ArrayList<>();
        //create a loop, if Q is not empty 
        //make the Q pick the min and once its used remove 
        //This starts from V 0 it checks whos its connected to and picks the smalled edge to add
        //once its done it checks for the next smalles until it addes all.
        while (!Q.isEmpty()) {
            pQEntry minEntry = Q.poll();
            int u = minEntry.vertex;

            inMST[u] = true;
            vertexToEntry.remove(u);

            if (connect[u] != null) {
                T.add(connect[u]);
            }
            for (Edge edge : edgeList.get(u)) {
                int v = edge.connectedVertex;
                int weight = edge.weight;

                if (!inMST[v]) {

                    if (weight < D[v]) {
                        D[v] = weight;
                        connect[v] = edge;

                        Q.remove(vertexToEntry.get(v));
                        pQEntry newEntry = new pQEntry(v, D[v]);
                        Q.add(newEntry);
                        vertexToEntry.put(v, newEntry);
                    }
                }
            }
        }

        return T;
    }
    

    public void printMST(List<Edge> mst) {
        System.out.println("Minimum Spanning Tree:");
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println("Edge: " + edge.startVertex + " - " + edge.connectedVertex +
                    " (Weight: " + edge.weight + ")");
            totalWeight += edge.weight;
        }
        System.out.println("Total MST Weight: " + totalWeight);
    }
}
