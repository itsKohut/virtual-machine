package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;

import static com.trabalho.sisop.memory.FrameStatus.FREE;
import static com.trabalho.sisop.memory.FrameStatus.OCCUPIED;

@Data
@ToString
public class MemoryFrame {

    public static int FRAME_ID = 0;
    public final static int ARRAY_FRAME_SECTOR_ID_ADJUST = 1;
    public final static int ARRAY_OFFSET_LIMIT_ADJUST = 1;

    public int frameID;
    private FrameOffset frameOffset;
    private FrameStatus status;

    public MemoryFrame(int memorySize, int quantitySectors) {
        this.frameID = FRAME_ID++;
        this.frameOffset = calculateOffset(this.frameID, memorySize, quantitySectors);
        this.status = FREE;
    }

    public FrameOffset calculateOffset(int sectorID, int memorySize, int quantitySectors) {
        return new FrameOffset(sectorID, memorySize, quantitySectors);
    }

    public void changeStatus() {
        if (this.status.equals(FREE)) {
            this.status = OCCUPIED;
        } else {
            this.status = FREE;
        }
    }
}
