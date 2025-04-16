package com.ocean.submersible.service.impl;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.service.IGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Profile("local")
public class InMemoryGridService implements IGridService {

    private final Map<Long, Grid> gridMap = new HashMap<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private Long gridId = 1L;
    private Long obstacleId = 1L;

    @Override
    public Grid createGrid(int width, int height) {
        Grid grid = Grid.builder()
                .id(gridId)
                .height(height)
                .width(width)
                .build();
        gridMap.put(gridId, grid);
        gridId++;
        return grid;
    }

    @Override
    public Obstacle addObstacle(Long gridId, int x, int y) {
        Grid grid = getGrid(gridId);

        boolean validObstacle = isValidObstacle(grid, x, y);
        if (!validObstacle) {
            throw new RuntimeException("Obstacle not within grid boundaries");
        }

        Obstacle obstacle = Obstacle.builder()
                .id(obstacleId)
                .x(x)
                .y(y)
                .grid(grid)
                .build();
        obstacles.add(obstacle);
        grid.setObstacles(obstacles);
        gridMap.replace(grid.getId(), grid);
        obstacleId++;
        return obstacle;
    }


    @Override
    public List<Obstacle> getObstacles(Long gridId) {
        Grid grid = getGrid(gridId);
        return grid.getObstacles();
    }

    @Override
    public Grid getGrid(Long gridId) {
        return Optional.ofNullable(gridMap.get(gridId))
                .orElseThrow(() -> new RuntimeException("Grid not found"));
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
