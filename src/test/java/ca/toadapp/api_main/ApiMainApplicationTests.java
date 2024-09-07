package ca.toadapp.api_main;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.toadapp.common.data.entity.DaoAgent;
import ca.toadapp.common.data.entity.DaoAgentLocation;
import ca.toadapp.common.data.entity.DaoAgentNotification;
import ca.toadapp.common.data.entity.DaoDelCo;
import ca.toadapp.common.data.entity.DaoDelCoBranding;
import ca.toadapp.common.data.entity.DaoDelCoHour;
import ca.toadapp.common.data.entity.DaoDelCoLocation;
import ca.toadapp.common.data.entity.DaoDeliveryType;
import ca.toadapp.common.data.enumeration.AgentRoles;
import ca.toadapp.common.data.enumeration.NotificationTypes;
import ca.toadapp.common.service.ServiceAgent;
import ca.toadapp.common.service.ServiceDelCo;
import ca.toadapp.common.service.ServiceDeliveryTypes;

@SpringBootTest
class ApiMainApplicationTests {
	@Autowired
	private ServiceDeliveryTypes deliveryTypes;

	@Autowired
	private ServiceDelCo deliveryCompany;

	@Autowired
	private ServiceAgent serviceAgent;

	@Test
	void contextLoads() {
		var delTypes = popDeliveryTypes();
		var delCo = popDeliveryCompany(delTypes);
		var agent = popAgent(delCo);

		deliveryCompany.updateDispatcher(delCo.getId(), agent.getId());
		
		serviceAgent.setOnDuty(agent.getId(), true);
		serviceAgent.setOnDuty(agent.getId(), false);
	}

	Collection<DaoDeliveryType> popDeliveryTypes() {
		final var delTypes = new ArrayList<DaoDeliveryType>();
		delTypes.add(new DaoDeliveryType("Standard Food Delivery", 7.00));
		delTypes.add(new DaoDeliveryType("Grocery Delivery", 10.00));
		delTypes.add(new DaoDeliveryType("Merchandice Delivery", 10.00));
		delTypes.add(new DaoDeliveryType("Catering Delivery", 15.00));
		return deliveryTypes.saveAll(delTypes);
	}

	DaoDelCo popDeliveryCompany(Collection<DaoDeliveryType> delTypes) {
		final var delCo = new DaoDelCo();
		delCo.setName("Stork Delivery");
		delCo.setCompanyCode("stork");
		delCo.setPhone("5558675309");
		delCo.setAddress("St.Ratford");
		delCo.setHasDeliveryOnDemand(true);
		delCo.setDeliveryTypes(delTypes.stream().map(c -> c.getId()).toList());

		final var hours = new ArrayList<DaoDelCoHour>();
		hours.add(new DaoDelCoHour(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(22, 0)));
		hours.add(new DaoDelCoHour(DayOfWeek.SUNDAY, LocalTime.of(11, 0), LocalTime.of(19, 0)));
		delCo.setHours(hours);

		final var locaitons = new ArrayList<DaoDelCoLocation>();
		locaitons.add(new DaoDelCoLocation(43.369986691835855, -80.98213896348206, 5, 5.00, 1.00, 1.50));
		delCo.setServiceAreas(locaitons);

		final var branding = new DaoDelCoBranding("fontName", "textColor", "fgColour", "bgColour", "http://logo",
				"http://web", "http://menu");
		delCo.setBranding(branding);

		return deliveryCompany.save(delCo, true, true, true);

	}

	private DaoAgent popAgent(DaoDelCo delCo) {
		final var roles = new ArrayList<AgentRoles>();
		roles.add(AgentRoles.admin);
		roles.add(AgentRoles.dispatch);
		roles.add(AgentRoles.driver);
		var agent = new DaoAgent("god@stork", roles, LocalDateTime.now(), "God", "Heaven", "558675309", delCo,
				delCo.getId(), false, false, false);

		agent = serviceAgent.save(agent);

		final var location = new DaoAgentLocation(null, agent.getId(), 43.369986691835855, -80.98213896348206);
		serviceAgent.setLocation(location);

		final var notificationOptions = new ArrayList<DaoAgentNotification>();
		notificationOptions
				.add(new DaoAgentNotification(null, agent.getId(), NotificationTypes.sms, "5558675309", true));
		notificationOptions
				.add(new DaoAgentNotification(null, agent.getId(), NotificationTypes.gcm, "firebaseGCM", false));
		serviceAgent.setNotificationConfig(notificationOptions);
		return agent;
	}

}
