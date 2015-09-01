package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
	
	public User(String login, String email, String password, String name, String surname) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
	}
	
	public User() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@Column(nullable=false, unique=true)
	protected String login;
	
	@Column(nullable=false)
	protected String password;
	
	@Column(nullable=false)
	protected String name;
	
	@Column(nullable=false)
	protected String surname;
	
	@Column(nullable=false, unique=true)
	protected String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [login=" + login + "]";
	}
	
	
	
}
