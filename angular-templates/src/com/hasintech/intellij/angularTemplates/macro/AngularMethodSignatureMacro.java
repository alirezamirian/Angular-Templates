package com.hasintech.intellij.angularTemplates.macro;

import com.hasintech.intellij.angularTemplates.AngularDirectiveFunction;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alireza Mirian (mirian@hasintech.com)
 * @since 1.0 (29/08/2015, 3:24 PM)
 */
public class AngularMethodSignatureMacro extends MacroBase {
    public AngularMethodSignatureMacro() {
        super("AngularMethodSignatureMacro", "angularMethodSignatureMacro(methodName)");
    }


    @Nullable
    @Override
    protected Result calculateResult(Expression[] expressions, ExpressionContext expressionContext, boolean b) {
        if(expressions.length != 1)
            return null;
        Result methodNameExp = expressions[0].calculateResult(expressionContext);

        if(methodNameExp == null)
            return null;

        AngularDirectiveFunction functionName;
        try{
            functionName = AngularDirectiveFunction.valueOf(methodNameExp.toString().toUpperCase());
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
        switch(functionName){
            case LINK:
                return new TextResult("scope, elem, attrs");
            case COMPILE:
                return new TextResult("tElem, tAttrs");
        }
        return null;
    }
}
