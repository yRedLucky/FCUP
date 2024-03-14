import java.util.Scanner;

public class ProbB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Lê o número de moedas disponíveis inicialmente
        int[] coins = new int[6];
        for (int i = 0; i < 6; i++) {
            coins[i] = scanner.nextInt();
        }

        int totalRetido = 0;
        int transacoesComRetencao = 0;
        int totalTransacoes = 0;

        while (true) {
            // Lê o montante a pagar
            int euros = scanner.nextInt();
            int centimos = scanner.nextInt();

            // Verifica se é o final da sequência de transações
            if (euros == 0 && centimos == 0) {
                break;
            }

            totalTransacoes++;

            // Calcula o valor total em cêntimos
            int valorTotal = euros * 100 + centimos;

            // Lê as moedas introduzidas pelo cliente
            int valorIntroduzido = 0;
            while (true) {
                int moeda = scanner.nextInt();
                if (moeda == 0) {
                    break;
                }
                valorIntroduzido += moeda;
            }

            // Calcula o troco
            int troco = valorIntroduzido - valorTotal;

            // Verifica se houve retenção de troco
            if (troco > 0) {
                totalRetido += troco;
                transacoesComRetencao++;
            }
        }

        // Output
        System.out.println(totalRetido / 100 + " " + totalRetido % 100);
        System.out.println(transacoesComRetencao + "/" + totalTransacoes);

        scanner.close();
    }
}
