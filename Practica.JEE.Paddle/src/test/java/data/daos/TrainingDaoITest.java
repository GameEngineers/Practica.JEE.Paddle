package data.daos;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})

public class TrainingDaoITest {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DaosService daosServices;

    @Autowired
    private CourtDao courtDao;
    
    @Autowired
    private UserDao userDao;

    @Test
    public void findCurrentByDayOfWeekTest() {
        List<Training> trainings = trainingDao.findCurrentsByDayOfWeek(DayOfWeek.MONDAY);
        assertEquals(4, trainings.size());
        Training tr1 = trainingDao.findOne(1);
        Training tr2 = trainingDao.findOne(2);
        Training tr3 = trainingDao.findOne(3);
        Training tr4 = trainingDao.findOne(4);
        assertEquals(tr1, trainings.get(0));
        assertEquals(tr2, trainings.get(1));
        assertEquals(tr3, trainings.get(2));
        assertEquals(tr4, trainings.get(3));
    }

    @Test
    public void findByTrainerTest() {
        User tr1 = (User) daosServices.getMap().get("tr1");
        List<Training> trainings = trainingDao.findByTrainer(tr1);

        for (Training t : trainings) {
            assertEquals(tr1, t.getTrainer());
        }
    }

    @Test
    public void findAllCurrentTest() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_YEAR, -2);
        c2.add(Calendar.DAY_OF_YEAR, -1);
        Training t = new Training("t", (User) daosServices.getMap().get("tr1"), courtDao.findOne(1), DayOfWeek.MONDAY,
                Calendar.getInstance().getTime(), new ArrayList<User>(), c1, c2);
        trainingDao.save(t);
        List<Training> trainings = trainingDao.findAllCurrent();
        assertEquals(28, trainings.size());
    }

    @Test
    public void findByPlayersContainingTest() {
        User u = new User("u", "u@u.com", "p", Calendar.getInstance());
        userDao.save(u);
        
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.add(Calendar.DAY_OF_YEAR, -1);
        c2.add(Calendar.DAY_OF_YEAR, 1);
        Training t = new Training("t", (User) daosServices.getMap().get("tr1"), courtDao.findOne(1), DayOfWeek.MONDAY,
                Calendar.getInstance().getTime(), new ArrayList<User>(), c1, c2);
        t.getPlayers().add(u);
        trainingDao.save(t);
        
        List<Training> trainings = trainingDao.findByPlayersContaining(u);
        assertEquals(1, trainings.size());
    }   
}
