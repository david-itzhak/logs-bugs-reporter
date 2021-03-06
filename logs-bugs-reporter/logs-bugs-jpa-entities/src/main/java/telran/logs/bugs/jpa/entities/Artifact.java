package telran.logs.bugs.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "artifacts", indexes = {@Index(columnList = "programmer_id")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Artifact {
	
	@Id
	@Column(name = "artifact_id")
	String artifacId;
	
	@ManyToOne
	@JoinColumn(name = "programmer_id", nullable = false)
	Programmer programmer;
}
