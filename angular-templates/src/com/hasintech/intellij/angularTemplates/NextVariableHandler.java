package com.hasintech.intellij.angularTemplates;

import com.hasintech.intellij.angularTemplates.usageStatistics.TemplateUsageStatisticReporter;
import com.hasintech.intellij.angularTemplates.usageStatistics.impl.TemplateUsageStatisticsReporterImpl;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.codeInsight.template.impl.TemplateState;
import com.intellij.ide.browsers.firefox.FirefoxSettings;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alireza on 12/4/2015.
 */
public class NextVariableHandler extends EditorActionHandler {
    private Logger logger = Logger.getInstance(EditorActionHandler.class);
    private static TemplateState lastReportedTemplate = null;
    private static TemplateUsageStatisticReporter usageStatisticReporter = new TemplateUsageStatisticsReporterImpl();

    private final EditorWriteActionHandler myOriginalHandler;

    public NextVariableHandler(EditorWriteActionHandler originalHandler) {
        super(true);
        myOriginalHandler = originalHandler;
    }

    @Override
    protected void doExecute(Editor editor, Caret caret, DataContext dataContext) {
        TemplateManager templateManager = TemplateManager.getInstance(editor.getProject());
        logger.info(templateManager.toString());

        TemplateState templateState = TemplateManagerImpl.getTemplateState(editor);
        if(lastReportedTemplate != templateState){
            // report actually
            logger.info("Templates reporting usage ...");
            usageStatisticReporter.reportUsage(templateState.getTemplate());
            logger.info(templateState.toString());
            lastReportedTemplate = templateState;
        }
        else{
            logger.info("Templates usage already reported");
        }
        logger.info(templateState.toString());
        myOriginalHandler.execute(editor, caret, dataContext);
    }
    @Override
    protected boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext dataContext) {
        if(myOriginalHandler != null)
            return myOriginalHandler.isEnabled(editor, caret, dataContext);
        return false;
    }
}
