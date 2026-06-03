package com.dyrnq.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.exception.StatusException;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.core.handle.Result;
import org.noear.solon.validation.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(index = 0) // index 为顺序位（不加，则默认为0）
public class AppExceptionFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(AppExceptionFilter.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(ctx);
        } catch (ValidatorException e) {
            log.error(e.getMessage(), e);
            ctx.render(Result.failure(e.getCode(), e.getMessage())); // e.getResult().getDescription()
        } catch (StatusException e) {
            log.error(e.getMessage(), e);
            if (e.getCode() == 404) {
                ctx.status(e.getCode());
            } else {
                String msg = e.getMessage();
                if (ExceptionUtil.getRootCause(e) != null
                        && ExceptionUtil.getRootCause(e).getMessage() != null) {
                    msg = ExceptionUtil.getRootCause(e).getMessage();
                }
                ctx.render(Result.failure(e.getCode(), msg));
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            ctx.render(Result.failure(500, "服务端运行出错"));
        }
    }
}
