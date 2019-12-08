package zqit.chat.loginApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(value="zqit.chat")
public class LoginApiApp extends SpringBootServletInitializer
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LoginApiApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(LoginApiApp.class, args);
	}

}
