package com.hasintech.intellij.angularTemplates;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alireza Mirian (mirian@hasintech.com)
 * @since 1.0 (29/08/2015, 3:24 PM)
 */
public class AngularFactoryNameMacro extends MacroBase {
    public AngularFactoryNameMacro() {
        super("angularFactory", "angularFactory(String componentType, String componentName)");
    }
    

    @Nullable
    @Override
    protected Result calculateResult(Expression[] expressions, ExpressionContext expressionContext, boolean b) {
        if(expressions.length<2)
            return null;
        Result componentTypeExp = expressions[0].calculateResult(expressionContext);
        Result componentNameExp = expressions[1].calculateResult(expressionContext);

        if(componentNameExp == null
                || componentTypeExp == null
                || componentTypeExp.toString().isEmpty()
                || componentNameExp.toString().isEmpty())
            return null;

        AngularComponentType componentType;
        try{
            componentType = AngularComponentType.valueOf(componentTypeExp.toString().toUpperCase());
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
        String componentName = componentTypeExp.toString();
        switch(componentType){
            case FILTER:
            case DIRECTIVE:
            case FACTORY:
            case PROVIDER:
                return new TextResult(componentName + capitalize(componentType.toString(), true));
            case CONTROLLER:
                return new TextResult(componentName);
            case SERVICE:
                return new TextResult(capitalize(componentName));

        }
        return null;
    }
    private String capitalize(String text, Boolean lowerCaseOthers){
        String restOfString = text.substring(1, text.length());
        return StringUtil.toUpperCase(text.substring(0, 1)) + (lowerCaseOthers ? restOfString.toLowerCase() : restOfString);
    }
    private String capitalize(String text){
        return capitalize(text, false);
    }
}
