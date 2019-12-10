package zqit.chat.echoServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import io.netty.channel.ChannelFuture;
import zqit.chat.echoServer.pulgins.netty.EchoServer;

@PropertySource(value = "classpath:zqit/chat/echoServer/pulgins/mq/activeMq.properties", encoding = "utf-8")

@ComponentScan(value="zqit.chat")
@EnableEurekaClient
@SpringBootApplication
public class EchoServerApp extends SpringBootServletInitializer implements CommandLineRunner
{
	
	@Autowired
	private EchoServer echoServer;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EchoServerApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(EchoServerApp.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/**
		 * 启动netty服务
		 */
		final ChannelFuture future = echoServer.start();
				
		//主线程关闭后, 钩子关闭->相关内存清理、对象销毁
		Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
            	/**
        		 * 销毁netty服务
        		 */
            	echoServer.destory(future);
            }
        });
		
	}
}
