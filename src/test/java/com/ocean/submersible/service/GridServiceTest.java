package com.ocean.submersible.service;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.repositories.GridRepository;
import com.ocean.submersible.repositories.ObstacleRepository;
import com.ocean.submersible.service.impl.GridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Automatically initializes mocks
public class GridServiceTest {

    @Mock
    private GridRepository gridRepository;

    @Mock
    private ObstacleRepository obstacleRepository;

    @InjectMocks
    private GridService gridService;

    private Grid mockGrid;
    private Obstacle mockObstacle;

    @BeforeEach
    void setUp() {
        mockGrid = Grid.builder()
                .id(1L)
                .width(10)
                .height(10)
                .build();
        mockObstacle = Obstacle.builder()
                .id(1L)
                .x(3)
                .y(4)
                .grid(mockGrid)
                .build();
    }

    @Test
    void testCreateGrid() {
        when(gridRepository.save(any(Grid.class))).thenReturn(mockGrid);
        Grid createdGrid = gridService.createGrid(10, 10);
        assertNotNull(createdGrid);
        assertEquals(10, createdGrid.getWidth());
        assertEquals(10, createdGrid.getHeight());
    }

    @Test
    void testAddObstacle() {
        when(gridRepository.findById(1L)).thenReturn(Optional.of(mockGrid));
        when(obstacleRepository.save(any(Obstacle.class))).thenReturn(mockObstacle);
        Obstacle createdObstacle = gridService.addObstacle(1L, 3, 4);
        assertNotNull(createdObstacle);
        assertEquals(3, createdObstacle.getX());
        assertEquals(4, createdObstacle.getY());
    }

    @Test
    void testAddObstacleOutsideGrid() {
        when(gridRepository.findById(1L)).thenReturn(Optional.of(mockGrid));
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () ->
                gridService.addObstacle(1L, 11, 4)
        );
        assertEquals("Obstacle not within grid boundaries", runtimeException.getMessage());
    }
}
