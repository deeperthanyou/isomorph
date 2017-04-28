package com.deep;

import java.text.SimpleDateFormat;
import java.util.*;

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

class Main {

    public static void main(String args[]) {
        // creates a class for the algorithm to be applied
        Algorithm algorithm = new Algorithm();

        // generate sample trees
        Node tree1 = initFirstTree();
        Node tree2 = initSecondTree();
        Node tree3 = initThirdTree();

        SimpleDateFormat f = new SimpleDateFormat("S");

        System.out.println("1) Printing tree1 \n");
        long start = System.currentTimeMillis();
        Printer.printTree(tree1);
        long end = System.currentTimeMillis();
        System.out.printf("Took %s ms to print", f.format(new Date(end - start)));
        System.out.println("\n");

        System.out.println("2) Printing tree2 \n");
        start = System.currentTimeMillis();
        Printer.printTree(tree2);
        end = System.currentTimeMillis();
        System.out.printf("Took %s ms to print", f.format(new Date(end - start)));
        System.out.println("\n");

        System.out.println("3) Printing tree3 \n");
        start = System.currentTimeMillis();
        Printer.printTree(tree3);
        end = System.currentTimeMillis();
        System.out.printf("Took %s ms to print", f.format(new Date(end - start)));
        System.out.println("\n");

        System.out.println("========================== Isomorphic Test ==========================\n");
        System.out.printf("1) Checking if tree1 and tree2 are Isomorphic... \n\t");
        start = System.currentTimeMillis();
        boolean isIsomorphic = algorithm.isIsomorphic(tree1, tree2);
        end = System.currentTimeMillis();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ms to check", f.format(new Date(end - start)));
        System.out.println("\n");

        System.out.printf("2) Checking if tree1 and tree3 are Isomorphic... \n\t");
        start = System.currentTimeMillis();
        isIsomorphic = algorithm.isIsomorphic(tree1, tree3);
        end = System.currentTimeMillis();
        System.out.printf("a) " + (isIsomorphic ? "Is" : "Not") + " Isomorphic, it took %s ms to check", f.format(new Date(end - start)));
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
 * Class that prints the Binary Trees
 */
class Printer {

    /**
     * prints the node as a tree
     *
     * @param tree the tree to print out
     */
    public static <T extends Comparable<?>> void printTree(Node tree) {
        int maxLevel = Printer.maxLevel(tree);
        printNodeInternal(Collections.singletonList(tree), 1, maxLevel);
    }

    /**
     * prints all the nodes as a tree
     *
     * @param nodes    the list of nodes to print
     * @param level    the level that you want to print
     * @param maxLevel the maximum level that you're on
     */
    private static <T extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || Printer.areAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        Printer.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            Printer.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                Printer.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    Printer.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null) System.out.print("/");
                else Printer.printWhitespaces(1);

                Printer.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null) System.out.print("\\");
                else Printer.printWhitespaces(1);

                Printer.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        Printer.printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) System.out.print(" ");
    }

    /**
     * gets the max level for the tree
     *
     * @param tree gets the max level of the node
     * @return the max level of the node
     */
    private static <T extends Comparable<?>> int maxLevel(Node tree) {
        if (tree == null) return 0;

        return Math.max(Printer.maxLevel(tree.left), Printer.maxLevel(tree.right)) + 1;
    }

    /**
     * checks to see if the list items are null
     *
     * @param list list to check
     */
    private static <T> boolean areAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null) return false;
        }

        return true;
    }

}