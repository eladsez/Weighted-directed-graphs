# Weighted directed graphs (directed networks)

> made by Elad Seznayev and Nerya Bigon.
* As part of OOP course assignment.

## Goal:
The goal of this assignment is to design and implement two key interfaces:
* Weighted directed graph interface.
* Weighted directed graphs algorithms interface.  

In addition we'll create a graphical inteface that allows you to load graphs from files, save them, edit them and run algorithms on them.   

## Algorithms:
* `isConnected` - return whether the graph is strongly connected or not.  
We've implemented the algorithm in the following way:    
  1. run BFS algorithms from a specific node to all of the other nodes
  2. run BFS again, this time on the graph transposed.
  3. check if the BFS's results are equals to each othe and to the nuber of nodes in the graph.
  4. if so - the graph is strongly connected.  

* `shortestPathDist` - return the distance of the shortest path between two nodes.  
We've implemented the algorithm in the following way:    
  1. run DIJKSTRA algorithm on the source node - in order to get in each node the shortest path from the source, and the distance. 
  2. the answer is contained in the destination node's weight parameter.
  3. so we return it.  

* `shortestPath` - return the shortest path between two nodes.  
We've implemented the algorithm in the following way:    
  1. run DIJKSTRA algorithm on the source node - in order to get in each node the shortest path from the source, and the distance. 
  2. because each node tag "carry" the node that came before it in the path, all there is to do is to loop from the destination node and ask who came before until we get to the source node.
  3. the results are then inserted into a list and returned.  

* `center` - return the node that is the closest to every other node.   
**Approach:** we are searching for the node with the shortest path, but from the longest result this node got from `shortestPathDist`.
We've implemented the algorithm in the following way:    
  1. loop through all of the nodes in the graph.
  2. for each node check with `shortestPathDist` what is the **longest** path
  3. return the node with the shortest one.  

* `tsp` - return the shrotest path between a list of nodes.   
**Approach:** we are using swaping algorithm, in order to get an acceptble path at reasonble time.
We've implemented the algorithm in the following way:    
  1. Start with a random route that start in the source node.
  2. Perform a swap between nodes (except the source).
  3. Keep new route if it is shorter.
  4. Repeat (2-3) for all possible swaps.
since in this assignment we are not required to return to the source node it's simplify the solution a bit.
This algorithm is both faster, O(M*N^2) and produces better solutions. The intuition behind the algorithm is that swapping untangles routes that cross over itself.  
This swap algorithm performed much better than greedy; the path it drew looks similar to something a human might draw.

<img align="center" width="500" src="2-opt.gif">


## Structure:  

Class | Description
----- | -----------
`Ex2` | The main class, that drive everything.
`DWGraph` | This class represent the graph.
`DWGraphAlgo` | this class holds all of the algorithms.
`Edge` | This class represent an edge.
`Node` | This class represent a node.


### UML:
![](diagram.jpg)


## How To Run:

# GUI:
 
