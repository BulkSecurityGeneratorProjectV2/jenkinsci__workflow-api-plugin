package org.jenkinsci.plugins.workflow.actions;

import hudson.model.Result;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

/**
 * Action to be attached to a {@link FlowNode} to signify that some non-fatal event occurred
 * during execution of a {@code Step} but execution continued normally.
 *
 * {@link #withMessage} should be used whenever possible to give context to the warning.
 * Visualizations should treat FlowNodes with this action as if the FlowNode's result was {@link #result}.
 */
public class WarningAction implements PersistentAction {
    private @NonNull Result result;
    private @CheckForNull String message;

    public WarningAction(@NonNull Result result) {
        this.result = result;
    }

    public WarningAction withMessage(String message) {
        this.message = message;
        return this;
    }

    public @CheckForNull String getMessage() {
        return message;
    }

    public @NonNull Result getResult() {
        return result;
    }

    @Override
    public String getDisplayName() {
        return "Warning" + (message != null ? ": " + message : "");
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }
}
