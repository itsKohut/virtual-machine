package com.trabalho.sisop.instruction.R1Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SWAP extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = null;
        this.REGISTER_TWO = null;
        this.PARAMETER = null;

        return this;
    }

    @Override
    public void execute(MemoryFrame[] memoryFrames) {

        int register_0 = 0;
        int register_1 = 1;
        int register_2 = 2;
        int register_3 = 3;
        int register_4 = 4;
        int register_5 = 5;
        int register_6 = 6;
        int register_7 = 7;

        log.info("SWAP");
        CPU.updateRegister(register_7, CPU.getValueFromRegister(register_3));
        CPU.updateRegister(register_6, CPU.getValueFromRegister(register_2));
        CPU.updateRegister(register_5, CPU.getValueFromRegister(register_1));
        CPU.updateRegister(register_4, CPU.getValueFromRegister(register_0));

        CPU.incrementPC();

    }
}
