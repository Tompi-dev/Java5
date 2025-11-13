package com;

import java.util.*;

public class MSTManager {

    public static void main(String[] args) {

        boolean USE_RANDOM = true;

        System.out.println("STEP 0 — INPUT GRAPH");

        Graph graph = USE_RANDOM
                ? createRandomGraph(10, 15)
                : createSampleGraph();

        printGraph(graph);

        System.out.println("STEP 1 — BUILDING MST");

        List<Edge> mst = graph.buildMST();
        printMST(mst);

        System.out.println("STEP 2 — REMOVING EDGE FROM MST");

        Edge edgeToRemove = mst.get(2);
        System.out.println("Removing edge: " + edgeToRemove);
        mst.remove(edgeToRemove);

        System.out.println("STEP 3 — FINDING COMPONENTS");

        List<List<Integer>> components = graph.getComponents(mst, edgeToRemove);
        System.out.println("Component A: " + components.get(0));
        System.out.println("Component B: " + components.get(1));

        System.out.println("STEP 4 — FINDING REPLACEMENT EDGE");

        Edge replacement = graph.findReplacementEdge(
                components.get(0),
                components.get(1)
        );

        System.out.println("Replacement edge: " + replacement);

        System.out.println("STEP 5 — NEW MST");

        mst.add(replacement);
        printMSTWithAdded(mst, replacement);

        System.out.println("SUMMARY");
        System.out.println("Removed edge:     " + edgeToRemove);
        System.out.println("Added edge:       " + replacement);
        System.out.println("MST successfully reconnected.");
    }


    private static Graph createRandomGraph(int n, int edgesCount) {
        Random rand = new Random();
        Graph g = new Graph(n);

        for (int i = 0; i < edgesCount; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);

            if (u == v) {
                i--;
                continue;
            }

            int w = rand.nextInt(10) + 1;
            g.addEdge(u, v, w);
        }

        return g;
    }

    private static Graph createSampleGraph() {
        Graph g = new Graph(6);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 3, 4);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 5, 6);
        return g;
    }

    private static void printGraph(Graph g) {
        for (Edge e : g.edges)
            System.out.println(e);
    }

    private static void printMST(List<Edge> mst) {
        for (Edge e : mst)
            System.out.println(e);
    }

    private static void printMSTWithAdded(List<Edge> mst, Edge added) {
        for (Edge e : mst) {
            if (e == added)
                System.out.println(e + "   <-   қосылды");
            else
                System.out.println(e);
        }
    }
}
