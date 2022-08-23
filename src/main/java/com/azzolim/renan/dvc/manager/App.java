package com.azzolim.renan.dvc.manager;

import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceCostRepository;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceRepository;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
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

	@Autowired
	private ServiceCostRepository serviceCostRepository;

	@Override
	public void run(String... args) throws Exception {
		var dtWindows = DeviceType.builder().name("Windows").build();
		var dtMac = DeviceType.builder().name("Mac").build();
		this.deviceTypeRepository.saveAll(List.of(dtWindows, dtMac));

		var st0 = ServiceType.builder().name("Device").build();
		var st1 = ServiceType.builder().name("Antivirus").build();
		var st2 = ServiceType.builder().name("Backup").build();
		var st3 = ServiceType.builder().name("PSA").build();
		var st4 = ServiceType.builder().name("Screen share").build();
		this.stRepository.saveAll(List.of(st0, st1, st2, st3, st4));

		var svcDevice = Service.builder().name("Any device").serviceType(st0).build();
		var svcAntivirus = Service.builder().name("Installation of antivirus").serviceType(st1).build();
		var svcBkp = Service.builder().name("Backup all data from any device").serviceType(st2).build();
		var svcPSA = Service.builder().name("PSA").serviceType(st3).build();
		var svcScreenShare = Service.builder().name("Screen share from anywhere").serviceType(st4).build();
		this.serviceRepository.saveAll(List.of(svcDevice, svcAntivirus, svcBkp, svcPSA, svcScreenShare));

		var costDeviceWin = ServiceCost.builder().service(svcDevice).deviceType(dtWindows).amount(new BigDecimal(4)).build();
		var costDeviceWMac = ServiceCost.builder().service(svcDevice).deviceType(dtMac).amount(new BigDecimal(4)).build();
		var costAntWin = ServiceCost.builder().service(svcAntivirus).deviceType(dtWindows).amount(new BigDecimal(5)).build();
		var costAntMac = ServiceCost.builder().service(svcAntivirus).deviceType(dtMac).amount(new BigDecimal(7)).build();
		var costBkpWin = ServiceCost.builder().service(svcBkp).deviceType(dtWindows).amount(new BigDecimal(3)).build();
		var costBkpMac = ServiceCost.builder().service(svcBkp).deviceType(dtMac).amount(new BigDecimal(3)).build();
		var screenWin = ServiceCost.builder().service(svcScreenShare).deviceType(dtWindows).amount(new BigDecimal(1)).build();
		var screenMac = ServiceCost.builder().service(svcScreenShare).deviceType(dtMac).amount(new BigDecimal(1)).build();
		var costPSAWin = ServiceCost.builder().service(svcPSA).deviceType(dtWindows).amount(new BigDecimal(10)).build();
		this.serviceCostRepository.saveAll(List.of(costDeviceWin, costDeviceWMac, costAntWin, costAntMac, costBkpWin, costBkpMac, screenWin, screenMac, costPSAWin));

		var dev1 = Device.builder().systemName("Laptop Sony Windows 10").deviceType(dtWindows).services(Set.of(svcDevice, svcAntivirus, svcBkp, svcScreenShare)).build();
		var dev2 = Device.builder().systemName("Laptop Dell Windows 11").deviceType(dtWindows).services(Set.of(svcDevice, svcAntivirus, svcBkp, svcScreenShare)).build();
		var dev3 = Device.builder().systemName("Macbook Air 1").deviceType(dtMac).services(Set.of(svcDevice, svcAntivirus, svcBkp, svcScreenShare)).build();
		var dev4 = Device.builder().systemName("Macbook Air 2").deviceType(dtMac).services(Set.of(svcDevice, svcAntivirus, svcBkp, svcScreenShare)).build();
		var dev5 = Device.builder().systemName("Macbook Air 3").deviceType(dtMac).services(Set.of(svcDevice, svcAntivirus, svcBkp, svcScreenShare)).build();
		this.deviceRepository.saveAll(List.of(dev1, dev2, dev3, dev4, dev5));
	}
}
