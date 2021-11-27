package classes.graphIterators;

import classes.DirectedWeightedGraph;
import classes.EdgeData;
import java.util.Iterator;

public class EdgesIterator implements Iterator<EdgeData> {

    DirectedWeightedGraph graph;
    int MCheck;
    Iterator<api.EdgeData> iter;

    public EdgesIterator(DirectedWeightedGraph g) {
        this.graph = g;
        this.MCheck = g.getMC();
        this.iter = g.getEdgeColl().iterator();
    }

    @Override
    public boolean hasNext() {
        if (this.MCheck != graph.getMC())
            throw new RuntimeException();

        return iter.hasNext();
    }

    @Override
    public EdgeData next() {
        if (this.MCheck != graph.getMC())
            throw new RuntimeException();

        return (EdgeData) iter.next();
    }
}