package com.hasintech.intellij.angularTemplates;

import com.hasintech.intellij.angularTemplates.impl.AngularModuleNameSuggesterImpl;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.javascript.JSTokenTypes;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.lang.javascript.psi.JSArgumentList;
import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ModuleNameCompletionContribute is now deprecated as AngularJS plugin has it out of the box in its new versions.
 */
@Deprecated
public class ModuleNameCompletionContributor extends CompletionContributor {
    public ModuleNameCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(JSTokenTypes.STRING_LITERAL).withLanguage(JavascriptLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        if(isModuleNameStringLiteral(parameters.getPosition())){
                            Editor editor = parameters.getEditor();
                            try{
                                LookupElement[] suggestions = AngularModuleNameSuggesterImpl.getInstance().suggest(editor.getProject(), editor);
                                for (LookupElement suggestion : suggestions) {
                                    resultSet.addElement(suggestion);
                                }
                            }
                            catch(Throwable e){
                                e.printStackTrace();
                            }

                        }

                    }
                }
        );
    }
    private boolean isModuleNameStringLiteral(PsiElement psiElement){
        if(psiElement == null)
            return false;
        List<PsiElement> parents = new ArrayList<>();
        PsiElement item = psiElement.getParent();
        while(item != null && parents.size()<3){
            parents.add(item);
            item = item.getParent();
        }
        if(parents.size() < 3)
            return false;

        return  parents.get(0) instanceof JSLiteralExpression &&
                parents.get(1) instanceof  JSArgumentList && // string literal is used as argument
                parents.get(2) instanceof JSCallExpression && // string literal is used as argument of function call
                parents.get(1).findElementAt(1) == psiElement && // string literal is the first argument
                parents.get(2).getFirstChild().getText().equals("angular.module"); // invoked function is angular.module
    }
}