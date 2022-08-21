package top.taofoo.backboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.taofoo.backboot.dto.LockRecordDTO;
import top.taofoo.backboot.entity.LockRecord;
import top.taofoo.backboot.enums.LockRecordStatusEnum;
import top.taofoo.backboot.mapper.LockRecordMapper;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class LockController {
    @Autowired
    LockRecordMapper lockRecordMapper;
    Random random = new Random(System.currentTimeMillis());

    @RequestMapping("preLock")
    public LockRecord preLock() {
        LockRecord lockRecord = new LockRecord();
        lockRecord.setLockNum(String.format("%04d",random.nextInt(10000)));
        lockRecord.setStatus(LockRecordStatusEnum.PRE_LOCK.ordinal());
        lockRecord.setCreateTimestamp(System.currentTimeMillis());
        try {
            lockRecordMapper.insert(lockRecord);
            return lockRecord;
        } catch (Exception e) {
            log.error("insert record error!", e);
            return null;
        }
    }

    @RequestMapping("lock")
    public boolean lock(Long id, String lockNum) {
        if (id == null || lockNum == null) {
            return false;
        }
        LockRecord lockRecord = lockRecordMapper.selectById(id);
        if (lockRecord != null && Objects.equals(lockRecord.getLockNum(), lockNum)) {
            lockRecord.setStatus(LockRecordStatusEnum.LOCKED.ordinal());
            int rows = lockRecordMapper.updateById(lockRecord);
            return rows == 1;
        }
        return false;
    }

    @RequestMapping("list")
    public List<LockRecordDTO> list(@RequestParam(required = false) Long startTimestamp) {
        if (startTimestamp == null) {
            startTimestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7;
        }
        LambdaQueryWrapper<LockRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(LockRecord::getCreateTimestamp, startTimestamp);
        queryWrapper.orderByDesc(LockRecord::getId);
        List<LockRecord> lockRecords = lockRecordMapper.selectList(queryWrapper);
        List< LockRecordDTO> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(lockRecords)) {
            return result;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (LockRecord lockRecord : lockRecords) {
            LockRecordDTO target = new LockRecordDTO();
            BeanUtils.copyProperties(lockRecord, target);
            Date date = new Date(target.getCreateTimestamp());
            target.setCreateTime(sdf.format(date));
            target.setStatusDescription(LockRecordStatusEnum.getDescription(lockRecord.getStatus()));
            result.add(target);
        }
        return result;
    }
}
