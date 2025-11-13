package com;

import java.util.*;

public class Graph {
    int n;
    List<Edge> edges = new ArrayList<>();

    public Graph(int n) {
        this.n = n;
    }

    public void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
    }

    // Kruskal
    public List<Edge> buildMST() {
        Collections.sort(edges);
        UF uf = new UF(n);

        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            if (uf.find(e.u) != uf.find(e.v)) {
                uf.union(e.u, e.v);
                mst.add(e);
            }
        }
        return mst;
    }

    // Create components after removing one edge
    public List<List<Integer>> getComponents(List<Edge> mst, Edge removed) {

        // adjacency list of MST without removed edge
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) adj.put(i, new ArrayList<>());

        for (Edge e : mst) {
            adj.get(e.u).add(e.v);
            adj.get(e.v).add(e.u);
        }

        List<Integer> compA = new ArrayList<>();
        boolean[] visited = new boolean[n];
        dfs(removed.u, adj, visited, compA);

        List<Integer> compB = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (!visited[i]) compB.add(i);

        return List.of(compA, compB);
    }

    private void dfs(int node, Map<Integer, List<Integer>> adj, boolean[] visited, List<Integer> comp) {
        visited[node] = true;
        comp.add(node);

        for (int next : adj.get(node))
            if (!visited[next])
                dfs(next, adj, visited, comp);
    }

    public Edge findReplacementEdge(List<Integer> A, List<Integer> B) {
        Edge best = null;

        for (Edge e : edges) {
            boolean c1 = A.contains(e.u) && B.contains(e.v);
            boolean c2 = A.contains(e.v) && B.contains(e.u);

            if (c1 || c2) {
                if (best == null || e.w < best.w)
                    best = e;
            }
        }
        return best;
    }
}
