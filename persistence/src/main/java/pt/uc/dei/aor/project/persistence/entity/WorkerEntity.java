package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import pt.uc.dei.aor.project.persistence.util.Role;

@Entity
@Table(name="worker")
public class WorkerEntity extends User {
	
	public WorkerEntity(String login, String email, String password, String name, String surname) {
		super(login, email, password, name, surname);
	}

	public WorkerEntity() {
		super();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role")
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private List<Role> roles;

}
