package com.ocean.submersible.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ocean.submersible.enums.Direction;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @Enumerated(EnumType.STRING)
    private Direction facingDirection;

    @OneToOne(mappedBy = "probe")
    @JsonBackReference
    private Grid grid;

    @ElementCollection
    private List<String> visitedCoordinates = new ArrayList<>();
}
