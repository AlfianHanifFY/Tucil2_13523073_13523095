package lib;

public class Compressor {

    public static void compress(Quadtree qt) {
        qt.getinfoImage().setAvgPixel();
        if (qt.getinfoImage().getSize() <= qt.getinfoImage().minimumBlockSize) {
            return;
        }

        double variance = qt.getinfoImage().getErrorMeasurement();
        // System.out.println("Variance: " + variance);
        if (variance > qt.getinfoImage().treshold ) {
            qt.split();
            compress(qt.getQ1());
            compress(qt.getQ2());
            compress(qt.getQ3());
            compress(qt.getQ4());
        }
    }
}
