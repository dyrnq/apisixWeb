package com.dyrnq.controller;

import com.dyrnq.model.User;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.i18n.annotation.I18n;

@Mapping("admin")
@Controller
@I18n
public class AdminController {

    @Mapping("about")
    public Object about() {
        ModelAndView model = new ModelAndView("admin/about.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("login")
    public Object login() {
        ModelAndView model = new ModelAndView("admin/login.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("")
    public Object index(Context ctx) {
        String user_name = ctx.session("user_name", "");
        if (Utils.isEmpty(user_name)) {
            ModelAndView model = new ModelAndView("admin/index-noauth.html");
            model.put("title", "dock");
            model.put("message", "你好 world!");
            return model;
        } else {
            ModelAndView model = new ModelAndView("admin/index-auth.html");
            model.put("title", "dock");
            model.put("message", "你好 world!");
            return model;
        }

    }
}
