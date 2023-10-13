import java.util.Scanner;

class Matrix {
    int data[][]; // os elementos da matriz em si
    int rows;     // numero de linhas
    int cols;     // numero de colunas

    // construtor padrao de matriz
    Matrix(int r, int c) {
        data = new int[r][c];
        rows = r;
        cols = c;
    }

    // Ler os rows x cols elementos da matriz
    public void read(Scanner in) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                data[i][j] = in.nextInt();
    }

    // Representacao em String da matriz
    public String toString() {
        String ans = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                ans += data[i][j] + " ";
            ans += "\n";
        }
        return ans;
    }

    // Método para criar uma matriz identidade de ordem n
    public static Matrix identity(int n) {
        Matrix identityMatrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    identityMatrix.data[i][j] = 1;
                } else {
                    identityMatrix.data[i][j] = 0;
                }
            }
        }
        return identityMatrix;
    }

    // Método para obter a transposta da matriz
    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix.data[j][i] = data[i][j];
            }
        }
        return transposedMatrix;
    }

    // Método para somar duas matrizes
    public Matrix sum(Matrix m) {
        if (rows != m.rows || cols != m.cols) {
            throw new IllegalArgumentException("As matrizes devem ter as mesmas dimensões para serem somadas.");
        }
        Matrix resultMatrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix.data[i][j] = data[i][j] + m.data[i][j];
            }
        }
        return resultMatrix;
    }

    // Método para multiplicar duas matrizes
    public Matrix multiply(Matrix m) {
        if (cols != m.rows) {
            throw new IllegalArgumentException("O número de colunas da primeira matriz deve ser igual ao número de linhas da segunda matriz para multiplicação.");
        }
        Matrix resultMatrix = new Matrix(rows, m.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                for (int k = 0; k < cols; k++) {
                    resultMatrix.data[i][j] += data[i][k] * m.data[k][j];
                }
            }
        }
        return resultMatrix;
    }
}
