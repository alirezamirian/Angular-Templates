package com.hasintech.intellij.angularTemplates.usageStatistics;

import com.intellij.codeInsight.template.Template;

/**
 * Created by alireza on 12/13/2015.
 */
public interface TemplateUsageStatisticReporter {

    /**
     * Reports usage statistics of a template.
     * @return
     */
    void reportUsage(Template template);
}
