package ca.toadapp.api_main.data.entity;

import ca.toadapp.common.data.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "firebaseAgents")
public class DaoFirebaseAgent extends BaseEntity {
	private String firebaseUID;
	private Long agentId;

}
