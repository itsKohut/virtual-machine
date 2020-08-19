package com.trabalho.sisop.instruction;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.IType.*;
import com.trabalho.sisop.instruction.JType.*;
import com.trabalho.sisop.instruction.R1Type.STOP;
import com.trabalho.sisop.instruction.R1Type.SWAP;
import com.trabalho.sisop.instruction.R2Type.*;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class Instruction {

    public static final Map<String, Instruction> instructions;

    static {
        Map<String, Instruction> instrucionsClasses = new HashMap<>();
        instrucionsClasses.put("JMP", new JMP());
        instrucionsClasses.put("JMPI", new JMPI());
        instrucionsClasses.put("JMPIG", new JMPIG());
        instrucionsClasses.put("JMPIL", new JMPIL());
        instrucionsClasses.put("JMPIE", new JMPIE());
        instrucionsClasses.put("ADDI", new ADDI());
        instrucionsClasses.put("SUBI", new SUBI());
        instrucionsClasses.put("ANDI", new ANDI());
        instrucionsClasses.put("ORI", new ORI());
        instrucionsClasses.put("LDI", new LDI());
        instrucionsClasses.put("LDD", new LDD());
        instrucionsClasses.put("STD", new STD());
        instrucionsClasses.put("ADD", new ADD());
        instrucionsClasses.put("SUB", new SUB());
        instrucionsClasses.put("MULT", new MULT());
        instrucionsClasses.put("LDX", new LDX());
        instrucionsClasses.put("STX", new STX());
        instrucionsClasses.put("SWAP", new SWAP());
        instrucionsClasses.put("STOP", new STOP());
        instructions = Collections.unmodifiableMap(instrucionsClasses);

        log.info("Initiliazed instruction map class");
    }

    public abstract void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] operation);

}
