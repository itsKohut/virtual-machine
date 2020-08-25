package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SUBI extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = parameters[1];
        this.REGISTER_TWO = null;
        this.PARAMETER = parameters[2];

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        int rd = Parser.parseParamater(REGISTER_ONE);
        int k = Parser.parseParamater(PARAMETER);

        int rdValue = cpu.getRegisters()[rd];
        int result = rdValue - k;

        log.info("Registrador {} = {}", rd + 1, result);
        cpu.updateRegister(rd, result);
        cpu.incrementPC();

    }
}
