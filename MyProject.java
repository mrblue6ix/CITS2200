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
        paths = 0;
        boolean[] visited = new boolean[adjlist.length];
        printPath(src, dst, visited, adjlist);
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
        //mark current vertex as false
        visited[src] = false;
    }

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) { //use BFS?
        short[] source = addrs[src]; //getting the ip address of the source
        int hops = 0; //hop count
        int[] hopCounts = new int[queries.length]; //array of all hop counts

        for(int i = 0; i < queries.length; i++){ //for each query
            //go from source to the query subnet
            source = addrs[src]; //getting the ip address of the source
            short[] query = queries[i]; //get current query
            int[] adjacency = adjlist[src]; //set adjacency to adjlist of src
            int sameCount = 0;

            for(int j = 0; j < query.length; j++){
                if(source[j] == query[j]){
                    sameCount++; //keep count of how many numbers are the same in source and query
                }
            }

            if(sameCount == query.length){ //if same count contains all of query then hops = 0
                hops = 0;
                hopCounts[i] = hops;
            }
            else{ //executed if source is not already in queried subnet
                for(int k = 0; k < adjacency.length; k++){
                    source = addrs[adjacency[k]]; //move source to the next item in adjacency
                    hops++; //increment hops
                }
            }
        }

        return hopCounts;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MyProject project = new MyProject();

        int[][] adjlist1 = new int[][] {
            { 1 },
            { 0 },
        };
        int y = project.numPaths(adjlist1, 0, 1);
        System.out.println("y: " + y); //1

        int[][] adjlist2 = new int[][] {
            { 1, 2 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };
        int z = project.numPaths(adjlist2, 0, 3);
        System.out.println("z: "+ z); //2

        int[][] adjlist = new int[][] {
            { 1, 2 },
            { 0, 2 },
            { },
            { 1, 2 },
        };

        int a = project.numPaths(adjlist, 0, 3);
        System.out.println("a: "+ a); //0

        adjlist = new int[][] {
            { 1, 2 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };

        int b = project.numPaths(adjlist, 0, 0);
        System.out.println("b: "+ b); //1

        adjlist = new int[][] {
            { 0 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };

        int c = project.numPaths(adjlist, 0, 3);
        System.out.println("c: " + c); //0
	}

}
