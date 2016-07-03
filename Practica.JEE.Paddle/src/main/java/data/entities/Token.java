package data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Token {

	public static final int HOURS_TO_EXPIRE = 1; 
	
	@Id
	@GeneratedValue
	private int id;

	@Column(unique = true, nullable = false)
	private String value;

	@ManyToOne
	@JoinColumn
	private User user;

	@Column(nullable = false)
	private Calendar expires;

	public Token() {
	}

	public Token(User user, int hoursToExpire) {
		assert user != null;
		this.user = user;
		this.value = new Encrypt().encryptInBase64UrlSafe(
				"" + user.getId() + user.getUsername() + Long.toString(new Date().getTime()) + user.getPassword());
		this.expires = Calendar.getInstance();
		this.expires.add(Calendar.HOUR_OF_DAY, hoursToExpire);
	}

	public Calendar getExpires() {
		return expires;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	public boolean isExpired() {
		if (this.expires.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return id == ((Token) obj).id;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", value=" + value + ", userId=" + user.getId() + ", caducity=" + 
				new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(expires.getTime())  + "]";
	}
}
