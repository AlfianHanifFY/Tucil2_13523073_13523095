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
         * -------------------------------
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

    public double getErrorMeasurement() {
        if (method == 1) {
            return Variance();
        }

        if (method == 2) {
            return MeanAbsoluteDeviation();
        }

        if (method == 3) {
            return MaxPixelDifference();
        }

        if (method == 4) {
            return Entropy();
        }

        if (method == 5) {
            return SSIM();
        }

        // kalo ga ada method yang sesuai
        return 0;
    }

    private double Variance() {
        double meanRed = 0, meanGreen = 0, meanBlue = 0;
        int totalPixels = getRow() * getCol();

        // Calculate mean values
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel p = getPixel(i, j);
                meanRed += p.getRed();
                meanGreen += p.getGreen();
                meanBlue += p.getBlue();
            }
        }
        meanRed /= totalPixels;
        meanGreen /= totalPixels;
        meanBlue /= totalPixels;

        // Calculate variance
        double variance = 0;
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel p = getPixel(i, j);
                variance += Math.pow(p.getRed() - meanRed, 2);
                variance += Math.pow(p.getGreen() - meanGreen, 2);
                variance += Math.pow(p.getBlue() - meanBlue, 2);
            }
        }
        return variance / (3 * totalPixels); // Normalize variance
    }

    public double MeanAbsoluteDeviation() {
        return 0;
    }

    public double MaxPixelDifference() {
        return 0;
    }

    public double Entropy() {
        return 0;
    }

    public double SSIM() {
        return 0;
    }
}