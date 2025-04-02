package com.ocean.submersible.repositories;


import com.ocean.submersible.entities.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridRepository extends JpaRepository<Grid, Long> {
}
