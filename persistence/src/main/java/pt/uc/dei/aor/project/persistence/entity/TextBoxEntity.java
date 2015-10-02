package pt.uc.dei.aor.project.persistence.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="textBoxEntity")
public class TextBoxEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column
	private String textBox;

	public TextBoxEntity() {
	}

	public TextBoxEntity(String textBox) {
		this.textBox = textBox;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTextBox() {
		return textBox;
	}

	public void setTextBox(String textBox) {
		this.textBox = textBox;
	}
	
	
	
}
	
	
	