package data.daos;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Court;
import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer>, TrainingExtended {

	List<Training> findByCourt(Court court);
	
	@Transactional
	@Query("delete from Training training where training.finalDate < CURRENT_DATE")
	public void deleteAllFinalized();
	
	public List<Training> findByDayOfWeek(DayOfWeek dayOfWeek);

}
