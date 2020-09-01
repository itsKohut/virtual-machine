package com.trabalho;

import com.trabalho.sisop.VirtualMachine;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.storage.Storage;

public class App {

    public static final int MEMORY_SIZE = 1024;
    public static final int QUANTITY_OF_MEMORY_SECTORS = 1;


    // TODO Para a segunda parte do trabalho será necessário criar no MemoryOffset uma subdivisão de instruções
    //  (o que é lido do arquivo de texto) e dados (inteiros escritos na memória);

    // TODO depois de criada a lógica acima, será necessário validar as operações leitura (LDD, LDX) e escrita (STD, STX)
    // hoje valida apenas se alguma dessa instrução fizer um acesso fora de seu setor e não se ela esta escrevendo num area da memoria indevida.
    // como a memoria hoje conta com apenas 1 setor, e os valores no inicio do programa são setados para iniciar em uma determinada posição [50]
    // inicialmente não haverá casos onde possa ocorrer um subscrita das instruções por valores de dados.


    public static void main(String[] args) {

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS);

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);

        virtualMachine.run();

        virtualMachine.getMemory().printAllMemoryStack();

    }
}
