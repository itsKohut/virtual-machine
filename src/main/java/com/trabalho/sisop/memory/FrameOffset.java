package com.trabalho.sisop.memory;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString
@Slf4j
public class FrameOffset {

    public final static int ARRAY_FRAME_SECTOR_ID_ADJUST = 1;
    public final static int ARRAY_OFFSET_LIMIT_ADJUST = 1;

    private Integer initial;
    private Integer limit;

    public FrameOffset(int sectorID, int memorySize, int quantitySectors) {
        this.initial = calculateOffsetInitial(sectorID, memorySize, quantitySectors);
        this.limit = calculateOffsetLimit(sectorID, memorySize, quantitySectors);

        log.info("FrameOffset {}, begins at {} and ends at {}", sectorID, initial, limit);

    }

    private int calculateOffsetInitial(int sectorID, int memorySize, int quantitySectors) {
        int quotient = memorySize / quantitySectors;
        return (quotient * (sectorID + ARRAY_FRAME_SECTOR_ID_ADJUST)) - quotient;
    }

    private int calculateOffsetLimit(int sectorID, int memorySize, int quantitySectors) {
        return ((memorySize / quantitySectors) * (sectorID + ARRAY_FRAME_SECTOR_ID_ADJUST)) - ARRAY_OFFSET_LIMIT_ADJUST;
    }

}
