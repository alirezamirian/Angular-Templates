package com.hasintech.intellij.angularTemplates.usageStatistics;

import java.util.Date;

/**
 * Created by alireza on 12/13/2015.
 */
public class UsageModel {
    private String templateName;
    private Long timestamp;
    private String version;
    private String ide;
    private String ideVersion;
    private String pluginVersion;

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getIdeVersion() {
        return ideVersion;
    }

    public void setIdeVersion(String ideVersion) {
        this.ideVersion = ideVersion;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }
}
