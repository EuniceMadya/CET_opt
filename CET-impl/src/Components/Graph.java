package Components;

import java.util.List;

public interface Graph {

    List<Integer> getStartPoints();

    List<Integer> getEndPoints();

    List<Integer> getNeighbours(int i);

    int getNumVertex();

}
