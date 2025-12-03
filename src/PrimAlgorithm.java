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
//create a vertex that collects the minimum weight key 
// one that stores which vertex were talking about and 
// the current minumum  minimum weight to connect that vertex to the MST (D[v] value)
class pQEntry implements Comparable<pQEntry> {
    int vertex;
    int key;

    public pQEntry(int vertex, int key) {
        this.vertex = vertex;
        this.key = key;
    }
    //create a comparison between to keys and select the smallest
    public int compareTo(pQEntry other) {
        return Integer.compare(this.key, other.key);
    }
}

//create the current graph with the amount of vertices we want to have
class Graph {
    int numVertices;
    List<List<Edge>> edgeList;

    //create a constructor to access the numVertices and connectedList
    //create a array list 
    public Graph(int numVertices) {
        this.numVertices = numVertices;
        edgeList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            edgeList.add(new ArrayList<>());
        }
    }

    public void addEdge(int startVertex, int connectedVertex, int weight) {
        edgeList.get(connectedVertex).add(new Edge(startVertex, connectedVertex, weight));
        edgeList.get(connectedVertex).add(new Edge(connectedVertex, startVertex, weight));
    }

    public List<Edge> primJarnik() {
        // Pick starting vertex s (vertex 0)
        int s = 0;

        // D[v] stores minimum weight to connect v to MST
        int[] D = new int[numVertices];

        // connect[v] stores the edge that achieves D[v]
        Edge[] connect = new Edge[numVertices];

        // Track which vertices are in the tree
        boolean[] inMST = new boolean[numVertices];

        // Initialize D values
        D[s] = 0;
        for (int v = 0; v < numVertices; v++) {
            if (v != s) {
                D[v] = Integer.MAX_VALUE;
            }
        }

        // Initialize priority queue Q
        PriorityQueue<pQEntry> Q = new PriorityQueue<>();
        Map<Integer, pQEntry> vertexToEntry = new HashMap<>();

        for (int v = 0; v < numVertices; v++) {
            pQEntry entry = new pQEntry(v, D[v]);
            Q.add(entry);
            vertexToEntry.put(v, entry);
        }

        // Initialize MST T
        List<Edge> T = new ArrayList<>();

        // Main loop
        while (!Q.isEmpty()) {
            // Get vertex u with minimum key
            pQEntry minEntry = Q.poll();
            int u = minEntry.vertex;

            // Mark u as in MST
            inMST[u] = true;
            vertexToEntry.remove(u);

            // Add edge to MST (except for starting vertex)
            if (connect[u] != null) {
                T.add(connect[u]);
            }

            // Check all edges from u to adjacent vertices
            for (Edge edge : edgeList.get(u)) {
                int v = edge.connectedVertex;
                int weight = edge.weight;

                // If v is still in Q (not in MST yet)
                if (!inMST[v]) {
                    // Check if edge (u,v) better connects v to T
                    if (weight < D[v]) {
                        D[v] = weight;
                        connect[v] = edge;

                        // Update priority queue
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
