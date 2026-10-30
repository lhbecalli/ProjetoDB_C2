package tela;

import java.util.Scanner;

// Leitura de dados no console, compartilhada pelas telas.
public class Entrada {
    private final Scanner scanner;

    public Entrada(Scanner scanner) {
        this.scanner = scanner;
    }

    // Se obrigatorio, repete ate o usuario digitar algo.
    public String lerTexto(String rotulo, boolean obrigatorio) {
        while (true) {
            System.out.print(rotulo + ": ");
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty() || !obrigatorio) {
                return valor;
            }
            System.out.println("Campo obrigatorio.");
        }
    }

    public int lerInteiro(String rotulo) {
        while (true) {
            System.out.print(rotulo + ": ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro.");
            }
        }
    }

    // Devolve null se o usuario so apertar Enter.
    public Integer lerInteiroOpcional(String rotulo) {
        while (true) {
            System.out.print(rotulo + " (Enter para deixar vazio): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro.");
            }
        }
    }

    public boolean confirmar(String pergunta) {
        while (true) {
            System.out.print(pergunta + " (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            }
            if (resposta.equals("n") || resposta.equals("nao")) {
                return false;
            }
            System.out.println("Responda s ou n.");
        }
    }

    // Mostra o valor atual e mantem ele se o usuario so apertar Enter.
    public String editarTexto(String rotulo, String atual) {
        System.out.print(rotulo + " [" + (atual == null ? "" : atual) + "]: ");
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? atual : valor;
    }
}
