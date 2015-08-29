package com.hasintech.intellij.angularTemplates;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alireza Mirian (mirian@hasintech.com)
 * @since 1.0 (15/07/2015, 4:21 PM)
 */
public class AngularLiveTemplateProvider implements DefaultLiveTemplatesProvider {
    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return new String[] {"liveTemplates/Angular_Templates"};
    }

    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return new String[0];
    }
}
