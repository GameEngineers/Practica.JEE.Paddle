package data.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Training;
import data.entities.User;

@Repository
public class TrainingDaoImpl implements TrainingExtended {

	
	@Autowired
	private TrainingDao trainingDao;

	@Override
	public void registerUserInTraining(User user, Training training) {

//		if (training.getPlayers().size() < Training.NUM_TRAINING_PLAYERS) {
//			training.getPlayers().add(user);
//			trainingDao.save(training);
//		} else {
//			
//		}
		
	}

}
