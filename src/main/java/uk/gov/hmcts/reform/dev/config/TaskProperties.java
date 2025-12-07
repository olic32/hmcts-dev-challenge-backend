package uk.gov.hmcts.reform.dev.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "task")
public class TaskProperties {

    private String defaultStatus;
    private int dueDaysOffset;

    public String getDefaultStatus() { return defaultStatus; }
    public void setDefaultStatus(String defaultStatus) { this.defaultStatus = defaultStatus; }

    public int getDueDaysOffset() { return dueDaysOffset; }
    public void setDueDaysOffset(int dueDaysOffset) { this.dueDaysOffset = dueDaysOffset; }
}
