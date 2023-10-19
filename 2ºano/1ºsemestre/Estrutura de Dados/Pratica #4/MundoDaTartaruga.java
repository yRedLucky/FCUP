import java.util.Scanner;

public class MundoDaTartaruga {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int flag = scanner.nextInt();  // Lê o valor da flag (0, 1 ou 2)
        int LINS = scanner.nextInt();  // Lê o número de linhas da grelha
        int COLS = scanner.nextInt();  // Lê o número de colunas da grelha
        char[][] grid = new char[LINS][COLS];  // Inicializa uma matriz para representar a grelha

        // Inicializa a grelha com '.'
        for (int i = 0; i < LINS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = '.';
            }
        }

        Tartaruga tartaruga = new Tartaruga(LINS, COLS);  // Inicializa a tartaruga com a grelha
        scanner.nextLine();  // Consome a quebra de linha após a leitura dos valores

        // Ler e executar as instruções da tartaruga
        while (true) {
            String instruction = scanner.nextLine();  // Lê a próxima instrução
            if (instruction.equals("end")) {
                break;  // Sai do loop se a instrução for "end"
            }
            tartaruga.executarInstrucao(instruction, grid);  // Executa a instrução na tartaruga
        }

        if (flag == 0) {
            // Imprimir a grelha resultante
            for (int i = 0; i < LINS; i++) {
                for (int j = 0; j < COLS; j++) {
                    System.out.print(grid[i][j]);  // Imprime o conteúdo da grelha
                }
                System.out.println();  // Imprime uma quebra de linha para a próxima linha da grelha
            }
        } else if (flag == 1) {
            // Calcular estatísticas
            int marked = tartaruga.contarMarcadas(grid);  // Calcula o número de posições marcadas
            int total = LINS * COLS;  // Calcula o total de posições na grelha
            int unmarked = total - marked;  // Calcula o número de posições não marcadas
            int percent = (marked * 100) / total;  // Calcula a porcentagem de posições marcadas
            System.out.println(percent + " " + unmarked);  // Imprime as estatísticas
        } else if (flag == 2) {
            // Ler o padrão e procurar na imagem
            int N = scanner.nextInt();  // Lê o número de linhas do padrão
            int M = scanner.nextInt();  // Lê o número de colunas do padrão
            char[][] pattern = new char[N][M];  // Inicializa o padrão
            for (int i = 0; i < N; i++) {
                pattern[i] = scanner.next().toCharArray();  // Lê as linhas do padrão
            }
            boolean encontrado = tartaruga.procurarPadrao(grid, pattern);  // Procura o padrão na imagem
            System.out.println(encontrado ? "Sim" : "Nao");  // Imprime "Sim" ou "Nao" com base no resultado
        }
    }
}

class Tartaruga {
    private int LINS;
    private int COLS;
    private int x;
    private int y;
    private boolean canetaBaixa;
    private char direcao;

    // Construtor da classe Tartaruga
    public Tartaruga(int LINS, int COLS) {
        this.LINS = LINS;
        this.COLS = COLS;
        this.x = 0;
        this.y = 0;
        this.canetaBaixa = false;
        this.direcao = 'E'; // Inicialmente virada para Este
    }

    // Método para executar uma instrução na tartaruga
    public void executarInstrucao(String instrucao, char[][] grid) {
        if (instrucao.equals("U")) {
            canetaBaixa = false;
        } else if (instrucao.equals("D")) {
            canetaBaixa = true;
            grid[y][x] = '*'; // Marcar a posição atual
        } else if (instrucao.startsWith("F")) {
            int passos = Integer.parseInt(instrucao.substring(2));
            moverTartaruga(passos, grid);
        } else if (instrucao.equals("L")) {
            virarEsquerda();
        } else if (instrucao.equals("R")) {
            virarDireita();
        }
    }

    // Método para mover a tartaruga
    private void moverTartaruga(int passos, char[][] grid) {
        for (int i = 0; i < passos; i++) {
            int newX = x;
            int newY = y;
            if (direcao == 'N') {
                newY = Math.max(0, y - 1);
            } else if (direcao == 'S') {
                newY = Math.min(LINS - 1, y + 1);
            } else if (direcao == 'E') {
                newX = Math.min(COLS - 1, x + 1);
            } else if (direcao == 'W') {
                newX = Math.max(0, x - 1);
            }

            if (canetaBaixa) {
                grid[newY][newX] = '*'; // Marcar a posição atual
            }

            x = newX;
            y = newY;
        }
    }

    // Método para virar a tartaruga para a esquerda
    private void virarEsquerda() {
        if (direcao == 'N') {
            direcao = 'W';
        } else if (direcao == 'S') {
            direcao = 'E';
        } else if (direcao == 'E') {
            direcao = 'N';
        } else if (direcao == 'W') {
            direcao = 'S';
        }
    }

    // Método para virar a tartaruga para a direita
    private void virarDireita() {
        if (direcao == 'N') {
            direcao = 'E';
        } else if (direcao == 'S') {
            direcao = 'W';
        } else if (direcao == 'E') {
            direcao = 'S';
        } else if (direcao == 'W') {
            direcao = 'N';
        }
    }

    // Método para contar as posições marcadas na grelha
    public int contarMarcadas(char[][] grid) {
        int marcadas = 0;
        for (int i = 0; i < LINS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j] == '*') {
                    marcadas++;
                }
            }
        }
        return marcadas;
    }

    // Método para procurar um padrão na imagem
    public boolean procurarPadrao(char[][] grid, char[][] pattern) {
        for (int i = 0; i <= LINS - pattern.length; i++) {
            for (int j = 0; j <= COLS - pattern[0].length; j++) {
                if (verificarPadrao(i, j, grid, pattern)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Método para verificar se um padrão está presente em uma posição específica
    private boolean verificarPadrao(int startX, int startY, char[][] grid, char[][] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                if (pattern[i][j] == '.' && grid[startY + i][startX + j] == '*') {
                    return false;
                }
            }
        }
        return true;
    }
}
