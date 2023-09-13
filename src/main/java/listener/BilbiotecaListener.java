package listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DAOFactory;
import interfaces.PrestamoDAO;

/**
 * Application Lifecycle Listener implementation class BilbiotecaListener
 *
 */
@WebListener
public class BilbiotecaListener implements ServletContextListener {

    
    public BilbiotecaListener() {
        // TODO Auto-generated constructor stub
    }

	
  
	
    public void contextInitialized(ServletContextEvent sce)  { 
         
    	Timer timer = new Timer(true);

        // Configura la zona horaria en UTC-5
        TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(timeZone);

        // Calcula la hora de inicio a las 22:00 UTC-5
        Calendar scheduledTime = Calendar.getInstance(timeZone);
        scheduledTime.set(Calendar.HOUR_OF_DAY, 22);
        scheduledTime.set(Calendar.MINUTE, 00);
        scheduledTime.set(Calendar.SECOND, 00);

        // Si la hora actual es posterior a las 22:00, programa para mañana
        if (Calendar.getInstance(timeZone).after(scheduledTime)) {
            scheduledTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Calcula el primer retraso hasta la próxima ejecución
        long delay = scheduledTime.getTimeInMillis() - System.currentTimeMillis();

        // Crea un TimerTask para ejecutar la tarea
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            	PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
            	
            	List<Integer> prestamos = daoPrestamo.listarPrestamosAtrasados();
            	for(int id : prestamos) {
            		int s = daoPrestamo.actualizarEstadoNoEntregado(id);
            		System.out.println(s +" - "+id);
            	}
            	
                System.out.println("Hola, mundo - Hora actual (UTC-5): " + dateFormat.format(new Date()));
            }
        };

        // Programa la tarea para que se ejecute todos los días a las 22:00 UTC-5
        timer.scheduleAtFixedRate(task, delay, 24 * 60 * 60 * 1000);
        
        /*24 * 60 * 60 * 1000*/
    }
    	
    
    
    public void contextDestroyed(ServletContextEvent sce)  { 
        // TODO Auto-generated method stub
   }

	
}
