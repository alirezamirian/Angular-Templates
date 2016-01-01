package settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(
        name = "AngularTemplatesSettings",
        storages = {
                @Storage(
                        file = StoragePathMacros.APP_CONFIG + "/angularTemplates.xml"
                )}
)
public class AngularTemplatesSettings implements PersistentStateComponent<AngularTemplatesSettings> {
    public boolean reportUsageStatistics = true;
    public boolean shouldNotifyUser = true;


    public static AngularTemplatesSettings getInstance() {
        return ServiceManager.getService(AngularTemplatesSettings.class);
    }

    public AngularTemplatesSettings getState() {
        return this;
    }

    public void loadState(AngularTemplatesSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}