package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServer {
    private final static Logger log = LoggerFactory.getLogger(WebApplicationServer.class);
    public static void main(String[] args) throws LifecycleException {
//        String currentDir = System.getProperty("user.dir");
//        log.debug("Current Working Directory: {}", currentDir);

        String baseDir = "webapps/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File(baseDir).getAbsolutePath());
        log.info("configuring app with basedir: {}", new File(baseDir).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}