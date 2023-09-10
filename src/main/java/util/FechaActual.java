package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaActual {
	
	public String fecha() {
		 String timestamp = LocalDateTime.now()
	                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	 
	        return timestamp;   
	}
	
	public String fechaAumentada(int dias) {
		String timestamp = LocalDate.now().plusDays(dias)
						   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
						   +" 20:00:00";
		return timestamp;
	}

}
