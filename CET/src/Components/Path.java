package Components;

import java.util.ArrayList;
import java.util.List;

public class Path{
    List<Integer> pathNodes;
    boolean satisfied;

    public Path(int start){
        satisfied = true;
        pathNodes = new ArrayList<>();
        pathNodes.add(start);
    }

    public List<Integer> getPathNodes() {
        return pathNodes;
    }

    public boolean isSatisfied(){
        return satisfied;
    }

    public void setSatisfied(boolean satisfied){
        this.satisfied = satisfied;
    }

    public void addNode(Integer node){
        pathNodes.add(node);

    }

    public void removeNode(Integer node){
        pathNodes.remove(node);
    }
}