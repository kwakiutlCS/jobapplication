package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import pt.uc.dei.aor.project.persistence.util.Role;

@Entity
@Table(name="worker")
public class WorkerEntity extends User {
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role")
	@Enumerated
	@Column(name="role")
	private List<Role> roles;

}
