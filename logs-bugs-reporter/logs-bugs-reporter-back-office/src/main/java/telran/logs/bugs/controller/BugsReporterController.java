package telran.logs.bugs.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import telran.logs.bugs.dto.AssignBugData;
import telran.logs.bugs.dto.BugAssignDto;
import telran.logs.bugs.dto.BugDto;
import telran.logs.bugs.dto.BugResponseDto;
import telran.logs.bugs.dto.ProgrammerDto;
import telran.logs.bugs.interfaces.BugsReporter;

@RestController
@Slf4j
@Validated
public class BugsReporterController {
	
	@Autowired
	BugsReporter bugsReporter;
	
	@PostMapping (value="/bugs/programmers", produces = "application/json")
	public ProgrammerDto addProgrammer (@RequestBody @Valid ProgrammerDto programmerDto) {
		log.debug("Recieved Post request with ProgrammerDto {}", programmerDto);
		ProgrammerDto newProgrammerDto = bugsReporter.addProgrammer(programmerDto);
		log.debug("Start sending newProgrammerDto {}", newProgrammerDto);
		return newProgrammerDto;
	}
	
	@PostMapping (value="/bugs/open", produces = "application/json")
	public BugResponseDto openBug (@RequestBody @Valid BugDto bugDto) {
		log.debug("Recieved Post request with BugDto {}", bugDto);
		BugResponseDto bugResponseDto = bugsReporter.openBug(bugDto);
		log.debug("Start sending BugResponseDto {}", bugResponseDto);
		return bugResponseDto;
	}

	@PostMapping (value="/bugs/open/assign", produces = "application/json")
	public BugResponseDto openAndAssignBug (@RequestBody @Valid BugAssignDto bugAssignDto) {
		log.debug("Recieved Post request with BugAssignDto {}", bugAssignDto);
		BugResponseDto bugResponseDto = bugsReporter.openBugAndAssignBug(bugAssignDto);
		log.debug("Start sending BugResponseDto {}", bugResponseDto);
		return bugResponseDto;
	}
	
	@PutMapping (value="/bugs/assign")
	public void assignBug (@RequestBody @Valid AssignBugData assignBugData) {
		log.debug("Recieved Put request with AssignBugData {}", assignBugData);
		bugsReporter.assignBug(assignBugData);
		log.debug("Method assignBug called");
	}
	
	@GetMapping (value="/bugs/programmers", produces = "application/json")
	public List<BugResponseDto> getBugsProgrammer (@RequestParam (name="programmer_id") @Min(1) long programmer_id) {
		log.debug("Recieved Get request with long programmer_id {}", programmer_id);
		List<BugResponseDto> listBugResponseDto = bugsReporter.getBugsProgrammer(programmer_id);
		log.debug("Start sending List<BugResponseDto> {}", listBugResponseDto);
		return listBugResponseDto;
	}
}