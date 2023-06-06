package com.dyrnq.controller;


import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.i18n.annotation.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mapping("admin")
@Controller
@I18n
public class AdminController {
    static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Mapping("inst")
    public Object inst() {
        ModelAndView model = new ModelAndView("admin/inst.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }
    @Mapping("user")
    public Object user() {
        ModelAndView model = new ModelAndView("admin/user.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("about")
    public Object about() {
        ModelAndView model = new ModelAndView("admin/about.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }
    @Mapping("route")
    public Object route() {
        ModelAndView model = new ModelAndView("admin/route.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("plugin")
    public Object plugin() {
        ModelAndView model = new ModelAndView("admin/plugin.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("streamRoute")
    public Object streamRoute() {
        ModelAndView model = new ModelAndView("admin/streamRoute.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("upstream")
    public Object upstream() {
        ModelAndView model = new ModelAndView("admin/upstream.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("service")
    public Object service() {
        ModelAndView model = new ModelAndView("admin/service.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("secret")
    public Object secret() {
        ModelAndView model = new ModelAndView("admin/secret.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("ssl")
    public Object ssl() {
        ModelAndView model = new ModelAndView("admin/ssl.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }


    @Mapping("consumer")
    public Object consumer() {
        ModelAndView model = new ModelAndView("admin/consumer.html");
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

    @Mapping("globalRule")
    public Object globalRule() {
        ModelAndView model = new ModelAndView("admin/globalRule.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("pluginConfig")
    public Object pluginConfig() {
        ModelAndView model = new ModelAndView("admin/pluginConfig.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("consumerGroup")
    public Object consumerGroup() {
        ModelAndView model = new ModelAndView("admin/consumerGroup.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }



    @Mapping("editor")
    public Object editor(Context ctx,String cls,String id) {
        ModelAndView model = new ModelAndView("admin/editor.html");
        model.put("id",id);
        model.put("cls",cls);
        return model;
    }




    @Mapping("")
    public Object index(Context ctx) {

        String token = ctx.cookie("TOKEN");
//        System.out.println(token);
//
//        String user_name = ctx.session("user_name", "");
        if (Utils.isEmpty(token)) {
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
