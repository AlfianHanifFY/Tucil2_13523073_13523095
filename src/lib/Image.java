package lib;

import java.util.Scanner;

public class Image {
    private Pixel[][] matrix;
    private int rows;
    private int cols;
    private int rowStart;
    private int colStart;
    private Pixel avgPixel;

    // param
    int method;
    double treshold;
    int minimumBlockSize;

    static Scanner inputScanner = new Scanner(System.in);

    // ==== ini buat nge tes co ==== //
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
    }

    // Konstruktor
    public Image(int _rows, int _cols) {
        this.rows = _rows;
        this.cols = _cols;
        this.rowStart = 0;
        this.colStart = 0;
        this.matrix = new Pixel[_rows][_cols];
        this.method = 1;
        this.treshold = 10;
        this.minimumBlockSize = 8;
    }

    // cctor
    public Image(Image img, int _rows, int _cols, int rowStart, int colStart, int region) {
        this.rows = _rows;
        this.cols = _cols;
        this.method = img.method;
        this.treshold = img.treshold;
        this.minimumBlockSize = img.minimumBlockSize;
        this.matrix = new Pixel[_rows][_cols];

        // rowStart dan colStart di sini sudah merupakan koordinat absolut untuk
        // sub-image
        this.rowStart = rowStart;
        this.colStart = colStart;

        // Salin piksel dari image induk dengan konversi indeks lokal
        for (int i = 0; i < _rows; i++) {
            for (int j = 0; j < _cols; j++) {
                int localRow = (this.rowStart + i) - img.getStartRow();
                int localCol = (this.colStart + j) - img.getStartCol();
                Pixel originalPixel = img.getPixel(localRow, localCol);
                setPixel(i, j, originalPixel);
            }
        }
    }

    public void setImageParam(int _method, double _treshold, int _minimumBlockSize) {
        this.method = _method;
        this.treshold = _treshold;
        this.minimumBlockSize = _minimumBlockSize;
    }

    public void setPixel(int row, int col, Pixel p) {
        this.matrix[row][col] = p;
    }

    public void setAvgPixel() {
        int red = 0;
        int green = 0;
        int blue = 0;
        int totalPixels = getRow() * getCol();

        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel p = getPixel(i, j);
                red += p.getRed();
                green += p.getGreen();
                blue += p.getBlue();
            }
        }

        this.avgPixel = new Pixel(red / totalPixels, green / totalPixels, blue / totalPixels);
    }

    public Pixel getPixel(int row, int col) {
        return this.matrix[row][col];
    }

    public Pixel getAvgPixel() {
        return this.avgPixel;
    }

    public int getSize() {
        return this.rows * this.cols;
    }

    public int getRow() {
        return this.rows;
    }

    public int getCol() {
        return this.cols;
    }

    public int getStartRow() {
        return this.rowStart;
    }

    public int getStartCol() {
        return this.colStart;
    }

    public void print(int limitRow) {
        int R;
        if (limitRow == 0) {
            R = getRow();
        } else {
            R = limitRow;
        }
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel p = getPixel(i, j);
                p.print();
                System.out.println();
            }
        }
    }

    public Image[] splitImage() {
        // membagi image menjadi 4 bagian

        /*
         * ILUSTRASI SPLIT
         * 
         * collection[0] | collection[1]
         * --------------|--------------
         * collection[2] | collection[3]
         * 
         */
        Image[] collection = new Image[4];
        int midRow = rows / 2;
        int midCol = cols / 2;

        collection[0] = new Image(this, midRow, midCol, this.rowStart, this.colStart, 0); // Top-left
        collection[1] = new Image(this, midRow, cols - midCol, this.rowStart, this.colStart + midCol, 1); // Top-right
        collection[2] = new Image(this, rows - midRow, midCol, this.rowStart + midRow, this.colStart, 2); // Bottom-left
        collection[3] = new Image(this, rows - midRow, cols - midCol, this.rowStart + midRow, this.colStart + midCol,
                3); // Bottom-right

        return collection;
    }

    /* Error Measurement Method */

    public double getErrorMeasurement(int i, int j, int r, int c, Pixel avgP) {
        if (method == 1) {
            return Variance(i, j, r, c, avgP);
        }

        if (method == 2) {
            return MeanAbsoluteDeviation(i, j, r, c, avgP);
        }

        if (method == 3) {
            return MaxPixelDifference(i, j, r, c, avgP);
        }

        if (method == 4) {
            return Entropy(i, j, r, c, avgP);
        }

        if (method == 5) {
            return SSIM();
        }

        // kalo ga ada method yang sesuai
        return 0;
    }

    private double Variance(int i, int j, int r, int c, Pixel avgP) {
        double redVariance = 0;
        double greenVariance = 0;
        double blueVariance = 0;

        for (int x = i; x < r + i; x++) {
            for (int y = j; y < c + j; y++) {
                Pixel p = getPixel(x, y);
                redVariance += Math.pow(p.getRed() - avgP.getRed(), 2);
                greenVariance += Math.pow(p.getGreen() - avgP.getGreen(), 2);
                blueVariance += Math.pow(p.getBlue() - avgP.getBlue(), 2);
            }
        }
        redVariance /= getSize();
        greenVariance /= getSize();
        blueVariance /= getSize();

        return (redVariance + greenVariance + blueVariance) / 3;

    }

    public double MeanAbsoluteDeviation(int i, int j, int r, int c, Pixel avgP) {
        double redMAD = 0;
        double greenMAD = 0;
        double blueMAD = 0;

        for (int x = i; x < r + i; x++) {
            for (int y = j; y < c + j; y++) {
                Pixel p = getPixel(x, y);
                redMAD += Math.abs(p.getRed() - avgP.getRed());
                greenMAD += Math.abs(p.getGreen() - avgP.getGreen());
                blueMAD += Math.abs(p.getBlue() - avgP.getBlue());
            }
        }
        redMAD /= getSize();
        greenMAD /= getSize();
        blueMAD /= getSize();

        return (redMAD + greenMAD + blueMAD) / 3;
    }

    public double MaxPixelDifference(int i, int j, int r, int c, Pixel avgP) {
        double redDiff = 0;
        double greenDiff = 0;
        double blueDiff = 0;
        double maxRed = 0;
        double maxGreen = 0;
        double maxBlue = 0;
        double minRed = 255;
        double minGreen = 255;
        double minBlue = 255;

        // Find max and min red, green, and blue
        for (int x = i; x < r + i; x++) {
            for (int y = j; y < c + j; y++) {
                Pixel p = getPixel(x, y);
                if (p.getRed() > maxRed) {
                    maxRed = p.getRed();
                } else if (p.getRed() < minRed) {
                    minRed = p.getRed();
                }
                if (p.getGreen() > maxGreen) {
                    maxGreen = p.getGreen();
                } else if (p.getGreen() < minGreen) {
                    minGreen = p.getGreen();
                }
                if (p.getBlue() > maxBlue) {
                    maxBlue = p.getBlue();
                } else if (p.getBlue() < minBlue) {
                    minBlue = p.getBlue();
                }
            }
        }

        redDiff = maxRed - minRed;
        greenDiff = maxGreen - minGreen;
        blueDiff = maxBlue - minBlue;

        return (redDiff + greenDiff + blueDiff) / 3;
    }

    public double Entropy(int i, int j, int r, int c, Pixel avgP) {
        double redEntropy = 0;
        double greenEntropy = 0;
        double blueEntropy = 0;
        int redCount[] = new int[256];
        int greenCount[] = new int[256];
        int blueCount[] = new int[256];

        // masukkan ke list agar bisa dihitung peluangnya
        for (int x = i; x < r + i; x++) {
            for (int y = j; y < c + j; y++) {
                Pixel p = getPixel(x, y);
                redCount[p.getRed()]++;
                greenCount[p.getGreen()]++;
                blueCount[p.getBlue()]++;
            }
        }

        for (int k = 0; k < 256; k++) {
            if (redCount[k] != 0) {
                redEntropy += (double) redCount[k] / getSize() * Math.log((double) redCount[k] / getSize());
            }
            if (greenCount[k] != 0) {
                greenEntropy += (double) greenCount[k] / getSize() * Math.log((double) greenCount[k] / getSize());
            }
            if (blueCount[k] != 0) {
                blueEntropy += (double) blueCount[k] / getSize() * Math.log((double) blueCount[k] / getSize());
            }
        }

        redEntropy *= -1;
        greenEntropy *= -1;
        blueEntropy *= -1;

        return (redEntropy + greenEntropy + blueEntropy) / 3;
    }

    public double SSIM() {
        return 0;
    }

}