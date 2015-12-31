package com.hasintech.intellij.angularTemplates;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

/**
 * Created by alireza on 12/29/2015.
 */
public interface AngularModuleNameSuggester {
    LookupElement[] suggest(Project project, Editor editor);
}
