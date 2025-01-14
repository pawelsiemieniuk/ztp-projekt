package entities;

import java.util.List;


public interface IGhost {
    RedGhost createGhost(GhostColor color, Behavior behavior);
}
