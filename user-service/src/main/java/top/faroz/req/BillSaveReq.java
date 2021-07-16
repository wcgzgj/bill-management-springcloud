package top.faroz.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BillSaveReq
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 下午2:11
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillSaveReq {
    /**
     * typeId:0,
     * title:"",
     * billTime:"",
     * billTimeStr:"",
     * price:"",
     * explain:"",
     * id:billId
     */
    private Long id;
    private String title;
    private String billTimeStr;
    private Double price;
    private String explain;
    private Long typeId;
}
