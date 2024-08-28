package ca.toadapp.api_main.data;

import java.util.ArrayList;
import java.util.Collection;

import ca.toadapp.api_main.data.enumeration.APIAccessRights;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ConverterAPIAccessRightsCollection implements AttributeConverter<Collection<APIAccessRights>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(Collection<APIAccessRights> attribute) {
		StringBuilder response = new StringBuilder();
		for (APIAccessRights val : attribute) {
			response.append(SPLIT_CHAR);
			response.append(val.name());
		}
		return response.toString();
	}

	@Override
	public Collection<APIAccessRights> convertToEntityAttribute(String dbData) {
		String[] listOfStrings = dbData.split(SPLIT_CHAR);
		Collection<APIAccessRights> response = new ArrayList<>();
		for (String val : listOfStrings) {
			response.add(APIAccessRights.valueOf(val));
		}
		return response;
	}

}
