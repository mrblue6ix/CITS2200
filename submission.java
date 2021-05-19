// Jordan Mossammaparast (22774949)
// Stanley McFarlane (22724248)

import java.util.*;

public class MyProject implements Project {
    int paths = 0;
	
	public MyProject() {
		//TODO constructor

	}
    /*
     *
     * 
     */
    public boolean allDevicesConnected(int[][] adjlist) {
        // TODO
        return true;
    }

    /*
     * This function determines the different paths a packet can take in a network to get from transmitting device to receiving device
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
     * This function determines the different paths a packet can take in a network to get from transmitting device to receiving device and increments global variable paths
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

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        // TODO
        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
    
	public static void main(String[] args){
        
    }
}
