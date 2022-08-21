package top.taofoo.backboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class LockRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long createTimestamp;
    private String lockNum;
    private Integer status;
}
