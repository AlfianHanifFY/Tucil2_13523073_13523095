package lib;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Locale;

public class Matrix {
    double[][] matrix;
    int rows;
    int cols;
    static Scanner inputScanner = new Scanner(System.in);

    // ==== ini buat nge tes co ==== //
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // Matrix m1 = new Matrix(1, 2);
        // Matrix m2 = new Matrix(3, 1);
        // Matrix M, l, r;
        // Matrix res;
        // int a, b;
        // System.out.println("matrix 1");
        // m1.readMatrix();
        // m1.generateEselon();
        // l = createHilbert(6);
        // l.printMatrix();
        // System.out.println();
        // m1.printMatrix();
        // System.out.println("matrix 2");
        // m2.readMatrix();
        // System.out.println("augmented:");
        // M = Matrix.createAugmented(m1, m2);
        // M.printMatrix();
        // r = Matrix.disassembleAugmented(M, false);
        // r.printMatrix();
        // l = Matrix.disassembleAugmented(M, true);
        // l.printMatrix();
        // M = Matrix.createMatrixIdentitas(3);
        // m2 = Matrix.createAugmented(M, m1);
        // m2.printMatrix();
        // m2 = Matrix.getHalfLeft(m2);
        // m2.printMatrix();
        // M.generateEselonReduksi();
        // M.printMatrix();
        // M = Matrix.getHalfRigth(M);
        // M.printMatrix();
        // System.out.println();
        // m2.printMatrix();
        // res = Matrix.multiplyMatrix(m1, m2);
        // System.out.println();
        // res.printMatrix();
        // m1.generateEselonReduksi();
        // m1.printMatrix();
        // System.out.println();
        // a = m1.colLength(0);
        // b = m1.rowLength(0);
        // System.out.println("list legth = " + b + " " + a);
        // m1.sortRowByZero();
        // m1.printMatrix();
        // System.out.println("plusKRow");
        // m1.plusKRow(0, 2, 1);
        // m1.printMatrix();
        // System.out.println("transpose");
        // m1.transpose();
        // m1.printMatrix();
        // m1 = changeCol(m1, 1, m2);
        // m1.printMatrix();
        // scanner.close();
    }

    // Konstruktor
    public Matrix(int _rows, int _cols) {
        this.rows = _rows;
        this.cols = _cols;
        this.matrix = new double[_rows][_cols];
    }

    // Selektor
    public double[] getRowContent(int row) {
        double[] result = new double[rows];
        for (int i = 0; i < this.cols; i++) {
            result[i] = matrix[row][i];
        }
        return result;

    }

    public double[] getColContent(int col) {
        double[] result = new double[this.cols];
        for (int i = 0; i < this.cols; i++) {
            result[i] = this.matrix[i][col];
        }
        return result;
    }

    public void readMatrix() {
        inputScanner.useLocale(Locale.US);

        while (true) {
            try {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = inputScanner.nextDouble();
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input matriks tidak valid. Silakan masukkan angka dan ulangi input matriks!\n");
                inputScanner.next();
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // format 2 angka dibelakang koma
                System.out.print(String.format(Locale.US, "%.4f ", matrix[i][j]));
            }
            System.out.print("\n");
        }
    }

    public double getElmt(int row, int col) {
        return this.matrix[row][col];
    }

    public int getCol() {
        return this.cols;
    }

    public int getRow() {
        return this.rows;
    }

    public void setElMT(int row, int col, double val) {
        this.matrix[row][col] = val;
    }

    // swap lane
    public void swapRow(int row1, int row2) {
        double temp;
        int i;
        for (i = 0; i < getCol(); i++) {
            temp = getElmt(row1, i);
            setElMT(row1, i, getElmt(row2, i));
            setElMT(row2, i, temp);
        }

    }

    public static Matrix changeCol(Matrix m, int nCol, Matrix newCol) {
        // newCol merupakan matrix n x 1
        int i, j;
        Matrix M = new Matrix(m.getRow(), m.getCol());
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getCol(); j++) {
                if (j == nCol) {
                    M.setElMT(i, j, newCol.getElmt(i, 0));
                } else {
                    M.setElMT(i, j, m.getElmt(i, j));
                }
            }

        }
        return M;

    }

    // plusKRow
    public void plusKRow(int row, double k, int krow) {
        int i;
        for (i = 0; i < getCol(); i++) {
            setElMT(row, i, getElmt(row, i) + getElmt(krow, i) * k);
        }
    }

    public void divideRow(int row, double k) {
        // ngebagi row pake k
        int i;
        for (i = 0; i < getCol(); i++) {
            if (getElmt(row, i) != 0) {
                setElMT(row, i, getElmt(row, i) / k);
            }

        }
    }

    // transpose
    public void transpose() {
        Matrix newM = new Matrix(getRow(), getCol());
        int i, j;
        for (i = 0; i < getRow(); i++) {
            for (j = 0; j < getCol(); j++) {
                newM.matrix[i][j] = getElmt(j, i);
            }
        }
        this.matrix = newM.matrix;
    }

    // cek elemen baris yang ga 0
    public int rowLength(int row) {
        int i, sum = 0;
        boolean valid = true;
        for (i = 0; i < getCol(); i++) {
            if (getElmt(row, i) == 0 && valid) {
                sum += 1;
            } else {
                valid = false;
            }
        }
        return sum;
    }

    public int colLength(int col) {
        int i, sum = 0;
        boolean valid = true;
        for (i = 0; i < getRow(); i++) {
            if (getElmt(i, col) == 0 && valid) {
                sum += 1;
            } else {
                valid = false;
            }
        }
        return sum;
    }

    public int sortRowByZero() {
        int i, j, swapCount = 0;
        for (i = 0; i < getRow(); i++) {
            for (j = i; j < getRow(); j++) {
                if (rowLength(i) > rowLength(j)) {
                    swapRow(i, j);
                    swapCount += 1;
                }
            }
        }
        return swapCount;
    }

    public void generateEselon() {
        int i;
        double k;
        sortRowByZero();
        for (i = 1; i < getRow(); i++) {
            if (rowLength(i) == rowLength(i - 1) && rowLength(i) != getCol()) {
                k = getElmt(i, rowLength(i)) / getElmt(i - 1, rowLength(i - 1));
                plusKRow(i, -k, i - 1);
                sortRowByZero();
                i--;
            }
        }
        for (i = 0; i < getRow(); i++) {
            if (rowLength(i) != getCol()) {
                k = getElmt(i, rowLength(i));
                divideRow(i, k);
            }

        }

    }

    public void generateEselonReduksi() {
        int i, j;
        double k;
        generateEselon();
        for (i = 1; i < getRow(); i++) {
            for (j = i - 1; j >= 0; j--) {
                if (rowLength(i) != getCol()) {
                    k = getElmt(j, rowLength(i));
                    plusKRow(j, -k, i);
                }
            }
        }
    }

    public static Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix resM = new Matrix(m1.getRow(), m2.getCol());
        int i, j, k;
        double val;
        // kalo ga valid nanti muncul matrix kosong :D
        if (m1.getCol() == m2.getRow()) {
            for (i = 0; i < resM.getRow(); i++) {
                for (j = 0; j < resM.getCol(); j++) {
                    val = 0;
                    for (k = 0; k < m1.getCol(); k++) {
                        val += m1.getElmt(i, k) * m2.getElmt(k, j);
                        resM.setElMT(i, j, val);
                    }
                }
            }
        }
        return resM;
    }

    public static Matrix createAugmented(Matrix m1, Matrix m2) {
        Matrix M = new Matrix(m1.getRow(), m1.getCol() + m2.getCol());
        int i, j;
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getCol(); j++) {
                if (j < m1.getCol()) {
                    M.setElMT(i, j, m1.getElmt(i, j));
                } else {
                    M.setElMT(i, j, m2.getElmt(i, j - m1.getCol()));
                }
            }
        }
        return M;
    }

    public static Matrix disassembleAugmented(Matrix m, boolean left) {
        int i, j;
        if (left) {
            Matrix M = new Matrix(m.getRow(), m.getCol() - 1);
            for (i = 0; i < M.getRow(); i++) {
                for (j = 0; j < M.getCol(); j++) {
                    M.setElMT(i, j, m.getElmt(i, j));
                }
            }
            return M;
        } else {
            Matrix M = new Matrix(m.getRow(), 1);
            for (i = 0; i < M.getRow(); i++) {
                M.setElMT(i, 0, m.getElmt(i, m.getCol() - 1));
            }
            return M;
        }

    }

    public static Matrix getHalfRigth(Matrix m) {
        // inputnya
        Matrix M = new Matrix(m.getRow(), m.getCol() / 2);
        int i, j;
        for (i = 0; i < m.getRow(); i++) {
            for (j = m.getCol() / 2; j < m.getCol(); j++) {
                M.setElMT(i, j - m.getCol() / 2, m.getElmt(i, j));
            }
        }
        return M;
    }

    public static Matrix getHalfLeft(Matrix m) {
        // inputnya
        Matrix M = new Matrix(m.getRow(), m.getCol() / 2);
        int i, j;
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol() / 2; j++) {
                M.setElMT(i, j, m.getElmt(i, j));
            }
        }
        return M;
    }

    public static Matrix createMatrixIdentitas(int nRowCol) {
        Matrix M = new Matrix(nRowCol, nRowCol);
        int i, j;
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getCol(); j++) {
                if (i == j) {
                    M.setElMT(i, j, 1);
                } else {
                    M.setElMT(i, j, 0);
                }
            }
        }
        return M;
    }

    public static Matrix createMatrixKosong(int nRowCol) {
        Matrix M = new Matrix(nRowCol, nRowCol);
        int i, j;
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getCol(); j++) {
                M.setElMT(i, j, 0);
            }
        }
        return M;
    }

    public boolean isSquare2x2() {
        return (getCol() == 2 && getRow() == 2);
    }

    public boolean isSquare() {
        return (getCol() == getRow());
    }

    public static Matrix multiplyMatrixByConst(Matrix m, double k) {
        int i, j;
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol(); j++) {
                m.setElMT(i, j, m.getElmt(i, j) * k);
            }
        }
        return m;
    }

    public static void copyMatrix(Matrix m1, Matrix m2) {
        int i, j;
        for (i = 0; i < m1.getRow(); i++) {
            for (j = 0; j < m1.getCol(); j++) {
                m2.setElMT(i, j, m1.getElmt(i, j));
            }
        }
    }

    public static Matrix createHilbert(int n) {
        Matrix m = new Matrix(n, n);
        int i, j;
        double a, b, k;
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol(); j++) {
                a = i;
                b = j;
                k = (1 / (a + b + 1));
                m.setElMT(i, j, k);
            }
        }
        return m;
    }

    public static Matrix createHilbertSol(int n) {
        Matrix m = new Matrix(n, 1);
        int i, j;
        for (i = 0; i < m.getRow(); i++) {
            for (j = 0; j < m.getCol(); j++) {
                if (i == 0) {
                    m.setElMT(i, j, 1);
                } else {
                    m.setElMT(i, j, 0);
                }
            }
        }
        return m;
    }
}