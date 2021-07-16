package top.faroz.controller;

import top.faroz.req.BillQueryReq;
import top.faroz.req.BillSaveReq;
import top.faroz.resp.BillQueryResp;
import top.faroz.resp.CommonResp;
import top.faroz.resp.PageResp;
import top.faroz.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName BillController
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午12:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/bill")
public class BillController {

    private static final Logger LOG= LoggerFactory.getLogger(BillController.class);

    @Resource
    private BillService billService;

    /**
     * 列表查询
     * 包括分页和模糊查询
     * @param req
     * @return
     */
    @GetMapping("/list")
    public CommonResp list(BillQueryReq req) {
        LOG.info("传入的查找参数为:{}",req);

        PageResp pageResp = billService.list(req);
        CommonResp commonResp = new CommonResp();
        commonResp.setContent(pageResp);
        return commonResp;
    }


    /**
     * 根据 id 进行查询
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public CommonResp selectById(@PathVariable("id") String id) {
        long billId = Long.parseLong(id);
        BillQueryResp billQueryResp = billService.selectById(billId);

        CommonResp commonResp = new CommonResp();
        commonResp.setContent(billQueryResp);
        return commonResp;
    }


    /**
     * 账单修改/插入 接口
     * @param req
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@RequestBody BillSaveReq req) {
        billService.save(req);
        return new CommonResp();
    }


    /**
     * 根据主键，删除id
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp deleteById(@PathVariable("id")String id) {
        long billId = Long.parseLong(id);
        billService.deleteById(billId);
        return new CommonResp();
    }

}
