package com.trabalho;

import com.trabalho.sisop.VirtualMachine;
import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.storage.Storage;

public class App {

    public static final int MEMORY_SIZE = 1024;
    public static final int QUANTITY_OF_MEMORY_SECTORS = 1;

    // TODO criar registrado de instrução, no método run da cpu deve criar o objeto da instrução em ve de executalo direto
    
    // TODO ao criar o objeto instrução.build(parameters) e na classe adicionar os 4 parametros (alguns devem ficar nulos mesmo,
    //  depende da instrução) terá alidação nisso também, ex: [OPCODE, REG, REG, PAR]

    // TODO criar as validações possiveis em cada instrução, jogar exceção de um instrução ou mudar a variavel global quando
    //  lançar para STOP, para que seja terminado o programa

    // TODO criar os programas na linguagem de 'assemble', P2, P3 e P4 (ver enunciado do trabalho)

    // TODO Validar escrita e leitura da dados na memória (inicialmente so vamos ter uma bloco de memoria)
    //   - daria para adicionar essa logica nas instruções no que diz respeito as validações necessárias a serem feitas
    //   caso tente escrever onde as instruções (ex: LDI, STX ... etc) estão deve ocorrer erro. (Espaço de dados e de instruções)

    // TODO programa apenas executa um programa independente do quantos programas estiverem na fila do storage - criar loop

    public static void main(String[] args) {

        CPU cpu = new CPU();
        Storage storage = new Storage();
        Memory memory = new Memory(MEMORY_SIZE, QUANTITY_OF_MEMORY_SECTORS);

        VirtualMachine virtualMachine = new VirtualMachine(cpu, storage, memory);

        virtualMachine.run();

        virtualMachine.getMemory().printAllMemoryStack();


    }
}
