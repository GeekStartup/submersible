package com.ocean.submersible.service.impl;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.repositories.GridRepository;
import com.ocean.submersible.repositories.ObstacleRepository;
import com.ocean.submersible.service.IGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GridService implements IGridService {

    private final GridRepository gridRepository;
    private final ObstacleRepository obstacleRepository;

    @Override
    public Grid createGrid(int width, int height) {
        Grid grid = Grid.builder()
                .height(height)
                .width(width)
                .build();
        return gridRepository.save(grid);
    }

    @Override
    public Obstacle addObstacle(Long gridId, int x, int y) {
        Grid grid = gridRepository.findById(gridId)
                .orElseThrow(() -> new RuntimeException("Grid not found"));
        Obstacle obstacle = Obstacle.builder()
                .x(x)
                .y(y)
                .grid(grid)
                .build();
        return obstacleRepository.save(obstacle);
    }


    @Override
    public List<Obstacle> getObstacles(Long gridId) {
        return null;
    }

    @Override
    public Grid getGrid(Long gridId) {
        return null;
    }
}
