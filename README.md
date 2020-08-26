# CET

## Implementation

Right now, it has implemented BFS and DFS traversal using stack. Furthermore, the hybrid one is almost done. 

## Usage

1. Compile and run the project by executing below:

````shell
./autoCompile.sh
````

The graph to traverse can either be random or read from existing file. The program will ask for a specified path if there is any. *(Might be changing to reading from file for the ease of modification and running multiple times)*

2. The program will ask for the number of times to run the algorithm, and the algorithm to run. Currently there are 5 existing ones available for experiment:
   - BFS
   - DFS
   - Hybrid(Sequential) -- in develop
   - M_CET
   - T_CET
   - (The concurrent one is in develop)
3. File intake: As long as the file conform the format of a valid DAG, it would be traverse successfully, the running time of each run and the average would be recorded in file for later comparison.The file format will be discussed in the next section.



## File intake format:

### Input config

*TODO: Intake config*



### Graph format

| Type                                                         | Format                                                    |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Grid<br /> (Adjacency matrix)                                |  Grid  <img width=400/>  <br />6 <br />0,0,1,0,1,0, <br />1,0,0,0,0,0, <br />0,0,0,0,0,0, <br />1,0,0,0,0,0, <br />0,0,1,0,0,0, <br />0,0,0,1,0,0, <br />No timestamp |
| Sparse matrix<br />Each pair is in the form of:<br />  `< Source Node, Dest Node>` | Sparse <br />10 <br />0,4 <br />2,5 <br />3,1 <br />4,2 <br />6,1 <br />6,2 <br />7,4 <br />7,6 <br />7,8 <br />7,9 <br />9,2 <br />9,5 <br />No timestamp |
| Random<br />It can be either sparse random or purely random, <br />therefore, two format are shown on the right. | Sparse Random <br />10<br /><br />Random<br />5              |

For file naming, in order to be able to easily recognize which type it belongs to, it is strongly suggested to use the form of: \<type\>\<num of nodes\>.txt

- Grid7.txt
- Sparse7.txt
- Random10.txt
- SparseRandom20.txt
- ...  
