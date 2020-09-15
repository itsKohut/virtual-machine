package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.trabalho.App.MEMORY_SIZE;
import static com.trabalho.App.QUANTITY_OF_PAGES;
import static com.trabalho.sisop.ProgramManager.pagTable;
import static com.trabalho.sisop.memory.FrameStatus.FREE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@ToString
@Data
public class MemoryManager {

    private String[] adresses;
    private MemoryFrame[] memoryFrames;

    public MemoryManager(int memorySize, int quantityMemorySectors) {

        this.adresses = new String[memorySize];
        this.memoryFrames = allocate(memorySize, quantityMemorySectors);

        log.info("Memory has {} size and {} frames", memorySize, this.memoryFrames);

    }

    public String getInstructions(int position) {
        return adresses[position];
    }

    public int logicalMemoryTranslator(int programID, int position) throws Exception {

        MemoryFrame[] memoryFrame = pagTable.get(programID);
        int logicalPosition = (memoryFrame[position / 16].getFrameID() * 16) + (position % 16);

        validateLogicalMemoryPosition(programID, logicalPosition);

        return logicalPosition;
    }

    public int getValueFromIndex(int idProgram, int position) throws Exception {
        return Integer.parseInt(this.getAdresses()[logicalMemoryTranslator(idProgram, position)]);
    }

    public void writeValueToMemory(int idProgram, int position, int value) throws Exception {

        this.getAdresses()[logicalMemoryTranslator(idProgram, position)] = String.valueOf(value);
    }

    public void printAllMemoryStack() { // Print toda a memória com valores não nulos
        List<String> collect = IntStream.range(0, MEMORY_SIZE)
                .filter(index -> isFalse(isNull(this.getAdresses()[index]))) // remover essa linha para printar todas as posições
                .mapToObj(index -> "[" + index + "]" + " : " + this.getAdresses()[index])
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public MemoryFrame[] getMemoryFreeFrames(int programSize) {

        int framesNeeded = calculateFramesNeeded(programSize);

        log.info("Searching for a set of free frames to run a program that needs {} free frames", framesNeeded);

        return Arrays.stream(memoryFrames)
                .filter(memoryOffset -> memoryOffset.getStatus().equals(FREE))
                .filter(memoryOffset -> (memoryOffset.getFrameID() + 1) % 2 == 0)
                .limit(framesNeeded)
                .toArray(MemoryFrame[]::new);
    }

    public void loadProgramToMemoryFrames(MemoryFrame[] memoryFrames, List<String> program) { // Carrega o programa para seus respectivos frames

        log.info("Change status of frames {} to OCCUPIED", memoryFrames);

        changeStatusFrames(memoryFrames);

        log.info("Load program to a memory frames");

        int programLineBeingLoaded = 0; // Carrega as instruções do programa
        for (int i = 0; i < memoryFrames.length; i++) {
            for (int j = memoryFrames[i].getFrameOffset().getInitial(); j <= memoryFrames[i].getFrameOffset().getLimit(); j++) {
                if (program.size() <= programLineBeingLoaded) {
                    return;
                }
                this.adresses[j] = program.get(programLineBeingLoaded++); // atribui a linha do programa lido ao endereço na memória
            }
        }
    }

    public void deallocate(int programID) { // Dump de memória, desaloca programa, muda os frames do mesmo para livre e printa programa limpando a memória.

        log.info("Deallocate memory for program id:  {}", programID);
        MemoryFrame[] memoryFrames = pagTable.get(programID);

        log.info("Change status of frames {} to FREE", memoryFrames);
        changeStatusFrames(memoryFrames);

        log.info("PRINTING MEMORY STATE OF PROGRAM WITH ID {}", programID);
        final int[] color = {0};
        Arrays.stream(memoryFrames)
                .forEach(memoryFrame -> {
                    int inicio = memoryFrame.getFrameOffset().getInitial();
                    int fim = memoryFrame.getFrameOffset().getLimit();
                    for (int i = inicio; i <= fim; i++) {
                        System.out.printf("\n\033[3%dm [%d] : %s", color[0], i, adresses[i]); // Printa o programa e cada cor significa um frame diferente
                        this.adresses[i] = null;
                    }
                    color[0]++;
                    System.out.println();
                });
    }

    private void validateLogicalMemoryPosition(int programID, int position) throws Exception {
        MemoryFrame[] memoryFrames = pagTable.get(programID);
        for (int i = 0; i < memoryFrames.length; i++) {
            if (position >= memoryFrames[i].getFrameOffset().getInitial() && position <= memoryFrames[i].getFrameOffset().getLimit()) {
                return;
            }
        }
        throw new Exception("Invalid memory acess");
    }

    private MemoryFrame[] allocate(int memorySize, int quantityOfMemoryFrames) { // Ao ser criado a memória faz a divisão de frames e páginas
        log.info("Allocate memory of size {} and {} frames", memorySize, quantityOfMemoryFrames);

        MemoryFrame[] memoryFramesCreated = new MemoryFrame[quantityOfMemoryFrames];
        for (int i = 0; i < quantityOfMemoryFrames; i++) {
            memoryFramesCreated[i] = new MemoryFrame(memorySize, quantityOfMemoryFrames);
        }

        return memoryFramesCreated;
    }

    private int calculateFramesNeeded(int programSize) { // Calculo para saber quantos frames serão necessários para execução do programa

        int sizeNeededToRunProgram = programSize * 3; // Programa tem 3x o seu tamanho como espaço de memória necessário para execução

        log.info("Calculate how many frames are needed to program of size {}", sizeNeededToRunProgram);

        if (sizeNeededToRunProgram <= QUANTITY_OF_PAGES) {
            return 1;
        }

        int calculus = sizeNeededToRunProgram / QUANTITY_OF_PAGES;

        return calculus == 0 ? calculus : calculus + 1;
    }

    private void changeStatusFrames(MemoryFrame[] memoryFrames) { // Muda o status do programa quando carregados na memória
        for (int i = 0; i < memoryFrames.length; i++) {
            this.memoryFrames[memoryFrames[i].getFrameID()].changeStatus();
        }
    }
}
