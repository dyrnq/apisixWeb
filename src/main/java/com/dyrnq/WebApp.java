import cn.hutool.system.SystemUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.noear.snack4.ONode;
import org.noear.solon.Solon;
import org.noear.solon.scheduling.annotation.EnableScheduling;
import org.noear.solon.view.freemarker.FreemarkerRender;
import org.noear.wood.WoodConfig;

@EnableScheduling
@Slf4j
public class WebApp {

    public static void main(String[] args) {

        if (args.length > 0 && "cli".equals(args[0])) {
            String[] cliArgs = java.util.Arrays.copyOfRange(args, 1, args.length);
            System.exit(new picocli.CommandLine(new com.dyrnq.apisix.cli.CliMain()).execute(cliArgs));
        }

        Solon.start(WebApp.class, args, app -> {
            // 白名单：仅允许以下配置项从环境变量覆盖，防止意外覆盖非预期配置
            Set<String> allowEnvOverride = new java.util.HashSet<>();
            // 服务器配置
            allowEnvOverride.add("server.port");
            allowEnvOverride.add("server.session.timeout");
            // JWT配置
            allowEnvOverride.add("jwt.name");
            allowEnvOverride.add("jwt.secret");
            allowEnvOverride.add("jwt.prefix");
            allowEnvOverride.add("jwt.expire");
            // 项目配置
            allowEnvOverride.add("project.home");
            // 数据库配置
            allowEnvOverride.add("spring.database.type");
            allowEnvOverride.add("spring.datasource.url");
            allowEnvOverride.add("spring.datasource.username");
            allowEnvOverride.add("spring.datasource.password");
            // Solon内部配置（日志等）
            allowEnvOverride.add("solon.app.name");
            allowEnvOverride.add("solon.logging.appender.console.level");
            allowEnvOverride.add("solon.logging.appender.file.level");

            Set<String> allNodes = Solon.cfg().stringPropertyNames();
            for (String entry : allNodes) {
                if (!allowEnvOverride.contains(entry)) {
                    continue;
                }
                String envName1 = StringUtils.upperCase(
                        StringUtils.replace(entry, "-", "").replace(".", "_"));
                String envName2 = StringUtils.upperCase(
                        StringUtils.replace(entry, "-", "_").replace(".", "_"));
                String envName3 =
                        StringUtils.upperCase(StringUtils.replace(entry.replaceAll("(?<!^)(?=[A-Z])", "_"), "-", "_")
                                .replace(".", "_"));

                String getValue = SystemUtil.get(envName1, true);
                if (getValue != null) {
                    Solon.cfg().setProperty(entry, getValue);
                }
                getValue = SystemUtil.get(envName2, true);
                if (getValue != null) {
                    Solon.cfg().setProperty(entry, getValue);
                }
                getValue = SystemUtil.get(envName3, true);
                if (getValue != null) {
                    Solon.cfg().setProperty(entry, getValue);
                }
            }

            // LogUtil.globalSet(new LogUtilToSlf4j());
            // app.onError(e -> logger.error(e.getMessage(), e));
            app.context().getBeanAsync(FreemarkerRender.class, e -> {
                freemarker.template.Configuration cfg = e.getProvider();
                try {
                    // cfg.setClassicCompatible(false);
                    // cfg.setStrictSyntaxMode(false);
                    cfg.setSetting(Configuration.NUMBER_FORMAT_KEY, "0.##");
                    cfg.setSetting(Configuration.DEFAULT_ENCODING_KEY, "UTF-8");
                    cfg.setSetting(Configuration.TEMPLATE_UPDATE_DELAY_KEY, "0");
                    cfg.setSetting(Configuration.CACHE_STORAGE_KEY, "strong:20, soft:250");
                    // rethrow,debug,html_debug,ignore;
                    if (Solon.cfg().isDebugMode()) {
                        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
                    } else {
                        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    }
                } catch (TemplateException ex) {
                    log.error(ex.getMessage(), ex);
                }
            });
            app.filter((c, chain) -> {
                String path = c.path();
                while (path.contains("//")) {
                    path = path.replace("//", "/");
                }
                c.pathNew(path);
                chain.doFilter(c);
            });

            WoodConfig.isUsingValueExpression = false;
            if (Solon.cfg().isDebugMode()) {
                // 执行后打印下sql
                WoodConfig.onExecuteAft(cmd -> {
                    System.out.println(cmd.text + "\r\n" + ONode.serialize(cmd.paramMap()));
                });

                WoodConfig.onException((cmd, err) -> {
                    System.out.println(cmd.text + "\r\n" + ONode.serialize(cmd.paramMap()));
                });
            }
        });
    }
}
