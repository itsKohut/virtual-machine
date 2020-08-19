package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;

import static com.trabalho.sisop.memory.MemorySectorStatus.*;

@Data
@ToString
public class MemorySector {

    public static int SECTOR_ID = 0;
    public final static int ARRAY_SECTOR_ID_ADJUST = 1;
    public final static int ARRAY_OFFSET_LIMIT_ADJUST = 1;

    public int sectorID;
    private MemoryOffset memoryOffset;
    private MemorySectorStatus status;

    public MemorySector(int memorySize, int quantitySectors) {
        this.sectorID = SECTOR_ID++;
        this.memoryOffset = calculateOffset(this.sectorID, memorySize, quantitySectors);
        this.status = FREE;
    }

    public MemoryOffset calculateOffset(int sectorID, int memorySize, int quantitySectors) {
        return new MemoryOffset(sectorID, memorySize, quantitySectors);
    }

    public void changeStatus() {
        if (this.status.equals(FREE)) {
            this.status = OCCUPIED;
        } else {
            this.status = FREE;
        }
    }
}
