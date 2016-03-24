package data.daos;

import data.entities.Training;
import data.entities.User;

public interface TrainingExtended {
	
	public void registerUserInTraining(User user, Training training);
	
}
