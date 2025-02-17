package bg.tuvarna.sit.repositories;

import bg.tuvarna.sit.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
