package ca.toadapp.api_main.service;

import org.springframework.stereotype.Component;

import ca.toadapp.common.service.ServiceNotification;
import lombok.extern.java.Log;

@Log
@Component
public class ServiceNotificationImpl implements ServiceNotification {

	@Override
	public boolean sendMessage( Long providerId, Long agentId, String message ) {
		log.info( String.format( "provider:%d, agent:%d, msg:%s", providerId, agentId, message ) );
		return true;
	}

}
