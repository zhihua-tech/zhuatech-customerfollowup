/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class WorkspaceService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public RunResult run(RunRequest request) {
        List<String> warnings = new ArrayList<>();
        if (!request.humanReview()) warnings.add("未启用人工复核，结果不能进入正式业务流程");
        if (request.confidenceFloor() < 70) warnings.add("置信度阈值低于建议值 70，需扩大人工抽检范围");
        if (request.context() == null || request.context().isBlank()) warnings.add("缺少补充上下文，本次仅按基础规则处理");

        List<Insight> insights = List.of(
            new Insight("事实", "最近两次互动均关注二期预算", 94),
            new Insight("关注", "业务负责人参与度稳定", 82),
            new Insight("边界", "财务决策人尚未进入沟通", 76)
        );
        List<Action> actions = List.of(
            new Action("明天发送二期收益测算", "业务负责人", "今天"),
            new Action("邀请财务决策人参加方案会", "审核人员", "本周"),
            new Action("七天后更新客户健康度", "系统管理员", "复核后")
        );
        Map<String, Object> providerPayload = new LinkedHashMap<>();
        providerPayload.put("subject", request.subject());
        providerPayload.put("scenario", request.scenario());
        providerPayload.put("context", request.context());
        providerPayload.put("confidenceFloor", request.confidenceFloor());
        providerPayload.put("provider", "deepseek-compatible");
        providerPayload.put("model", "deepseek-chat");

        String status = request.humanReview() ? "REVIEW_READY" : "HUMAN_REVIEW_REQUIRED";
        return new RunResult(status, "MEDIUM", "客户认可一期交付效果，但二期预算尚未确认。建议围绕可量化收益准备简版方案，并在预算会前完成关键人沟通。", insights, actions,
            List.copyOf(warnings), providerPayload, "LOCAL_DEMO_PIPELINE", OffsetDateTime.now());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record RunRequest(
        @NotBlank String subject,
        @NotBlank String scenario,
        @Min(0) @Max(100) int confidenceFloor,
        boolean humanReview,
        @Size(max = 1200) String context
    ) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Insight(String type, String content, int confidence) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Action(String task, String ownerRole, String dueHint) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record RunResult(String status, String riskLevel, String summary, List<Insight> insights,
                            List<Action> actions, List<String> warnings, Map<String, Object> providerPayload,
                            String executionMode, OffsetDateTime generatedAt) {}
}
