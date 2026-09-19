/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup;
import cn.zhuatech.customerfollowup.service.CustomerContactGovernanceService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class CustomerContactGovernanceServiceTests {
    private final CustomerContactGovernanceService service = new CustomerContactGovernanceService();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void marksCompliantOverdueFollowupAsContactable() {
        var r = service.evaluate(new CustomerContactGovernanceService.Request("CUS-001", true, false, true, false, 20, 3, 14));
        assertEquals("OVERDUE", r.decision()); assertTrue(r.slaOverdue()); assertTrue(r.contactAllowed());
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksCustomerWithoutConsent() {
        var r = service.evaluate(new CustomerContactGovernanceService.Request("CUS-002", false, true, true, false, 10, 3, 14));
        assertEquals("BLOCKED", r.decision()); assertEquals(2, r.controls().size()); assertFalse(r.contactAllowed());
    }
}
