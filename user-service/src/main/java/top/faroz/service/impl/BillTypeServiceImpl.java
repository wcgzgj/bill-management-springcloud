package top.faroz.service.impl;

import top.faroz.mapper.BillTypeMapper;
import top.faroz.pojo.BillType;
import top.faroz.service.BillTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName BillTypeServiceImpl
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午12:36
 * @Version 1.0
 **/
@Service
public class BillTypeServiceImpl implements BillTypeService {

    @Resource
    private BillTypeMapper billTypeMapper;

    /**
     * 获取所有账单类型
     *
     * @return
     */
    @Override
    public List<BillType> getAll() {
        return billTypeMapper.selectByExample(null);
    }
}
