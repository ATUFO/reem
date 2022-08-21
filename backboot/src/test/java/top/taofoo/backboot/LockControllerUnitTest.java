package top.taofoo.backboot;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import top.taofoo.backboot.controller.LockController;
import top.taofoo.backboot.dto.LockRecordDTO;
import top.taofoo.backboot.entity.LockRecord;
import top.taofoo.backboot.enums.LockRecordStatusEnum;
import top.taofoo.backboot.mapper.LockRecordMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(JMockit.class)
public class LockControllerUnitTest {

    @Tested
    LockController lockController;
    @Injectable
    LockRecordMapper lockRecordMapper;
    @Mocked
    LambdaQueryWrapper lambdaQueryWrapper;

    @Test
    public void listTest() {
        ArrayList<LockRecord> result1 = new ArrayList<>();
        LockRecord e = new LockRecord();
        e.setStatus(LockRecordStatusEnum.LOCKED.ordinal());
        e.setId(12L);
        e.setCreateTimestamp(1232131L);
        result1.add(e);
        new Expectations() {{
            lockRecordMapper.selectList(withAny(new LambdaQueryChainWrapper<>(null)));
            result = result1;
        }};

        List<LockRecordDTO> list = lockController.list(1L);
        assert Objects.equals(list.get(0).getId(),12L);
    }

    @Test
    public void preLockTest() {
        ArrayList<LockRecord> result1 = new ArrayList<>();
        LockRecord e = new LockRecord();
        e.setStatus(LockRecordStatusEnum.LOCKED.ordinal());
        e.setCreateTimestamp(1232131L);
        result1.add(e);
        new Expectations() {{
            lockRecordMapper.insert(withInstanceOf(LockRecord.class));
            result = 1;
        }};

        LockRecord lockRecord = lockController.preLock();
        assert lockRecord != null;
    }

    @Test
    public void preLockTest_withException() {
        ArrayList<LockRecord> result1 = new ArrayList<>();
        LockRecord e = new LockRecord();
        e.setStatus(LockRecordStatusEnum.LOCKED.ordinal());
        e.setCreateTimestamp(1232131L);
        result1.add(e);
        new Expectations() {{
            lockRecordMapper.insert(withInstanceOf(LockRecord.class));
            result = new Exception("insert error");
        }};

        LockRecord lockRecord = lockController.preLock();
        assert lockRecord == null;
    }
}
