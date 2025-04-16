package com.ocean.submersible.service;


import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;

import java.util.List;

public interface IGridService {

    Grid createGrid(int width, int height);

    Obstacle addObstacle(Long gridId, int x, int y);

    Grid getGrid(Long gridId);

    List<Obstacle> getObstacles(Long gridId);

    Grid updateGrid(Grid updatedGrid);
}
