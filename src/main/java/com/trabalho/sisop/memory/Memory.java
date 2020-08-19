package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.trabalho.App.MEMORY_SIZE;
import static com.trabalho.sisop.memory.MemorySectorStatus.FREE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@ToString
@Data
public class Memory {

    private String[] adresses;
    private MemorySector[] memorySectors;

    public Memory(int memorySize, int quantityMemorySectors) {

        this.adresses = new String[memorySize];
        this.memorySectors = allocateMemory(memorySize, quantityMemorySectors);

        log.info("Memory has {} size and {} sectors", memorySize, memorySectors);

    }

    public String getInstructions(int position) {
        return adresses[position];
    }

    public int getValueFromIndex(int position) {
        return Integer.parseInt(getAdresses()[position]);
    }

    public void writeValueToMemory(int position, int value) {
        this.getAdresses()[position] = String.valueOf(value);
    }

    public void printAllMemoryStack() {
        List<String> collect = IntStream.range(0, MEMORY_SIZE)
                .filter(index -> isFalse(isNull(this.getAdresses()[index]))) // remover essa linha para printar todas as posições
                .mapToObj(index -> "[" + index + "]" + " : " + this.getAdresses()[index])
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public MemorySector getMemorySectorFree() {
        log.info("Search for free memory sector");

        return Arrays.stream(memorySectors)
                .filter(memoryOffset -> memoryOffset.getStatus().equals(FREE))
                .findFirst()
                .orElse(null);

    }

    public void loadProgramToMemorySector(MemorySector memorySector, List<String> program) {
        log.info("Load program to a memory sector");

        for (int i = memorySector.getMemoryOffset().getInitial(); i < program.size(); i++) {
            this.adresses[i] = program.get(i);
        }

        this.memorySectors[memorySector.getSectorID()].changeStatus();
    }

    private MemorySector[] allocateMemory(int memorySize, int quantityMemorySectors) {
        log.info("Allocate memory for memory with size of {} and {} sectors", memorySize, quantityMemorySectors);

        MemorySector[] memorySectorsPopulated = new MemorySector[quantityMemorySectors];
        for (int i = 0; i < quantityMemorySectors; i++) {
            memorySectorsPopulated[i] = new MemorySector(memorySize, quantityMemorySectors);
        }

        return memorySectorsPopulated;
    }

}
