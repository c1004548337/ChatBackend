package com.chatapp.repository;

import com.chatapp.model.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MomentRepository extends JpaRepository<Moment, String> {
    List<Moment> findAllByOrderByTimestampDesc();
}
