package data.daos;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.entities.Authorization;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Role;
import data.entities.Token;
import data.entities.Training;
import data.entities.User;
import data.services.DataService;

@Service
public class DaosService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private AuthorizationDao authorizationDao;

	@Autowired
	private CourtDao courtDao;

	@Autowired
	private ReserveDao reserveDao;

	@Autowired
	private TrainingDao trainingDao;

	@Autowired
	private DataService genericService;

	private Map<String, Object> map;

	@PostConstruct
	public void populate() {
		map = new HashMap<>();

		User[] users = this.createPlayers(0, 4);
		for (User user : users) {
			map.put(user.getUsername(), user);
		}
		for (Token token : this.createTokens(users)) {
			map.put("t" + token.getUser().getUsername(), token);
		}
		for (User user : this.createPlayers(4, 1)) {
			map.put(user.getUsername(), user);
		}

		User[] usersExpired = this.createPlayers(5, 3);
		for (User user : usersExpired) {
			map.put(user.getUsername(), user);
		}
		for (Token token : this.createExpiredTokens(usersExpired)) {
			map.put("et" + token.getUser().getUsername(), token);
		}

		this.createCourts(1, 4);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, 1);
		date.set(Calendar.HOUR_OF_DAY, 9);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		for (int i = 0; i < 4; i++) {
			date.add(Calendar.HOUR_OF_DAY, 1);
			reserveDao.save(new Reserve(courtDao.findOne(i + 1), users[i], date));
		}

		for (User user : this.createTrainiers(1, 4)) {
			map.put(user.getUsername(), user);
		}

		
		for (DayOfWeek d : DayOfWeek.values()) {
			Calendar time = Calendar.getInstance();
			time.set(Calendar.HOUR_OF_DAY, 14);
			time.set(Calendar.MINUTE, 0);
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			Calendar initialDate, finalDate;
			initialDate = Calendar.getInstance();
			finalDate = Calendar.getInstance();
			initialDate.add(Calendar.MONTH, -1);
			finalDate.add(Calendar.MONTH, 1);
			
			for (int i = 0; i < 4; i++) {
				time.add(Calendar.HOUR_OF_DAY, 1);
				trainingDao.save(new Training("tr" + (i + 1) + d, (User) map.get("t" + (i + 1)), courtDao.findOne(i + 1), d,
						time.getTime(), Arrays.asList(users), initialDate, finalDate));
			}
		}
	}

	public User[] createPlayers(int initial, int size) {
		User[] users = new User[size];
		for (int i = 0; i < size; i++) {
			users[i] = new User("u" + (i + initial), "u" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
			userDao.save(users[i]);
			authorizationDao.save(new Authorization(users[i], Role.PLAYER));
		}
		return users;
	}

	public User[] createTrainiers(int initial, int size) {
		User[] users = new User[size];
		for (int i = 0; i < size; i++) {
			users[i] = new User("t" + (i + initial), "t" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
			userDao.save(users[i]);
			authorizationDao.save(new Authorization(users[i], Role.TRAINER));
		}
		return users;
	}

	public List<Token> createTokens(User[] users) {
		List<Token> tokenList = new ArrayList<>();
		Token token;
		for (User user : users) {
			token = new Token(user, Token.HOURS_TO_EXPIRE);
			tokenDao.save(token);
			tokenList.add(token);
		}
		return tokenList;
	}

	public List<Token> createExpiredTokens(User[] users) {
		List<Token> tokenList = new ArrayList<>();
		Token token;
		for (User user : users) {
			token = new Token(user, -1);
			tokenDao.save(token);
			tokenList.add(token);
		}
		return tokenList;
	}

	public void createCourts(int initial, int size) {
		for (int id = 0; id < size; id++) {
			courtDao.save(new Court(id + initial));
		}
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void deleteAll() {
		genericService.deleteAllExceptAdmin();
	}
}
