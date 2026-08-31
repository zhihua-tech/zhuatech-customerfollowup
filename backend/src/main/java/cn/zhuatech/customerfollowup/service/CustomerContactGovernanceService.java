/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup.service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class CustomerContactGovernanceService {
    public Result evaluate(Request request) {
        List<String> controls = new ArrayList<>();
        if (!request.consentValid()) controls.add("客户触达授权无效");
        if (request.doNotContact()) controls.add("客户已设置禁止联系");
        if (!request.ownerAssigned()) controls.add("客户跟进责任人未分配");
        if (request.openComplaint()) controls.add("存在未关闭投诉，需由客户成功负责人审核");
        if (request.daysSinceLastContact() < request.minimumIntervalDays()) controls.add("未达到最小触达间隔");
        String decision = !request.consentValid() || request.doNotContact() ? "BLOCKED"
                : request.openComplaint() || !request.ownerAssigned() ? "REVIEW"
                : request.daysSinceLastContact() > request.maximumGapDays() ? "OVERDUE" : controls.isEmpty() ? "READY" : "WAIT";
        return new Result(request.customerId(), decision, List.copyOf(controls),
                request.daysSinceLastContact() > request.maximumGapDays(), "READY".equals(decision) || "OVERDUE".equals(decision));
    }
    public record Request(@NotBlank String customerId, boolean consentValid, boolean doNotContact,
                          boolean ownerAssigned, boolean openComplaint, @Min(0) int daysSinceLastContact,
                          @Min(0) int minimumIntervalDays, @Min(1) int maximumGapDays) {
        public Request {
            if (customerId == null || customerId.isBlank()) throw new IllegalArgumentException("customerId is required");
            if (daysSinceLastContact < 0 || minimumIntervalDays < 0 || maximumGapDays < 1 || minimumIntervalDays > maximumGapDays)
                throw new IllegalArgumentException("invalid contact cadence");
        }
    }
    public record Result(String customerId, String decision, List<String> controls,
                         boolean slaOverdue, boolean contactAllowed) {}
}
