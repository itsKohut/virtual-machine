package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LDD extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = parameters[1];
        this.REGISTER_TWO = null;
        this.PARAMETER = parameters[2];

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) throws Exception {

        int rd = Parser.parseParamater(REGISTER_ONE);
        int A = Parser.parseParamater(PARAMETER);

        int result = memory.getValueFromIndex(A);

        valid(memorySector, A);

        log.info("Registrador {} = {}", rd + 1, result);
        cpu.updateRegister(rd, result);
        cpu.incrementPC();

    }

    private void valid(MemorySector memorySector, int willBeLoadedFromPos) throws Exception {
        int initial = memorySector.getMemoryOffset().getInitial();
        int limit = memorySector.getMemoryOffset().getLimit();

        if (willBeLoadedFromPos < initial || willBeLoadedFromPos > limit) {
            throw new Exception("Tentativa de carregar uma posição de memória inválida");        }
    }
}
