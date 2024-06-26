import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
    private Node root;
    private final Node nil;

    public BinarySearchTree() {
        nil = new Node(Integer.MIN_VALUE);
        nil.left = nil.right = nil.parent = nil;
        root = nil;
    }

    public void add(int value) {
        Node addedNode = new Node(value);
        add(addedNode);
        System.out.println(value + " added to tree");
    }

    private void add(Node z) {
        Node y = nil;
        Node x = root;

        while (x != nil) {
            y = x;
            if (z.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if (y == nil) {
            root = z;
        } else if (z.value < y.value) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = z.right = nil;
    }

    public void delete(int value) {
        Node nodeToDelete = findNode(root, value);
        if (nodeToDelete != nil) {
            delete(nodeToDelete);
            System.out.println(value + " deleted from tree");
        } else {
            System.out.println(value + " not found in tree");
        }
    }

    private void delete(Node z) {
        if (z.left == nil) {
            transplant(z, z.right);
        } else if (z.right == nil) {
            transplant(z, z.left);
        } else {
            Node y = treeMinimum(z.right);
            if (y.parent != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
        }
    }

    private Node findNode(Node root, int value) {
        Node current = root;
        while (current != nil && current.value != value) {
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != nil) {
            v.parent = u.parent;
        }
    }

    private Node treeMinimum(Node x) {
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    public int height() {
        if (root == nil) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            height++;

            for (int i = 0; i < levelSize; i++) {
                Node currentNode = queue.poll();

                if (currentNode.left != nil) {
                    queue.add(currentNode.left);
                }

                if (currentNode.right != nil) {
                    queue.add(currentNode.right);
                }
            }
        }

        return height;
    }

    public void printTree() {
        int height = height();
        char[] leftTrace = new char[height];
        char[] rightTrace = new char[height];
        printTreeRecursive(root, 0, '-', leftTrace, rightTrace);
    }

    private void printTreeRecursive(Node node, int depth, char prefix, char[] leftTrace, char[] rightTrace) {
        if (node == nil) {
            return;
        }

        if (node.left != nil) {
            printTreeRecursive(node.left, depth + 1, '/', leftTrace, rightTrace);
        }

        if (prefix == '/') {
            leftTrace[depth - 1] = '|';
        }
        if (prefix == '\\') {
            rightTrace[depth - 1] = ' ';
        }
        if (depth == 0) {
            System.out.print("-");
        }
        if (depth > 0) {
            System.out.print(" ");
        }
        for (int i = 0; i < depth - 1; i++) {
            if (leftTrace[i] == '|' || rightTrace[i] == '|') {
                System.out.print("| ");
            } else {
                System.out.print("  ");
            }
        }
        if (depth > 0) {
            System.out.print(prefix + "-");
        }
        System.out.println("[" + node.value + "]");
        leftTrace[depth] = ' ';
        if (node.right != nil) {
            rightTrace[depth] = '|';
            printTreeRecursive(node.right, depth + 1, '\\', leftTrace, rightTrace);
        }
    }
}
