package top.faroz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MyMvcConfig
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/10 下午7:22
 * @Version 1.0
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    /**
     * 为首页添加视图跳转
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/bill/list");
        // registry.addViewController("/login.html").setViewName("login");
        // registry.addViewController("/index.html").setViewName("index");
    }

    /**
     * 为静态资源排除拦截
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("classpath:/static/");
    }

    /**
     * 通过@Bean注解，将我们定义的拦截器注册到Spring容器
     * @return
     */
    // @Bean
    // public LoginInterceptor loginInterceptor() {
    //     return new LoginInterceptor();
    // }


    /**
     * 为登录拦截器配置拦截路径
     * 目前设置的，是所有路径都被拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(this.loginInterceptor())
        //         .addPathPatterns("/**");
    }
}
