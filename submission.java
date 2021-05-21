// Jordan Mossammaparast (22774949), Stanley McFarlane (22724248)

import java.util.*;
import java.lang.Math;

class Edge {
    public int flow;
    public int capacity; //capacity of an edge
    public int nodeA; //src vertex for an edge
    public int nodeB; //dst vertex for an edge
    public Edge residual; //the residual edge

    /*
     * Constructor for class Edge
     * @param nodeA the src node of the edge
     * @param nodeB the dst node of the edge
     * @param capacity the capacity of the edge
     */
    public Edge(int nodeA, int nodeB, int capacity) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.capacity = capacity;
    }

    /*
     * A method to check if a Edge is a residual edge or not
     * @return returns a boolean 
     */
    public boolean isResidual() {
        return capacity == 0;
    }

    /*
     * function to get remaining capacity after flow
     * @return returns the value of capacity - flow
     */
    public int remainingCapacity() {
        try {
            return capacity - flow;
        }
        catch (NullPointerException e) {
            return 0;
        }
    }

    /*
     * creates an augmented path with a specified flow
     * @param bottleNeck a bottleneck value provided from other path speeds
     */
    public void augment(int bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }
}

/*
 * A class node to represent vertices of the graph and the edges connected to the vertices
 */
class Node {
    Edge[] edges; //the associated edges with a node

    /*
     * Constructor for class node
     * @param adjrow a row of the adjlist 2d array
     * @param adjlength the length of adjrow
     * @param index the index of the vertex
     * @param speedsrow a row of the speeds 2d array
     */
    public Node(int[] adjrow, int adjlength, int index, int[] speedsrow) {
        for(int j = 0; j < adjlength; j++) {
            edges = new Edge[adjlength];
            edges[j] = new Edge(index, adjrow[j], speedsrow[j]);
            edges[j].residual = new Edge(adjrow[j], index, 0);
        }
    }
}

public class MyProject implements Project {
    int paths;
	int[] level;
    Node[] graph; 
    int[] next;
	
	public MyProject() {
	}

    /*
     * This method determines if all devices in the network are connected based on adjlist.
     * @param adjlist the structure of the network being analysed.
     * @return boolean value depending on whether all devices are connected or not. True for connected, false for not connected. 
     */
    public boolean allDevicesConnected(int[][] adjlist) {
        boolean[] visited = new boolean[adjlist.length]; 
        LinkedList<Integer> q = new LinkedList<Integer>();
        int source = 0;
        q.add(source);

        while(!q.isEmpty()){
            int item = q.getFirst();
            visited[item] = true;
            for(int i = 0; i < adjlist[item].length; i++){
                int[] neighbours = adjlist[item];
                if(!visited[neighbours[i]]){
                    q.add(neighbours[i]);
                }
            }
            q.remove();
        }
        for(boolean visit : visited){
            if(!visit){
                return false;
            }
        }
        return true;
    }

    /*
     * This method determines the number different paths a packet can take in a network to get from transmitting device to receiving device
     * @param adjlist the structure of the network
     * @param src the device id of the transmitting device
     * @param dst the device id of the receiving device
     * @return the number of possible paths that the packet may take
     */
    public int numPaths(int[][] adjlist, int src, int dst) {
        paths = 0; //reset global paths variable to 0 on each call to numPaths
        boolean[] visited = new boolean[adjlist.length]; //creating new boolean array to keep track of which vertices have been visited
        getPaths(src, dst, visited, adjlist); //calls helper function to do the actual work 
        int ans = paths;

        return ans;
    }

    /*
     * Helper method for numPaths()
     * This method determines the different paths a packet can take in a network to get from transmitting device to receiving device and increments global variable paths
     * @param src the device id of the transmitting device
     * @param dst the device id of the receiving device
     * @param visited boolean array of which vertices have been visited
     * @param adjlist the structure of the network
     */
    private void getPaths(int src, int dst, boolean[] visited, int[][] adjlist){
        if(src == dst){
            paths++;
            return;
        }

        visited[src] = true;

        for(int i = 0; i < adjlist[src].length; i++){
            int j = adjlist[src][i];
            if (!visited[j]) {
                getPaths(j, dst, visited, adjlist);
            }
        }
        visited[src] = false;
    }

    /*
     * This method computes the minimum number of hops required to reach a device in each subnet query
     * @param adjlist the structure of the network being analysed
     * @param addrs an array of IP addresses such that device id i has address addrs[i]
     * @param src the device id of the transmitting device
     * @param queries an array of queries where each query is a subnet prefix
     * @return an int array of the number of hops required to reach each subnet from src
     */
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int D = adjlist.length;
        int Q = queries.length;

        int[] shortest = lGraph(adjlist, src);

        int[] solutions = new int[Q];
        for(int i = 0; i < Q; i++) {
            solutions[i] = Integer.MAX_VALUE;
        }

