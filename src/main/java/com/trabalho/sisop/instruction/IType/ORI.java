package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ORI extends Instruction {

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] operation) {

        int rd = Parser.parseParamater(operation[1]);
        int k = Parser.parseParamater(operation[2]);

        int rdValue = cpu.getRegisters()[rd];
        int result = rdValue | k;

        log.info("Registrador {} = {}", rd, result);
        cpu.updateRegister(rd, result);
        cpu.incrementPC();

    }
}
