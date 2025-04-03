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
        return null;
    }

    @Override
    public Obstacle addObstacle(Long gridId, int x, int y) {
        return null;
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
