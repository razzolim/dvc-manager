package com.azzolim.renan.dvc.manager;

import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceRepository;
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

	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public void run(String... args) throws Exception {
		var dt1 = DeviceType.builder().name("Windows").build();
		var dt2 = DeviceType.builder().name("Mac").build();
		this.deviceTypeRepository.saveAll(List.of(dt1, dt2));

		var dev1 = Device.builder().systemName("Laptop Sony Windows 10").deviceType(dt1).build();
		var dev2 = Device.builder().systemName("Laptop Dell Windows 11").deviceType(dt1).build();
		var dev3 = Device.builder().systemName("Macbook Air 1").deviceType(dt2).build();
		var dev4 = Device.builder().systemName("Macbook Air 2").deviceType(dt2).build();
		var dev5 = Device.builder().systemName("Macbook Air 3").deviceType(dt2).build();
		this.deviceRepository.saveAll(List.of(dev1, dev2, dev3, dev4, dev5));

		var st0 = ServiceType.builder().name("Device").build();
		var st1 = ServiceType.builder().name("Antivirus").build();
		var st2 = ServiceType.builder().name("Backup").build();
		var st3 = ServiceType.builder().name("PSA").build();
		var st4 = ServiceType.builder().name("Screen share").build();
		this.stRepository.saveAll(List.of(st0, st1, st2, st3, st4));

		var serv0 = Service.builder().name("Any device").serviceType(st0).build();
		var serv1 = Service.builder().name("Installation of antivirus").serviceType(st1).build();
		var serv2 = Service.builder().name("Backup all data from any device").serviceType(st2).build();
		var serv3 = Service.builder().name("PSA").serviceType(st3).build();
		var serv4 = Service.builder().name("Screen share from anywhere").serviceType(st4).build();
		this.serviceRepository.saveAll(List.of(serv0, serv1, serv2, serv3, serv4));
	}
}
