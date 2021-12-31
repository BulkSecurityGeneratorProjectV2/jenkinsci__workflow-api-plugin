package org.jenkinsci.plugins.workflow.graphanalysis;

import org.jenkinsci.plugins.workflow.graph.BlockEndNode;
import org.jenkinsci.plugins.workflow.graph.BlockStartNode;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Matches start and end of a block.  Any block!
 * @author Sam Van Oort
 */
public class BlockChunkFinder implements ChunkFinder {

    /** NOTE: you will need to handle cases where you have a {@link BlockStartNode} where the {@link BlockEndNode} has not been generated yet!
     *  This means you need to keep nodes around even after hitting the EndNode */
    @Override
    public boolean isStartInsideChunk() {
        return false;
    }

    @Override
    public boolean isChunkStart(@NonNull FlowNode current, @CheckForNull FlowNode previous) {
        return current instanceof BlockStartNode;
    }

    @Override
    public boolean isChunkEnd(@NonNull FlowNode current, @CheckForNull FlowNode previous) {
        return current instanceof BlockEndNode;
    }
}
