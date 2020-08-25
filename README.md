# CET

## Implementation

Right now, it has implemented BFS and DFS traversal using stack. Furthermore, the hybrid one is almost done.


##Usage
Compile and run the project by executing below:

````jshelllanguage
./autoCompile.sh
````

Some sample file will be later given to prove how a graph can be read from file.

There are several improvement that will be made(which is in progress, will be finished by the end of next week).
1. Utility.java will be able to take command line argument to read the graph generation method.
2. The argument will be an xml file. For **Random** type, it will have a structure similar to the following:

    ````xml
    <type>random</type>
    <nodeNum>20</nodeNum>
    ````
    For **ReadFile** type, the xml file will look similar as following:
    
    ````xml
    <type>readFile</type>
    <path>/home/etc/data/someFile.txt</path>
    ````
3. Optimize the M_CET since the implementation is not really sufficient yet. 
 
 
 
