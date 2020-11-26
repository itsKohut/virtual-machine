package com.trabalho.sisop;

import com.google.common.io.Resources;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.loader.Loader;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.process.ProcessManager;
import com.trabalho.sisop.storage.Storage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.trabalho.sisop.loader.Loader.RESOURCE_PROGRAM_BASE_PATH;

@Slf4j
@Getter
public class VirtualMachine {

    private Console console;
    private Dispatcher dispatcher;
    private CPU cpu;

    public VirtualMachine() {

        this.console = new Console();
        this.dispatcher = new Dispatcher();
        this.cpu = new CPU();

        populateMemory();
        loadProgramsToCacheStorage();
    }

    @SneakyThrows
    public void run() {

        console.start();
        dispatcher.start();
        cpu.start();

        boolean rodar = true;
        while (rodar) {

            //TODO criar logs para mostrar fluxo;

            System.out.println("================== MENU ===============");
            System.out.println("======= Escolha uma opção abaixo ======");
            System.out.println("=======================================");

            System.out.println("1 - Shell");
            System.out.println("2 - Console - IN");
            System.out.println("3 - Ver fila de prontos");
            System.out.println("4 - Ver fila de bloqueados");
            System.out.println("5 - Ver fila de IO");
            System.out.println("6 - Ver fila de terminados");

            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            int choose = scanner.nextInt();
            System.out.print("Opção selecionada: " + choose);
            System.out.println("\n======================================");

            int program;

            switch (choose) {

                case 1:
                    System.out.println("1 - Sequência fibonacci dos primeiros dez números");
                    System.out.println("2 - Sequência fibonacci dos números = M[A]");
                    System.out.println("3 - Fatorial do número = M[A]");
                    System.out.println("4 - Bubble sort de 10 números de a partir de M[A]");
                    System.out.println("5 - TRAP 1");
                    System.out.println("6 - TRAP 2");

                    System.out.println("Escolher um programa para rodar: ");
                    program = scanner.nextInt();

                    URL url = Resources.getResource(RESOURCE_PROGRAM_BASE_PATH + (program - 1));
                    List<String> programList = Resources.readLines(url, StandardCharsets.UTF_8);
                    ProcessManager.processCreation(programList);
                    break;

                case 2:

                    System.out.println("Escrever na memoria");
                    console.transferencia = scanner.nextInt();
                    console.consoleSemaphore.release();
                    break;
            }

        }
    }

    private void loadProgramsToCacheStorage() { //Carrega os programs em memória (cache)
        Storage.setPrograms(Loader.loadPrograms());
    }

    private void populateMemory() { //Popula memória com valores aleatórios de 1 a 10
        Random random = new Random();

        for (int index = 0; index < MemoryManager.ADDRESS.length; index++) {
            MemoryManager.ADDRESS[index] = String.valueOf(random.nextInt(10) + 1);
        }
    }
}
