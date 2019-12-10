package zqit.chat.mqListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:zqit/chat/mqListener/pulgins/mq/activeMq.properties", encoding = "utf-8")

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(value="zqit.chat")
public class MQListenerApp extends SpringBootServletInitializer
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MQListenerApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MQListenerApp.class, args);
	}

}
