package com.trabalho.sisop.storage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;

@Data
@Slf4j
public class Storage {

    Queue<List<String>> programs;;

    public List<String> getProgramWaitingProcessingQueue() {
        log.info("Program removed from storage and added to memory for processing");
        return programs.remove();
    }
}
