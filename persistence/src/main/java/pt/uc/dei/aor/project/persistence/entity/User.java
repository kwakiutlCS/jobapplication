package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
	@Id
	protected long id;
	
	@Column
	protected String login;
	
	@Column
	protected String password;
	
	@Column
	protected String name;
	
	@Column
	protected String surname;
	
	@Column
	protected String email;
}
