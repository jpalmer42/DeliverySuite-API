package ca.toadapp.api_main;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.entity.DaoDelCoHour;
import ca.toadapp.common.data.entity.DaoDeliveryType;
import ca.toadapp.common.data.repository.RepoDelCo;
import ca.toadapp.common.data.repository.RepoDelCoHour;
import ca.toadapp.common.data.repository.RepoDeliveryTypes;

@SpringBootTest
class ApiMainApplicationTests {

	@Test
	void contextLoads() {
		popDB();
	}

	@Autowired
	private RepoDeliveryTypes deliveryTypes;

	@Autowired
	private RepoDelCo deliveryCompany;

	@Autowired
	private RepoDelCoHour deliveryCompanyHour;

	void popDB() {
		final var delTypes = new ArrayList<DaoDeliveryType>();
		delTypes.add(new DaoDeliveryType("Standard Food Delivery", 7.00));
		delTypes.add(new DaoDeliveryType("Grocery Delivery", 10.00));
		delTypes.add(new DaoDeliveryType("Merchandice Delivery", 10.00));
		delTypes.add(new DaoDeliveryType("Catering Delivery", 15.00));
		final var addedDelTypes = deliveryTypes.saveAll(delTypes);

		final var delCo = new DaoDelCo();
		delCo.setName("Stork Delivery");
		delCo.setCompanyCode("stork");
		delCo.setPhone("5558675309");
		delCo.setAddress("St.Ratford");
		delCo.setHasDeliveryOnDemand(true);
		delCo.setDeliveryTypes(addedDelTypes.stream().map(c -> c.getId()).toList());

		final var addedDelCo = deliveryCompany.save(delCo);

		final var delCoHours = new ArrayList<DaoDelCoHour>();
		delCoHours.add(new DaoDelCoHour(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		delCoHours.add(new DaoDelCoHour(DayOfWeek.SUNDAY, LocalTime.of(11, 0), LocalTime.of(19, 0)));

		final var savedHours = deliveryCompanyHour.saveAll(delCoHours);
		
		
	}

}
