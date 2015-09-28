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
@Table(name="notification")
@NamedQueries({
	@NamedQuery(name = "Notification.findNotificationsByUser", 
			query = "from NotificationEntity u where u.user = :user"),
	@NamedQuery(name = "Notification.findUnreadByUser", 
		query = "from NotificationEntity u where u.user = :user and u.read is FALSE"),
	@NamedQuery(name = "Notification.findReadByUser", 
	query = "from NotificationEntity u where u.user = :user and u.read is TRUE"),
	@NamedQuery(name = "Notification.countUnread", 
	query = "select count(u) from NotificationEntity u where u.user = :user and u.read is FALSE"),
})
public class NotificationEntity {
	
	public NotificationEntity(String msg, UserEntity entity, String type) {
		this.msg = msg;
		this.user = entity;
		this.date = new Date();
		this.read = false;
		this.type = type;
	}

	public NotificationEntity() {
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
	
	@Column(nullable=false)
	private String type;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private UserEntity user;

	
	// getters and setters
	
	public void setRead(boolean b) {
		read = b;
	}

	public String getMsg() {
		return msg;
	} 
	
	public Date getDate() {
		return date;
	}

	public boolean isRead() {
		return read;
	}
	
	public String getType() {
		return type;
	}
}
