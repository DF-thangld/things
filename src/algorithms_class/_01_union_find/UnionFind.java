package algorithms_class._01_union_find;

public class UnionFind {
    private int numberOfMembers = 0;
    private int[] ids;
    private int[] size;

    public UnionFind(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
        this.ids = new int[numberOfMembers];
        for (int i=0; i<numberOfMembers; i++) {
            this.ids[i] = i;
            this.size[i] = 1;
        }
    }

    public void connect(int node1, int node2) {
        int root1 = this.getRoot(node1);
        int root2 = this.getRoot(node2);
        if (root1 == root2) {
            return;
        }

        int size1 = this.size[root1];
        int size2 = this.size[root2];
        if (size1 > size2) {
            this.ids[root2] = root1;
        }
        else {
            this.ids[root1] = root2;
        }

        this.size[root1] = this.size[root2] = size1 + size2;
    }

    public boolean isConnected(int node1, int node2) {
        return this.getRoot(node1) == this.getRoot(node2);
    }

    private int getRoot(int node) {
        while (true) {
            if (this.ids[node] != node) {
                node = this.ids[node];
            }
            else {
                return node;
            }
        }
    }
}
