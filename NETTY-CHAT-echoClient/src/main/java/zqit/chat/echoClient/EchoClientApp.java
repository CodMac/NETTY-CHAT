package zqit.chat.echoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import io.netty.channel.ChannelFuture;
import zqit.chat.echoClient.pulgins.netty.EchoClient;

@ComponentScan(value="zqit.chat")
@SpringBootApplication
public class EchoClientApp extends SpringBootServletInitializer implements CommandLineRunner
{
	@Autowired
	EchoClient echoClient;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EchoClientApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(EchoClientApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ChannelFuture future = echoClient.start();
		
		//主线程关闭后, 钩子关闭->相关内存清理、对象销毁
		Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
            	/**
        		 * 销毁netty服务
        		 */
            	echoClient.destory(future);
            }
        });
		
	}
}
