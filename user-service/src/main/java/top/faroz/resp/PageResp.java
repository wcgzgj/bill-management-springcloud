package top.faroz.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResp
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/6/23 上午12:12
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> {
    /**
     * 记录后端分页查询后，查询出的总个数
     */
    private Long total;
    /**
     * 因为分页操作，只可能在查询的时候出现
     * 所以，返回的必然是个 list
     */
    private List<T> list;
}
