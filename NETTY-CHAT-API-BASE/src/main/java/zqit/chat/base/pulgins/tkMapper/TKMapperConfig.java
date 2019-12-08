package zqit.chat.base.pulgins.tkMapper;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * tkMapper配置
 * @author mc
 *
 */
@Configuration
public class TKMapperConfig {

//	可不加，因为框架提供了默认实现
	@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("zqit.chat.base.mapper.**");
        Properties properties = new Properties();
        properties.setProperty("ORDER", "BEFORE");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY","SELECT REPLACE(UUID(),''-'','''')");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
	
}
