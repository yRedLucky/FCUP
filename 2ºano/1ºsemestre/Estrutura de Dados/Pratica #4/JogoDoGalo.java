import java.util.Scanner;

public class JogoDoGalo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        char[][] board = new char[n][n];

        // Preenche o tabuleiro
        for (int i = 0; i < n; i++) {
            String row = scanner.next();
            for (int j = 0; j < n; j++) {
                board[i][j] = row.charAt(j);
            }
        }

        String result = checkGame(board);

        System.out.println(result);
    }

    // Verifica o estado do jogo
    public static String checkGame(char[][] board) {
        int n = board.length;

        // Verifica as linhas e colunas
        for (int i = 0; i < n; i++) {
            char rowChar = board[i][0];
            char colChar = board[0][i];

            boolean rowWin = true;
            boolean colWin = true;

            for (int j = 1; j < n; j++) {
                if (board[i][j] != rowChar) {
                    rowWin = false;
                }
                if (board[j][i] != colChar) {
                    colWin = false;
                }
            }

            if (rowWin) {
                return "Ganhou o " + rowChar;
            }
            if (colWin) {
                return "Ganhou o " + colChar;
            }
        }

        // Verifica as diagonais
        char mainDiagonalChar = board[0][0];
        char antiDiagonalChar = board[0][n - 1];

        boolean mainDiagonalWin = true;
        boolean antiDiagonalWin = true;

        for (int i = 1; i < n; i++) {
            if (board[i][i] != mainDiagonalChar) {
                mainDiagonalWin = false;
            }
            if (board[i][n - 1 - i] != antiDiagonalChar) {
                antiDiagonalWin = false;
            }
        }

        if (mainDiagonalWin) {
            return "Ganhou o " + mainDiagonalChar;
        }
        if (antiDiagonalWin) {
            return "Ganhou o " + antiDiagonalChar;
        }

        // Verifica se o jogo está completo ou incompleto
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '.') {
                    return "Jogo incompleto";
                }
            }
        }

        // Se nenhum jogador ganhou e o jogo está completo, é um empate
        return "Empate";
    }
}
