package com.dyrnq.controller.home;

import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.User;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.i18n.annotation.I18n;

@Controller
@I18n
@Mapping("")
public class Home {

    @Inject
    UserMapper userMapper;
    @Mapping("")
    public Object index() {
        ModelAndView model = new ModelAndView("index.html");
        model.put("title","dock");
        model.put("message","你好 world!");
        User user = userMapper.selectById("1");
        System.out.println(user.getName());
        return model;
    }

}
