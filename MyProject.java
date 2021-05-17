// Jordan Mossammaparast (22774949)
// Stanley McFarlane (22724248)

import java.util.*;

public class MyProject implements Project {
	
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
        q.add(0);

        while(!q.isEmpty()){
            //add whats on the queue to the visited array
            //then check adjlist[removed_item], add all elements that aren't in visited array to the queue
            //keep repeating until you've checked adjlist for all elements
            //if all elements of visited array are true then all devices are connected (return true)
            //if not then all devices are not connected (return false)
        }

        return true;
    }

    public int numPaths(int[][] adjlist, int src, int dst) {
        // TODO
        return 0;
    }

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        // TODO
        short[] og = addrs[src]; //getting the ip address of the source

        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        System.out.println("soosk");

        //below is a test for closestInSubnet()
        int[][] adjlist = new int[][] {
            { 1 },
            { 0 },
        };

        short[][] addrs = new short[][] {
            { 10, 1, 1, 1 },
            { 10, 1, 2, 1 },
        };

        short[][] queries = new short[][] {
            { 10, 1, 1 },
            { 10, 1, 2 },
            { 10, 1 },
            { 10, 2 },
        };
        MyProject project = new MyProject();
        int[] x = project.closestInSubnet(adjlist, addrs, 0, queries);
        System.out.println(x);
        //Expected answer = 0, 1, 0, Integer.MAX_VALUE
	}

}
