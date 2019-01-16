import com.intellij.codeInsight.template.postfix.templates.SoutPostfixTemplate;
import com.intellij.execution.*;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;


public class ExecutionSound implements ProjectComponent {

    public ExecutionSound(@NotNull Project project) {
        MessageBusConnection busConnection = project.getMessageBus().connect();
        busConnection.subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            @Override
            public void processTerminated(
                    @NotNull String executorId, @NotNull ExecutionEnvironment env,
                    @NotNull ProcessHandler handler, int exitCode
            ) {
                PluginSettings settings = ServiceManager.getService(PluginSettings.class);
                if (exitCode == 0) {
                    if (settings.playSuccessSound)
                        UIUtil.playSoundFromResource("/success.wav");
                }
                else
                    if (settings.playFailedSound)
                        UIUtil.playSoundFromResource("/failed.wav");
            }
        });
    }

}
