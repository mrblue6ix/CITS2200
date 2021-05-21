// Jordan Mossammaparast (22774949)
// Stanley McFarlane (22724248)

import java.util.*;

class Edge {
    public int flow;
    public final int capacity;
    public int nodeA;
    public int nodeB;
    public Edge residual;

    public Edge(int nodeA, int nodeB, int capacity) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.capacity = capacity;
    }
    
    public boolean isResidual() {
        return capacity == 0;
    }

    public int remainingCapacity() {
        return capacity - flow;
    }

    public void augment(int bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }
}

class Node {
    Edge[] edges; //the associated edges with a node
}



public class MyProject implements Project {
    int paths = 0;
	int[] level;
    Node[] graph; 

	public MyProject() {
		//TODO constructor
	}
	
    public boolean allDevicesConnected(int[][] adjlist) {
        // TODO
        // useful link: https://stackabuse.com/graphs-in-java-breadth-first-search-bfs/
        // he said in lecture to use BFS with 2 nested loops
        boolean[] visited = new boolean[adjlist.length];
        LinkedList<Integer> q = new LinkedList<Integer>();
        int source = 0;
        q.add(source);

        while(!q.isEmpty()){
            //add whats on the queue to the visited array
            //then check adjlist[removed_item], add all elements that aren't in visited array to the queue
            //keep repeating until you've checked adjlist for all elements
            //if all elements of visited array are true then all devices are connected (return true)
            //if not then all devices are not connected (return false)
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
    This function determines the different paths a packet can take in a network to get from transmitting device to receiving device
     * @param adjlist the structure of the network
     * @param src the device id of the transmitting device
     * @param dst the device id of the receiving device
     * @return the number of possible paths that the packet may take
     */
    public int numPaths(int[][] adjlist, int src, int dst) {
        paths = 0; //reset global paths variable to 0 on each call to numPaths
        boolean[] visited = new boolean[adjlist.length]; //creating new boolean array to keep track of which vertices have been visited
        printPath(src, dst, visited, adjlist); //calls helper function to do the actual work 
        int ans = paths;

        return ans;
    }
    private void printPath(int src, int dst, boolean[] visited, int[][] adjlist){
        if(src == dst){
            paths++;
            return;
        }
        visited[src] = true;
        for(int i = 0; i < adjlist[src].length; i++){
            int j = adjlist[src][i];
            if (!visited[j]) {
                printPath(j, dst, visited, adjlist);
            }
        }
        visited[src] = false;
    }

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

        while(q.poll() != null) {
            int node = q.poll();
            int[] neighbours = adjlist[node];
            for(int i = 0; i <neighbours.length; i++) {
                if(!vis[i]) {
                    q.offer(i);
                    vis[i] = true;
                    if(shortest[i] < shortest[node] ) {
                        continue;
                    }
                    shortest[i] = shortest[node] + 1;
                }
            }
        }

        return shortest; 
    }
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) { //use BFS?
        int D = adjlist.length;
        int Q = queries.length;

        int[] shortest = lGraph(adjlist, src);

        //need to implement an IP/Querie comparison method in N + Q time

        return shortest;         
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        int D = adjlist.length;
        int maxFlow = 0;
        boolean[] minCut = new boolean[D];
        Node[] graph = new Node[D];
        for(int i = 0; i < D; i++) {
            for(int j = 0; j < adjlist[i].length; j++) {
                graph[i].edges = new Edge[adjlist[i].length];
                graph[i].edges[j] = new Edge(i, adjlist[i][j], speeds[i][j]);
            }
            minCut[i] = false;            
        }
        
        int[] next = new int[D];
        level = new int[D];
    
        while (bfs(src, D)) {
          Arrays.fill(next, 0);
          // Find max flow by adding all augmenting path flows.
          for (long f = dfs(graph, level, src, dst, next, Integer.MAX_VALUE); f != 0; f = dfs(graph, level, src, dst, next, Integer.MAX_VALUE)) {
            maxFlow += f;
          }
        }
    
        for (int i = 0; i < D; i++) if (level[i] != -1) minCut[i] = true;
        
        
        return maxFlow;
    }

    private boolean bfs(int src, int dst) {
        int D = graph.length;
        Arrays.fill(level, -1);
        level[src] = 0;
        Deque<Integer> q = new ArrayDeque<>(D);
        q.offer(src);
        while (!q.isEmpty()) {
        int node = q.poll();
            for (Edge edge : graph[node].edges) {
                long cap = edge.remainingCapacity();
                if (cap > 0 && level[edge.nodeB] == -1) {
                    level[edge.nodeB] = level[node] + 1;
                    q.offer(edge.nodeB);
                }
            }
        }
    return level[dst] != -1;
    }

    private long dfs(Node[] graph, int[] level, int at, int dst, int[] next, long flow) {
        if (at == dst) return flow;
        final int numEdges = graph[at].edges.length;
    
        for (; next[at] < numEdges; next[at]++) {
          Edge edge = graph[at].get(next[at]);
          int cap = edge.remainingCapacity();
          if (cap > 0 && level[edge.nodeB] == level[at] + 1) {
    
            int bottleNeck = dfs(edge.nodeB, next, min(flow, cap));
            if (bottleNeck > 0) {
              edge.augment(bottleNeck);
              return bottleNeck;
            }
          }
        }
        return 0;
      }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MyProject project = new MyProject();

	}

}
