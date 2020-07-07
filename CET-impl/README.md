#CET

##Implementation

Right now, it is only implementing the T_CET and M_CET.


##Usage
Run file Utility.java by:

````jshelllanguage
javac Utility.java 
java  Utility
````

It is now not yet susceptible to change the graph generate method dymanically.
In *Utility.java* file:

````java
// the argument takes in is to specify whether the graph is randomly generated or read from file.
graphBuilder.generateGraph("random");
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
 
 
 