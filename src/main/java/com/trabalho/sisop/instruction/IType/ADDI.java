package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ADDI extends Instruction {

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] parameters) {

        int rd = Parser.parseParamater(parameters[1]);
        int k = Parser.parseParamater(parameters[2]);

        int registerValue = cpu.getValueFromRegister(rd);
        int result = registerValue + k;

        log.info("Registrador {} = {}", rd, result);
        cpu.updateRegister(rd, result);
        cpu.incrementPC();

    }
}
