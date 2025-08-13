package com.rookies4.MySpringBootLab.runner;

import com.rookies4.MySpringBootLab.config.MyEnviroment;
import com.rookies4.MySpringBootLab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Autowired
    private MyPropProperties myPropProperties;

    @Autowired(required = false)
    private MyEnviroment myEnviroment;

    @Value("${myprop.username}")
    String username;

    @Value("${myprop.port}")
    int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("username = " + username);
        System.out.println("port = " + port);

        System.out.println("username = " + myPropProperties.getUsername());
        System.out.println("port = " + myPropProperties.getPort());

        if (myEnviroment != null){
            logger.info("{}", myEnviroment);
        }
        logger.info("사용자 이름 : " + myPropProperties.getUsername());
        logger.debug("포트 : " + myPropProperties.getPort());
    }
}
