package com.trabalho.sisop.process;

import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.queues.BlockedQueue;
import com.trabalho.sisop.queues.ConsoleOrderQueue;
import com.trabalho.sisop.order.Order;
import com.trabalho.sisop.queues.ReadyQueue;

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

    public synchronized static void IO_1(int processID, IOTYpe IOTYpe, int positionMemory) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);

        pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
        pcb.setRegisters(CPU.REGISTERS);

        pcb.setStatus(StatusPCB.BLOCKED);
        BlockedQueue.addProcess(pcb); // salva o contexto e adiciona na fila de bloqueados

        ConsoleOrderQueue.addProcess(new Order(pcb.getPID(), IOTYpe, positionMemory, pcb.getMemoryFrames(), 10));
    }

    public synchronized static void IO_2(int processID) {
        ProcessControlBlock pcb = PCB_TABLE.get(processID);

        if (nonNull(pcb)) {
            pcb.setPC(CPU.PC);
            pcb.setRegisterInstruction(CPU.REG_INSTRUCTION);
            pcb.setRegisters(CPU.REGISTERS);

            pcb.setStatus(StatusPCB.BLOCKED);
            ReadyQueue.addProcess(pcb);
        }
    }

    private synchronized static void attachProcessToPCB(int processID, ProcessControlBlock pcb) {
        PCB_TABLE.put(processID, pcb);
    }
}
