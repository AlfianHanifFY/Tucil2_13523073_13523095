package lib;

public class Compressor {

    public static void compress(Quadtree qt) {
        qt.calcAvgPixel();
        double variance = IO.infoImage.getErrorMeasurement(qt.getStartRow(), qt.getStartCol(), qt.getRow(),
                qt.getCol(), qt.getAvgPixel());
        // System.out.println("Variance: " + variance);

        if (qt.getCol() * qt.getRow() <= IO.infoImage.minimumBlockSize) {
            return;
        } else if (variance <= IO.infoImage.treshold) {
            return;
        } else {
            qt.split();
            compress(qt.getQ1());
            compress(qt.getQ2());
            compress(qt.getQ3());
            compress(qt.getQ4());
        }
    }
}
