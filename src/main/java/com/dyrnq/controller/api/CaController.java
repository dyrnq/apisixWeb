package com.dyrnq.controller.api;

import com.dyrnq.service.CertService;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

@Mapping("api/ca")
@Controller
public class CaController extends ApiController {

    @Inject
    CertService certService;
}
