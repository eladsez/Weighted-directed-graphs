package classes.graphIterators;

import classes.DirectedWeightedGraph;
import classes.NodeData;
import java.util.Iterator;

public class NodeIterator implements Iterator<NodeData> {

    DirectedWeightedGraph graph;
    int MCheck;
    Iterator<api.NodeData> iter;

    public NodeIterator(DirectedWeightedGraph g) {
        this.graph = g;
        this.MCheck = g.getMC();
        this.iter = g.getNodeColl().iterator();
    }

    @Override
    public boolean hasNext() {
        if (this.MCheck != graph.getMC())
            throw new RuntimeException();

        return iter.hasNext();
    }

    @Override
    public NodeData next() {
        if (this.MCheck != graph.getMC())
            throw new RuntimeException();

        return (NodeData) iter.next();
    }
}
