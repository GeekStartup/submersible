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
        return null;
    }

    @Override
    public Probe moveBackward(Long probeId) {
        return null;
    }

    @Override
    public Probe turnLeft(Long probeId) {
        return null;
    }

    @Override
    public Probe turnRight(Long probeId) {
        return null;
    }


}
