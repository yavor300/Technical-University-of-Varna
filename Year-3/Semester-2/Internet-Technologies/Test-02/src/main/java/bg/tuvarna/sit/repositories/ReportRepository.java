package bg.tuvarna.sit.repositories;

import bg.tuvarna.sit.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
