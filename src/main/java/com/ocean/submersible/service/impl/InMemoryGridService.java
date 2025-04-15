package com.ocean.submersible.service.impl;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.service.IGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Profile("local")
public class InMemoryGridService implements IGridService {

    private final Map<Long, Grid> gridMap = new HashMap<>();
    private Long gridId = 0L;
    private Long obstacleId = 0L;

    @Override
    public Grid createGrid(int width, int height) {
        Grid grid = Grid.builder()
                .id(gridId++)
                .height(height)
                .width(width)
                .build();
        return gridMap.put(gridId, grid);
    }

    @Override
    public Obstacle addObstacle(Long gridId, int x, int y) {
        Grid grid = getGrid(gridId);

        boolean validObstacle = isValidObstacle(grid, x, y);
        if (!validObstacle) {
            throw new RuntimeException("Obstacle not within grid boundaries");
        }

        Obstacle obstacle = Obstacle.builder()
                .id(obstacleId++)
                .x(x)
                .y(y)
                .grid(grid)
                .build();
        grid.getObstacles().add(obstacle);
        return obstacle;
    }


    @Override
    public List<Obstacle> getObstacles(Long gridId) {
        Grid grid = getGrid(gridId);
        return grid.getObstacles();
    }

    @Override
    public Grid getGrid(Long gridId) {
        return gridMap.get(gridId);
    }

    @Override
    public Grid updateGrid(Grid updatedGrid) {
        gridMap.replace(updatedGrid.getId(), updatedGrid);
        return updatedGrid;
    }

    private boolean isValidObstacle(Grid grid, int x, int y) {
        int width = grid.getWidth();
        int height = grid.getHeight();

        int maxX = width / 2;
        int minX = -maxX;
        int maxY = height / 2;
        int minY = -maxY;

        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
