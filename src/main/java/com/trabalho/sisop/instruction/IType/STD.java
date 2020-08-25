package com.trabalho.sisop.instruction.IType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
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
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) throws Exception {

        int A = Parser.parseParamater(PARAMETER);
        int rs = Parser.parseParamater(REGISTER_ONE);

        int rsValue = cpu.getValueFromRegister(rs);

        valid(memorySector, A);

        log.info("Memoria [{}] = {}", A, rsValue);
        memory.writeValueToMemory(A, rsValue);

        cpu.incrementPC();

    }

    private void valid(MemorySector memorySector, int willBeSavedAtPos) throws Exception {
        int initial = memorySector.getMemoryOffset().getInitial();
        int limit = memorySector.getMemoryOffset().getLimit();

        if (willBeSavedAtPos < initial || willBeSavedAtPos > limit) {
            throw new Exception("Tentativa de salvar em uma posição de memória inválida");
        }
    }
}