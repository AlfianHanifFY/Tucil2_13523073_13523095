package lib;

import java.util.Scanner;

public class Image {
    private Pixel[][] matrix;
    private int rows;
    private int cols;

    // param
    private int variance;
    private double treshold;
    private int minimumBlockSize;

    static Scanner inputScanner = new Scanner(System.in);

    // ==== ini buat nge tes co ==== //
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
    }

    // Konstruktor
    public Image(int _rows, int _cols) {
        this.rows = _rows;
        this.cols = _cols;
        this.matrix = new Pixel[_rows][_cols];
    }

    // cctor
    public Image(Image img, int _rows, int _cols, int rowStart, int colStart) {
        this.rows = _rows;
        this.cols = _cols;
        this.variance = img.variance;
        this.treshold = img.treshold;
        this.minimumBlockSize = img.minimumBlockSize;
        this.matrix = new Pixel[_rows][_cols];

        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel originalPixel = img.getPixel(rowStart + i, colStart + j);
                Pixel p = new Pixel(originalPixel);
                setPixel(i, j, p);
            }
        }

    }

    public void setImageParam(int _variance, double _treshold, int _minimumBlockSize) {
        this.variance = _variance;
        this.treshold = _treshold;
        this.minimumBlockSize = _minimumBlockSize;
    }

    public void setPixel(int row, int col, Pixel p) {
        this.matrix[row][col] = p;
    }

    public Pixel getPixel(int row, int col) {
        return this.matrix[row][col];
    }

    public int getRow() {
        return this.rows;
    }

    public int getCol() {
        return this.cols;
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

        collection[0] = new Image(this, midRow, midCol, 0, 0); // Top-left
        collection[1] = new Image(this, midRow, cols - midCol, 0, midCol); // Top-right
        collection[2] = new Image(this, rows - midRow, midCol, midRow, 0); // Bottom-left
        collection[3] = new Image(this, rows - midRow, cols - midCol, midRow, midCol); // Bottom-right

        return collection;
    }
}