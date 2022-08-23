package com.azzolim.renan.dvc.manager;

import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
public class App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Autowired
	private DeviceTypeRepository deviceTypeRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ServiceTypeRepository stRepository;

	@Override
	public void run(String... args) throws Exception {
		var dt1 = DeviceType.builder().id(1L).name("Windows Server").build();
		var dt2 = DeviceType.builder().id(2L).name("Linux Server").build();

		this.deviceTypeRepository.save(dt1);
		this.deviceTypeRepository.save(dt2);

		var dev1 = Device.builder().id(1L).systemName("Windows 10").deviceType(dt1).build();
		var dev2 = Device.builder().id(2L).systemName("Ubuntu").deviceType(dt2).build();
		this.deviceRepository.save(dev1);
		this.deviceRepository.save(dev2);

		var st1 = ServiceType.builder().name("Antivirus").build();
		var st2 = ServiceType.builder().name("Backup").build();
		var st3 = ServiceType.builder().name("PSA").build();
		var st4 = ServiceType.builder().name("Screen share").build();

		this.stRepository.saveAll(List.of(st1, st2, st3, st4));
	}
}
