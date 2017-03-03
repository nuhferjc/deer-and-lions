import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

/*
 * Author: Joseph Nuhfer
 * Version: 11.30.16
 *
 * The program solves the n-lions and n-deer boat problem using a Java Queue.
 * - There are n lions and n deer on one side of the river (the left side).
 * - You want to take all of them to the other side of the river.
 * - You have a boat, and you can take at most two animals at a time. (you must take at least one each trip).
 * - If at any point the deer fall to below half the number of lions, the lions will eat the deer and you will fail.
 * - What is the minimum number of trips it will take to take all of the animals from one side to the other?
 */

public class LionDeer {
    private static int n;
    private static Node[][] pred;
    private static Node goal;
    private static int[][] dist;
    private static Queue<Node> q;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        q = new LinkedList<Node>();
        n = keyboard.nextInt();
        pred = new Node[n * 2 + 2][n * 2 + 2];
        dist = new int[n * 2 + 2][n * 2 + 2];
        for (int i = 0; i < n * 2 + 1; i++) {
            for (int j = 0; j < n * 2 + 1; j++) {
                pred[i][j] = new Node(-1, -1, -1, -1, false);
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        dist[n][n] = 0;
        q.add(new Node(n, n, 0, 0, false));
        while (goal == null)
            bfs(q.remove());
        System.out.println("Yay!");
        printPath(goal);
        System.out.println(dist[n * 2 + 1][n * 2 + 1] + " trips required.");
    }

    private static void bfs(Node node) {
        if (node.rightDeer == n && node.rightLions == n) {
            if (node.isRightBoat)
                dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n];
            else
                dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions];
            goal = new Node(node.leftDeer, node.leftLions, node.rightDeer, node.rightLions, node.isRightBoat);
            return;
        }
        if (((node.leftLions > node.leftDeer * 2) && node.leftDeer != 0) || ((node.rightLions > node.rightDeer * 2) && node.rightDeer != 0)) {
            return;
        }
        if (node.isRightBoat) {
            if (node.rightDeer >= 2) {
                if (node.rightDeer - 2 == n && node.rightLions == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    goal = new Node(node.leftDeer + 2, node.leftLions, node.rightDeer - 2, node.rightLions, false);
                    return;
                }
                if (dist[node.leftDeer + 2][node.leftLions] == Integer.MAX_VALUE) {
                    pred[node.leftDeer + 2][node.leftLions] = node;
                    dist[node.leftDeer + 2][node.leftLions] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    q.add(new Node(node.leftDeer + 2, node.leftLions, node.rightDeer - 2, node.rightLions, false));
                }
            }
            if (node.rightLions >= 2) {
                if (node.rightDeer == n && node.rightLions - 2 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    goal = new Node(node.leftDeer, node.leftLions + 2, node.rightDeer, node.rightLions - 2, false);
                    return;
                }
                if (dist[node.leftDeer][node.leftLions + 2] == Integer.MAX_VALUE) {
                    pred[node.leftDeer][node.leftLions + 2] = node;
                    dist[node.leftDeer][node.leftLions + 2] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    q.add(new Node(node.leftDeer, node.leftLions + 2, node.rightDeer, node.rightLions - 2, false));
                }
            }
            if (node.rightDeer != 0 && node.rightLions != 0) {
                if (node.rightDeer - 1 == n && node.rightLions - 1 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    goal = new Node(node.leftDeer + 1, node.leftLions + 1, node.rightDeer - 1, node.rightLions - 1, false);
                    return;
                }
                if (dist[node.leftDeer + 1][node.leftLions + 1] == Integer.MAX_VALUE) {
                    pred[node.leftDeer + 1][node.leftLions + 1] = node;
                    dist[node.leftDeer + 1][node.leftLions + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    q.add(new Node(node.leftDeer + 1, node.leftLions + 1, node.rightDeer - 1, node.rightLions - 1, false));
                }
            }
            if (node.rightDeer != 0) {
                if (node.rightDeer - 1 == n && node.rightLions == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    goal = new Node(node.leftDeer + 1, node.leftLions, node.rightDeer - 1, node.rightLions, false);
                    return;
                }
                if (dist[node.leftDeer + 1][node.leftLions] == Integer.MAX_VALUE) {
                    pred[node.leftDeer + 1][node.leftLions] = node;
                    dist[node.leftDeer + 1][node.leftLions] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    q.add(new Node(node.leftDeer + 1, node.leftLions, node.rightDeer - 1, node.rightLions, false));
                }
            }
            if (node.rightLions != 0) {
                if (node.rightDeer == n && node.rightLions - 1 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    goal = new Node(node.leftDeer, node.leftLions + 1, node.rightDeer, node.rightLions - 1, false);
                    return;
                }
                if (dist[node.leftDeer][node.leftLions + 1] == Integer.MAX_VALUE) {
                    pred[node.leftDeer][node.leftLions + 1] = node;
                    dist[node.leftDeer][node.leftLions + 1] = dist[node.leftDeer + n][node.leftLions + n] + 1;
                    q.add(new Node(node.leftDeer, node.leftLions + 1, node.rightDeer, node.rightLions - 1, false));
                }
            }
        }
        else {
            if (node.leftDeer >= 2) {
                if (node.rightDeer + 2 == n && node.rightLions == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions] + 1;
                    goal = new Node(node.leftDeer - 2, node.leftLions, node.rightDeer + 2, node.rightLions, true);
                    return;
                }
                if (dist[node.leftDeer - 2 + n][node.leftLions + n] == Integer.MAX_VALUE) {
                    pred[node.leftDeer - 2 + n][node.leftLions + n] = node;
                    dist[node.leftDeer - 2 + n][node.leftLions + n] = dist[node.leftDeer][node.leftLions] + 1;
                    q.add(new Node(node.leftDeer - 2, node.leftLions, node.rightDeer + 2, node.rightLions, true));
                }
            }
            if (node.leftLions >= 2) {
                if (node.rightDeer == n && node.rightLions + 2 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions] + 1;
                    goal = new Node(node.leftDeer, node.leftLions - 2, node.rightDeer, node.rightLions + 2, true);
                    return;
                }
                if (dist[node.leftDeer + n][node.leftLions - 2 + n] == Integer.MAX_VALUE) {
                    pred[node.leftDeer + n][node.leftLions - 2 + n] = node;
                    dist[node.leftDeer + n][node.leftLions - 2 + n] = dist[node.leftDeer][node.leftLions] + 1;
                    q.add(new Node(node.leftDeer, node.leftLions - 2, node.rightDeer, node.rightLions + 2, true));
                }
            }
            if (node.leftDeer != 0 && node.leftLions != 0) {
                if (node.rightDeer + 1 == n && node.rightLions + 1 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions] + 1;
                    goal = new Node(node.leftDeer - 1, node.leftLions - 1, node.rightDeer + 1, node.rightLions + 1, true);
                    return;
                }
                if (dist[node.leftDeer - 1 + n][node.leftLions - 1 + n] == Integer.MAX_VALUE) {
                    pred[node.leftDeer - 1 + n][node.leftLions - 1 + n] = node;
                    dist[node.leftDeer - 1 + n][node.leftLions - 1 + n] = dist[node.leftDeer][node.leftLions] + 1;
                    q.add(new Node(node.leftDeer - 1, node.leftLions - 1, node.rightDeer + 1, node.rightLions + 1, true));
                }
            }
            if (node.leftDeer != 0) {
                if (node.rightDeer + 1 == n && node.rightLions == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions] + 1;
                    goal = new Node(node.leftDeer - 1, node.leftLions, node.rightDeer + 1, node.rightLions, true);
                    return;
                }
                if (dist[node.leftDeer - 1 + n][node.leftLions + n] == Integer.MAX_VALUE) {
                    pred[node.leftDeer - 1 + n][node.leftLions + n] = node;
                    dist[node.leftDeer - 1 + n][node.leftLions + n] = dist[node.leftDeer][node.leftLions] + 1;
                    q.add(new Node(node.leftDeer - 1, node.leftLions, node.rightDeer + 1, node.rightLions, true));
                }
            }
            if (node.leftLions != 0) {
                if (node.rightDeer == n && node.rightLions + 1 == n) {
                    pred[n * 2 + 1][n * 2 + 1] = node;
                    dist[n * 2 + 1][n * 2 + 1] = dist[node.leftDeer][node.leftLions] + 1;
                    goal = new Node(node.leftDeer, node.leftLions - 1, node.rightDeer, node.rightLions + 1, true);
                    return;
                }
                if (dist[node.leftDeer + n][node.leftLions - 1 + n] == Integer.MAX_VALUE) {
                    pred[node.leftDeer + n][node.leftLions - 1 + n] = node;
                    dist[node.leftDeer + n][node.leftLions - 1 + n] = dist[node.leftDeer][node.leftLions] + 1;
                    q.add(new Node(node.leftDeer, node.leftLions - 1, node.rightDeer, node.rightLions + 1, true));
                }
            }
        }
    }
    
    public static void printPath(Node node) {
        if (node.leftDeer == -1)
            return;
        if (node == goal)
            printPath(pred[2 * n + 1][2 * n + 1]);
        else if (node.isRightBoat)
            printPath(pred[node.leftDeer + n][node.leftLions + n]);
        else
            printPath(pred[node.leftDeer][node.leftLions]);
        for (int i = 0; i < node.leftDeer; i++) {
            if (i == node.leftDeer - 1)
                System.out.print("d ");
            else
                System.out.print("d");
        }
        if (node.leftDeer == 0)
            System.out.print(" ");
        for (int j = 0; j < node.leftLions; j++) {
            if (j == node.leftLions - 1)
                System.out.print("l ");
            else
                System.out.print("l");
        }
        if (node.leftLions == 0)
            System.out.print(" ");
        System.out.print("| ");
        for (int k = 0; k < node.rightDeer; k++) {
            if (k == node.rightDeer - 1)
                System.out.print("d ");
            else
                System.out.print("d");
        }
        if (node.rightDeer == 0)
            System.out.print(" ");
        for (int l = 0; l < node.rightLions; l++) {
            if (l == node.rightLions - 1)
                System.out.print("l ");
            else
                System.out.print("l");
        }
        System.out.println();
    }
}
