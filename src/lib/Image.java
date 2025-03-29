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

    public void print() {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                Pixel p = getPixel(i, j);
                p.print();
                System.out.println();
            }
        }
    }
}