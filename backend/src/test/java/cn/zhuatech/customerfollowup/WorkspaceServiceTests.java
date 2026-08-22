/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.customerfollowup;

import cn.zhuatech.customerfollowup.service.WorkspaceService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceServiceTests {
    private final WorkspaceService service = new WorkspaceService();

    @Test
    void returnsReviewableDomainResult() {
        var request = new WorkspaceService.RunRequest("海岳设备集团", "二期项目商机跟进", 75, true, "演示上下文");
        var result = service.run(request);
        assertThat(result.status()).isEqualTo("REVIEW_READY");
        assertThat(result.executionMode()).isEqualTo("LOCAL_DEMO_PIPELINE");
        assertThat(result.insights()).hasSize(3);
        assertThat(result.actions()).hasSize(3);
        assertThat(result.providerPayload()).containsEntry("provider", "deepseek-compatible");
    }

    @Test
    void blocksDirectAutomationWithoutHumanReview() {
        var request = new WorkspaceService.RunRequest("海岳设备集团", "二期项目商机跟进", 60, false, "");
        var result = service.run(request);
        assertThat(result.status()).isEqualTo("HUMAN_REVIEW_REQUIRED");
        assertThat(result.warnings()).hasSizeGreaterThanOrEqualTo(2);
    }
}
