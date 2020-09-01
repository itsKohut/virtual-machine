package com.trabalho.sisop.instruction.R1Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
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
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        int register_0 = 0;
        int register_1 = 1;
        int register_2 = 2;
        int register_3 = 3;
        int register_4 = 4;
        int register_5 = 5;
        int register_6 = 6;
        int register_7 = 7;

        log.info("SWAP");
        cpu.updateRegister(register_7, cpu.getValueFromRegister(register_3));
        cpu.updateRegister(register_6, cpu.getValueFromRegister(register_2));
        cpu.updateRegister(register_5, cpu.getValueFromRegister(register_1));
        cpu.updateRegister(register_4, cpu.getValueFromRegister(register_0));

        cpu.incrementPC();

    }
}
