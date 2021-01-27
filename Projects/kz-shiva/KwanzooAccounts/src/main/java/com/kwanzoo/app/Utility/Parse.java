package com.kwanzoo.app.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.util.*;

import org.springframework.stereotype.Component;

@Component
public class Parse {

	public List<Persona> getPersonas(Map<String, Integer> personas) {
		
		char seperator = '$';
		Pair<String, String> p;

		List<Persona> personaList = new ArrayList();
		for (Map.Entry<String, Integer> entry : personas.entrySet()) {
			Persona persona = new Persona();
			String parser = entry.getKey();
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < parser.length(); i++) {
				if(parser.charAt(i) == seperator) {
					persona.setJobLevel(builder.toString());
					builder.delete(0, builder.length());
					continue;
				}
				builder.append(parser.charAt(i));
			}
			persona.setJobFunction(builder.toString());
			persona.setCount(entry.getValue());
			
			personaList.add(persona);
		}
		
		return personaList;
	}

	public List<Location> getLocations(Map<String, Integer> locations){
		List<Location> locationList = new ArrayList();
		
		char seperator = '$';
		for (Map.Entry<String, Integer> entry : locations.entrySet()) {
			Location location = new Location();
			String parser = entry.getKey();
			StringBuilder builder = new StringBuilder();
			boolean flag = false;
			for(int i = 0; i < parser.length(); i++) {
				if(parser.charAt(i) == seperator && flag) {
					location.setState(builder.toString());
					builder.delete(0, builder.length());
					continue;
				}
				if(parser.charAt(i) == seperator) {
					location.setCity(builder.toString());
					builder.delete(0, builder.length());
					flag = true;
					continue;
				}
				builder.append(parser.charAt(i));
			}
			location.setCountry(builder.toString());
			location.setCount(entry.getValue());
			
			locationList.add(location);
		}
		
		return locationList;
	}
	
}
