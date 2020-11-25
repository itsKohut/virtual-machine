package com.trabalho.sisop;

import com.google.common.io.Resources;
import com.trabalho.sisop.process.ProcessManager;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static com.trabalho.sisop.io.IO.RESOURCE_PROGRAM_BASE_PATH;

public class Shell extends Thread {

    @SneakyThrows
    public void run() {

        while (true) {

            System.out.println("1 - Sequência fibonacci dos primeiros dez números");
            System.out.println("2 - Sequência fibonacci dos números = M[A]");
            System.out.println("3 - Fatorial do número = M[A]");
            System.out.println("4 - Bubble sort de 10 números de a partir de M[A]");
            System.out.println("0 - Sair");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolha um programa para rodar: ");
            int escolha = scanner.nextInt();

            if (escolha == 0) {
                System.out.println("sair do shell");
                break;
            }

            if (escolha >= 1 && escolha <= 4) {
                URL url = Resources.getResource(RESOURCE_PROGRAM_BASE_PATH + (escolha - 1));
                List<String> program = Resources.readLines(url, StandardCharsets.UTF_8);
                ProcessManager.processCreation(program);

            }

            if (escolha < 0 || escolha > 5) {
                System.out.println("Opcao Invalida");
            }
        }
    }
}