package lib;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    public Pixel(int _red, int _green, int _blue) {
        this.red = _red;
        this.green = _green;
        this.blue = _blue;
    }

    public int getGreen() {
        return this.green;
    }

    public int getRed() {
        return this.red;
    }

    public int getBlue() {
        return this.blue;
    }
}
