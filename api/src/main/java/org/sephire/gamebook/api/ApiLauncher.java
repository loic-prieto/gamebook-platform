package org.sephire.gamebook.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Takes care of launching the web server and the application underneath.
 */
@SpringBootApplication
public class ApiLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ApiLauncher.class, args);
    }
}
