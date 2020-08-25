package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.io.IO;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.storage.Storage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
public class VirtualMachine {

    private CPU cpu;
    private Storage storage;
    private Memory memory;

    public VirtualMachine(CPU cpu, Storage storage, Memory memory) {
        this.cpu = cpu;
        this.memory = memory;
        this.storage = storage;

        loadProgramsToStorage();
    }

    private void loadProgramsToStorage() {
        storage.setPrograms(IO.loadPrograms());
    }

    public void run() {

        String[] x = memory.getAdresses();

        for (int i = 0; i < x.length; i++) {   //popula a memoria com valores para testar o program 2 3 4;
            x[i] = String.valueOf(x.length - i);
        }

        MemorySector memorySector = this.memory.getMemorySectorFree();
        List<String> program = this.storage.getProgramWaitingProcessingQueue();

        //TODO criar loop para execução de todos os programas

        if (isFalse(isNull(memorySector)) && isNotEmpty(program)) {
            memory.loadProgramToMemorySector(memorySector, program);

            cpu.execute(memory, memorySector);
        }
    }
}
