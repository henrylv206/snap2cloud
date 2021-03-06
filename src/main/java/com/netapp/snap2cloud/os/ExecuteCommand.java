/*
 * Copyright (c) 2014. Emitrom, Inc. All Rights Reserved.
 */

package com.netapp.snap2cloud.os;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteCommand.class);
    private static final String CMD_EXECUTE = "Executing command: %s";
    private static final String CMD_EXECUTE_FAILED = "Command %s failed with exit code %s";
    private static final String SLEEP_INTERVAL = "Sleeping %s seconds";

    public void executeCmd(List<String> cmd) throws Exception {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);

            LOGGER.info(String.format(CMD_EXECUTE, cmd.toString()));

            pb.redirectErrorStream(true);
            Process p = pb.start();

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new IOException(String.format(CMD_EXECUTE_FAILED, cmd.toString(), exitCode));
            }

        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
        
        LOGGER.info(String.format(CMD_EXECUTE, cmd.toString() + " completed successfully"));
    }
    
    public void sleep(int seconds) throws IOException {
        LOGGER.debug(String.format(SLEEP_INTERVAL, seconds));

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
