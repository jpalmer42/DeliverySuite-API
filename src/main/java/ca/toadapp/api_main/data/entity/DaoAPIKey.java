package ca.toadapp.api_main.data.entity;

import java.util.Collection;

import ca.toadapp.api_main.data.enumeration.APIAccessRights;
import ca.toadapp.common.data.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "apiKeys")
public class DaoAPIKey extends BaseEntity {
	private String key;
	
	
	private Collection<APIAccessRights> rights;
}
