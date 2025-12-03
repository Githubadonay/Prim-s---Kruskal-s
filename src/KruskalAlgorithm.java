public class KruskalAlgorithm implements Comparable<KruskalAlgorithm> {
    int startVertex;
    int endVertex;
    int weight;

    public KruskalAlgorithm(int startVertex, int endVertex, int weight) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
    }

    @Override
    public int compareTo(KruskalAlgorithm other) {
        return Integer.compare(this.weight, other.weight);
    }
    
}

class UnionFind {
    int[] parent;
    int[] rank;

    public UnionFind(int numVertices) {
        parent = new int[numVertices];
        rank = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent[vertex]);
        }
        return parent[vertex];
    }

    public boolean union(int vertex1, int vertex2) {
        int root1 = find(vertex1);
        int root2 = find(vertex2);

        if (root1 == root2) {
            return false;
        }

        if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else {
            parent[root2] = root1;
            rank[root1]++;
        }
        return true;
    }
} // Added this closing brace for UnionFind class

class KruskalGraph {
    int numVertices;
    KruskalAlgorithm[] edges;
    int edgeCount;
    int capacity;

    public KruskalGraph(int numVertices) {
        this.numVertices = numVertices;
        this.capacity = 20;
        this.edges = new KruskalAlgorithm[capacity];
        this.edgeCount = 0;
    }

    public void addEdge(int startVertex, int endVertex, int weight) {
        if (edgeCount == capacity) {
            capacity *= 2;
            KruskalAlgorithm[] newEdges = new KruskalAlgorithm[capacity];
            for (int i = 0; i < edgeCount; i++) {
                newEdges[i] = edges[i];
            }
            edges = newEdges;
        }
        edges[edgeCount] = new KruskalAlgorithm(startVertex, endVertex, weight);
        edgeCount++; // Only increment once, and removed the line above that said edgeCount++
    }

    private void sortEdges() {
        for (int i = 0; i < edgeCount - 1; i++) {
            for (int j = 0; j < edgeCount - i - 1; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    KruskalAlgorithm temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    public KruskalAlgorithm[] kruskal() {
        KruskalAlgorithm[] mstEdges = new KruskalAlgorithm[numVertices - 1];
        int mstEdgeCount = 0;

        sortEdges();

        UnionFind uf = new UnionFind(numVertices);

        for (int i = 0; i < edgeCount; i++) {
            KruskalAlgorithm edge = edges[i];
            int root1 = edge.startVertex;
            int root2 = edge.endVertex;

            if(uf.union(root1, root2)) {
                mstEdges[mstEdgeCount] = edge;
                mstEdgeCount++;

                if(mstEdgeCount == numVertices - 1) {
                    break;
                }
            }
        }
        return mstEdges;
    }

    public void printMST(KruskalAlgorithm[] mstEdges) {
        System.out.println("Edges in the Minimum Spanning Tree:");
        int totalWeight = 0;
        for (KruskalAlgorithm edge : mstEdges) {
            System.out.println("Edge:" + edge.startVertex + " - " + edge.endVertex +
                " (Weight: " + edge.weight + ")");
            totalWeight += edge.weight;
        }
        System.out.println("Total MST Weight:" + totalWeight);
    }
}