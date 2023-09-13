package util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FechaActual {
	
	public String fecha() {
		Instant instant = Instant.now();

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC-5"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(zonedDateTime);
        
        return formattedDateTime;
		
	}
	
	public String fechaAumentada(int dias) {
		
		Instant instant = Instant.now();

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC-5"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = formatter.format(zonedDateTime)+" 20:00:00";
        
        return formattedDateTime;
		
	}

}
