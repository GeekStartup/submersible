package com.ocean.submersible.repositories;


import com.ocean.submersible.entities.Probe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
