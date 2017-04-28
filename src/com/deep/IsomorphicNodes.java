package com.deep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by deep on 4/27/17.
 * implements an algorithm to check if two nodes are isomorphic
 */

class Node {
    final int data;
    Node left, right;

    Node(int item) {
        data = item;
        left = right = null;
    }
}

class Algorithm {
    /**
     * recursively checks if the 2 nodes are isomorphic
     *
     * @param first  the first node to compare
     * @param second the second node to compare
     */
    boolean isIsomorphic(Node first, Node second) {
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
        return (this.isIsomorphic(first.left, second.left) && this.isIsomorphic(first.right, second.right)) ||
                (this.isIsomorphic(first.left, second.right) && this.isIsomorphic(first.right, second.left));
    }

}

class IsomorphicNodes {

    public static void main(String args[]) {
        // creates a class for the algorithm to be applied
        Algorithm algorithm = new Algorithm();

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
        boolean isIsomorphic = algorithm.isIsomorphic(tree1, tree2);
        end = System.nanoTime();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check", end - start);
        System.out.println("\n");

        System.out.printf("2) Checking if tree1 and tree3 are Isomorphic... \n\t");
        start = System.nanoTime();
        isIsomorphic = algorithm.isIsomorphic(tree1, tree3);
        end = System.nanoTime();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ns to check", end - start);
        System.out.println("\n");
    }

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

        //prints spaces before the value for a node at a given point
        printWhitespaces(startSpaces);

        //holds the list of nodes that are supposed to be expanded for the next level
        List<Node> newNodes = new ArrayList<>();

        //prints out the data from the list of nodes
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node != null) {
                System.out.print(node.data);
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

        //prints debug info to the console
        //System.out.printf("\t(currentLevel:%s, numOfLevels:%s, distanceFromFloor:%s, edgeLineNum:%s, startSpaces:%s, spacesBetween:%s)", currentLevel, numOfLevels, distanceFromFloor, edgeLineNum, startSpaces, spacesBetween);

        if (areAllElementsNull(newNodes)) {
            System.out.println();
            //System.out.print(" - END\n");
            return;
        }

        System.out.println();

        //prints the edge lines if they need to be printed
        for (int i = 1; i <= edgeLineNum; i++) {
            for (Node node : nodes) {
                printWhitespaces(startSpaces - i);
                if (node == null) {
                    printWhitespaces(edgeLineNum * 2 + i + 1);
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
                //or a white space because the node's data will probably be there
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