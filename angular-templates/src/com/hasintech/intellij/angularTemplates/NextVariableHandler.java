package com.hasintech.intellij.angularTemplates;

import com.hasintech.intellij.angularTemplates.usageStatistics.TemplateUsageStatisticReporter;
import com.hasintech.intellij.angularTemplates.usageStatistics.impl.TemplateUsageStatisticsReporterImpl;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.codeInsight.template.impl.TemplateState;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.NotNull;
import settings.AngularTemplatesSettings;

/**
 * Created by alireza on 12/4/2015.
 */
public class NextVariableHandler extends EditorActionHandler {
    private Logger logger = Logger.getInstance(EditorActionHandler.class);
    private static TemplateState lastReportedTemplate = null;
    private static TemplateUsageStatisticReporter usageStatisticReporter = new TemplateUsageStatisticsReporterImpl();

    private final EditorActionHandler myOriginalHandler;

    public NextVariableHandler(EditorActionHandler originalHandler) {
        super(true);
        myOriginalHandler = originalHandler;
    }

    @Override
    protected void doExecute(Editor editor, Caret caret, DataContext dataContext) {
        TemplateState templateState = TemplateManagerImpl.getTemplateState(editor);
        // We have nothing to do with people's template! so first check if its an AngularTemplates template
        if(templateState.getTemplate().getGroupName().equals("Angular Templates")){
            // Report usage statistics if applicable
            if(     AngularTemplatesSettings.getInstance().reportUsageStatistics &&
                    lastReportedTemplate != templateState){
                // report actually
                logger.info("Templates reporting usage ...");
                usageStatisticReporter.reportUsage(templateState.getTemplate());
                logger.info(templateState.toString());
                lastReportedTemplate = templateState;
            }
            // add module dependencies to template if module name is new
//            templateState.getTemplate().addVariable("alireza",new TextExpression("mirian"),true);
            /*TemplateImpl template = (TemplateImpl) TemplateManagerImpl.getInstance(editor.getProject())
                    .createTemplate("angularTemplates", "test");
            template.addVariable("var1", new TextExpression("value1"), true);
            templateState.start(template,null, null);*/
            /*if(templateState.getCurrentVariableNumber() == 0){
                templateState.getTemplate().removeVariable(1);
            }*/
        }

        myOriginalHandler.execute(editor, caret, dataContext);
    }
    @Override
    protected boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext dataContext) {
        if(myOriginalHandler != null)
            return myOriginalHandler.isEnabled(editor, caret, dataContext);
        return false;
    }
}
