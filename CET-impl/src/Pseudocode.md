#Pseudocode for BFS and DFS

As right now, there's no implementation for path identification, therefore, this is currently just a traversal method with an empty "identify method".

##BFS
```
// check if all the points have been visited
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

##DFS
```





```