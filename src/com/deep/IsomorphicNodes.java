package com.deep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by deep on 4/27/17.
 * implements an algorithm to check if two nodes are isomorphic
 */

class Node {
    final int data;
    Node left, right;

    Node(int item) {
        this.data = item;
        this.left = this.right = null;
    }
}

class Isomorphic {
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

    /**
     * recursively checks if the 2 nodes are isomorphic
     *
     * @param first  the first node to compare
     * @param second the second node to compare
     */
    boolean recursive(Node first, Node second) {
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
        return (this.recursive(first.left, second.left) && this.recursive(first.right, second.right)) ||
                (this.recursive(first.left, second.right) && this.recursive(first.right, second.left));
    }

    boolean iterative(final Node tree1, final Node tree2) {
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
            if (!nodesEqual(first, second)) return false;


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
}

class IsomorphicNodes {

    public static void main(String args[]) {
        // creates a class for the algorithm to be applied
        Isomorphic algorithm = new Isomorphic();

        // generate sample trees
        Node tree1 = initFirstTree();
        Node tree2 = initSecondTree();
        Node tree3 = initThirdTree();

        SimpleDateFormat f = new SimpleDateFormat("S");
        System.out.println("1) Printing tree1");
        long start = System.nanoTime();
        Printer.printTree(tree1);
        long end = System.nanoTime();
        System.out.printf("Took %s ns to print", end - start);
        System.out.println("\n");

        System.out.println("2) Printing tree2");
        start = System.nanoTime();
        Printer.printTree(tree2);
        end = System.nanoTime();
        System.out.printf("Took %s ns to print", end - start);
        System.out.println("\n");

        System.out.println("3) Printing tree3");
        start = System.nanoTime();
        Printer.printTree(tree3);
        end = System.nanoTime();
        System.out.printf("Took %s ns to print", end - start);
        System.out.println("\n");

        System.out.println("========================== Isomorphic Test ==========================\n");
        System.out.printf("1) Checking if tree1 and tree2 are Isomorphic... \n\t");
        start = System.nanoTime();
        boolean isIsomorphic = algorithm.recursive(tree1, tree2);
        end = System.nanoTime();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check recursively\n\t", end - start);
        start = System.nanoTime();
        isIsomorphic = algorithm.iterative(tree1, tree2);
        end = System.nanoTime();
        System.out.printf("b) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check iteratively", end - start);
        System.out.println("\n");

        System.out.printf("2) Checking if tree1 and tree3 are Isomorphic... \n\t");
        start = System.nanoTime();
        isIsomorphic = algorithm.recursive(tree1, tree3);
        end = System.nanoTime();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check recursively\n\t", end - start);
        start = System.nanoTime();
        isIsomorphic = algorithm.iterative(tree1, tree3);
        end = System.nanoTime();
        System.out.printf("b) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check iteratively", end - start);
        System.out.println("\n");

        System.out.printf("3) Checking if tree2 and tree3 are Isomorphic... \n\t");
        start = System.nanoTime();
        isIsomorphic = algorithm.recursive(tree2, tree3);
        end = System.nanoTime();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check recursively\n\t", end - start);
        start = System.nanoTime();
        isIsomorphic = algorithm.iterative(tree2, tree3);
        end = System.nanoTime();
        System.out.printf("b) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check iteratively", end - start);
    }

    //creates the first tree
    static Node initFirstTree() {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.left.left = new Node(4);
        node.left.right = new Node(5);
        node.right.left = new Node(6);
        node.left.right.left = new Node(7);
        node.left.right.right = new Node(8);
        return node;
    }

    //creates the second tree
    static Node initSecondTree() {
        Node node = new Node(1);
        node.left = new Node(3);
        node.right = new Node(2);
        node.right.left = new Node(4);
        node.right.right = new Node(5);
        node.left.right = new Node(6);
        node.right.right.left = new Node(8);
        node.right.right.right = new Node(7);
        return node;
    }

    //creates the third tree
    static Node initThirdTree() {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.right.left = new Node(6);
        node.right.right = new Node(5);
        node.left.right = new Node(4);
        node.right.right.left = new Node(8);
        node.right.right.right = new Node(7);
        return node;
    }

}

/**
 * Class that prints the Binary Trees recursively
 */
class Printer {

