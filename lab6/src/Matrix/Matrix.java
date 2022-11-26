package Matrix;

import java.util.Random;

public class Matrix {
    public static final Random random = new Random();
    public final int[] data;
    public final String name;
    public final int rows;
    public final int columns;

    public Matrix(int rows, int columns, String name) {
        this.rows = rows;
        this.columns = columns;
        this.data = new int[columns * rows];
        this.name = name;
    }

    public Matrix(int dimension, String name) {
        this.rows = dimension;
        this.columns = dimension;
        this.data = new int[columns * rows];
        this.name = name;
    }

    public void fill(int upperBound) {
        for (int i = 0; i < rows * columns; i++) {
            data[i] = random.nextInt(upperBound);
        }
    }
}