import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "SaveSoundsSettings",
        storages = {@Storage(file = "savesounds_settings.xml")})
class PluginSettings implements PersistentStateComponent<PluginSettings> {

    public boolean playSuccessSound=true;
    public boolean playFailedSound=true;

    @Nullable
    @Override
    public PluginSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }


    void setConfig(boolean playSuccessSound, boolean playFailedSound) {
        this.playSuccessSound = playSuccessSound;
        this.playFailedSound = playFailedSound;
    }


}
