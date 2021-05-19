// Jordan Mossammaparast (22774949)
// Stanley McFarlane (22724248)

import java.util.*;

public class MyProject implements Project {
    int paths = 0;
	
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

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) { //use BFS?
        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MyProject project = new MyProject();

	}

}
