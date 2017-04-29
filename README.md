**Isomorphic nodes** - Two trees are called isomorphic if one of them can be obtained from other by a series of flips, i.e. by swapping left and right children of a number of nodes
------

## Overview
This project goes over the implementation of both recursive and iterative algorithms for the following.
- Check if two nodes are isomorphic with two methods
  - [Recursively](#recursive)
  - [Iteratively](#iterative)
- [Printing](Printing) out a binary tree recursively - just because I thought it would be useful to have

There are a few different classes in this program that might be worth looking at later ([Isomorphic](#isomorphic), [IsomorphicNodes](#isomorphicnodes), [Node](#node), and [Printer](#printer)).


### Running the program
You run the program, now what? The console should look something like the snippet below with minor differences in the times. 
The driver class [IsomorphicNodes](#IsomorphicNodes) creates and prints out 4 different nodes, the first, second and fourth nodes are isomorphic. Then the nodes are printed out with the [Printer](#Printer) class. All time measurements are made using 
```System.nanoTime();```
for precision.


#### Hypothesis
- I hypothesize that the recursive algorithm would be slower than the iterative implementation.

Before we run the tests, in many computer science classes it's taught that Recursion is a guarenteed but last result to find the solution. In other words, you should only use recursion as a last resort. Iteration is usually the first way to go. Use loops for everything they said.

## Experiment
Run the program and behold the magic...

```
1) Printing tree1
          1        
         / \       
        /   \      
       /     \     
      /       \    
      2       3    
     / \     /     
    /   \   /      
    4   5   6      
       / \         
       7 8         
Took 6460013 ns to print

2) Printing tree2
          1        
         / \       
        /   \      
       /     \     
      /       \    
      3       2    
       \     / \   
        \   /   \  
        6   4   5  
               / \ 
               8 7 
Took 1839256 ns to print

3) Printing tree3
          1        
         / \       
        /   \      
       /     \     
      /       \    
      2       3    
       \     / \   
        \   /   \  
        4   6   5  
               / \ 
               8 7 
Took 2669751 ns to print

4) Printing tree4
          1        
         / \       
        /   \      
       /     \     
      /       \    
      2       3    
     / \     /     
    /   \   /      
    4   5   6      
       / \         
       8 7         
Took 1566381 ns to print



========================== Isomorphic Test ==========================

1) Comparing tree1 & tree2...
	Recursive result IS isomorphic
	Iterative result IS isomorphic
	+----------------+----------------+
	|                |     winner     |
	+----------------+----------------+
	| recursive time | iterative time |
	|----------------|----------------|
	|    599080 ns   |    80578 ns    |
	+----------------+----------------+

2) Comparing tree1 & tree3...
	Recursive result IS NOT isomorphic
	Iterative result IS NOT isomorphic
	+----------------+----------------+
	|     winner     |                |
	+----------------+----------------+
	| recursive time | iterative time |
	|----------------|----------------|
	|     1497 ns    |    11582 ns    |
	+----------------+----------------+

3) Comparing tree2 & tree3...
	Recursive result IS NOT isomorphic
	Iterative result IS NOT isomorphic
	+----------------+----------------+
	|     winner     |                |
	+----------------+----------------+
	| recursive time | iterative time |
	|----------------|----------------|
	|     1183 ns    |    11976 ns    |
	+----------------+----------------+

4) Comparing tree4 & tree1...
	Recursive result IS isomorphic
	Iterative result IS isomorphic
	+----------------+----------------+
	|     winner     |                |
	+----------------+----------------+
	| recursive time | iterative time |
	|----------------|----------------|
	|     2321 ns    |    51718 ns    |
	+----------------+----------------+

5) Comparing tree4 & tree2...
	Recursive result IS isomorphic
	Iterative result IS isomorphic
	+----------------+----------------+
	|     winner     |                |
	+----------------+----------------+
	| recursive time | iterative time |
	|----------------|----------------|
	|     2051 ns    |    39800 ns    |
	+----------------+----------------+
```

### Analyzing the data

Test results
1. The iterative method was faster by 518502 ns
2. The recursive method was faster.
3. The recursive method was faster.
4. The recursive method was faster.
5. The recursive method was faster.

How???

Well let's look at why I was wrong...


## Implementation of the algorithm
### Recursive
This is the code that runs when the recursive method is called to check for isomorphic nodes
``` java
    /**
     * recursively checks if the 2 nodes are isomorphic
     *
     * @param first  the first node/tree to compare
     * @param second the second node/tree to compare
     */
    static boolean recursive(Node first, Node second) {
        // Both roots are NULL, trees isomorphic by definition
        if (first == null && second == null) return true;

        // Exactly one of the first and second is NULL, trees not isomorphic
        if (first == null || second == null) return false;

        // if the two are not equal, trees are not isomorphic
        if (first.data != second.data) return false;

        // There are two possible cases for first and second to be isomorphic
        // Case 1: The subtrees rooted at these nodes have NOT been "Flipped".
        // Both of these subtrees have to be isomorphic.
        // Case 2: The subtrees rooted at these nodes have been "Flipped"
        return (recursive(first.left, second.left) && recursive(first.right, second.right)) ||
                (recursive(first.left, second.right) && recursive(first.right, second.left));
    }
```

### Iterative
This is the code that runs when the iterative method is called to check for isomorphic nodes
Because the method couldn't be called within itself, I used stacks to pop nodes and iterate through.
``` java
    /**
     * iteratively checks if the two nodes are isomorphic
     */
    static boolean iterative(Node tree1, Node tree2) {
        Stack<Node> stack1 = new Stack<>(); //nodes from tree1
        Stack<Node> stack2 = new Stack<>(); //nodes from tree2
        stack1.push(tree1);
        stack2.push(tree2);

        Node first, second;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            first = stack1.pop();
            second = stack2.pop();

            //might be isomorphic continue...
            if ((first == null) && (second == null)) continue;

            //not isomorphic if comparing null nodes
            if ((first == null) || (second == null)) return false;

            //if they're not equal they're not isomorphic
            if (!(first.data == second.data)) return false;


            // There are two possible cases for first and second to be isomorphic
            // Case 1: The subtrees rooted at these nodes are "Symmetric".
            if (nodesEqual(first.left, second.left) &&
                    nodesEqual(first.right, second.right)) {
                //add the child nodes to the stacks in symmetric order so they'll be pulled symmetrically
                stack1.push(first.left);
                stack2.push(second.left);
                stack1.push(first.right);
                stack2.push(second.right);
                continue;
            }

            // Both of these subtrees have to be isomorphic.
            // Case 2: The subtrees rooted at these nodes have been "Flipped"
            if (nodesEqual(first.left, second.right) &&
                    nodesEqual(first.right, second.left)) {
                //add the child nodes to the stacked in flipped order so they'll be pulled flipped and the nodes match up
                stack1.push(first.left);
                stack2.push(second.right);
                stack1.push(first.right);
                stack2.push(second.left);
                continue;
            }

            return false;
        }

        return true;
    }

    private static boolean nodesEqual(Node first, Node second) {
        // if Both trees are NULL, trees isomorphic by definition
        if ((first == null) && (second == null)) {
            return true;
        } else {
            // Exactly one of the first and second is NULL, trees not equal nor are they isomorphic
            if ((first == null) || (second == null)) {
                return false;
            } else {
                //if the two are equal trees might be isomorphic
                return first.data == second.data;
            }
        }
    }
```


## Classes
This is the list of classes that this program uses

#### [Node](src/com/deep/IsomorphicNodes.java#L16)
- This is just a basic class for the nodes that we'll be working with for this project, there isn't much to this class you can take a look at it here [Node]

#### [Isomorphic](src/com/deep/IsomorphicNodes.java#L29)
- This is the implementation of the algorithm to check if nodes are isomorphic

#### [IsomorphicNodes](src/com/deep/IsomorphicNodes.java#L127)
- This is the driver class that has the main method within it. It creates the nodes, prints them and applies both algorithms to 2 different nodes five times

#### [Printer](src/com/deep/IsomorphicNodes.java#L272)
- This class is just a utility class to print out the binary nodes in a visually pleasing format to the console
