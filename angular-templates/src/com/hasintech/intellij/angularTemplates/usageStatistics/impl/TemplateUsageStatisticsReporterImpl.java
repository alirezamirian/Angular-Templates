package com.hasintech.intellij.angularTemplates.usageStatistics.impl;

import com.google.gson.Gson;
import com.hasintech.intellij.angularTemplates.usageStatistics.TemplateUsageStatisticReporter;
import com.hasintech.intellij.angularTemplates.usageStatistics.UsageModel;
import com.intellij.codeInsight.template.Template;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;

import java.util.Date;

/**
 * Created by alireza on 12/13/2015.
 */
public class TemplateUsageStatisticsReporterImpl implements TemplateUsageStatisticReporter {
    private Logger logger = Logger.getInstance(TemplateUsageStatisticReporter.class);


    @Override
    public void reportUsage(Template template) {
        try{

            // Create usage model
            final UsageModel usageModel = new UsageModel();
            PluginId pluginId = PluginId.getId("com.hasintech.intellij.angularTemplates");
            usageModel.setIde(ApplicationInfo.getInstance().getVersionName());
            usageModel.setTemplateName(template.getKey());
            usageModel.setTimestamp(new Date().getTime());
            usageModel.setVersion(ApplicationInfo.getInstance().getFullVersion());
            usageModel.setPluginVersion(PluginManager.getPlugin(pluginId).getVersion());


            // Serialize it
            Gson gson= new Gson();
            StringEntity  postingString =new StringEntity(gson.toJson(usageModel));//convert your pojo to   json

            String url = "http://angular-templates.herokuapp.com/v1/usages";

            // Send request asynchronously
            Async async = Async.newInstance();
            Request post = Request.Post(url);
            post.body(postingString);
            post.setHeader("Content-type", "application/json");
            async.execute(post, new FutureCallback<Content>() {
                @Override
                public void completed(Content content) {
                    logger.info("Angular template usage statistics reported successfully");
                }

                @Override
                public void failed(Exception e) {
                    logger.info("error in reporting usage statistics: " + e.getMessage());
                }

                @Override
                public void cancelled() {

                }
            });

        }
        catch(Exception exception){
            logger.info("error in reporting usage statistics: " + exception.getMessage());
        }
    }
}
