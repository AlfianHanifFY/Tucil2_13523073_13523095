package lib;

import java.util.*;

import java.io.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class IO {
    public String fileName;
    public Scanner fileScanner;
    public static Scanner inputScanner = new Scanner(System.in);

    // Konstruktor
    public IO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    // Open & Close File
    public void openFile() {
        try {
            File file = new File(getFileName());
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : File " + getFileName()
                    + " tidak ditemukan di directory...");
            System.exit(0);
        }
    }

    public void closeFile() {
        if (fileScanner != null) {
            fileScanner.close();
        }
    }

    public static int readErorrMethod() {
        int method = -1;

        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Pilih Error Measurement !");
        System.out.println("  [1] Variance");
        System.out.println("  [2] Mean Absolute Deviation");
        System.out.println("  [3] Max Pixel Difference");
        System.out.println("  [4] Entropy");
        System.out.println("  [5] SSIM\n");
        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                method = inputScanner.nextInt();
                if (method >= 1 || method <= 5) {
                    break;
                } else {
                    System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan integer (1 -- 5) !");
                }

            } catch (NumberFormatException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan wajib integer !");
            }
        }
        return method;

    }

    /* HARUS DIUBAH */
    public static double readThreshold() {
        double Threshold = -1;

        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan Threshold !");
        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                Threshold = inputScanner.nextInt();
                /* nanti di atur validasi Threshold sesuai variance */
                break;
            } catch (NumberFormatException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan wajib integer !");
            }
        }

        return Threshold;
    }

    public static int readMinBlock() {
        int minBlock = -1;

        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan Minimum Block !");
        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                minBlock = inputScanner.nextInt();
                /* nanti di atur validasi Threshold sesuai variance */
                break;
            } catch (NumberFormatException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan wajib integer !");
            }
        }

        return minBlock;
    }

    public static String readOutputPath() {
        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan alamat (ABSOLUT) output !");
        String fileName = "";
        inputScanner.nextLine();
        while (true) {
            System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
            fileName = inputScanner.nextLine();
            // Ambil ekstensi file dari filePath
            String format = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            // Cek apakah format didukung oleh ImageIO
            if (!isSupportedFormat(format)) {
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m : Format tidak didukung! Gunakan format gambar yang valid.");
            } else {
                break;
            }

        }
        return fileName;
    }

    // Read file name from input
    public static String readFileName() {
        String fileName = "";
        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                fileName = inputScanner.nextLine();
                File file = new File(fileName);
                Scanner tempScanner = new Scanner(file);
                tempScanner.close();

                break;
            } catch (FileNotFoundException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : File " + fileName
                        + " tidak ditemukan di directory test/input.");
            }
        }
        return fileName;
    }

    public static Image readImage() {
        Image image = new Image(0, 0);
        try {
            String fileName = IO.readFileName();
            File file = new File(fileName);
            BufferedImage img = ImageIO.read(file);
            int width = img.getWidth();
            int height = img.getHeight();
            image = new Image(height, width);

            // get rgb
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int rgb = img.getRGB(j, i);
                    Color color = new Color(rgb);
                    Pixel pixel = new Pixel(color.getRed(), color.getGreen(), color.getBlue());
                    image.setPixel(i, j, pixel);
                }
            }

        } catch (Exception e) {
            System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Gagal memproses image !");
        }

        return image;
    }

    private static void reconstructImage(Quadtree qt, BufferedImage image) {
        if (qt == null)
            return;

        if (qt.isLeaf()) {
            Pixel p = qt.getinfoImage().getAvgPixel();
            int startRow = qt.getinfoImage().getStartRow();
            int startCol = qt.getinfoImage().getStartCol();
            int endRow = startRow + qt.getinfoImage().getRow();
            int endCol = startCol + qt.getinfoImage().getCol();

            for (int y = startRow; y < endRow; y++) {
                for (int x = startCol; x < endCol; x++) {
                    // Periksa bahwa koordinat berada dalam batas gambar
                    if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
                        int r = p.getRed();
                        int g = p.getGreen();
                        int b = p.getBlue();
                        image.setRGB(x, y, new Color(r, g, b).getRGB());
                    }
                }
            }

            // System.out.println(
            // " | Filling block at: (" + startCol + "," + startRow + ") size: "
            // + qt.getinfoImage().getCol()
            // + "x" + qt.getinfoImage().getRow());

        } else {
            if (qt.getQ1() != null)
                reconstructImage(qt.getQ1(), image);
            if (qt.getQ2() != null)
                reconstructImage(qt.getQ2(), image);
            if (qt.getQ3() != null)
                reconstructImage(qt.getQ3(), image);
            if (qt.getQ4() != null)
                reconstructImage(qt.getQ4(), image);
        }

    }

    public static void saveImage(String filePath, Quadtree qt) {

        try {
            int width = qt.getinfoImage().getCol();
            int height = qt.getinfoImage().getRow();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            reconstructImage(qt, image);

            // Ambil ekstensi file dari filePath
            String format = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();

            // Cek apakah format didukung oleh ImageIO
            if (!isSupportedFormat(format)) {
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m : Format tidak didukung! Gunakan format gambar yang valid.");
            }
            File outputFile = new File(filePath);
            ImageIO.write(image, format, outputFile);
            System.out.println("\u001B[32m[SUKSES]\u001B[0m : Gambar berhasil disimpan sebagai " + filePath);

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR]\u001B[0m : Gagal menyimpan gambar!");
            e.printStackTrace();
        }
    }

    /**
     * Mengecek apakah format gambar didukung oleh ImageIO
     */
    public static boolean isSupportedFormat(String format) {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix(format);
        return writers.hasNext();
    }
}