package business.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistCourtIdException;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.TokenExpiredException;
import business.controllers.CourtController;
import business.controllers.TokenController;
import business.wrapper.CourtState;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.COURTS)
public class CourtResource {

	private CourtController courtController;

	private TokenController tokenController;

	@Autowired
	public void setTokenController(TokenController tokenController) {
		this.tokenController = tokenController;
	}

	@Autowired
	public void setCourtController(CourtController courtController) {
		this.courtController = courtController;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createCourt(@RequestHeader(value = "Authorization") String basic, @RequestParam(required = true) int id)
			throws AlreadyExistCourtIdException, TokenExpiredException {
		if (!tokenController.IsValid(basic)){
			throw new TokenExpiredException("token: " + basic);
		}
		if (!this.courtController.createCourt(id)) {
			throw new AlreadyExistCourtIdException();
		}
	}

	@RequestMapping(value = Uris.ID + Uris.ACTIVE, method = RequestMethod.POST)
	public void changeCourtActivationTrue(@RequestHeader(value = "Authorization") String basic, @PathVariable int id)
			throws NotFoundCourtIdException, TokenExpiredException {
		if (!tokenController.IsValid(basic)) {
			throw new TokenExpiredException("token :" + basic);
		}
		if (!courtController.changeCourtActivation(id, true)) {
			throw new NotFoundCourtIdException("id: " + id);
		}
	}

	@RequestMapping(value = Uris.ID + Uris.ACTIVE, method = RequestMethod.DELETE)
	public void changeCourtActivationFalse(@RequestHeader(value = "Authorization") String basic, @PathVariable int id)
			throws NotFoundCourtIdException, TokenExpiredException {
		if (!tokenController.IsValid(basic)) {
			throw new TokenExpiredException("token :" + basic);
		}
		if (!courtController.changeCourtActivation(id, false)) {
			throw new NotFoundCourtIdException("id: " + id);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<CourtState> showCourts(@RequestHeader(value = "Authorization") String basic) throws TokenExpiredException {
		if (!tokenController.IsValid(basic)){
			throw new TokenExpiredException("token :" + basic);
		}
		return courtController.showCourts();
	}

}
