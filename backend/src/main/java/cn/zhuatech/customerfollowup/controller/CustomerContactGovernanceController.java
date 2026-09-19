/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup.controller;
import cn.zhuatech.customerfollowup.service.CustomerContactGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/customer-followup")
public class CustomerContactGovernanceController {
    private final CustomerContactGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CustomerContactGovernanceController(CustomerContactGovernanceService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/contact-governance")
    public CustomerContactGovernanceService.Result evaluate(@Valid @RequestBody CustomerContactGovernanceService.Request request) {
        return service.evaluate(request);
    }
}
