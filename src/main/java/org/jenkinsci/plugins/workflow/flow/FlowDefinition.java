/*
 * The MIT License
 *
 * Copyright (c) 2013-2014, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.flow;

import hudson.ExtensionPoint;
import hudson.Util;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Action;
import hudson.model.TaskListener;
import hudson.util.LogTaskListener;
import hudson.scm.SCM;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Actual executable script.
 *
 * @author Kohsuke Kawaguchi
 * @author Jesse Glick
 */
public abstract class FlowDefinition extends AbstractDescribableImpl<FlowDefinition> implements ExtensionPoint {
    /**
     * Starts a brand new execution of this definition from the beginning.
     *
     * @param actions
     *      Additional parameters to how
     */
    public /*abstract*/ FlowExecution create(FlowExecutionOwner handle, TaskListener listener, List<? extends Action> actions) throws Exception {
        if (Util.isOverridden(FlowDefinition.class, getClass(), "create", FlowExecutionOwner.class, List.class)) {
            return create(handle, actions);
        } else {
            throw new NoSuchMethodError();
        }
    }

    @Deprecated
    public FlowExecution create(FlowExecutionOwner handle, List<? extends Action> actions) throws IOException {
        if (Util.isOverridden(FlowDefinition.class, getClass(), "create", FlowExecutionOwner.class, TaskListener.class, List.class)) {
            try {
                return create(handle, new LogTaskListener(Logger.getLogger(FlowDefinition.class.getName()), Level.INFO), actions);
            } catch (IOException | RuntimeException x) {
                throw x;
            } catch (Exception x) {
                throw new IOException(x);
            }
        } else {
            throw new NoSuchMethodError();
        }
    }

    @Override public FlowDefinitionDescriptor getDescriptor() {
        return (FlowDefinitionDescriptor) super.getDescriptor();
    }

    /**
     * Returns a list of all {@link SCM SCMs} that are part of the static configuration of the {@link FlowDefinition}.
     * Subclasses of {@link FlowDefinition} may override this method to return statically configured SCMs 
     * that they may be aware of. For example, {@code CpsScmFlowDefinition} returns SCM used to retrieve 
     * Jenkinsfile. 
     * Does not include any SCMs used dynamically during Pipeline execution.
     * May be empty (or not overridden) if the Pipeline does not include any statically configured SCMs.
     * This method is used in {@code WorkflowJob} class which will combine lists of static and dynamic SCMs.
     */
    public Collection<? extends SCM> getSCMs() {
        return Collections.emptyList();
    }

}
