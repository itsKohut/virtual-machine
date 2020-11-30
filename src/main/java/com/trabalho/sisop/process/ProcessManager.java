package com.trabalho.sisop.process;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.order.Order;
import com.trabalho.sisop.queues.BlockedQueue;
import com.trabalho.sisop.queues.ConsoleOrderQueue;
import com.trabalho.sisop.queues.FinishedQueue;
import com.trabalho.sisop.queues.ReadyQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.trabalho.App.PROCESS_ID;
import static java.util.Objects.nonNull;

@Slf4j
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

            log.info("Process created with ID {}", PROCESS_ID);
        }
    }

    public synchronized static void deallocate(int processID) {

        FinishedQueue.addProcess(PCB_TABLE.get(processID));

        MemoryManager.deallocate(PCB_TABLE.get(processID).getMemoryFrames());
        PCB_TABLE.remove(processID);

        log.info("Process with ID {} was deallocated", processID);
    }

    public synchronized static void saveContext(int processID) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);
        pcb.setPC(CPU.PC);
        pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
        pcb.setRegisters(CPU.REGISTERS);

        pcb.setStatus(StatusPCB.READY);
        ReadyQueue.addProcess(pcb);

        log.info("Context of process with ID {} saved", processID);
    }

    public synchronized static void resolveIOInterruption(int processID, IOTYpe IOTYpe, int positionMemory) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);

        pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
        pcb.setRegisters(CPU.REGISTERS);

        pcb.setStatus(StatusPCB.BLOCKED);
        BlockedQueue.addProcess(pcb);

        ConsoleOrderQueue.addProcess(new Order(pcb.getPID(), IOTYpe, positionMemory, pcb.getMemoryFrames()));
    }

    public synchronized static void returnIOInterruption(int processID) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);

        if (nonNull(pcb)) {
            pcb.setPC(CPU.PC);
            pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
            pcb.setRegisters(CPU.REGISTERS);

            pcb.setStatus(StatusPCB.READY);
            ReadyQueue.addProcess(BlockedQueue.removeProcess());
        }
    }

    private synchronized static void attachProcessToPCB(int processID, ProcessControlBlock pcb) {
        PCB_TABLE.put(processID, pcb);
    }
}
