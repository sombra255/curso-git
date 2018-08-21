package br.com.fabricio.youtubeclone.model;

public class Thumbnails {

    private High high;
    private Medium medium;

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Thumbnails{" +
                "high=" + high +
                ", medium=" + medium +
                '}';
    }
}
