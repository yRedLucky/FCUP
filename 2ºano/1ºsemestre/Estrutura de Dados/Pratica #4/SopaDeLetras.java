import java.util.Scanner;

public class SopaDeLetras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (true) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            if (rows == 0 && cols == 0) {
                break;
            }

            char[][] soup = new char[rows][cols];

            for (int i = 0; i < rows; i++) {
                String row = scanner.next();
                soup[i] = row.toCharArray();
            }

            int wordsCount = scanner.nextInt();
            String[] words = new String[wordsCount];

            for (int i = 0; i < wordsCount; i++) {
                words[i] = scanner.next();
            }

            if (caseNumber > 1) {
                System.out.println();
            }

            System.out.println("Input #" + caseNumber);
            caseNumber++;

            for (String word : words) {
                findAndMarkWord(soup, word);
            }

            printSoup(soup);
        }
    }

    public static void findAndMarkWord(char[][] soup, String word) {
        int rows = soup.length;
        int cols = soup[0].length;

        int[] dx = {0, 1};
        int[] dy = {1, 0};

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                for (int dir = 0; dir < 2; dir++) {
                    boolean found = true;

                    for (int i = 0; i < word.length(); i++) {
                        int newX = x + i * dx[dir];
                        int newY = y + i * dy[dir];

                        if (newX >= rows || newY >= cols || soup[newX][newY] != word.charAt(i)) {
                            found = false;
                            break;
                        }
                    }

                    if (found) {
                        for (int i = 0; i < word.length(); i++) {
                            int newX = x + i * dx[dir];
                            int newY = y + i * dy[dir];
                            soup[newX][newY] = '.';
                        }
                    }
                }
            }
        }
    }

    public static void printSoup(char[][] soup) {
        for (int i = 0; i < soup.length; i++) {
            for (int j = 0; j < soup[i].length; j++) {
                System.out.print(soup[i][j]);
            }
            System.out.println();
        }
    }
}
