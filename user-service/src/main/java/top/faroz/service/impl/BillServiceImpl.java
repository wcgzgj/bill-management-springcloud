package top.faroz.service.impl;

import top.faroz.mapper.BillMapper;
import top.faroz.mapper.BillTypeMapper;
import top.faroz.pojo.Bill;
import top.faroz.pojo.BillExample;
import top.faroz.pojo.BillType;
import top.faroz.req.BillQueryReq;
import top.faroz.req.BillSaveReq;
import top.faroz.resp.BillQueryResp;
import top.faroz.resp.PageResp;
import top.faroz.service.BillService;
import top.faroz.util.CopyUtil;
import top.faroz.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName BillServiceImpl
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午11:16
 * @Version 1.0
 **/
@Service
public class BillServiceImpl implements BillService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOG= LoggerFactory.getLogger(BillServiceImpl.class);

    @Resource
    private BillMapper billMapper;

    @Resource
    private BillTypeMapper billTypeMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 获取账单的信息
     * 查询条件包括分页参数和模糊查询
     *
     * @param req
     * @return
     */
    @Override
    public PageResp list(BillQueryReq req) {
        String startDateStr = req.getStartDate();
        String endDateStr = req.getEndDate();
        Date startDate=null;
        Date endDate=null;

        if (startDateStr!=null && startDateStr.length()>0) {
            startDateStr=startDateStr.substring(0,startDateStr.lastIndexOf("T"));
            startDateStr=startDateStr.replaceAll("\"","");
            LOG.info("裁剪后的时间信息为:{}",startDateStr);
            try {
                startDate = sdf.parse(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (endDateStr!=null && endDateStr.length()>0) {
            endDateStr=endDateStr.substring(0,endDateStr.lastIndexOf("T"));
            endDateStr = endDateStr.replaceAll("\"","");
            try {
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        

        /**
         * 条件查询
         */
        BillExample billExample = new BillExample();
        BillExample.Criteria criteria = billExample.createCriteria();
        if (startDate!=null) {
            criteria.andBillTimeGreaterThanOrEqualTo(startDate);
        }
        if (endDate!=null) {
            criteria.andBillTimeLessThanOrEqualTo(endDate);
        }
        if (req.getTypeId()!=0L) {
            criteria.andTypeIdEqualTo(req.getTypeId());
        }

        /**
         * 分页查询
         */
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<Bill> bills = billMapper.selectByExample(billExample);
        PageInfo<Bill> pageInfo = new PageInfo<>(bills);

        /**
         * 将查询出的数据，封装为返回类型
         */
        List<BillQueryResp> billQueryResps = CopyUtil.copyList(bills, BillQueryResp.class);
        /**
         * 将返回类型中的 billType 查询出来
         */
        for (BillQueryResp billQueryResp : billQueryResps) {
            BillType billType = billTypeMapper.selectByPrimaryKey(billQueryResp.getTypeId());
            billQueryResp.setBillTypeName(billType.getName());
            billQueryResp.setBillTimeStr(sdf.format(billQueryResp.getBillTime()));
        }

        PageResp pageResp = new PageResp();
        pageResp.setList(billQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    /**
     * 根据 id 进行查询
     *
     * @param id
     * @return
     */
    @Override
    public BillQueryResp selectById(Long id) {
        Bill bill = billMapper.selectByPrimaryKey(id);
        BillQueryResp copy = CopyUtil.copy(bill, BillQueryResp.class);
        BillType billType = billTypeMapper.selectByPrimaryKey(copy.getTypeId());
        copy.setBillTypeName(billType.getName());
        copy.setBillTimeStr(sdf.format(copy.getBillTime()));
        return copy;
    }

    /**
     * 账单保存/插入
     *
     * @param req
     */
    @Override
    public void save(BillSaveReq req) {
        /**
         * 主键为空，说明是新增
         */
        if (req.getId()==null) {
            Bill bill = CopyUtil.copy(req, Bill.class);
            Date billDate = null;
            try {
                billDate = sdf.parse(req.getBillTimeStr());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bill.setBillTime(billDate);
            bill.setId(snowFlake.nextId());
            billMapper.insert(bill);
        } else {
            /**
             * 主键不为空，说明是更新
             */
            Bill bill = CopyUtil.copy(req, Bill.class);
            Date billDate = null;
            try {
                billDate = sdf.parse(req.getBillTimeStr());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bill.setBillTime(billDate);
            billMapper.updateByPrimaryKeySelective(bill);
        }
    }

    /**
     * 根据 id 删除账单信息
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        billMapper.deleteByPrimaryKey(id);
    }
}
