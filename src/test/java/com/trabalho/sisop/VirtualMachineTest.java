package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.storage.Storage;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.trabalho.App.MEMORY_SIZE;
import static com.trabalho.App.QUANTITY_OF_MEMORY_SECTORS;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.junit.Assert.assertEquals;

/** Rodar os testes separadamente, devido a problemas de concorrencia os testes ao rodarem junto quebram  */
public class VirtualMachineTest {

    @Test
    public void teste_programa_1() { //FIBONACCI

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 0);
        assertEquals(memory.getValueFromIndex(51), 1);
        assertEquals(memory.getValueFromIndex(52), 1);
        assertEquals(memory.getValueFromIndex(53), 2);
        assertEquals(memory.getValueFromIndex(54), 3);
        assertEquals(memory.getValueFromIndex(55), 5);
        assertEquals(memory.getValueFromIndex(56), 8);
        assertEquals(memory.getValueFromIndex(57), 13);
        assertEquals(memory.getValueFromIndex(58), 21);
        assertEquals(memory.getValueFromIndex(59), 34);
        assertEquals(memory.getValueFromIndex(60), 55);
    }

    @Test
    public void teste_programa_2_caso_valor_negativo() { //FIBONACCI

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento

        memory.writeValueToMemory(50, -999);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), -1);
    }


    @Test
    public void teste_programa_2_caso_valor_igual_zero() { //FIBONACCI

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento

        memory.writeValueToMemory(50, 0);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 0);
    }

    @Test
    public void teste_programa_2_caso_valor_maior_que_zero() { //FIBONACCI

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento

        memory.writeValueToMemory(50, 5);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 0);
        assertEquals(memory.getValueFromIndex(51), 1);
        assertEquals(memory.getValueFromIndex(52), 1);
        assertEquals(memory.getValueFromIndex(53), 2);
        assertEquals(memory.getValueFromIndex(54), 3);
    }

    @Test
    public void teste_programa_3_caso_valor_negativo() { //FATORIAL

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento
        storage.getProgramWaitingProcessingQueue(); //remove program1 da fila de processamento

        memory.writeValueToMemory(50, -999);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), -1);
    }

    @Test
    public void teste_programa_3_caso_valor_igual_zero() { //FATORIAL

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento
        storage.getProgramWaitingProcessingQueue(); //remove program1 da fila de processamento

        memory.writeValueToMemory(50, 0);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 1);

    }

    @Test
    public void teste_programa_3_caso_valor_maior_que_zero() { //FATORIAL

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento
        storage.getProgramWaitingProcessingQueue(); //remove program1 da fila de processamento

        memory.writeValueToMemory(50, 5);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 120);

    }

    @Test
    public void teste_programa_4() { //BubbleSort

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS); //1024 memory size  //1 sector

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento
        storage.getProgramWaitingProcessingQueue(); //remove program1 da fila de processamento
        storage.getProgramWaitingProcessingQueue(); //remove program0 da fila de processamento

        memory.writeValueToMemory(50, 10);
        memory.writeValueToMemory(51, 9);
        memory.writeValueToMemory(52, 8);
        memory.writeValueToMemory(53, 7);
        memory.writeValueToMemory(54, 6);
        memory.writeValueToMemory(55, 5);
        memory.writeValueToMemory(56, 4);
        memory.writeValueToMemory(57, 3);
        memory.writeValueToMemory(58, 2);
        memory.writeValueToMemory(59, 1);
        memory.writeValueToMemory(60, 0);

        virtualMachine.run();

        printMemoryPopulated(memory);

        assertEquals(memory.getValueFromIndex(50), 0);
        assertEquals(memory.getValueFromIndex(51), 1);
        assertEquals(memory.getValueFromIndex(52), 2);
        assertEquals(memory.getValueFromIndex(53), 3);
        assertEquals(memory.getValueFromIndex(54), 4);
        assertEquals(memory.getValueFromIndex(55), 5);
        assertEquals(memory.getValueFromIndex(56), 6);
        assertEquals(memory.getValueFromIndex(57), 7);
        assertEquals(memory.getValueFromIndex(58), 8);
        assertEquals(memory.getValueFromIndex(59), 9);
        assertEquals(memory.getValueFromIndex(60), 10);

    }

    private void printMemoryPopulated(Memory memory) {
        List<String> collect = IntStream.range(0, MEMORY_SIZE)
                .filter(index -> isFalse(isNull(memory.getAdresses()[index]))) // remover essa linha para printar todas as posições
                .mapToObj(index -> "[" + index + "]" + " : " + memory.getAdresses()[index])
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}