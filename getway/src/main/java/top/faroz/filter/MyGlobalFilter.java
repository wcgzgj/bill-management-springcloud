package top.faroz.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName MyGlobalFilter
 * @Description TODO
 * @Author faro_z
 * @Date 2021/7/18 10:40 上午
 * @Version 1.0
 **/
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("-----全局过滤器------");
        // 获取头部携带的 token 信息
        String token = exchange.getRequest().getHeaders().getFirst("token");
        System.out.println("获得的token信息为:"+token);
        // 如果没有 token 信息，设置返回状态为未授权
        if (StringUtils.isBlank(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 如果得到 token 信息，则过滤器放行
        return chain.filter(exchange);
    }

    /**
     * 设置过滤器的优先级
     * 数字越小，优先级越大
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
