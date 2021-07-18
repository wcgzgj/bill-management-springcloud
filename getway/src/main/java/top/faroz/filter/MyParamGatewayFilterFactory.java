package top.faroz.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MyParamGatewayFilterFactory
 * @Description TODO
 * @Author faro_z
 * @Date 2021/7/17 10:51 下午
 * @Version 1.0
 **/
@Component
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.Config> {

    /**
     * Config 中的参数名称
     */
    static final String PARAM_NAME = "kkk";

    public MyParamGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * shortcutFieldOrder,这个值决定了Config中配置的属性，配置的参数都会被封装到该属性当中
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_NAME);
    }

    /**
     * 这里面编写过滤器的逻辑
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 获取请求对象
            ServerHttpRequest request = exchange.getRequest();
            if (request.getQueryParams().containsKey(config.kkk)) {
                request.getQueryParams().get(config.kkk)
                        .forEach(value -> System.out.printf("---局部过滤器---%s = %s ---",config.kkk,value));
            }
            return chain.filter(exchange);
        };
    }


    /**
     * 过滤器配置
     */
    public static class Config {
        private String kkk;

        public String getKkk() {
            return kkk;
        }

        public void setKkk(String kkk) {
            this.kkk = kkk;
        }
    }
}
