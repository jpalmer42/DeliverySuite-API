package ca.toadapp.api_main.data.entity;

import ca.toadapp.common.data.entity.BaseEntity;
import ca.toadapp.common.data.entity.DaoAgent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "firebaseAgents")
public class DaoFirebaseAgent extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "agentId")
	private DaoAgent	agent;

	@Column(name = "agentId", updatable = false, insertable = false)
	private Long		agentId;

	private String		firebaseId;
}
