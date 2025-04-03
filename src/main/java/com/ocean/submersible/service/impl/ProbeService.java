package com.ocean.submersible.service.impl;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Probe;
import com.ocean.submersible.enums.Direction;
import com.ocean.submersible.repositories.GridRepository;
import com.ocean.submersible.repositories.ProbeRepository;
import com.ocean.submersible.service.IProbeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProbeService implements IProbeService {

    private final ProbeRepository probeRepository;
    private final GridRepository gridRepository;

    @Override
    public Probe createProbe(Long gridId, int x, int y, Direction facingDirection) {
        Grid grid = gridRepository.findById(gridId)
                .orElseThrow(() -> new RuntimeException("Grid not found"));
        Probe probe = Probe.builder()
                .x(x)
                .y(y)
                .facingDirection(facingDirection)
                .grid(grid)
                .build();
        grid.setProbe(probe);
        return probeRepository.save(probe);
    }

    @Override
    public Probe getProbe(Long probeId) {
        return probeRepository.findById(probeId)
                .orElseThrow(() -> new RuntimeException("Probe not found"));
    }

    @Override
    public Probe moveForward(Long probeId) {
        Probe probe = getProbe(probeId);
        int newX = probe.getX();
        int newY = probe.getY();

        switch (probe.getFacingDirection()) {
            case NORTH:
                newY++;
                break;
            case EAST:
                newX++;
                break;
            case SOUTH:
                newY--;
                break;
            case WEST:
                newX--;
                break;
        }

        if (!isValidMovement(probe.getGrid(), newX, newY)) {
            throw new RuntimeException("Movement is not within grid boundaries");
        }

        if (isObstaclePresent(probe.getGrid(), newX, newY)) {
            throw new RuntimeException("Movement not possible due to obstacles");
        }

        probe.setX(newX);
        probe.setY(newY);
        addVisitedCoordinate(probe);
        return probeRepository.save(probe);
    }

    @Override
    public Probe moveBackward(Long probeId) {
        Probe probe = getProbe(probeId);
        int newX = probe.getX();
        int newY = probe.getY();

        switch (probe.getFacingDirection()) {
            case NORTH:
                newY--;
                break;
            case EAST:
                newX--;
                break;
            case SOUTH:
                newY++;
                break;
            case WEST:
                newX++;
                break;
        }

        if (!isValidMovement(probe.getGrid(), newX, newY)) {
            throw new RuntimeException("Movement is not within grid boundaries");
        }

        if (isObstaclePresent(probe.getGrid(), newX, newY)) {
            throw new RuntimeException("Movement not possible due to obstacles");
        }
        probe.setX(newX);
        probe.setY(newY);
        addVisitedCoordinate(probe);
        return probeRepository.save(probe);
    }

    @Override
    public Probe turnLeft(Long probeId) {
        Probe probe = getProbe(probeId);
        switch (probe.getFacingDirection()) {
            case NORTH:
                probe.setFacingDirection(Direction.WEST);
                break;
            case EAST:
                probe.setFacingDirection(Direction.NORTH);
                break;
            case SOUTH:
                probe.setFacingDirection(Direction.EAST);
                break;
            case WEST:
                probe.setFacingDirection(Direction.SOUTH);
                break;
        }
        return probeRepository.save(probe);
    }

    @Override
    public Probe turnRight(Long probeId) {
        Probe probe = getProbe(probeId);
        switch (probe.getFacingDirection()) {
            case NORTH:
                probe.setFacingDirection(Direction.EAST);
                break;
            case EAST:
                probe.setFacingDirection(Direction.SOUTH);
                break;
            case SOUTH:
                probe.setFacingDirection(Direction.WEST);
                break;
            case WEST:
                probe.setFacingDirection(Direction.NORTH);
                break;
        }
        return probeRepository.save(probe);
    }

    private boolean isValidMovement(Grid grid, int x, int y) {
        int width = grid.getWidth();
        int height = grid.getHeight();

        int maxX = width / 2;
        int minX = -maxX;
        int maxY = height / 2;
        int minY = -maxY;

        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    private boolean isObstaclePresent(Grid grid, int x, int y) {
        return grid.getObstacles().stream()
                .anyMatch(obstacle -> obstacle.getX() == x && obstacle.getY() == y);
    }

    private void addVisitedCoordinate(Probe probe) {
        probe.getVisitedCoordinates().add(probe.getX() + "," + probe.getY());
    }


}
