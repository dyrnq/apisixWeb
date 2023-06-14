package com.dyrnq.controller.api;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.User;
import com.dyrnq.service.BusinessLogic;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api/user")
@Controller
public class UserController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Inject
    UserMapper userMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<User> p = userMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, User user) {
        try {
            userMapper.insert(user, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                userMapper.deleteById(i);
            }
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get")
    public Result get(Context ctx, String id) {
        try {
            User user = userMapper.selectById(id);
            return Result.succeed(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, User user) {
        throw new RuntimeException("not support");
//        try {
//            userMapper.updateById(user, true);
//            return Result.succeed("ok");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return Result.failure(e.getMessage());
//        }
    }
    @Mapping("changePass")
    public Result changePass(Context ctx,String id,String newPass){
        try {
            businessLogic.changePass(id, newPass);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }
}
