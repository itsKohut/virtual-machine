package com.trabalho;

import com.trabalho.sisop.VirtualMachine;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.storage.Storage;

public class App {

    public static final int MEMORY_SIZE = 1024;
    public static final int QUANTITY_OF_FRAMES = 64;
    public static final int QUANTITY_OF_PAGES = 16;

    public static void main(String[] args) {

        CPU cpu = new CPU();
        Storage storage = new Storage();
        MemoryManager memoryManager = new MemoryManager(MEMORY_SIZE, QUANTITY_OF_FRAMES);

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memoryManager);

        virtualMachine.run();

    }
}
