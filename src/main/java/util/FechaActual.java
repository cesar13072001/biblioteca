package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaActual {
	
	public String fecha() {
		 String timestamp = LocalDateTime.now()
	                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	 
	        return timestamp;   
	}

}
