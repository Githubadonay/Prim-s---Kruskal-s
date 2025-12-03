import java.util.List;

public class Imp {
    public static void main(String[] args) {
        // Create a graph with 5 vertices
        Graph graph = new Graph(6);

        // Add edges (v1, v2, weight)
        graph.addEdge(0, 1, 8);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 9);
        graph.addEdge(2, 3, 3);
        graph.addEdge(2, 4, 4);
        graph.addEdge(4, 5, 7);

        // Run Prim's algorithm
        List<Edge> mst = graph.primJarnik();

        // Print the MST
        graph.printMST(mst);

        System.out.println("\n Kruskal's Algorithm:\n");

        KruskalGraph kruskalGraph = new KruskalGraph(6);

        kruskalGraph.addEdge(0, 1, 8);
        kruskalGraph.addEdge(0, 2, 5);
        kruskalGraph.addEdge(1, 2, 9);
        kruskalGraph.addEdge(2, 3, 3);
        kruskalGraph.addEdge(2, 4, 4);
        kruskalGraph.addEdge(4, 5, 7);

        KruskalAlgorithm[] mstKruskal = kruskalGraph.kruskal();

        kruskalGraph.printMST(mstKruskal);
    }
}