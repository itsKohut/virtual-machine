package com.trabalho.sisop.cpu;

import com.trabalho.sisop.ProgramManager;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.utils.Parser;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
@ToString
public class CPU {

    private final static int QUANTITY_OF_REGISTERS = 8;
    private final static String STOP = "STOP";

    private int pc = 0;
    private Integer[] registers;
    private Instruction registerInstruction;

    public CPU() {
        this.registers = new Integer[QUANTITY_OF_REGISTERS];
    }

    public void incrementPC() {
        this.pc++;
    }

    public void updatePC(int jumpPosition) {
        this.pc = jumpPosition;
    }

    public void updateRegister(int register, int value) {
        this.registers[register] = value;
    }

    public int getValueFromRegister(int register) {
        return this.getRegisters()[register];
    }

    public void execute(int programID, MemoryManager memoryManager) {

        log.info("PROGRAM WITH ID: {} RUNNING", programID);

        this.pc = 0;

        String OPCODE = "";

        while (isFalse(OPCODE.equals(STOP))) {

            try {

                int logicalMemoryPosition = memoryManager.logicalMemoryTranslator(programID, this.pc); // Traduz a posição da memória física para a memória lógica
                String instruction = memoryManager.getInstructions(logicalMemoryPosition); // Carrega a instrução contida na posição da memória lógica (ex: LDI R1, 1)
                String[] parameters = Parser.parseOperation(instruction); // Faz o split dos parametros da instrução

                OPCODE = parameters[0]; // Obtém o OPCODE para chamar o executor correto da instrução

                log.info(instruction);

                registerInstruction = Instruction.instructions.get(OPCODE).construct(parameters); // Constroi a instrução baseado no opcode e seus parametros
                registerInstruction.execute(programID, this, memoryManager); // Executa a operação

            } catch (Exception e) { // Em caso de erro na execução do programa, o programa irá parar

                OPCODE = STOP;

                log.info(e.getMessage());
                log.info("PROGRAM WITH ID {} FINISHED DUE INTERRUPTION", programID);
            }
        }

        memoryManager.deallocate(programID);

        try { // até achar problema, alguns logs estão sobrepondo o print do dump da memoria
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(" PROGRAM WITH ID {} FINISHED", programID);

        log.info("Clear page table for program with id {}", programID);
        ProgramManager.pagTable.remove(programID);

    }
}
