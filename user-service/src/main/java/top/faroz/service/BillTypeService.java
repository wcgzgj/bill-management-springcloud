package top.faroz.service;

import top.faroz.pojo.BillType;

import java.util.List;

public interface BillTypeService {

    /**
     * 获取所有账单类型
     * @return
     */
    List<BillType> getAll();



}
