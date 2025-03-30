package lib;

public class Compressor {

    public static void compress(Quadtree qt) {
        qt.getinfoImage().setAvgPixel();
        if (qt.getinfoImage().getRow() <= qt.getinfoImage().minimumBlockSize
                || qt.getinfoImage().getCol() <= qt.getinfoImage().minimumBlockSize) {
            return;
        }

        double variance = qt.getinfoImage().getErrorMeasurement();
        if (variance > qt.getinfoImage().treshold) {
            qt.split();
            compress(qt.getQ1());
            compress(qt.getQ2());
            compress(qt.getQ3());
            compress(qt.getQ4());
        }
    }
}
