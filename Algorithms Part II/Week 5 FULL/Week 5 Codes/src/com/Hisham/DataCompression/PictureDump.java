package com.Hisham.DataCompression;

import edu.princeton.cs.algs4.*;

import java.awt.*;

public class PictureDump {

    public static void main(String[] args) {
        int width = Integer.parseInt(StdIn.readLine());
        int height = Integer.parseInt(StdIn.readLine());
        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!BinaryStdIn.isEmpty()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) picture.set(col, row, Color.BLACK);
                    else picture.set(col, row, Color.WHITE);
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();

    }
}
