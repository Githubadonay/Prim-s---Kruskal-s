import java.util.List;
import java.util.Random;

public class Imp {

    // build a random graph
    private static Graph RandomGraph(int nVertex, int edgesPerVertex) {
        Graph graphed = new Graph(nVertex);
        Random random = new Random();
        // create a connected graph
        for (int i = 0; i < nVertex - 1; i++) {
            int w = random.nextInt(100) + 1;
            graphed.addEdge(i, i + 1, w);
        }
        // create extra random edges
        for (int v = 0; v < nVertex; v++) {
            for (int k = 0; k < edgesPerVertex; k++) {
                int u = random.nextInt(nVertex);
                if (u != v) {
                    int w = random.nextInt(100) + 1;
                    graphed.addEdge(v, u, w);
                }
            }
        }

        return graphed;
    }

    // run Prim time complexity
    private static void primTimeComplexity(int nVertex) {
        int edgesPerVertex = 2;
        Graph graphed = RandomGraph(nVertex, edgesPerVertex);

        long start = System.currentTimeMillis();
        List<Edge> mst = graphed.primJarnik();
        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.println(
                "Prim on nVertex = " + nVertex + " took " + elapsed + " milliseconds, MST edges = " + mst.size());
    }

    private static KruskalGraph RandomGraphKruskal(int nVertex, int edgesPerVertex) {
        KruskalGraph graphed = new KruskalGraph(nVertex);
        Random random = new Random();

        // create a connected graph
        for (int i = 0; i < nVertex - 1; i++) {
            int w = random.nextInt(100) + 1;
            graphed.addEdge(i, i + 1, w);
        }
        // create extra random edges
        for (int v = 0; v < nVertex; v++) {
            for (int k = 0; k < edgesPerVertex; k++) {
                int u = random.nextInt(nVertex);
                if (u != v) {
                    int w = random.nextInt(100) + 1;
                    graphed.addEdge(v, u, w);
                }
            }
        }

        return graphed;
    }

    private static void kruskalTimeComplexity(int nVertex) {
        int edgesPerVertex = 2;
        KruskalGraph graphed = RandomGraphKruskal(nVertex, edgesPerVertex);

        long start = System.currentTimeMillis();
        KruskalAlgorithm[] mst = graphed.kruskal();
        long end = System.currentTimeMillis();

        long elapsed = end - start;
        System.out.println(
                "Kruskal on nVertex = " + nVertex + " took " + elapsed + " milliseconds, MST edges = " + mst.length);
    }

    public static void main(String[] args) {
        // Prims
        Graph graph = new Graph(6);

        graph.addEdge(0, 1, 8);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 9);
        graph.addEdge(2, 3, 3);
        graph.addEdge(2, 4, 4);
        graph.addEdge(4, 5, 7);

        System.out.println("Prim's Algorithm on graph with 6 vertices):");
        List<Edge> mstSmall = graph.primJarnik();
        graph.printMST(mstSmall);

        System.out.println("\nTiming Prim's Algorithm on larger graphs");
        primTimeComplexity(100);
        primTimeComplexity(1000);
        primTimeComplexity(10000);

        // Kruskal
        System.out.println("\nKruskal's Algorithm:\n");

        System.out.println("\nKruskal's Algorithm on graph with 6 vertices):");
        KruskalGraph kruskalGraph = new KruskalGraph(6);

        kruskalGraph.addEdge(0, 1, 8);
        kruskalGraph.addEdge(0, 2, 5);
        kruskalGraph.addEdge(1, 2, 9);
        kruskalGraph.addEdge(2, 3, 3);
        kruskalGraph.addEdge(2, 4, 4);
        kruskalGraph.addEdge(4, 5, 7);

        KruskalAlgorithm[] mstKruskal = kruskalGraph.kruskal();

        kruskalGraph.printMST(mstKruskal);

        System.out.println("\nTiming Kruskal's Algorithm on larger graphs");
        kruskalTimeComplexity(100);
        kruskalTimeComplexity(1000);
        kruskalTimeComplexity(10000);
    }
}