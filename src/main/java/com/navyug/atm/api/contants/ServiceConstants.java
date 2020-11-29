package com.navyug.atm.api.contants;

import java.text.SimpleDateFormat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;


@ConfigurationProperties(prefix = "campaign")
@Service
public class ServiceConstants {
	
	public static final SimpleDateFormat sdfFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z");
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
	
	
	
	

}