        for(int i = 0; i < queries.length; i++) {
            for(int k = 0; k < addrs.length; k++) {
                boolean match = true;
                for(int j = 0; j < queries[i].length; j++) {
                    if(addrs[k][j] != queries[i][j]) {
                        match = false;
                        break;
                    }
                } 
                if(match && solutions[i] > shortest[k]) {
                    solutions[i] = shortest[k];
                }
            }
        }

        return solutions;         
    }

    /*
     * Helper method for closestInSubnet
     * This method finds an array of the shortest number of hops required to get to every node
     * @param adjlist the structure of the network
     * @param src the device id of the transmitting device
     * @return shortest, an int array of the shortest number of hops required to every node
     */
    private int[] lGraph(int[][] adjlist, int src) {
        int D = adjlist.length;

        Queue<Integer> q = new LinkedList<Integer>();
        boolean[] vis = new boolean[D];
        int[] shortest = new int[D];
        for(int i = 0; i < D; i++) {
            vis[i] = false;
            shortest[i] = Integer.MAX_VALUE;
        }

        vis[src] = true;
        shortest[src] = 0;
        q.offer(src);

        while(q.peek() != null) {
            int node = q.poll();
            int[] neighbours = adjlist[node];
            for(int i = 0; i < neighbours.length; i++) {
                int j = neighbours[i];
                if(!vis[j]) {
                    q.offer(j);
                    vis[j] = true;
                    if(shortest[j] > shortest[node]) {
                        shortest[j] = shortest[node] + 1;
                    }
                    
                }
            }
        }

        return shortest; 
    }

    /*
     * This method computes the maximum possible download speed from a transmitting device to a receiving device.
     * @param adjlist the structure of the network being analysed
     * @param speeds the maximum speeds of the links in the network
     * @param src the device id of the transmitting device
     * @param dst the device id of the receiving device
     * @return if transmitting and receiving devices are the same, returns -1. Else: returns the max download speed from transmitting to receiving device
     */
    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        if(src == dst){ //base case for when src and dst are the same device, returns -1
            return -1;
        }
        int D = adjlist.length;
        int maxFlow = 0;
        boolean[] minCut = new boolean[D];
        graph = new Node[D];
        for(int i = 0; i < D; i++) {
            graph[i] = new Node(adjlist[i], adjlist[i].length, i, speeds[i]);
            minCut[i] = false;            
        }
        
        int[] next = new int[D];
        level = new int[D];
    
        while (bfs(src, dst, D)) {
            Arrays.fill(next, 0);
            // Find max flow by adding all augmenting path flows.
            for (long f = dfs(graph, level, src, dst, next, Integer.MAX_VALUE); f != 0; f = dfs(graph, level, src, dst, next, Integer.MAX_VALUE)) {
                maxFlow += f;
            }
        }
    
        for (int i = 0; i < D; i++) if (level[i] != -1) minCut[i] = true;
        
        
        return maxFlow;
    }

    /*
     * This method runs a breadth-first search on the graph
     * @param src the device id of the transmitting device
     * @param dst the device id of the receiving device
     * @param D the length of the adjlist given in maxDownloadSpeed
     * @return if the dst has been visited or not
     */
    private boolean bfs(int src, int dst, int D) {
        Arrays.fill(level, -1);
        level[src] = 0;
        Deque<Integer> q = new ArrayDeque<>(D);
        q.offer(src);
        while (!q.isEmpty()) {
            int node = q.poll();
            int length;
            try{
                length = graph[node].edges.length;
            }
            catch(NullPointerException e){
                length = 0;
            }
            for (int i = 0; i < length; i++) {
                Edge edge = graph[node].edges[i];
                int cap;
                try {
                    cap = edge.remainingCapacity();
                }
                catch(NullPointerException e) {
                    cap = 0;
                }
                if (cap > 0 && level[edge.nodeB] == -1) {
                    level[edge.nodeB] = level[node] + 1;
                    q.offer(edge.nodeB);
                }
            }
        }

    return level[dst] != -1;
    }

    /*
     * This method performs a depth-first search on the graph
     * @param graph the graph to be searched
     * @param level a map of the number of hops to each device
     * @param at the device id of the current node
     * @param dst the device id of the receiving device
     * @param next keeps a record of what paths have not been taken yet
     * @param flow the flow down a path
     */
    private int dfs(Node[] graph, int[] level, int at, int dst, int[] next, int flow) {
        if (at == dst) return flow;
        int numEdges = graph[at].edges.length;
    
        for (; next[at] < numEdges; next[at]++) {
            Edge edge = graph[at].edges[next[at]];
            int cap;
            try {
                cap = edge.remainingCapacity();
            }
            catch(NullPointerException e) {
                cap = 0;
                
            }
            if (cap > 0 && level[edge.nodeB] == level[at] + 1) {
                int bottleNeck = dfs(graph, level, edge.nodeB, dst, next, Math.min(flow, cap));
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
          }
        }
        return 0;
      }
    
	public static void main(String[] args){
        
    }
}