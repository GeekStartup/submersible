package com.ocean.submersible.controller;


import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.service.IGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/grid")
public class GridController {

    private final IGridService gridService;

    @PostMapping("/create")
    public Grid createGrid(@RequestParam int width, @RequestParam int height) {
        return gridService.createGrid(width, height);
    }

    @PostMapping("/{gridId}/addObstacle")
    public Obstacle addObstacle(@PathVariable Long gridId, @RequestParam int x, @RequestParam int y) {
        return gridService.addObstacle(gridId, x, y);
    }

    @GetMapping("/{gridId}")
    public Grid getGrid(@PathVariable Long gridId) {
        return gridService.getGrid(gridId);
    }

    @GetMapping("/{gridId}/obstacles")
    public List<Obstacle> getObstacles(@PathVariable Long gridId) {
        return gridService.getObstacles(gridId);
    }
}
