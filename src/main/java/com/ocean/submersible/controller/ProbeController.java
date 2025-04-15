package com.ocean.submersible.controller;

import com.ocean.submersible.entities.Probe;
import com.ocean.submersible.enums.Direction;
import com.ocean.submersible.service.IProbeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probe")
@RequiredArgsConstructor
public class ProbeController {

    private final IProbeService probeService;

    @PostMapping("/create")
    public Probe createProbe(@RequestParam Long gridId, @RequestParam int x, @RequestParam int y, @RequestParam Direction facingDirection) {
        return probeService.createProbe(gridId, x, y, facingDirection);
    }

    @GetMapping("/{probeId}")
    public Probe getProbe(@PathVariable Long probeId) {
        return probeService.getProbe(probeId);
    }

    @PostMapping("/{probeId}/moveForward")
    public Probe moveForward(@PathVariable Long probeId) {
        return probeService.moveForward(probeId);
    }

    @PostMapping("/{probeId}/moveBackward")
    public Probe moveBackward(@PathVariable Long probeId) {
        return probeService.moveBackward(probeId);
    }

    @PostMapping("/{probeId}/turnLeft")
    public Probe turnLeft(@PathVariable Long probeId) {
        return probeService.turnLeft(probeId);
    }

    @PostMapping("/{probeId}/turnRight")
    public Probe turnRight(@PathVariable Long probeId) {
        return probeService.turnRight(probeId);
    }

}
