import java.util.Scanner;

public class Tartaruga {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int flag = scanner.nextInt();
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }

        int x = 0, y = 0;
        char direction = 'E';
        boolean penDown = false;

        while (true) {
            String instruction = scanner.nextLine().trim();
            if (instruction.equals("end")) {
                break;
            }

            if (penDown) {
                grid[x][y] = '*';
            }

            if (instruction.equals("D")) {
                penDown = false;
            } else if (instruction.equals("U")) {
                penDown = true;
            } else if (instruction.startsWith("F")) {
                int steps = Integer.parseInt(instruction.substring(2));
                for (int i = 0; i < steps; i++) {
                    if (direction == 'E') {
                        y = Math.min(y + 1, cols - 1);
                    } else if (direction == 'W') {
                        y = Math.max(y - 1, 0);
                    } else if (direction == 'N') {
                        x = Math.max(x - 1, 0);
                    } else if (direction == 'S') {
                        x = Math.min(x + 1, rows - 1);
                    }
                }
            } else if (instruction.equals("L")) {
                if (direction == 'E') {
                    direction = 'N';
                } else if (direction == 'N') {
                    direction = 'W';
                } else if (direction == 'W') {
                    direction = 'S';
                } else if (direction == 'S') {
                    direction = 'E';
                }
            } else if (instruction.equals("R")) {
                if (direction == 'E') {
                    direction = 'S';
                } else if (direction == 'S') {
                    direction = 'W';
                } else if (direction == 'W') {
                    direction = 'N';
                } else if (direction == 'N') {
                    direction = 'E';
                }
            }
        }

        if (flag == 0) {
            printGrid(grid);
        } else if (flag == 1) {
            int markedCount = countMarkedPositions(grid);
            int unmarkedCount = countUnmarkedPositions(grid);
            int totalCount = rows * cols;
            int percentage = (markedCount * 100) / totalCount;
            System.out.println(percentage + " " + unmarkedCount + " " + markedCount);
        } else if (flag == 2) {
            int patternRows = scanner.nextInt();
            int patternCols = scanner.nextInt();
            char[][] pattern = new char[patternRows][patternCols];
            for (int i = 0; i < patternRows; i++) {
                String patternRow = scanner.next();
                for (int j = 0; j < patternCols; j++) {
                    pattern[i][j] = patternRow.charAt(j);
                }
            }

            boolean patternFound = findPattern(grid, rows, cols, pattern, patternRows, patternCols);
            if (patternFound) {
                System.out.println("Sim");
            } else {
                System.out.println("Nao");
            }
        }
    }

    public static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public static int countMarkedPositions(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '*') {
                    count++;
                }
            }
        }
        return count;
    }

    public static int countUnmarkedPositions(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '.') {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean findPattern(char[][] grid, int rows, int cols, char[][] pattern, int patternRows, int patternCols) {
        for (int i = 0; i <= rows - patternRows; i++) {
            for (int j = 0; j <= cols - patternCols; j++) {
                if (matchesPattern(grid, i, j, pattern, patternRows, patternCols)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean matchesPattern(char[][] grid, int x, int y, char[][] pattern, int patternRows, int patternCols) {
        for (int i = 0; i < patternRows; i++) {
            for (int j = 0; j < patternCols; j++) {
                if (grid[x + i][y + j] != pattern[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
