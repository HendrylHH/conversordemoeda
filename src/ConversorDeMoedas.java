import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMoedas {

    // Começamos pela API, que pode ser obtida no site exchangerate-api.com, tal como demanda a atividade.
    private static final String API_KEY = "API";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("=========== Conversor de Moedas ===========");
            System.out.println("Escolha uma das opções de conversão:");
            System.out.println("1. Dólar (USD) para Real (BRL)");
            System.out.println("2. Euro (EUR) para Real (BRL)");
            System.out.println("3. Libra Esterlina (GBP) para Real (BRL)");
            System.out.println("4. Real (BRL) para Dólar (USD)");
            System.out.println("5. Real (BRL) para Euro (EUR)");
            System.out.println("6. Real (BRL) para Libra Esterlina (GBP)");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção (1-7): ");

            // Verifica se o input é válido
            int opcao;
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida! Por favor, insira um número entre 1 e 7.");
                scanner.next(); // Limpa o valor inválido
                continue;
            }

            if (opcao == 7) {
                continuar = false;
                System.out.println("Encerrando o aplicativo. Obrigado por testar!!!");
                break;
            }

            if (opcao < 1 || opcao > 7) {
                System.out.println("Opção inválida! Por favor, escolha um número entre 1 e 7.");
                continue;
            }

            System.out.print("Digite o valor a ser convertido: ");
            double valor;
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
            } else {
                System.out.println("Entrada inválida! Por favor, insira um valor numérico.");
                scanner.next(); // Limpa o valor inválido
                continue;
            }

            String moedaOrigem = "";
            String moedaDestino = "";

            switch (opcao) {
                case 1:
                    moedaOrigem = "USD";
                    moedaDestino = "BRL";
                    break;
                case 2:
                    moedaOrigem = "EUR";
                    moedaDestino = "BRL";
                    break;
                case 3:
                    moedaOrigem = "GBP";
                    moedaDestino = "BRL";
                    break;
                case 4:
                    moedaOrigem = "BRL";
                    moedaDestino = "USD";
                    break;
                case 5:
                    moedaOrigem = "BRL";
                    moedaDestino = "EUR";
                    break;
                case 6:
                    moedaOrigem = "BRL";
                    moedaDestino = "GBP";
                    break;
            }

            double taxaDeCambio = obterTaxaDeCambio(moedaOrigem, moedaDestino);

            if (taxaDeCambio != -1) {
                double valorConvertido = valor * taxaDeCambio;
                System.out.printf("Taxa de câmbio atual: 1 %s = %.2f %s\n", moedaOrigem, taxaDeCambio, moedaDestino);
                System.out.printf("Valor convertido: %.2f %s\n", valorConvertido, moedaDestino);
            } else {
                System.out.println("Erro ao obter a taxa de câmbio. Tente novamente mais tarde.");
            }
        }

        scanner.close();
    }

    private static double obterTaxaDeCambio(String moedaOrigem, String moedaDestino) {
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", API_KEY, moedaOrigem, moedaDestino);

        try {
            // Conexão simples com a URL
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            // Valida se a conexão foi bem-sucedida, importante para o funcionamento e evita requisição excessiva
            if (conexao.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conexao.getInputStream());
                StringBuilder resposta = new StringBuilder();

                while (scanner.hasNext()) {
                    resposta.append(scanner.nextLine());
                }
                scanner.close();

                // Gson para analisar a resposta JSON, demandado pela atividade
                JsonObject jsonResposta = JsonParser.parseString(resposta.toString()).getAsJsonObject();
                String resultado = jsonResposta.get("result").getAsString();

                if ("success".equalsIgnoreCase(resultado)) {
                    return jsonResposta.get("conversion_rate").getAsDouble();
                } else {
                    System.out.println("Erro na resposta da API: " + resultado);
                    return -1;
                }
            } else {
                System.out.println("Erro na conexão com a API: Código HTTP " + conexao.getResponseCode());
                return -1;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return -1;
        }
    }
}
