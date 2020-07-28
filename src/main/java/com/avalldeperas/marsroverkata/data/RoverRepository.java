package com.avalldeperas.marsroverkata.data;

import com.avalldeperas.marsroverkata.model.Rover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoverRepository extends JpaRepository<Rover, Long> {
}
