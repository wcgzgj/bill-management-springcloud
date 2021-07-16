package top.faroz.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.faroz.pojo.Bill;
import top.faroz.req.BillQueryReq;
import top.faroz.req.BillSaveReq;
import top.faroz.resp.CommonResp;

@FeignClient("user-service") // 添加 feign 注解
public interface UserClient {

    //http://user-service/Bill/123
    @GetMapping("/bill/{id}")
    Bill queryById(@PathVariable("id") Long id);


    /**
     * 列表查询
     * 包括分页和模糊查询
     * @param req
     * @return
     */
    @GetMapping("/bill//list")
    CommonResp list(BillQueryReq req);


    /**
     * 根据 id 进行查询
     * @param id
     * @return
     */
    @GetMapping("/bill/selectById/{id}")
    CommonResp selectById(@PathVariable("id") String id);


    /**
     * 账单修改/插入 接口
     * @param req
     * @return
     */
    @PostMapping("/bill/save")
    CommonResp save(@RequestBody BillSaveReq req);


    /**
     * 根据主键，删除id
     * @param id
     * @return
     */
    @DeleteMapping("/bill/delete/{id}")
    CommonResp deleteById(@PathVariable("id")String id);

    /**
     * 获取账单类型
     * @return
     */
    @GetMapping("/billType/list")
    CommonResp getAll();

}
