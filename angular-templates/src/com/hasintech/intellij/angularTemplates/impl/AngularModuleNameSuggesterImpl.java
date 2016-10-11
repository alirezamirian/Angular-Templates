package com.hasintech.intellij.angularTemplates.impl;

import com.hasintech.intellij.angularTemplates.AngularModuleNameSuggester;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.ide.PowerSaveMode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.ID;

import java.util.*;

/**
 * Created by alireza on 12/29/2015.
 */
public class AngularModuleNameSuggesterImpl  implements AngularModuleNameSuggester{
    private Logger logger = Logger.getInstance(AngularModuleNameSuggesterImpl.class);
    private int MAX_NUMBER_OF_RESULTS = 10;
    private static AngularModuleNameSuggester instance;

    public static AngularModuleNameSuggester getInstance(){
        if(instance == null){
            instance = new AngularModuleNameSuggesterImpl();
        }
        return instance;
    }
    @Override
    public LookupElement[] suggest(Project project, Editor editor) {

        if(PowerSaveMode.isEnabled()){
            return LookupElement.EMPTY_ARRAY;
        }
        final Set<LookupElement> suggestions = new LinkedHashSet<>();

        final StubIndex stubIndex = StubIndex.getInstance();
        final FileBasedIndex fileIndex = FileBasedIndex.getInstance();
        final ID<String, ?> angularModulesIndexId = StubIndexKey.findByName("angularjs.module.index");
        if(angularModulesIndexId == null){
            // AngularJS plugin is not installed and so there is no index for angular modules. So we give up in
            // suggesting module names
            return LookupElement.EMPTY_ARRAY;
        }

        if(project == null){
            // We will not suggest all indexed modules in the world if macro is not used in context of a project
            return LookupElement.EMPTY_ARRAY;
        }

        final Document document = editor.getDocument();
        final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        final VirtualFile currentFile = psiFile == null  ? null : psiFile.getVirtualFile();
//        final TextRange range = TemplateManagerImpl.getTemplateState(editor).getCurrentVariableRange();
//        final String currentText = editor.getDocument().getText(range);



        try {

            // Retrieve all keys inside angulajs.module index
            Collection<String> allKeys =
                    angularModulesIndexId instanceof StubIndexKey ?
                            stubIndex.getAllKeys((StubIndexKey<String, ?>)angularModulesIndexId, project) :
                            fileIndex.getAllKeys(angularModulesIndexId, project);

            // Create a mapping from module names (which are inside current project BTW), to their distance from
            // currently editing file. If the currently editing document doesn't belong to any file, these scores will
            // be zero for all module names
            final HashMap<String, Integer> moduleNamesToDistance = new HashMap<>();
            final HashMap<String, VirtualFile> moduleNamesToFile = new HashMap<>();

            // Get a hold of project scope and path
            GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
            String projectPath = project.getBaseDir().getCanonicalPath();

            // Iterate ver all keys (module names) inside index. For each module name, find files inside project which
            // contains that module name. If there are containing files, add (update) module name score in the module
            // score hash map, based on closeness of containing file and currently editing file
            for (String key : allKeys) {
                if(key.isEmpty()){
                    continue;
                }
                final Collection<VirtualFile> containingFiles = new HashSet<>();
                try{
                    // try file index
                    containingFiles.addAll(fileIndex.getContainingFiles(angularModulesIndexId, key, projectScope));
                } catch(Exception e){
                    final Collection<PsiElement> elements = stubIndex.getElements(
                            (StubIndexKey<String, PsiElement>) angularModulesIndexId, key, project, projectScope,
                            PsiElement.class);
                    for (PsiElement element : elements) {
                        containingFiles.add(element.getContainingFile().getVirtualFile());
                    }
                }

                if(containingFiles.size() == 0){
                    // if module name is not used inside any file in current project, we should not suggest it!
                    continue;
                }
                int distance = Integer.MAX_VALUE;
                // If macro is used inside a file, score module name based on closeness of this file and closest
                // containing file
                if(currentFile != null){
                    String currentPath = currentFile.getParent().getCanonicalPath();
                    // I wish I had functional Collection.map here!
                    for (VirtualFile containingFile : containingFiles) {
                        String containingFilePath = containingFile.getParent().getCanonicalPath();
                        int newDistance = getPathesDistance(currentPath, containingFilePath, projectPath);
                        if(newDistance < distance){
                            moduleNamesToFile.put(key, containingFile);
                            distance = newDistance;
                        }
                    }
                }
                // Add module name and its distance to map
                moduleNamesToDistance.put(key, distance);

            }
            // Sort module names based on their distance score
            String[] moduleNames = sortModuleNames(moduleNamesToDistance);

            // Add module names to suggestion list
            for (String moduleName : moduleNames) {
                if(suggestions.size() >= MAX_NUMBER_OF_RESULTS)
                    break;
                suggestions.add(LookupElementBuilder.create(moduleNamesToFile.get(moduleName), moduleName)
                        .withIcon(IconLoader.getIcon("/icons/AngularJS.png"))
                        .withTypeText(moduleNamesToFile.get(moduleName).getPresentableName()));
            }

            return suggestions.toArray(new LookupElement[suggestions.size()]);
        } catch (Throwable e) {
            logger.info("something went wrong in module name suggestion!");
            logger.info(e.getMessage());
            return LookupElement.EMPTY_ARRAY;
        }
    }

    private String[] sortModuleNames(final HashMap<String, Integer> moduleNamesToDistance) {
        Object[] objects = moduleNamesToDistance.keySet().toArray();
        String[] moduleNames = Arrays.asList(objects).toArray(new String[objects.length]);
        Arrays.sort(moduleNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return moduleNamesToDistance.get(o1).compareTo(moduleNamesToDistance.get(o2));
            }
        });
        return moduleNames;
    }


    private int getPathesDistance(String path1, String path2, String base){
        path1 = path1.replace(base,"");
        path2 = path2.replace(base,"");

        String[] pathSegments = path1.split("/");
        String[] pathSegments2 = path2.split("/");

        int index = 0;
        while(index<Math.min(pathSegments.length, pathSegments2.length) &&
                pathSegments[index].equals(pathSegments2[index])){
            index++;
        }
        return pathSegments.length - index;
    }
}
