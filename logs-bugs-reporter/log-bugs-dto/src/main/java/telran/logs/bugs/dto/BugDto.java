package telran.logs.bugs.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BugDto {
	
	@NotNull
	public Seriousness seriosness;
	
	@NotEmpty
	public String description;
	
	public LocalDate dateOpen;
}
