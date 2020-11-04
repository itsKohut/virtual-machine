package com.trabalho.sisop.instruction.R2Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ADD extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = parameters[1];
        this.REGISTER_TWO = parameters[2];
        this.PARAMETER = null;

        return this;
    }

    @Override
    public void execute(MemoryFrame[] memoryFrames) {

        int rd = Parser.parseParamater(REGISTER_ONE);
        int rs = Parser.parseParamater(REGISTER_TWO);

        int rdValue = CPU.REGISTERS[rd];
        int rsValue = CPU.REGISTERS[rs];

        int result = rdValue + rsValue;

        log.info("Registrador {} = {}", rd + 1, result);
        CPU.updateRegister(rd, result);
        CPU.incrementPC();

    }
}

