package telran.logs.bugs.interfaces;

import java.util.List;

import telran.logs.bugs.dto.ArtifactDto;
import telran.logs.bugs.dto.AssignBugData;
import telran.logs.bugs.dto.BugAssignDto;
import telran.logs.bugs.dto.BugDto;
import telran.logs.bugs.dto.BugResponseDto;
import telran.logs.bugs.dto.CloseBugData;
import telran.logs.bugs.dto.EmailBugsCount;
import telran.logs.bugs.dto.ProgrammerDto;
import telran.logs.bugs.dto.Seriousness;
import telran.logs.bugs.dto.SeriousnessBugCount;

public interface BugsReporter {
	ProgrammerDto addProgrammer(ProgrammerDto programmerDto);
	ArtifactDto addArtifact (ArtifactDto artifactDto);
	BugResponseDto openBug(BugDto bugDto);
	BugResponseDto openAndAssignBug(BugAssignDto bugDto);
	void assignBug(AssignBugData assignData);
	List<BugResponseDto> getNonAssignedBugs();
	void closeBug(CloseBugData closeData);
	List<BugResponseDto> getUnClosedBugsMoreDuration(int days);
	List<BugResponseDto> getBugsProgrammer(long programmerId);
	List<EmailBugsCount> getEmailBugsCounts();
	List<String> getProgrammersMostBugs(int nProgrammer);
	List<String> getProgrammersLeastBugs(int nProgrammer);
	List<Seriousness> getSeriousnessTypesWithMostBugs(int nTypes);
	List<SeriousnessBugCount> getSeriousnessDistribution();
}
