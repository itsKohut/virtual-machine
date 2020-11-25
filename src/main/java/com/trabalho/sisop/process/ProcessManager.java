package com.trabalho.sisop.process;

import com.trabalho.sisop.Dispatcher;
import com.trabalho.sisop.queues.ReadyQueue;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.trabalho.App.PROCESS_ID;
import static java.util.Objects.nonNull;

public class ProcessManager {

    public static Map<Integer, ProcessControlBlock> PCB_TABLE = new HashMap<>();

    public synchronized static void processCreation(List<String> program) {

        MemoryFrame[] freeFrames = MemoryManager.getMemoryFreeFrames(program.size());

        if (nonNull(freeFrames)) {
            ProcessControlBlock pcb = new ProcessControlBlock(PROCESS_ID, freeFrames);
            attachProcessToPCB(PROCESS_ID++, pcb);
            MemoryManager.allocate(freeFrames, program);
            ReadyQueue.addProcess(pcb);
            pcb.setStatus(StatusPCB.READY);

            processSchedulling();

        }
    }

    public synchronized static void deallocate(int processID) {
        MemoryManager.deallocate(PCB_TABLE.get(processID).getMemoryFrames());
        PCB_TABLE.remove(processID);
    }

    public synchronized static void saveContext(int processID) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);
        pcb.setPC(CPU.PC);
        pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
        pcb.setRegisters(CPU.REGISTERS);

        pcb.setStatus(StatusPCB.READY);
        ReadyQueue.addProcess(pcb);
    }

    public synchronized static void processSchedulling() {

        Thread th = new Dispatcher();
        th.start();

    }

    private synchronized static void attachProcessToPCB(int processID, ProcessControlBlock pcb) {
        PCB_TABLE.put(processID, pcb);
    }
}
