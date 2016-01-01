package settings;

import com.intellij.openapi.options.BeanConfigurable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.ui.Gray;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.panels.HorizontalBox;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Dennis.Ushakov
 */
public class AngularTemplatesSettingsPage extends BeanConfigurable<AngularTemplatesSettings> implements Configurable,
        Configurable.NoScroll
{
  protected AngularTemplatesSettingsPage() {
    super(AngularTemplatesSettings.getInstance());
    checkBox("reportUsageStatistics", "Allow sending anonymous usage statistics");
  }

  @Override
  public JComponent createComponent() {
    JComponent parent = super.createComponent();
    assert parent != null;
    JComponent result = new JPanel(new VerticalLayout());

    HorizontalBox labelContainer = new HorizontalBox();
    labelContainer.setMinimumSize(new Dimension(200,100));
    JLabel label = new JLabel("Angular Templates sends anonymous template usage statistics. ");
    result.add(label);
    labelContainer.add(new JLabel("This data is limited to template names you invoke. It will be shown at "));
    JXHyperlink link = new JXHyperlink();
    try {
      link.setURI(new URI("https://github.com/alirezamirian/Angular-Templates"));
      link.setText("the github repository");
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    labelContainer.add(link);
    labelContainer.add(new JLabel("."));
    result.add(labelContainer);
    result.add(parent);

    return result;
  }


  @Nls
  @Override
  public String getDisplayName() {
    return "Angular Templates";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }
}