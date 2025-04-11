package lib;

import java.util.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.BufferedImage;
import java.awt.Color;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

public class IO {
    public String fileName;
    public Scanner fileScanner;
    public static Scanner inputScanner = new Scanner(System.in);
    public static Image infoImage;

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
                System.out.println(method);
                if (method >= 1 && method <= 5) {
                    break;
                } else {
                    System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan integer (1 -- 4) !");
                }

            } catch (NumberFormatException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukan wajib integer !");
            }
        }
        return method;

    }

    public static double readThreshold(int method) {
        double Threshold = -1;

        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan Threshold !");

        if (method == 1) {

            System.out.println("\u001B[34m[INFO]\u001B[0m : Rekomendasi Threshold : 0,5 - 5");
        }

        else if (method == 2) {

            System.out.println("\u001B[34m[INFO]\u001B[0m : Rekomendasi Threshold : 1 - 5");
        }

        else if (method == 3) {

            System.out.println("\u001B[34m[INFO]\u001B[0m : Rekomendasi Threshold : 10 - 25");
        }

        else if (method == 4) {

            System.out.println("\u001B[34m[INFO]\u001B[0m : Rekomendasi Threshold : 0,1 - 1,5");
        }

        else if (method == 5) {

            System.out.println("\u001B[34m[INFO]\u001B[0m : Rekomendasi Threshold : 0,5 - 10");
        }

        System.out.println("");

        // System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan Threshold !");

        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                Threshold = inputScanner.nextDouble(); // Gunakan nextDouble() untuk angka desimal

                if (Threshold < 0) {
                    System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan Threshold positif !");
                    continue;
                }

                break;
            } catch (InputMismatchException e) { // Tangkap InputMismatchException
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m" + " : Masukkan angka yang valid (gunakan koma untuk desimal)!");
                inputScanner.next(); // Bersihkan input yang salah
            }
        }

        return Threshold;
    }

    public static double readTargetCompression(){
        double targetCompression = -1;

        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan Target Compression (0 - 1) !");

        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                targetCompression = inputScanner.nextDouble(); 
                if (targetCompression < 0 || targetCompression > 1) {
                    System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : Masukkan Target Compression antara 0-1!");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m" + " : Masukkan angka yang valid (gunakan koma untuk desimal)!");
                inputScanner.next(); // Bersihkan input yang salah
            }
        }

        return targetCompression;
    }

    public static int readMinBlock(Image img) {
        int minBlock = -1;

        System.out.println("");

        System.out.print("\u001B[34m[INFO]\u001B[0m : Ukuran Gambar Anda : " + img.getRow() + " x " + img.getCol()
                + " atau " + img.getSize() + " pixel");
        System.out.print("\n\u001B[34m[INFO]\u001B[0m : Masukkan Minimum Block !");

        if (img.getSize() < (128 * 128)) {
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Rekomendasi Minimum Block : 16 - 36 (4x4 - 6x6)");
        } else if (img.getSize() < (256 * 256)) {
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Rekomendasi Minimum Block : 36 - 64 (6x6 - 8x8)");
        } else if (img.getSize() < (512 * 512)) {
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Rekomendasi Minimum Block : 64 - 144 (8x8 - 12x12)");
        } else if (img.getSize() < (1024 * 1024)) {
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Rekomendasi Minimum Block : 144 - 256 (12x12 - 16x16)");
        } else {
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Rekomendasi Minimum Block : 256 - 400 (16x16 - 20x20)");
        }
        System.out.println("");

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

    public static String readOutputPath(String inputPath) {
        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan alamat (ABSOLUT) output hasil kompresi !");
        String fileName = "";
        inputScanner.nextLine();
        String inputFormat = inputPath.substring(inputPath.lastIndexOf('.') + 1).toLowerCase();
        while (true) {
            System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
            fileName = inputScanner.nextLine();
            // Ambil ekstensi file dari filePath
            String format = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            // Cek apakah format output sama seperti format input
            if (!format.equals(inputFormat)) {
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m : Format tidak sama dengan format input! Gunakan format gambar yang sama (."
                                + inputFormat + ") ");
            } else {
                break;
            }

        }
        return fileName;
    }

    public static String readOutputGIFPath() {
        System.out.println("\n\u001B[34m[INFO]\u001B[0m : Masukkan alamat (ABSOLUT) output GIF !");
        String fileName = "";
        while (true) {
            System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
            fileName = inputScanner.nextLine();
            // Ambil ekstensi file dari filePath
            String format = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            // Cek apakah format didukung oleh ImageIO
            if (!format.equals("gif")) {
                System.out.println(
                        "\u001B[33m[WARNING]\u001B[0m : Format tidak didukung! Gunakan format GIF yang valid ! (.gif)");
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
                        + " tidak ditemukan di directory.");
            }
        }
        return fileName;
    }

    public static void readImage(String fileName) {
        Image image = new Image(0, 0);
        try {
            // String fileName = IO.readFileName();
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
        infoImage = image;
    }

    private static void reconstructImage(Quadtree qt, BufferedImage image) {
        if (qt == null)
            return;

        if (qt.isLeaf()) {
            Pixel p = qt.getAvgPixel();
            int startRow = qt.getStartRow();
            int startCol = qt.getStartCol();
            int endRow = startRow + qt.getRow();
            int endCol = startCol + qt.getCol();

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
            int width = qt.getCol();
            int height = qt.getRow();
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
            // System.out.println("\u001B[32m[SUKSES]\u001B[0m : Gambar berhasil disimpan
            // sebagai " + filePath);

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

    public static long getFileSize(String filePath) {
        Path path = Paths.get(filePath);
        long fileSize = -1; // Jika file tidak ditemukan atau error, return -1

        try {
            fileSize = Files.size(path);
        } catch (IOException e) {
            System.err.println("\u001B[31m[ERROR]\u001B[0m : Gagal membaca ukuran file " + filePath);
            e.printStackTrace();
        }

        return fileSize;
    }

    public static double calculateCompressionPercentage(long inputSize, long outputSize) {
        if (inputSize == 0) {
            return 0; // Hindari pembagian dengan nol
        }
        return 100.0 * (1 - ((double) outputSize / inputSize));
    }

    public static BufferedImage[] reconstructImageByDepth(Quadtree qt, int width, int height) {
        int depth = qt.getDepth();
        BufferedImage[] frames = new BufferedImage[depth + 1];

        for (int i = 0; i <= depth; i++) {
            frames[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            if (i > 0) {
                frames[i].getGraphics().drawImage(frames[i - 1], 0, 0, null);
            }
        }

        fillImages(qt, frames, 0);

        return frames;
    }

    private static void fillImages(Quadtree qt, BufferedImage[] frames, int currentDepth) {
        if (qt == null)
            return;

        Pixel p = qt.getAvgPixel();
        int startRow = qt.getStartRow();
        int startCol = qt.getStartCol();
        int endRow = startRow + qt.getRow();
        int endCol = startCol + qt.getCol();

        for (int y = startRow; y < endRow; y++) {
            for (int x = startCol; x < endCol; x++) {
                if (x >= 0 && x < frames[0].getWidth() && y >= 0 && y < frames[0].getHeight()) {
                    int color = new Color(p.getRed(), p.getGreen(), p.getBlue()).getRGB();
                    for (int i = currentDepth; i < frames.length; i++) {
                        frames[i].setRGB(x, y, color);
                    }
                }
            }
        }

        if (qt.getQ1() != null)
            fillImages(qt.getQ1(), frames, currentDepth + 1);
        if (qt.getQ2() != null)
            fillImages(qt.getQ2(), frames, currentDepth + 1);
        if (qt.getQ3() != null)
            fillImages(qt.getQ3(), frames, currentDepth + 1);
        if (qt.getQ4() != null)
            fillImages(qt.getQ4(), frames, currentDepth + 1);
    }

    public static void createGIF(BufferedImage[] frames, String outputFilePath, int delay) {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(outputFilePath);
        e.setDelay(delay);
        e.setRepeat(0); // 0 = loop forever

        for (BufferedImage frame : frames) {
            e.addFrame(frame);
        }

        e.finish();
    }

}