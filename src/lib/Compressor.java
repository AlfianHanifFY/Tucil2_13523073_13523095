package lib;

public class Compressor {

    public static void compress(Quadtree qt) {
        qt.calcAvgPixel();
        if (qt.getCol() * qt.getRow() <= IO.infoImage.minimumBlockSize
                || qt.getCol() / 2 * qt.getRow() / 2 <= IO.infoImage.minimumBlockSize) {
            return;
        }

        double variance = IO.infoImage.getErrorMeasurement(qt.getStartRow(), qt.getStartCol(), qt.getRow(),
                qt.getCol(), qt.getAvgPixel());

        // System.out.println("Variance: " + variance);
        if (variance > IO.infoImage.treshold) {
            qt.split();
            compress(qt.getQ1());
            compress(qt.getQ2());
            compress(qt.getQ3());
            compress(qt.getQ4());
        }
    }
}
