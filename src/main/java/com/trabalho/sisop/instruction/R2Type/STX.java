package com.trabalho.sisop.instruction.R2Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class STX extends Instruction {

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] operation) {

        int rd = Parser.parseParamater(operation[1]);
        int rs = Parser.parseParamater(operation[2]);

        int rdValue = cpu.getValueFromRegister(rd);
        int rsValue = cpu.getValueFromRegister(rs);

        log.info("Memoria [{}] = {}", rdValue, rsValue);
        memory.writeValueToMemory(rdValue, rsValue);

        cpu.incrementPC();

    }
}
