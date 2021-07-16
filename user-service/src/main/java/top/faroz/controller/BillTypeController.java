package top.faroz.controller;

import top.faroz.resp.CommonResp;
import top.faroz.service.BillTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName BillTypeController
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午12:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/billType")
public class BillTypeController {

    @Resource
    private BillTypeService billTypeService;


    @GetMapping("/list")
    public CommonResp getAll() {
        CommonResp commonResp = new CommonResp();
        commonResp.setContent(billTypeService.getAll());
        return commonResp;
    }
}
