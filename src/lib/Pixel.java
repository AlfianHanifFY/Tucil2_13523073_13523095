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

    public Pixel(Pixel other) {
        this.red = other.red;
        this.green = other.green;
        this.blue = other.blue;
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

    public void print() {
        System.out.print("R : " + getRed());
        System.out.print(" | G : " + getGreen());
        System.out.print(" | B : " + getBlue());
    }
}
