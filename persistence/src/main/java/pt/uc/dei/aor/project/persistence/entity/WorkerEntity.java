 package pt.uc.dei.aor.project.persistence.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.Role;

@Entity
@Table(name="worker")
@NamedQueries({
	@NamedQuery(name = "Worker.findWorkerByLogin",query="from WorkerEntity u where u.login = :login"),
	@NamedQuery(name = "Worker.findWorkerByEmail",query="from WorkerEntity u where u.email = :email"),
	@NamedQuery(name = "Worker.findAllWorkers",query="from WorkerEntity u"),
})
public class WorkerEntity extends User {
	
	public WorkerEntity(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		super(login, email, password, name, surname);
		this.roles = new HashSet<>();
		this.roles.addAll(roles);
	}

	public WorkerEntity() {
		super();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role", indexes={@Index(columnList="workerentity_id")})
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Set<Role> roles;

	
	@ManyToMany
	private Set<InterviewEntity> interviews;
	
	
	public Set<Role> getRoles() {
		return roles;
	}
}
