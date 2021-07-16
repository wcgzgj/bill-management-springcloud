package top.faroz.service;

import top.faroz.req.BillQueryReq;
import top.faroz.req.BillSaveReq;
import top.faroz.resp.BillQueryResp;
import top.faroz.resp.PageResp;

public interface BillService {

    /**
     * 获取账单的信息
     * 查询条件包括分页参数和模糊查询
     * @param req
     * @return
     */
    PageResp list(BillQueryReq req);

    /**
     * 根据 id 进行查询
     * @param id
     * @return
     */
    BillQueryResp selectById(Long id);

    /**
     * 账单保存/插入
     * @param req
     */
    void save(BillSaveReq req);


    /**
     * 根据 id 删除账单信息
     * @param id
     */
    void deleteById(Long id);
}
