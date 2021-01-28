package com.kwanzoo.app.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.util.*;

import org.springframework.stereotype.Component;

@Component
public class Parse {

	public List<Persona> getPersonas(Map<Pair<String, String>, Integer> personas) {
		List<Persona> personaList = new ArrayList<Persona>();
		
		for (Map.Entry<Pair<String, String>, Integer> entry : personas.entrySet()) {
			Persona persona = new Persona();
			Pair<String, String> parser = entry.getKey();
			
			persona.setJobLevel(parser.getKey());
			persona.setJobFunction(parser.getValue());
			persona.setCount(entry.getValue());
			
			personaList.add(persona);
		}
		
		return personaList;
	}

	public List<Location> getLocations(Map<Pair<String, Pair<String, String>>, Integer> locations){
		List<Location> locationList = new ArrayList<Location>();
		
		for (Map.Entry<Pair<String, Pair<String, String>>, Integer> entry : locations.entrySet()) {
			Location location = new Location();
			Pair<String, String> parser = entry.getKey().getValue();
			
			location.setCity(entry.getKey().getKey());
			location.setState(parser.getKey());
			location.setCountry(parser.getValue());
			location.setCount(entry.getValue());
			
			locationList.add(location);
		}
		
		return locationList;
	}
	
}
