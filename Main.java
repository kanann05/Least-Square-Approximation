import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Input data in the form of a 2D array, each row is a (x, y) pair.
        double[][] input = {
            {1, 2},
            {2, 4.5},
            {3, 5.9},
            {4, 8.1},
            {5, 9.8},
            {6, 12.3}
        };

        int m = input.length; // Number of data points
        int n = input[0].length; // Number of features (x, y)

        // Separate x and y values from input
        double[] x = new double[m];
        double[] y = new double[m];
        for (int i = 0; i < m; i++) {
            x[i] = input[i][0];
            y[i] = input[i][1];
        }

        // Building the Design Matrix (X)
        double[][] X = new double[m][2];
        for (int i = 0; i < m; i++) {
            X[i][0] = 1; // First column is filled with ones
            X[i][1] = x[i]; // Second column contains the x values
        }

        // Calculate coefficients (a) using the least squares method
        double[][] Xt = transpose(X);
        double[][] XtX = multiply(Xt, X);
        double[][] XtXInv = inverse(XtX);
        double[] XtY = multiply(Xt, y);
        double[] a = multiply(XtXInv, XtY);

        // Calculate fitted values (b)
        double[] b = new double[m];
        for (int i = 0; i < m; i++) {
            b[i] = a[0] + a[1] * x[i];
        }

        // Calculate least square error
        double leastSquareError = 0;
        for (int i = 0; i < m; i++) {
            leastSquareError += Math.pow(b[i] - y[i], 2);
        }

        // Print coefficients and least square error
        System.out.println("Coefficients (a): " + Arrays.toString(a));
        System.out.println("Least Square Error: " + leastSquareError);

        // Plot the best fit line (visualization not provided as Java doesn't have built-in plotting)
    }

    // Method to transpose a matrix
    public static double[][] transpose(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] transposed = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }

    // Method to multiply two matrices
    public static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        int m1 = matrix1.length;
        int n1 = matrix1[0].length;
        int m2 = matrix2.length;
        int n2 = matrix2[0].length;
        if (n1 != m2) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication");
        }
        double[][] result = new double[m1][n2];
        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                for (int k = 0; k < n1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    // Method to multiply a matrix and a vector
    public static double[] multiply(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (n != vector.length) {
            throw new IllegalArgumentException("Matrix and vector dimensions are not compatible for multiplication");
        }
        double[] result = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    // Method to find the inverse of a matrix (using a simple 2x2 matrix inverse formula)
    public static double[][] inverse(double[][] matrix) {
        double det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        double invDet = 1.0 / det;
        double[][] inverse = {
            {matrix[1][1] * invDet, -matrix[0][1] * invDet},
            {-matrix[1][0] * invDet, matrix[0][0] * invDet}
        };
        return inverse;
    }
}
