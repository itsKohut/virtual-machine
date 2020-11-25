package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.trabalho.App.*;
import static com.trabalho.sisop.memory.FrameStatus.FREE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@ToString
@Data
public class MemoryManager {

    public static String[] ADDRESS = new String[MEMORY_SIZE];
    public static MemoryFrame[] MEMORY_FRAMES =  structure(MEMORY_SIZE, QUANTITY_OF_FRAMES);

    public static String getInstructions(int position) {
        return ADDRESS[position];
    }

    public static int logicalMemoryTranslator(MemoryFrame[] memoryFrames, int position){

        int logicalPosition = (memoryFrames[position / 16].getFrameID() * 16) + (position % 16);

        if(isFalse(validateLogicalMemoryPosition(memoryFrames, logicalPosition))){
            return -1;
        }

        return logicalPosition;
    }

    public static int getValueFromIndex(MemoryFrame[] memoryFrames, int position) {
        return Integer.parseInt(ADDRESS[logicalMemoryTranslator(memoryFrames, position)]);
    }

    public static void writeValueToMemory(MemoryFrame[] memoryFrames, int position, int value) {
        ADDRESS[logicalMemoryTranslator(memoryFrames, position)] = String.valueOf(value);
    }

    public static MemoryFrame[] getMemoryFreeFrames(int programSize) {

        int framesNeeded = calculateFramesNeeded(programSize);

        log.info("Searching for a set of free frames to run a program that needs {} free frames", framesNeeded);

        return Arrays.stream(MEMORY_FRAMES)
                .filter(memoryOffset -> memoryOffset.getStatus().equals(FREE))
                .filter(memoryOffset -> (memoryOffset.getFrameID() + 1) % 2 == 0)
                .limit(framesNeeded)
                .toArray(MemoryFrame[]::new);
    }

    public static void allocate(MemoryFrame[] memoryFrames, List<String> program) { // Carrega o programa para seus respectivos frames

        log.info("Change status of frames {} to OCCUPIED", memoryFrames);

        changeStatusFrames(memoryFrames);

        log.info("Load program to a memory frames");

        int programLineBeingLoaded = 0; // Carrega as instruções do programa
        for (int i = 0; i < memoryFrames.length; i++) {
            for (int j = memoryFrames[i].getFrameOffset().getInitial(); j <= memoryFrames[i].getFrameOffset().getLimit(); j++) {
                if (program.size() <= programLineBeingLoaded) {
                    return;
                }
              ADDRESS[j] = program.get(programLineBeingLoaded++); // atribui a linha do programa lido ao endereço na memória
            }
        }
    }

    public static void deallocate(MemoryFrame[] memoryFrames) { // Dump de memória, desaloca programa, muda os frames do mesmo para livre e printa programa limpando a memória.

        changeStatusFrames(memoryFrames);

        final int[] color = {0};
        Arrays.stream(memoryFrames)
                .forEach(memoryFrame -> {
                    int inicio = memoryFrame.getFrameOffset().getInitial();
                    int fim = memoryFrame.getFrameOffset().getLimit();
                    for (int i = inicio; i <= fim; i++) {
                        System.out.printf("\n\033[3%dm [%d] : %s", color[0], i, ADDRESS[i]); // Printa o programa e cada cor significa um frame diferente
                        ADDRESS[i] = String.valueOf(new Random().nextInt(10) + 1);
                    }
                    color[0]++;
                });
    }

    private static boolean validateLogicalMemoryPosition(MemoryFrame[] memoryFrames, int position) {
        for (int i = 0; i < memoryFrames.length; i++) {
            if (position >= memoryFrames[i].getFrameOffset().getInitial() && position <= memoryFrames[i].getFrameOffset().getLimit()) {
                return true;
            }
        }
        return false;
    }

    private static MemoryFrame[] structure(int memorySize, int quantityOfMemoryFrames) { // Ao ser criado a memória faz a divisão de frames e páginas
        log.info("Allocate memory of size {} and {} frames", memorySize, quantityOfMemoryFrames);

        MemoryFrame[] memoryFramesCreated = new MemoryFrame[quantityOfMemoryFrames];
        for (int i = 0; i < quantityOfMemoryFrames; i++) {
            memoryFramesCreated[i] = new MemoryFrame(memorySize, quantityOfMemoryFrames);
        }

        return memoryFramesCreated;
    }

    private static int calculateFramesNeeded(int programSize) { // Calculo para saber quantos frames serão necessários para execução do programa

        int sizeNeededToRunProgram = programSize * 3; // Programa tem 3x o seu tamanho como espaço de memória necessário para execução

        log.info("Calculate how many frames are needed to program of size {}", sizeNeededToRunProgram);

        if (sizeNeededToRunProgram <= QUANTITY_OF_PAGES) {
            return 1;
        }

        int calculus = sizeNeededToRunProgram / QUANTITY_OF_PAGES;

        return calculus == 0 ? calculus : calculus + 1;
    }

    private static void changeStatusFrames(MemoryFrame[] memoryFrames) { // Muda o status do programa quando carregados na memória
        for (int i = 0; i < memoryFrames.length; i++) {
            MEMORY_FRAMES[memoryFrames[i].getFrameID()].changeStatus();
        }
    }
}
