package telran.logs.bugs.controllers;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import telran.logs.bugs.dto.ArtifactCount;
import telran.logs.bugs.dto.LogDto;
import telran.logs.bugs.dto.LogType;
import telran.logs.bugs.dto.LogTypeCount;
import telran.logs.bugs.interfaces.LogsInfo;

@RestController
@Slf4j
@Validated
public class LogsInfoController {

	@Autowired
	LogsInfo logsInfo;

	@GetMapping(value="/logs", produces="application/stream+json")
	Flux<LogDto> getAllLogs() {
		Flux<LogDto> result = logsInfo.getAllLogs();
		log.debug("Logs sent to a client");
		return result;
	}
	
	@GetMapping(value="/logs/type")
	Flux<LogDto> getLogsByTypes(@RequestParam (name="type") LogType logType) {
		Flux<LogDto> result = logsInfo.getLogsType(logType);
		log.debug("Logs of type {} sent to a client", logType);
		return result;
	}

	@GetMapping(value="/logs/exceptions")
	Flux<LogDto> getAllExceptionsLogs() {
		Flux<LogDto> result = logsInfo.getAllExceptions();
		log.debug("Logs of all exceptions sent to a client");
		return result;
	}
	
	@GetMapping(value="/logs/distribution")
	Flux<LogTypeCount> getLogTypeOccurrencies() {
		return logsInfo.getLogTypeOccurrences();
	}

	@GetMapping(value="/logs/mostencountered_exception_types")
	Flux<LogType> getMostEncounteredExceptionTypes(@RequestParam (name="n_types") @Min(1) int nTypes) {
		return logsInfo.getMostEncounteredExceptionTypes(nTypes);
	}

	@GetMapping(value="/logs/artifacts_distribution")
	Flux<ArtifactCount> getArtifactOccurrences() {
		return logsInfo.getArtifactOccurrences();
	}
	
	@GetMapping(value="/logs/mostencountered_artifacts")
//	Flux<String> getMostEncounterdArtifacts(@RequestParam (name="n_artifacts") int nArtifacts) {
//		return logsInfo.getMostEncounterdArtifacts(nArtifacts);
	Mono<List<String>>  getMostEncounterdArtifacts(@RequestParam (name="n_artifacts") @Min(1) int nArtifacts) {
		return logsInfo.getMostEncounterdArtifacts(nArtifacts).collectList();
	}
}
