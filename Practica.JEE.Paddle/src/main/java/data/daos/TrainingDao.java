package data.daos;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public interface TrainingDao extends JpaRepository<Training, Integer> {

	List<Training> findByCourt(Court court);
	
//	@Transactional
//	@Query("delete from Training training where training.finalDate < CURRENT_DATE")
//	public void deleteAllFinalized();
	
	@Query("select training from Training training where training.dayOfWeek = ?1 and training.finalDate >= CURRENT_DATE")
	public List<Training> findCurrentsByDayOfWeek(DayOfWeek dayOfWeek);

	public List<Training> findByTrainer(User trainer);
	
	@Query("select training from Training training where training.finalDate >= CURRENT_DATE")
	public List<Training> findAllCurrent();
	
	@Query("SELECT t FROM Training t INNER JOIN t.players player where player = ?1")
	public List<Training> findByPlayersContaining(User player);
}
