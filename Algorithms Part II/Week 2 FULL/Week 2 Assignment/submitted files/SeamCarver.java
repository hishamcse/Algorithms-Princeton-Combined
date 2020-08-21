import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;
    private double[] distTo;
    private int[][] edgeTo;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture is null");
        }
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validatePixel(x, y);
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            return 1000.00;
        }

        Color colorW1 = picture.get(x + 1, y);
        Color colorW2 = picture.get(x - 1, y);
        int rx = colorW1.getRed() - colorW2.getRed();
        int gx = colorW1.getGreen() - colorW2.getGreen();
        int bx = colorW1.getBlue() - colorW2.getBlue();
        int delX = rx * rx + gx * gx + bx * bx;

        Color colorH1 = picture.get(x, y + 1);
        Color colorH2 = picture.get(x, y - 1);
        int ry = colorH1.getRed() - colorH2.getRed();
        int gy = colorH1.getGreen() - colorH2.getGreen();
        int by = colorH1.getBlue() - colorH2.getBlue();
        int delY = ry * ry + gy * gy + by * by;

        return Math.sqrt(delX + delY);

    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        int[] indices = new int[width];
        distTo = new double[height];
        edgeTo = new int[width][height];
        for (int j = 0; j < height; j++) {
            distTo[j] = 1000.00;
        }
        double[][] energy = energyArray();
        for (int i = 1; i < width; i++) {
            double[] lastDist = distTo.clone();
            for (int j = 0; j < height; j++) {
                distTo[j] = Double.POSITIVE_INFINITY;
            }
            for (int j = 1; j < height; j++) {
                relaxHorizontal(j - 1, i, j, lastDist, energy[i][j]);
                relaxHorizontal(j, i, j, lastDist, energy[i][j]);
                relaxHorizontal(j + 1, i, j, lastDist, energy[i][j]);
            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minVertex = 0;
        for (int i = 0; i < height; i++) {
            if (distTo[i] < min) {
                min = distTo[i];
                minVertex = i;
            }
        }

        for (int i = width - 1; i >= 0; i--) {
            indices[i] = minVertex;
            minVertex = edgeTo[i][minVertex];
        }
        return indices;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] indices = new int[height];
        distTo = new double[width];
        edgeTo = new int[width][height];
        for (int j = 0; j < width; j++) {
            distTo[j] = 1000.00;
        }
        double[][] energy = energyArray();
        for (int i = 1; i < height; i++) {
            double[] lastDist = distTo.clone();
            for (int j = 0; j < width; j++) {
                distTo[j] = Double.POSITIVE_INFINITY;
            }
            for (int j = 1; j < width; j++) {
                relaxVertical(j - 1, j, i, lastDist, energy[j][i]);
                relaxVertical(j, j, i, lastDist, energy[j][i]);
                relaxVertical(j + 1, j, i, lastDist, energy[j][i]);
            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minVertex = 0;
        for (int i = 0; i < width; i++) {
            if (distTo[i] < min) {
                min = distTo[i];
                minVertex = i;
            }
        }

        for (int i = height - 1; i >= 0; i--) {
            indices[i] = minVertex;
            minVertex = edgeTo[minVertex][i];
        }
        return indices;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        if (height <= 1 || seam.length != width) {
            throw new IllegalArgumentException("height not valid");
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height - 1) {
                throw new IllegalArgumentException("out of range");
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("difference is big");
            }
        }

        Picture pic = new Picture(width, height - 1);
        int k = 0;
        for (int i = 0; i < seam.length; i++) {
            for (int j = 0; j < seam[i]; j++) {
                pic.set(i, k, picture.get(i, j));
                k++;
            }
            for (int j = seam[i] + 1; j < height; j++) {
                pic.set(i, k, picture.get(i, j));
                k++;
            }
            k = 0;
        }
        picture = pic;
        width = picture.width();
        height = picture.height();
//        System.out.println(picture.width() + " " + picture.height());
//        picture.show();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        if (width <= 1 || seam.length != height) {
            throw new IllegalArgumentException("width not valid");
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width - 1) {
                throw new IllegalArgumentException("out of range");
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("difference is big");
            }
        }

        Picture pic = new Picture(width - 1, height);
        int k = 0;
        for (int i = 0; i < seam.length; i++) {
            for (int j = 0; j < seam[i]; j++) {
                pic.set(k, i, picture.get(j, i));
                k++;
            }
            for (int j = seam[i] + 1; j < width; j++) {
                pic.set(k, i, picture.get(j, i));
                k++;
            }
            k = 0;
        }
        picture = pic;
        width = picture.width();
        height = picture.height();
//        System.out.println(picture.width()+" "+picture.height());
//        picture.show();
    }

    // helper methods
    private void validatePixel(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            throw new IllegalArgumentException("out of range");
        }
    }

    private double[][] energyArray() {
        double[][] allEnergy = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                allEnergy[i][j] = energy(i, j);
            }
        }
        return allEnergy;
    }

    private void relaxVertical(int prev, int x, int y, double[] lastDist, double energy) {
        if (prev < 0 || prev > width - 1) {
            return;
        }
        if (distTo[x] > lastDist[prev] + energy) {
            distTo[x] = lastDist[prev] + energy;
            edgeTo[x][y] = prev;
        }
    }

    private void relaxHorizontal(int prev, int x, int y, double[] lastDist, double energy) {
        if (prev < 0 || prev > height - 1) {
            return;
        }
        if (distTo[y] > lastDist[prev] + energy) {
            distTo[y] = lastDist[prev] + energy;
            edgeTo[x][y] = prev;
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        // small test
//        Picture picture = new Picture("3x4.png");
//        SeamCarver seamCarver = new SeamCarver(picture);
//        System.out.println(seamCarver.energy(1, 1));
//
//        picture = new Picture("7x10.png");
//        seamCarver = new SeamCarver(picture);
//        System.out.println(Arrays.toString(seamCarver.findVerticalSeam()));
//        System.out.println(Arrays.toString(seamCarver.findHorizontalSeam()));
//        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
//
//        picture = new Picture("chameleon.png");
//        seamCarver = new SeamCarver(picture);
//        System.out.println(Arrays.toString(seamCarver.findVerticalSeam()));
//        System.out.println(Arrays.toString(seamCarver.findHorizontalSeam()));
//        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
//        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());

        // big test
        Picture picture = new Picture("1.jpg");
        SeamCarver seamCarver = new SeamCarver(picture);
        picture.show();
        System.out.println(seamCarver.energy(1, 1));
        System.out.println(picture.width() + " " + picture.height());
        for (int i = 0; i < 150; i++) {
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        }
        seamCarver.picture().show();
        System.out.println(seamCarver.picture.width() + " " + seamCarver.picture.height());
    }

}
