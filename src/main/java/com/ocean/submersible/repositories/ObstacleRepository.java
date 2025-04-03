package com.ocean.submersible.repositories;


import com.ocean.submersible.entities.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObstacleRepository extends JpaRepository<Obstacle, Long> {
}
