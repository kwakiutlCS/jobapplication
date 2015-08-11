 package pt.uc.dei.aor.project.persistence.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.uc.dei.aor.project.persistence.util.Role;

@Entity
@Table(name="worker")
@NamedQueries({
	@NamedQuery(name = "findWorkerByLogin",query="from WorkerEntity u where u.login = :login"),
})
public class WorkerEntity extends User {
	
	public WorkerEntity(String login, String email, String password, String name, String surname) {
		super(login, email, password, name, surname);
	}

	public WorkerEntity() {
		super();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role", indexes={@Index(columnList="workerentity_id")})
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}
}
