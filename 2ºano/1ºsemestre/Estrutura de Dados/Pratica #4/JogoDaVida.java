import java.util.Scanner;

public class JogoDaVida {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int iterations = scanner.nextInt();

        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String row = scanner.next();
            grid[i] = row.toCharArray();
        }

        for (int iteration = 0; iteration < iterations; iteration++) {
            grid = getNextGeneration(grid, rows, cols);
        }

        printGrid(grid);
    }

    public static char[][] getNextGeneration(char[][] grid, int rows, int cols) {
        char[][] newGrid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(grid, i, j);

                if (grid[i][j] == 'O') {
                    if (liveNeighbors <= 1 || liveNeighbors >= 4) {
                        newGrid[i][j] = '.';
                    } else {
                        newGrid[i][j] = 'O';
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newGrid[i][j] = 'O';
                    } else {
                        newGrid[i][j] = '.';
                    }
                }
            }
        }

        return newGrid;
    }

    public static int countLiveNeighbors(char[][] grid, int x, int y) {
        int liveNeighbors = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && grid[newX][newY] == 'O') {
                liveNeighbors++;
            }
        }

        return liveNeighbors;
    }

    public static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}
