package telran.logs.bugs.random;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.logs.bugs.dto.LogDto;
import telran.logs.bugs.dto.LogType;

@Component
@NoArgsConstructor
@Getter
@EnableConfigurationProperties
@PropertySource("classpath:random.properties") 
public class RandomLogs {

	@Value("${app-count-classes}")
	int nClasses;

	@Value("${app-secExceptionProb}")
	int secExceptionProb;

	@Value("${app-exceptionProb}")
	int exceptionProb;

	@Value("${app-authenticationProb}")
	int authenticationProb;

	@Value("${app-min-response-time}")
	int minResponseTime;

	@Value("${app-max-response-time}")
	int maxResponseTime;

	public LogDto createRandomLog() {
		LogType logType = getLogType();
		return new LogDto(new Date(), logType, getArtifact(logType), getResponseTime(logType), "");
	}

	private int getResponseTime(LogType logType) {
		return logType == LogType.NO_EXCEPTION ? ThreadLocalRandom.current().nextInt(minResponseTime, maxResponseTime)
				: 0;
	}

	private String getArtifact(LogType logType) {
		EnumMap<LogType, String> logArtifact = getLogArtifactMap();
		return logArtifact.get(logType);
	}

	private EnumMap<LogType, String> getLogArtifactMap() {
		EnumMap<LogType, String> res = new EnumMap<>(LogType.class);
		Arrays.asList(LogType.values()).forEach(lt -> fillLogTypeArtifactMap(res, lt));
		return res;
	}

	private void fillLogTypeArtifactMap(EnumMap<LogType, String> res, LogType lt) {
		switch (lt) {
		case AUTHENTICATION_EXCEPTION -> res.put(LogType.AUTHENTICATION_EXCEPTION, "authentication");
		case AUTHORIZATION_EXCEPTION  -> res.put(LogType.AUTHORIZATION_EXCEPTION, "authorization");
		case BAD_REQUEST_EXCEPTION    -> res.put(LogType.BAD_REQUEST_EXCEPTION, getRandomClassName());
		case DUPLICATED_KEY_EXCEPTION -> res.put(LogType.DUPLICATED_KEY_EXCEPTION, getRandomClassName());
		case NOT_FOUND_EXCEPTION      -> res.put(LogType.NOT_FOUND_EXCEPTION, getRandomClassName());
		case NO_EXCEPTION             -> res.put(LogType.NO_EXCEPTION, getRandomClassName());
		case SERVER_EXCEPTION         -> res.put(LogType.SERVER_EXCEPTION, getRandomClassName());
		}
	}

	private String getRandomClassName() {
		return "class" + ThreadLocalRandom.current().nextInt(1, nClasses + 1);
	}

	private LogType getLogType() {
		int chance = getChance();
		return chance <= exceptionProb ? getExceptionLog() : LogType.NO_EXCEPTION;
	}

	private LogType getExceptionLog() {
		return getChance() <= secExceptionProb ? getSecurityExceptionLog() : getNonSecurityExceptionLog();
	}

	private LogType getNonSecurityExceptionLog() {
		LogType[] nonSecExceptions = { LogType.BAD_REQUEST_EXCEPTION, LogType.DUPLICATED_KEY_EXCEPTION,
				LogType.NOT_FOUND_EXCEPTION, LogType.SERVER_EXCEPTION };
		int ind = ThreadLocalRandom.current().nextInt(0, nonSecExceptions.length);
		return nonSecExceptions[ind];
	}

	private LogType getSecurityExceptionLog() {
		return getChance() <= authenticationProb ? LogType.AUTHENTICATION_EXCEPTION : LogType.AUTHORIZATION_EXCEPTION;
	}

	private int getChance() {
		return ThreadLocalRandom.current().nextInt(1, 101);
	}
}
