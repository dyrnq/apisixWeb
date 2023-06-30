package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONUtil;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.DeployMapper;
import com.dyrnq.model.Deploy;
import com.dyrnq.model.Manifest;
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
import java.util.Map;

@Mapping("api/deploy")
@Controller
public class DeployController extends ApiController {


    @Inject
    DeployMapper deployMapper;

    public Class get(String name) {
        try {
            if (StringUtils.contains(name, ".")) {
                Class.forName(name);
            }
            return Class.forName("com.dyrnq.apisix.domain." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Deploy> p = deployMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get")
    public Result get(Context ctx, String id) {
        try {
            Deploy deploy = deployMapper.selectById(id);
            return Result.succeed(deploy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Deploy deploy) {
        try {
            deployMapper.updateById(deploy,true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("deploy")
    public Result deploy(Context ctx, String id) {
        try {
            Yaml yaml = new Yaml();
            Deploy deploy = deployMapper.selectById(id);
            Iterable<Object> blocks = yaml.loadAll(new StringReader(deploy.getContent()));
            Class[] clss = new Class[]{GlobalRule.class, PluginConfig.class, Proto.class, SSL.class, Secret.class, Consumer.class,Upstream.class, Service.class, Route.class, StreamRoute.class, ConsumerGroup.class};
            //按照依赖关系部署
            for(Class clz:clss) {
                for (Object block : blocks) {
                    //System.out.println(BeanUtil.getProperty(block,"kind"));
                    if (block instanceof Map) {
                        Map<String, ?> map = (Map) block;
                        if (map.containsKey("kind")) {
                            String className = map.get("kind").toString();
                            String _id = map.get("id").toString();
                            Class cz = get(className);
                            if(clz == cz) {
                                String json = JSONUtil.toJsonStr(map);
                                Factory.create(className).putRaw(businessLogic.getAdminClient(deploy.getInstId()), _id, json);
                                break;
                            }
                        }

                    } else {
                        throw new RuntimeException("not support! must yaml!");
                    }
                }
            }
            deploy.setState(1);
            deployMapper.updateById(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("undeploy")
    public Result undeploy(Context ctx, String id) {
        try {
            Yaml yaml = new Yaml();
            Deploy deploy = deployMapper.selectById(id);
            Iterable<Object> blocks = yaml.loadAll(new StringReader(deploy.getContent()));
            Class[] clss = new Class[]{ConsumerGroup.class,StreamRoute.class, Route.class ,Service.class,Upstream.class,Consumer.class,Secret.class, GlobalRule.class, PluginConfig.class, Proto.class, SSL.class};
            //按照依赖关系删除部署
            for(Class clz:clss) {
                for (Object block : blocks) {
                    //System.out.println(BeanUtil.getProperty(block,"kind"));
                    if (block instanceof Map) {
                        Map<String, ?> map = (Map) block;
                        if (map.containsKey("kind")) {
                            String className = map.get("kind").toString();
                            String _id = map.get("id").toString();
                            Class cz = get(className);
                            if(clz == cz) {
                                String json = JSONUtil.toJsonStr(map);
                                Factory.create(className).del(businessLogic.getAdminClient(deploy.getInstId()), _id);
                                break;
                            }
                        }

                    } else {
                        throw new RuntimeException("not support! must yaml!");
                    }
                }
            }
            deploy.setState(0);
            deployMapper.updateById(deploy, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

}
