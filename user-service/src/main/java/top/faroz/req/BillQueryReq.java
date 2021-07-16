package top.faroz.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BillQueryReq
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午11:13
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillQueryReq extends PageReq {
    private String startDate;
    private String endDate;
    private String title;
    private Long typeId;
}
