package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class STD extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = parameters[2];
        this.REGISTER_TWO = null;
        this.PARAMETER = parameters[1];

        return this;
    }

    @Override
    public void execute(MemoryFrame[] memoryFrames) {

        int A = Parser.parseParamater(PARAMETER);
        int rs = Parser.parseParamater(REGISTER_ONE);

        int rsValue = CPU.getValueFromRegister(rs);

        log.info("Memoria [{}] = {}", A, rsValue);
        MemoryManager.writeValueToMemory(memoryFrames, A, rsValue);

        CPU.incrementPC();

    }
}