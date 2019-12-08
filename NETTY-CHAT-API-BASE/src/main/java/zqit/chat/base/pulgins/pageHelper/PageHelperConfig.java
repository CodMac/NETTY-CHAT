package zqit.chat.base.pulgins.pageHelper;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * 分页配置 pagehelper
 * @author mc
 *
 */
@Configuration
public class PageHelperConfig {

	@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        /**
         * 默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。 
         * 当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页。
         */
        p.setProperty("offsetAsPageNum", "true");
        /**
         * 默认值为false，该参数对使用 RowBounds 作为分页参数时有效。 
         * 当该参数设置为true时，使用 RowBounds 分页会进行 count 查询。
         */
        p.setProperty("rowBoundsWithCount", "true");
        /**
         * 分页合理化参数
         * 默认值为false。
         * 当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
         */
        p.setProperty("reasonable", "true");
        /**
         * 多数据源自动切换
         * 默认值为 false。
         * 设置为 true 时，允许在运行时根据多数据源自动识别对应方言的分页 （不支持自动选择sqlserver2012，只能使用sqlserver）
         */
        p.setProperty("autoRuntimeDialect", "false");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