    /**
     * prints the node as a tree
     * <p>
     * ex) Something like the below diagram
     * <p>
     * 1
     * / \
     * /   \
     * /     \
     * /       \
     * 2       3
     * \     / \
     * \   /   \
     * 4   6   5
     * / \
     * 8 7
     *
     * @param tree the tree to print out
     */
    public static void printTree(Node tree) {
        int maxLevel = getNumOfLevels(tree);
        printNodeInternal(Collections.singletonList(tree), 1, maxLevel);
    }

    /**
     * prints the nodes as a tree
     *
     * @param nodes        the list of nodes to print
     * @param currentLevel the level that you want to print
     * @param numOfLevels  the maximum level that you're on
     */
    private static void printNodeInternal(List<Node> nodes, int currentLevel, int numOfLevels) {
        //don't do anything if there are not nodes or if all the list items are null
        if (nodes.isEmpty() || areAllElementsNull(nodes)) return;

        int distanceFromFloor = numOfLevels - currentLevel; //the distance from the floor of the tree
        int edgeLineNum = (int) Math.pow(2, Math.max(distanceFromFloor - 1, 0)); //number of lines to put separating each node
        int startSpaces = (int) Math.pow(2, distanceFromFloor) - 1; //number of spaces to add before the data node
        int spacesBetween = (int) Math.pow(2, distanceFromFloor + 1) - 1; //number of spaces between each node

        //prints spaces before printing the data value for a node at the given point
        int offsetToRight = 1;
        printWhitespaces(startSpaces + offsetToRight);

        //holds the list of nodes that are supposed to be expanded for the next level
        List<Node> newNodes = new ArrayList<>();

        //prints out the data from the list of nodes
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node != null) {
                System.out.printf("%s", node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                System.out.print(" ");
                newNodes.add(null);
                newNodes.add(null);
            }

            //if the node is the last node then just print the number of spaces to fill in the image, not overflow
            boolean isLastNode = i != (nodes.size() - 1);
            if (isLastNode) {
                printWhitespaces(spacesBetween);
            } else {
                printWhitespaces(startSpaces + 1);
            }
        }

        //prints debug info
        //System.out.printf("\t\t(currentLevel:%s, numOfLevels:%s, distanceFromFloor:%s, edgeLineNum:%s, startSpaces:%s, spacesBetween:%s)", currentLevel, numOfLevels, distanceFromFloor, edgeLineNum, startSpaces, spacesBetween);
        System.out.println();

        //if there are no more non null nodes in the list of nodes to check then you're done
        if (areAllElementsNull(newNodes)) {
            //System.out.print("END");
            System.out.println();
            return;
        }

        // Loops through and prints the edge lines
        // if they need to be printed otherwise just print the spaces
        for (int i = 1; i <= edgeLineNum; i++) {
            printWhitespaces(offsetToRight);
            for (Node node : nodes) {
                printWhitespaces(startSpaces - i);
                //System.out.printf("(%s)", startSpaces - i);
                if (node == null) {
                    printWhitespaces(edgeLineNum * 2 + i + 1);
                    //System.out.printf("(%s)", edgeLineNum * 2 + i + 1);
                    continue;
                }

                //print the left node edge line
                //or a white space because the node's data will probably be there
                if (node.left != null) {
                    System.out.print("/");
                } else {
                    printWhitespaces(1);
                }

                //print the spaces between edge lines
                printWhitespaces(i * 2 - 1);

                //print the right node edge line
                //or a white space because the node doesn't have a child there
                if (node.right != null) {
                    System.out.print("\\");
                } else {
                    printWhitespaces(1);
                }

                //print the spaces after edge lines
                printWhitespaces(edgeLineNum * 2 - i);
            }

            System.out.println();
        }

        printNodeInternal(newNodes, currentLevel + 1, numOfLevels);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    /**
     * gets the number of levels for the tree
     *
     * @param node gets the max level of the node
     * @return the max level of the node
     */
    private static int getNumOfLevels(Node node) {
        if (node == null) return 0;
        return Math.max(getNumOfLevels(node.left), getNumOfLevels(node.right)) + 1;
    }

    /**
     * checks to see if the list items are null
     *
     * @param list list to check
     */
    private static boolean areAllElementsNull(List<Node> list) {
        for (Node node : list) {
            if (node != null) return false;
        }
        return true;
    }

}