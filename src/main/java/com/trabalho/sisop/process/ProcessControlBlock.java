package com.trabalho.sisop.process;


import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import lombok.Data;

import static com.trabalho.sisop.cpu.CPU.QUANTITY_OF_REGISTERS;

@Data
public class ProcessControlBlock {

    private int PID;
    private int PC = 0;
    private StatusPCB status;
    private Instruction registerInstruction = null;
    private Integer[] registers = new Integer[QUANTITY_OF_REGISTERS];
    private MemoryFrame[] memoryFrames;

    public ProcessControlBlock(int PID, MemoryFrame[] memoryFrames) {
        this.PID = PID;
        this.status = StatusPCB.CREATED;
        this.memoryFrames = memoryFrames;
    }
}