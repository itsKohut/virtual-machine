package com.trabalho.sisop;

import com.trabalho.sisop.io.IO;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.storage.Storage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@Getter
public class VirtualMachine {

    public VirtualMachine() {
        populateMemory();
        loadProgramsToCacheStorage();
    }

    @SneakyThrows
    public void run() {


        Thread shellThread = new Shell();
        shellThread.start();
    }

    private void loadProgramsToCacheStorage() {
        Storage.setPrograms(IO.loadPrograms());
    }

    private void populateMemory() {
        Random random = new Random();

        for (int index = 0; index < MemoryManager.ADDRESS.length; index++) {
            MemoryManager.ADDRESS[index] = String.valueOf(random.nextInt(10) + 1);
        }
    }
}
