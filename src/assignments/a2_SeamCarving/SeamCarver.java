package assignments.a2_SeamCarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    01/02/2017
 * Description :
 */

public class SeamCarver {

    private Picture picture;
    private int H,W;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new NullPointerException("Null constructor argument");
        this.picture = new Picture(picture);
        H = this.picture.height();
        W = this.picture.width();
        energy = new double[H][W];
        for (int h = 0; h < H; h++) {
            for (int w = 0; w < W; w++) {
                energy[h][w] = energy(w,h);
            }
        }
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
       return W;
    }

    // height of current picture
    public int height() {
        return H;
    }


    // energy of pixel at column x and row y/
    public  double energy(int x, int y) {
        if (x < 0 || x >= W) throw new IndexOutOfBoundsException();
        if (y < 0 || y >= H) throw new IndexOutOfBoundsException();
        // edges
        if (isBorder(x, y))
            return 1000;

        Color l = picture.get(x - 1, y); // left cell
        Color r = picture.get(x + 1, y); // right cell
        Color t = picture.get(x, y - 1); // top cell
        Color d = picture.get(x, y + 1); // down cell

        double deltaX = gradient(l, r);
        double deltaY = gradient(t, d);

        return Math.sqrt(deltaX + deltaY);
    }

    private double gradient(Color c1, Color c2) {
        int r = c2.getRed() - c1.getRed();
        int g = c2.getGreen() - c1.getGreen();
        int b = c2.getBlue() - c1.getBlue();

        return Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2);
    }

    private boolean isBorder(int x, int y) {
        return x == 0 || x == W - 1 || y == 0 || y == H - 1;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
       return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new NullPointerException("seam is null");

        H--;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new NullPointerException("seam is null");

        W--;
    }
}
