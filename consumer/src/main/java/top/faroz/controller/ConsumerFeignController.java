package top.faroz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.faroz.req.BillQueryReq;
import top.faroz.resp.CommonResp;

import javax.annotation.Resource;

/**
 * @ClassName ConsumerFeignController
 * @Description TODO
 * @Author faro_z
 * @Date 2021/7/16 4:52 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerFeignController {
    @Resource
    private UserClient userClient;

    @GetMapping("/selectById/{id}")
    public CommonResp selectById(@PathVariable("id")String id) {
        return userClient.selectById(id);
    }

    @GetMapping("/list")
    public CommonResp list(BillQueryReq req) {
        return userClient.list(req);
    }

    

}
