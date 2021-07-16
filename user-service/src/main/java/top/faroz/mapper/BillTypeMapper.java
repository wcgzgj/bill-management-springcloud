package top.faroz.mapper;

import top.faroz.pojo.BillType;
import top.faroz.pojo.BillTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillTypeMapper {
    long countByExample(BillTypeExample example);

    int deleteByExample(BillTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BillType record);

    int insertSelective(BillType record);

    List<BillType> selectByExample(BillTypeExample example);

    BillType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BillType record, @Param("example") BillTypeExample example);

    int updateByExample(@Param("record") BillType record, @Param("example") BillTypeExample example);

    int updateByPrimaryKeySelective(BillType record);

    int updateByPrimaryKey(BillType record);
}