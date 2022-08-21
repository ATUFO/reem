package top.taofoo.backboot.dto;

import lombok.Data;

@Data
public class LockRecordDTO {
    private Long id;
    private Long createTimestamp;
    private String createTime;
    private String lockNum;
    private Integer status;
    private String statusDescription;
}
