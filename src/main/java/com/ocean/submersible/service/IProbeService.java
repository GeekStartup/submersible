package com.ocean.submersible.service;


import com.ocean.submersible.entities.Probe;
import com.ocean.submersible.enums.Direction;

public interface IProbeService {

    Probe createProbe(Long gridId, int x, int y, Direction facingDirection);

    Probe getProbe(Long probeId);

    Probe moveForward(Long probeId);

    Probe moveBackward(Long probeId);

    Probe turnLeft(Long probeId);

    Probe turnRight(Long probeId);
}
