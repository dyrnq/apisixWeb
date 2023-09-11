package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONUtil;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.DeployMapper;
import com.dyrnq.model.Deploy;
import com.dyrnq.service.op.Factory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.yaml.snakeyaml.Yaml;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Mapping("api/deploy")
@Controller
public class DeployController extends ApiController {


    @Inject
    DeployMapper deployMapper;


    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Deploy> p = deployMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Deploy deploy) {
        try {
            if (StringUtils.isBlank(deploy.getId())) {
                deploy.setId(UUID.randomUUID(true).toString());
            }

            if (deploy.getUploadFile() != null) {
                deploy.setContent(IOUtils.toString(deploy.getUploadFile().getContent(), StandardCharsets.UTF_8));
            }

            deploy.setState(0);
            deploy.setInsertTime(new java.util.Date());
            deploy.setUpdateTime(new java.util.Date());
            deploy.setInstId(instId());
            deployMapper.insert(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                deployMapper.deleteById(i);
            }
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get")
    public Result get(Context ctx, String id) {
        try {
            Deploy deploy = deployMapper.selectById(id);
            return Result.succeed(deploy);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Deploy deploy) {
        try {
            deployMapper.updateById(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    private Integer order(Map<String, ?> map) {
        Map<Class, Integer> classMap = new HashMap<>();
        classMap.put(GlobalRule.class, 1);
        classMap.put(PluginConfig.class, 2);
        classMap.put(Proto.class, 3);
        classMap.put(SSL.class, 4);
        classMap.put(Secret.class, 5);
        classMap.put(Consumer.class, 6);
        classMap.put(Upstream.class, 7);
        classMap.put(Service.class, 8);
        classMap.put(Route.class, 9);
        classMap.put(StreamRoute.class, 10);
        classMap.put(ConsumerGroup.class, 11);
        String className = map.get("kind") != null ? map.get("kind").toString() : "";
        Class class_ = null;
        try {
            class_ = StringUtils.contains(className, ".") ? Class.forName(className) : Class.forName("com.dyrnq.apisix.domain." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return classMap.containsKey(class_) ? classMap.get(class_) : 0;
    }

    @Mapping("deploy")
    public Result deploy(Context ctx, String id) {
        try {
            Yaml yaml = new Yaml();
            Deploy deploy = deployMapper.selectById(id);
            Iterable<Object> blocks = yaml.loadAll(new StringReader(deploy.getContent()));


            //按照依赖关系部署
            List<Map<String, ?>> list = new LinkedList<Map<String, ?>>();

            for (Object block : blocks) {
                if (block instanceof Map) {
                    Map<String, ?> map = (Map) block;
                    list.add(map);
                } else {
                    throw new RuntimeException("not support! must yaml!");
                }
            }

            class OrderComparator implements Comparator<Map<String, ?>> {
                @Override
                public int compare(Map<String, ?> s1, Map<String, ?> s2) {
                    return order(s1).compareTo(order(s2));
                }
            }

            list.sort(new OrderComparator());

            for (Map<String, ?> map : list) {
                if (map.containsKey("kind")) {
                    String className = map.get("kind").toString();
                    String _id = map.get("id").toString();
                    String json = JSONUtil.toJsonStr(map);
                    Factory.create(className).putRaw(businessLogic.getAdminClient(deploy.getInstId()), _id, json);
                }
            }
            deploy.setState(1);
            deployMapper.updateById(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("undeploy")
    public Result undeploy(Context ctx, String id) {
        try {
            Yaml yaml = new Yaml();
            Deploy deploy = deployMapper.selectById(id);
            Iterable<Object> blocks = yaml.loadAll(new StringReader(deploy.getContent()));
            //按照依赖关系删除部署
            List<Map<String, ?>> list = new LinkedList<Map<String, ?>>();

            for (Object block : blocks) {
                if (block instanceof Map) {
                    Map<String, ?> map = (Map) block;
                    list.add(map);
                } else {
                    throw new RuntimeException("not support! must yaml!");
                }
            }


            class OrderComparator implements Comparator<Map<String, ?>> {
                @Override
                public int compare(Map<String, ?> s1, Map<String, ?> s2) {
                    return order(s2).compareTo(order(s1));
                }
            }

            list.sort(new OrderComparator());

            for (Map<String, ?> map : list) {
                if (map.containsKey("kind")) {
                    String className = map.get("kind").toString();
                    String _id = map.get("id").toString();
                    Factory.create(className).del(businessLogic.getAdminClient(deploy.getInstId()), _id);
                }
            }
            deploy.setState(0);
            deployMapper.updateById(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

}
