package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPush {

    private static final Logger logger = LoggerFactory.getLogger(LogPush.class);

    public static void main(String[] args) {
        logger.info("Hello from Java application!");
    }
}