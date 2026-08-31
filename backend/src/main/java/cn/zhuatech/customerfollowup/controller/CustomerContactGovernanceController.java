/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup.controller;
import cn.zhuatech.customerfollowup.service.CustomerContactGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enterprise/customer-followup")
public class CustomerContactGovernanceController {
    private final CustomerContactGovernanceService service;
    public CustomerContactGovernanceController(CustomerContactGovernanceService service) { this.service = service; }
    @PostMapping("/contact-governance")
    public CustomerContactGovernanceService.Result evaluate(@Valid @RequestBody CustomerContactGovernanceService.Request request) {
        return service.evaluate(request);
    }
}
