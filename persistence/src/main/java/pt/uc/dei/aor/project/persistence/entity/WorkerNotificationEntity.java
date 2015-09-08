package pt.uc.dei.aor.project.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="worker_notification")
@NamedQueries({
	@NamedQuery(name = "WorkerNotification.findNotificationsByWorker", 
			query = "from WorkerNotificationEntity u where u.worker = :worker"),
	@NamedQuery(name = "WorkerNotification.findUnreadByWorker", 
		query = "from WorkerNotificationEntity u where u.worker = :worker and u.read is FALSE"),
	@NamedQuery(name = "WorkerNotification.findReadByWorker", 
	query = "from WorkerNotificationEntity u where u.worker = :worker and u.read is TRUE"),
})
public class WorkerNotificationEntity {
	
	public WorkerNotificationEntity(String msg, WorkerEntity entity, Date date) {
		this.msg = msg;
		this.worker = entity;
		this.date = date;
		this.read = false;
	}

	public WorkerNotificationEntity() {
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	private String msg;
	
	@Column(nullable=false)
	private boolean read;
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private WorkerEntity worker;

	
	// getters and setters
	
	public void setRead(boolean b) {
		read = b;
	} 
	
}
