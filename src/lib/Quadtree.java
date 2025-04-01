package lib;

public class Quadtree {
    private Image info;
    private boolean leaf;
    private Quadtree Q1, Q2, Q3, Q4;

    public Quadtree(Image image) {
        this.info = image;
        this.leaf = true;
        this.Q1 = null;
        this.Q2 = null;
        this.Q3 = null;
        this.Q4 = null;
    }

    public Image getinfoImage() {
        return this.info;
    }

    public void setLeaf(boolean b) {
        this.leaf = b;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public Quadtree getQ1() {
        if (!isLeaf()) {
            return this.Q1;
        }
        return null;
    }

    public Quadtree getQ2() {
        if (!isLeaf()) {
            return this.Q2;
        }
        return null;
    }

    public Quadtree getQ3() {
        if (!isLeaf()) {
            return this.Q3;
        }
        return null;
    }

    public Quadtree getQ4() {
        if (!isLeaf()) {
            return this.Q4;
        }
        return null;
    }

    public void split() {
        if (isLeaf()) {
            setLeaf(false);
            Image[] collectionImages = this.info.splitImage();
            this.Q1 = new Quadtree(collectionImages[0]);
            this.Q2 = new Quadtree(collectionImages[1]);
            this.Q3 = new Quadtree(collectionImages[2]);
            this.Q4 = new Quadtree(collectionImages[3]);
        }
    }

    public void print(Quadtree qt, int n) {
        if (qt == null)
            return;

        System.err.println(" ".repeat(n) + (qt.isLeaf() ? "A" : "Q"));

        if (!qt.isLeaf()) {
            print(qt.getQ1(), n + 2);
            print(qt.getQ2(), n + 2);
            print(qt.getQ3(), n + 2);
            print(qt.getQ4(), n + 2);
        }
    }

    public int getDepth() {
        if (isLeaf()) {
            return 0;
        } else {
            int d1 = Q1.getDepth();
            int d2 = Q2.getDepth();
            int d3 = Q3.getDepth();
            int d4 = Q4.getDepth();
            return 1 + Math.max(Math.max(d1, d2), Math.max(d3, d4));
        }
    }

    public int countNodes() {
        if (isLeaf()) {
            return 1;
        } else {
            return 1 + Q1.countNodes() + Q2.countNodes() + Q3.countNodes() + Q4.countNodes();
        }
    }

}
