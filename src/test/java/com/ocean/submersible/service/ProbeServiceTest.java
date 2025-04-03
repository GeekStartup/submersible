package com.ocean.submersible.service;

import com.ocean.submersible.entities.Grid;
import com.ocean.submersible.entities.Obstacle;
import com.ocean.submersible.entities.Probe;
import com.ocean.submersible.repositories.GridRepository;
import com.ocean.submersible.repositories.ProbeRepository;
import com.ocean.submersible.service.impl.ProbeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.ocean.submersible.enums.Direction.NORTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProbeServiceTest {

    @Mock
    private ProbeRepository probeRepository;

    @Mock
    private GridRepository gridRepository;

    @InjectMocks
    private ProbeService probeService;

    private Grid mockGrid;
    private Probe mockProbe;

    @BeforeEach
    void setUp() {
        mockGrid = Grid.builder()
                .id(1L)
                .width(10)
                .height(10)
                .build();
        mockProbe = Probe.builder()
                .id(1L)
                .x(0)
                .y(0)
                .facingDirection(NORTH)
                .grid(mockGrid)
                .visitedCoordinates(new ArrayList<>())
                .build();
    }

    @Test
    void testCreateProbe() {
        when(gridRepository.findById(1L)).thenReturn(Optional.of(mockGrid));
        when(probeRepository.save(any(Probe.class))).thenReturn(mockProbe);
        Probe probe = probeService.createProbe(1L, 0, 0, NORTH);
        assertNotNull(probe);
        assertEquals(1L, probe.getId());
        assertEquals(0, probe.getX());
        assertEquals(0, probe.getY());
        assertEquals(NORTH, probe.getFacingDirection());
        assertEquals(mockGrid, probe.getGrid());
        verify(gridRepository, times(1)).findById(1L);
        verify(probeRepository, times(1)).save(any(Probe.class));
    }

    @Test
    void testGetProbe_ProbeFound() {
        when(probeRepository.findById(1L)).thenReturn(Optional.of(mockProbe));
        Probe probe = probeService.getProbe(1L);
        assertNotNull(probe);
        assertEquals(1L, probe.getId());
        verify(probeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProbe_ProbeNotFound() {
        when(probeRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> probeService.getProbe(1L));
        assertEquals("Probe not found", exception.getMessage());
        verify(probeRepository, times(1)).findById(1L);
    }

    @Test
    void testMoveForward_Success() {
        Obstacle obstacle = Obstacle.builder()
                .x(0)
                .y(2)
                .grid(mockGrid)
                .build();
        mockGrid.setObstacles(Collections.singletonList(obstacle));
        when(probeRepository.findById(mockProbe.getId())).thenReturn(Optional.of(mockProbe));
        when(probeRepository.save(any(Probe.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        Probe probe = probeService.moveForward(mockProbe.getId());
        assertEquals(0, probe.getX());
        assertEquals(1, probe.getY());
        assertEquals(1,probe.getVisitedCoordinates().size());
        verify(probeRepository, times(1)).save(probe);
    }

    @Test
    void testMoveForward_OutOfGridBounds() {
        mockProbe.setX(6);
        mockProbe.setY(6);
        when(probeRepository.findById(mockProbe.getId())).thenReturn(Optional.of(mockProbe));
        assertThrows(RuntimeException.class, () -> probeService.moveForward(mockProbe.getId()));
    }

    @Test
    void testMoveForward_ObstacleInTheWay() {
        Obstacle obstacle = Obstacle.builder()
                .x(0)
                .y(1)
                .grid(mockGrid)
                .build();
        mockGrid.setObstacles(Collections.singletonList(obstacle));
        when(probeRepository.findById(mockProbe.getId())).thenReturn(Optional.of(mockProbe));
        assertThrows(RuntimeException.class, () -> probeService.moveForward(mockProbe.getId()));
    }

    @Test
    void testMoveBackward_Success() {
        Obstacle obstacle = Obstacle.builder()
                .x(0)
                .y(-2)
                .grid(mockGrid)
                .build();
        mockGrid.setObstacles(Collections.singletonList(obstacle));
        when(probeRepository.findById(mockProbe.getId())).thenReturn(Optional.of(mockProbe));
        when(probeRepository.save(any(Probe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Probe probe = probeService.moveBackward(mockProbe.getId());

        assertEquals(0, probe.getX());
        assertEquals(-1, probe.getY());
        assertEquals(1, probe.getVisitedCoordinates().size());
        verify(probeRepository, times(1)).save(probe);
    }

}
