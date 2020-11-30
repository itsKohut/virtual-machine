package com.trabalho.sisop.order;

import com.trabalho.sisop.memory.MemoryFrame;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private Integer PID;
    private IOTYpe IO_OPCODE;
    private Integer positionMemory;
    private MemoryFrame[] memoryFrames;
    private Integer value;

    public Order(Integer PID, IOTYpe IO_OPCODE, Integer positionMemory, MemoryFrame[] memoryFrames) {
        this.PID = PID;
        this.IO_OPCODE = IO_OPCODE;
        this.positionMemory = positionMemory;
        this.memoryFrames = memoryFrames;
    }
}
