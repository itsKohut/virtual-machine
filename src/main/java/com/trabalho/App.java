package com.trabalho;

import com.trabalho.sisop.VirtualMachine;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.storage.Storage;

public class App {

    public static final int MEMORY_SIZE = 1024;
    public static final int QUANTITY_OF_MEMORY_SECTORS = 1;


    // TODO Criar no MemoryOffset partição de instruções (o que é lido do arquivo de textp) e dados (inteiros escritos na memória);

    // TODO depois de criado o item acima, ajustar as validações das intruções de acesso de memória: leitura (LDD, LDX) e escrita (STD, STX)
    // Hoje valida apenas se alguma dessa instrução fizer um acesso fora de seu setor.

    // TODO programa apenas executa um programa independente do quantos programas estiverem na fila do storage - criar loop

    public static void main(String[] args) {

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS);

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);

        virtualMachine.run();

        virtualMachine.getMemory().printAllMemoryStack();

    }
}
