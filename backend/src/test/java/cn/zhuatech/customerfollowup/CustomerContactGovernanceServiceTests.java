/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup;
import cn.zhuatech.customerfollowup.service.CustomerContactGovernanceService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CustomerContactGovernanceServiceTests {
    private final CustomerContactGovernanceService service = new CustomerContactGovernanceService();
    @Test void marksCompliantOverdueFollowupAsContactable() {
        var r = service.evaluate(new CustomerContactGovernanceService.Request("CUS-001", true, false, true, false, 20, 3, 14));
        assertEquals("OVERDUE", r.decision()); assertTrue(r.slaOverdue()); assertTrue(r.contactAllowed());
    }
    @Test void blocksCustomerWithoutConsent() {
        var r = service.evaluate(new CustomerContactGovernanceService.Request("CUS-002", false, true, true, false, 10, 3, 14));
        assertEquals("BLOCKED", r.decision()); assertEquals(2, r.controls().size()); assertFalse(r.contactAllowed());
    }
}
