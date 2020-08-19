package com.trabalho.sisop.instruction.R2Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LDX extends Instruction {

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] operation) {

        int rd = Parser.parseParamater(operation[1]);
        int rs = Parser.parseParamater(operation[2]);

        int rsValue = memory.getValueFromIndex(rs);

        log.info("Registrador {} = {}", rd, rsValue);
        cpu.updateRegister(rd, rsValue);
        cpu.incrementPC();

    }
}
