import api.DirectedWeightedGraphAlgorithms;
import logicControl.DWGraphAlgo;

public class mainJar {
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DWGraphAlgo();
        ans.load(json_file);
        return ans;
    }

    public static void main(String[] args) {
        Ex2.runGUI(args[1]);
    }
}
