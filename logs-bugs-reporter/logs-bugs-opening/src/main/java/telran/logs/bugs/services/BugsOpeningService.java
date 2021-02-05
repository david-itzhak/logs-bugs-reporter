package telran.logs.bugs.services;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import telran.logs.bugs.dto.LogDto;

@Service
public class BugsOpeningService {
	
	@Autowired
	LogDtoToBugConverterInterface controller;

	@Bean
	public Consumer<LogDto> getLogDtoConsumer() {
		return controller::takeLogDtoAndOpenBug;
	}
}
