package lib;

public class Quadtree {
    private boolean leaf;
    private int startRow;
    private int startCol;
    private int row;
    private int col;
    private Pixel avgPixel;
    private Quadtree Q1, Q2, Q3, Q4;

    public Quadtree(int row, int col, int startRow, int startCol) {
        this.row = row;
        this.col = col;
        this.startCol = startCol;
        this.startRow = startRow;
        this.avgPixel = null;
        this.leaf = true;
        this.Q1 = null;
        this.Q2 = null;
        this.Q3 = null;
        this.Q4 = null;
    }

    public Pixel getAvgPixel() {
        return this.avgPixel;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getStartCol() {
        return this.startCol;
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
            int midRow = row / 2;
            int midCol = col / 2;
            this.Q1 = new Quadtree(midRow, midCol, this.startRow, this.startCol);
            this.Q2 = new Quadtree(midRow, this.col - midCol, this.startRow, this.startCol + midCol);
            this.Q3 = new Quadtree(this.row - midRow, midCol, this.startRow + midRow, this.startCol);
            this.Q4 = new Quadtree(row - midRow, col - midCol, this.startRow + midRow, this.startCol + midCol);
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

    public void calcAvgPixel() {
        int red = 0;
        int green = 0;
        int blue = 0;
        int totalPixels = getRow() * getCol();

        for (int i = startRow; i < getRow() + startRow; i++) {
            for (int j = startCol; j < getCol() + startCol; j++) {
                Pixel p = IO.infoImage.getPixel(i, j);
                red += p.getRed();
                green += p.getGreen();
                blue += p.getBlue();
            }
        }

        this.avgPixel = new Pixel(red / totalPixels, green / totalPixels, blue / totalPixels);
    }
}
