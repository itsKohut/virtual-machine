package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.io.IO;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.storage.Storage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.trabalho.sisop.ProgramManager.pagTable;
import static java.util.Objects.isNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
public class VirtualMachine {

    private CPU cpu;
    private Storage storage;
    private MemoryManager memoryManager;

    public VirtualMachine(CPU cpu, Storage storage, MemoryManager memoryManager) {
        this.cpu = cpu;
        this.memoryManager = memoryManager;
        this.storage = storage;

        loadProgramsToStorage();
    }

    private void loadProgramsToStorage() {
        storage.setPrograms(IO.loadPrograms());
    }

    public void run() {

        memoryPopulated(); // Comentar caso necessário para realizar os testes unitários, necessário popular devido aos testes dos programas 2, 3, 4 que não fazem carga de dados, apenas manipulação.

        MemoryFrame[] memoryFrame;
        int qtdPrograms = storage.getSizeProcessingQueue();

        for (int i = 0; i < qtdPrograms; i++) {

            List<String> program = this.storage.getWaitingProgramInProcessingQueue();
            memoryFrame = this.memoryManager.getMemoryFreeFrames(program.size());

            if (isFalse(isNull(memoryFrame)) && isNotEmpty(program)) { // Verifica se há programas a serem executados ou frames livres.

                pagTable.put(i, memoryFrame); // Salva o id do programa e os frames que ele utilizará na tabela de páginas
                memoryManager.loadProgramToMemoryFrames(memoryFrame, program); // Caso haja, carrega o programa na memória.
            }
        }

        chooseProgramToRun();
    }

    public void chooseProgramToRun() {

        while (pagTable.keySet().size() != 0) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolha o programa que desejas executar:");
            pagTable.keySet().forEach(id -> System.out.println(id + " - " + programDescription(id)));

            System.out.printf("Digite o número do ID do Programa a ser executado: ");

            int programID = scanner.nextInt();

            cpu.execute(programID, memoryManager);

        }
    }

    private String programDescription(int programID) {

        switch (programID) {
            case 0:
                return "Sequência fibonacci dos primeiros dez números "; //pos 115
            case 1:
                return "Sequência fibonacci dos números = M[A]  "; //pos 242
            case 2:
                return "Fatorial do número = M[A]"; //pos 466
            case 3:
                return "Bubble sort de 10 números de a partir de M[A]"; //pos 626"
        }
        return null; // pos significa a posição de memória que esta sendo escrita o resultado do programa
    }

    private void memoryPopulated() {
        Random random = new Random();

        int size = memoryManager.getAdresses().length;

        for (int i = 0; i < size; i++) {
            memoryManager.getAdresses()[i] = String.valueOf(random.nextInt(10) + 1);
        }
    }
}
