package Components;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<Integer> pathNodes;
    boolean satisfied;

    public Path() {
        satisfied = true;
        pathNodes = new ArrayList<>();
    }

    public Path(int start) {
        satisfied = true;
        pathNodes = new ArrayList<>();
        pathNodes.add(start);
    }

    public Path(List<Integer> path, int end) {
        satisfied = true;
        pathNodes = new ArrayList<>(path);
        pathNodes.add(end);

    }

    public Path(List<Integer> path) {
        satisfied = true;
        pathNodes = new ArrayList<>(path);
    }

    public List<Integer> getPathNodes() {
        return pathNodes;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public List<Integer> addNode(Integer node) {
        pathNodes.add(node);
        return pathNodes;

    }

    public List<Integer> removeNode(Integer node) {
        pathNodes.remove(node);
        return pathNodes;
    }

    public boolean addPathNodes(Path path) {
        if (path == null) return false;

        return pathNodes.addAll(path.getPathNodes());
    }

    public boolean setPathNodes(Path path) {
        if (path == null) return false;

        pathNodes = new ArrayList<>(path.getPathNodes());
        return true;
    }


}