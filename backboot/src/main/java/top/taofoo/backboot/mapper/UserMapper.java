package top.taofoo.backboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.taofoo.backboot.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
