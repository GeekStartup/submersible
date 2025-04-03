package com.ocean.submersible.service;

import com.ocean.submersible.entities.Grid;
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

import java.util.Optional;

import static com.ocean.submersible.enums.Direction.NORTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
