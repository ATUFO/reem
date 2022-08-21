package top.taofoo.backboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.taofoo.backboot.entity.User;
import top.taofoo.backboot.mapper.UserMapper;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
//    @RequestMapping("list")
    public Object list(){
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
