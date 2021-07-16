package top.faroz.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName BillQueryResp
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午11:27
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillQueryResp {
    private Long id;

    private String title;

    private Date billTime;

    private String billTimeStr ;


    private Long typeId;

    private Double price;

    private String explain;

    /**
     * 对一查询
     * 要查询出该 bill 的 billtype信息
     */
    private String billTypeName;

}
