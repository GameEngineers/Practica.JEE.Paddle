package business.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.TokenExpiredException;
import business.controllers.AdminController;
import business.controllers.TokenController;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.ADMINS)
public class AdminResource {

	private AdminController adminController;

	private TokenController tokenController;

	@Autowired
	public void setTokenController(TokenController tokenController) {
		this.tokenController = tokenController;
	}

	@Autowired
	public void setAdminController(AdminController adminController) {
		this.adminController = adminController;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAllExceptAdmin(@RequestHeader(value = "Authorization") String basic) throws TokenExpiredException {
		if (tokenController.IsValid(basic)) {
			adminController.deleteAllExceptAdmin();
		} else {
			throw new TokenExpiredException("Token: " + basic);
		}
	}

}
