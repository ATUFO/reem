package top.taofoo.backboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.taofoo.backboot.entity.User;
import top.taofoo.backboot.mapper.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class BackbootApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() throws SQLException {

        List<User> users = userMapper.selectList(null);
        System.out.println(users);
//        List<User> query = jdbcTemplate.query("select * from user", new BeanPropertyRowMapper<>(User.class));
//        System.out.println(query);
    }


}
