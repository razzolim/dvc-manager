package com.azzolim.renan.dvc.manager;

import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Autowired
	private DeviceTypeRepository deviceTypeRepository;

	@Override
	public void run(String... args) throws Exception {
		this.deviceTypeRepository.save(DeviceType.builder()
				.id(1L)
				.name("Windows Server")
				.build());
		this.deviceTypeRepository.save(DeviceType.builder()
				.id(2L)
				.name("Linux Server")
				.build());
	}
}
