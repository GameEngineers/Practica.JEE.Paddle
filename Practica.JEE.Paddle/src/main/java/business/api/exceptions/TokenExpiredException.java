package business.api.exceptions;

public class TokenExpiredException extends ApiException {

	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "El token está expirado";

	public static final int CODE = 333;

	public TokenExpiredException() {
		this("");
	}

	public TokenExpiredException(String detail) {
		super(DESCRIPTION + ". " + detail, CODE);
	}
}
