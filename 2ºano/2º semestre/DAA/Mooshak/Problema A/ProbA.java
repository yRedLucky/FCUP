import java.util.ArrayList;
import java.util.Scanner;

public class ProbA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leitura do número de pessoas
        int n = scanner.nextInt();
        scanner.nextLine(); // Consumir o caractere de nova linha

        // Leitura da sequência que define o cenário
        int[] sequence = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }

        // Imprime a sequência que define o cenário
        System.out.print("Sequência que define o cenário: ");
        for (int i = 0; i < n; i++) {
            System.out.print(sequence[i] + " ");
        }
        System.out.println();

        // Processamento da informação
        ArrayList<ArrayList<Integer>> grupos = new ArrayList<>(); // Inicializa uma lista para armazenar os grupos
        boolean[] visitado = new boolean[n]; // Inicializa um array para marcar se cada pessoa foi visitada ou não
        for (int i = 0; i < n; i++) { // Loop sobre todas as pessoas
            if (!visitado[i]) { // Se a pessoa aindfoi visitada
                ArrayList<Integer> grupo = new ArrayList<>(); // Inicializa uma lista para armazenar o grupo atual
                int atual = i; // Define a pessoa atual como a pessoa i
                int maiorElemento = Integer.MIN_VALUE; // Inicializa o maior elemento do grupo como o menor valor
                                                       // possível
                while (!visitado[atual]) { // Enquanto a pessoa atual não foi visitada
                    visitado[atual] = true; // Marca a pessoa atual como visitada
                    grupo.add(atual + 1); // Adiciona a pessoa atual ao grupo (adicionando 1 para ajustar para os
                                          // identificadores começando de 1)
                    if (atual + 1 > maiorElemento) { // Se a pessoa atual for maior que o maior elemento do grupo até
                                                     // agora
                        maiorElemento = atual + 1; // Atualiza o maior elemento do grupo
                    }
                    atual = sequence[atual] - 1; // Define a próxima não a pessoa como a pessoa à direita da pessoa
                                                 // atual na
                                                 // sequência (subtraindo 1 para ajustar para os índices de array
                                                 // começando de 0)
                }
                if (grupo.size() >= 3) { // Se o grupo atual tiver três ou mais pessoas
                    int idx = grupo.indexOf(maiorElemento); // Encontra o índice do maior elemento no grupo
                    if (idx != -1 && idx != grupo.size() - 1) { // Se o maior elemento não for o último elemento do
                                                                // grupo
                        int temp = sequence[grupo.get(idx) - 1]; // Obtém o número à direita do maior elemento
                        grupo.add(idx + 1, temp); // Adiciona o número à direita do maior elemento como o segundo
                                                  // elemento do grupo
                    }
                    grupos.add(grupo); // Adiciona o grupo à lista de grupos
                }
            }
        }

        // Saída dos grupos
        for (ArrayList<Integer> grupo : grupos) { // Para cada grupo na lista de grupos
            System.out.print(grupo.size()); // Imprime o número de pessoas no grupo
            for (int i = grupo.size() - 1; i >= 0; i--) { // Loop reverso sobre as pessoas no grupo
                System.out.print(" " + grupo.get(i)); // Imprime o identificador da pessoa, começando com a pessoa com o
                                                      // maior identificador
            }
            System.out.println(); // Imprime uma nova linha para separar os grupos
        }
    }
}
