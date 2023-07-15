package ch.frankel.blog.loki;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.toString());

    public static void main(String[] args) throws InterruptedException {
        String who;
        if (args.length == 0) {
            who = "Java app";
        } else {
            who = args[0];
        }
        LOGGER.info("Hello from {}!", who);
        Thread.sleep(5000);
    }
}
