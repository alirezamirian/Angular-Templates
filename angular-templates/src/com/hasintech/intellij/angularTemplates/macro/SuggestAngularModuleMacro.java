package com.hasintech.intellij.angularTemplates.macro;

import com.hasintech.intellij.angularTemplates.impl.AngularModuleNameSuggesterImpl;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alireza on 10/12/2015.
 */
public class SuggestAngularModuleMacro extends MacroBase {

    private Logger logger = Logger.getInstance("AngularTemplate");

    public SuggestAngularModuleMacro() {
        super("suggestAngularModuleName", "suggestAngularModuleName()");
    }

    @Nullable
    @Override
    protected Result calculateResult(@NotNull Expression[] params, ExpressionContext context, boolean b) {
        if (params.length == 0) return null;
        return params[0].calculateResult(context);
    }


    @Override
    public LookupElement[] calculateLookupItems(@NotNull Expression[] params, ExpressionContext context) {
        try{
            return AngularModuleNameSuggesterImpl.getInstance().suggest(context.getProject(), context.getEditor());
        } catch (Throwable e) {
            logger.info("something went wrong in module name suggestion!");
            logger.info(e.getMessage());
            return null;
        }
    }
}
