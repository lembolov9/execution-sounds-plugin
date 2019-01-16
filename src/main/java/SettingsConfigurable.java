import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.VerticalFlowLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SettingsConfigurable implements Configurable {
    private SettingsPanel panel;
    private PluginSettings settings = ServiceManager.getService(PluginSettings.class);

    private class SettingsPanel extends JPanel {
        private boolean isModified = false;

        private JPanel controlPanel;
        JCheckBox playSuccessSound;
        JCheckBox playFailedSound;

        SettingsPanel() {
            initView();
        }

        private void apply() {
            settings.setConfig(playSuccessSound.isSelected(), playFailedSound.isSelected());

            isModified = false;
        }

        private void prepareGUI() {
            setLayout(new GridLayout(3, 1));

            controlPanel = new JPanel();
            controlPanel.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 10));

            add(controlPanel);
            setVisible(true);
        }

        private void initView() {
            prepareGUI();

            JLabel playSuccessSoundLabel = new JLabel("Play sound after success: ", JLabel.LEFT);
            JLabel playFailedSoundLabel = new JLabel("Play sound after fail: ", JLabel.LEFT);

            playSuccessSound = new JCheckBox();
            playSuccessSound.setSelected(settings.playSuccessSound);
            playSuccessSound.setSize(20, playSuccessSound.getHeight());

            playFailedSound = new JCheckBox();
            playFailedSound.setSelected(settings.playFailedSound);

            controlPanel.add(playSuccessSoundLabel);
            controlPanel.add(playSuccessSound);
            controlPanel.add(playFailedSoundLabel);
            controlPanel.add(playFailedSound);

            playSuccessSound.addChangeListener(e ->  isModified = true);

            playFailedSound.addChangeListener(e -> isModified = true);
        }
    }

    @Override
    public String getDisplayName() {
        return "Execution Sounds";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (panel == null) {
            panel = new SettingsPanel();

        }

        return panel;
    }

    @Override
    public boolean isModified() {
        return panel != null && panel.isModified;
    }

    @Override
    public void apply() {
        if (panel != null) {
            panel.apply();
        }
    }

    @Override
    public void reset() {
        panel.playFailedSound.setSelected(true);
        panel.playSuccessSound.setSelected(true);
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }
}