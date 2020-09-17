##Graph matrix intake:

A 10*10 matrix to be read in would look like this:
```
0,0,0,0,0,0,0,0,0,0,
0,0,0,1,0,0,0,0,0,0,
1,0,0,0,0,0,0,1,0,0,
0,0,0,0,0,0,1,0,0,0,
1,0,0,0,0,0,0,1,0,1,
0,1,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,1,
0,0,0,0,0,0,0,0,0,0,
1,0,0,0,1,0,0,0,0,0,
1,0,0,0,0,0,0,0,0,0,
```


##Pseudocode for BFS and DFS

As right now, there's no implementation for path identification, therefore, this is currently just a traversal method with an empty "identify method".

###BFS
```
// check if all the points have been reached
boolean [] reached;

// store all the intermediate paths 
List <Path>[] paths;

// create queue to visit all vertices 
Queue <> queue;

// Collect final paths 
ArrayList<Path> finalPaths;


// init all elements except the start point in *reached* as false 
reached[i] = false; (for all i = 0 ~ reached.length - 1)
reached[start] = true;

queue.pop(start);
path[start].add(new Path(start));

while(queue is not empty): 
    //get the first element out 
    int current = queue.poll(); 
    
    // get all adjacent neighbours of the current node 
    for( neighbour : graph.getEdges(current) ):
        for each intermediate path_i in paths[current]: 
            Path newPath = path_i + neighbour;
            
            // empty method for now, used to identify if the path is what we want
            if (identifyPattern(newPath)):
                finalPaths.add(newPath);
                
        if reached[neighbour] == false:
            reached[neighbour] = true;
            queue.enqueue(neighbour);
            
for(path in finalPaths):
    print(path)
        
```

###DFS
```
void traversal( int start ) {
    boolean[] visited;
    Path path = new Path(start);
    List<Path> validPaths;
    
    // Call the recursive helper function to print DFS traversal
    DFStraversal(start, visited, path, validPaths);
    
    for(path in finalPaths):
        print(path)
}

void DFStraversal(int s, boolean[] visited, Path path, ArrayList<Path>validPaths) {
    visited[s] = true;
         
    if( s has no outgoing edges):
    if(path is valid):
        validPaths.add(path);
    return;

    // Get all neighbours
    List<Integer> edges = graph.getEdges(s);
    
    for (Integer edge : edges) {
        if (!visited[edge]){
            path.addNode(edge);
            identifyPattern(path);
            DFStraversal(edge, visited, path, validPaths);
            path.removeNode(edge);
        }
    }
}

```