package com.hasintech.intellij.angularTemplates;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.BalloonLayout;
import com.intellij.ui.components.panels.HorizontalBox;
import com.intellij.ui.components.panels.VerticalBox;
import org.jdesktop.swingx.JXHyperlink;
import settings.AngularTemplatesSettings;
import settings.AngularTemplatesSettingsPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alireza on 1/1/2016.
 */
public class PostStartup implements StartupActivity {

    @Override
    public void runActivity(Project project) {
        AngularTemplatesSettings settings = AngularTemplatesSettings.getInstance();
        if(settings.shouldNotifyUser){
            notifyUserAboutUsageStatisticReport(project);
            settings.shouldNotifyUser = false;
        }
    }

    private void notifyUserAboutUsageStatisticReport(final Project project){
        HorizontalBox row1 = new HorizontalBox();
        HorizontalBox row2 = new HorizontalBox();
        row1.add(new JLabel("Angular Templates sends anonymous usage statistics by default."));
        row2.add(new JLabel("You can always "));
        JXHyperlink link = new JXHyperlink();
        link.setText("change it");
        link.setClickedColor(link.getUnclickedColor());
        link.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowSettingsUtil.getInstance().showSettingsDialog(project,
                        AngularTemplatesSettingsPage.class);
            }
        });
        row2.add(link);

        VerticalBox content = new VerticalBox();
        content.add(row1);
        content.add(row2);
        final Balloon balloon = JBPopupFactory.getInstance()
                .createBalloonBuilder(content)
                .setShowCallout(true)
                .setBorderInsets(new Insets(5, 10, 5, 10))
                .setHideOnAction(true)
                .setFadeoutTime(6000)
                .setCloseButtonEnabled(true)
                .setDialogMode(true)
                .createBalloon();
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = WindowManager.getInstance().getFrame(project);
                if (window == null) {
                    window = JOptionPane.getRootFrame();
                }
                if (window instanceof IdeFrame) {
                    BalloonLayout layout = ((IdeFrame) window).getBalloonLayout();
                    if (layout != null) {
                        layout.add(balloon);
                    }
                }
            }
        });
    }
}
