package listener;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DAOFactory;
import interfaces.PrestamoDAO;

/**
 * Application Lifecycle Listener implementation class BibliotecaListener
 *
 */
@WebListener
public class BibliotecaListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

   
    public BibliotecaListener() {
        // TODO Auto-generated constructor stub
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	// Crear un servicio de ejecución programado
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Crear una tarea para ejecutar
        Runnable task = () -> {
            System.out.println("Hola mundo");

            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
            
            List<Integer> lista = daoPrestamo.listarPrestamosAtrasados();
            
            for(int l : lista) {
            	daoPrestamo.actualizarEstadoNoEntregado(l);
            }
                  
        };

     // Programar la tarea para ejecutarse todos los días a las 17:50 UTC-5
        //scheduler.scheduleAtFixedTime(task, ZonedDateTime.now(ZoneId.of("UTC-5")).withHour(22).withMinute(0).withSecond(0).toInstant(), TimeUnit.MILLISECONDS);

        
        /*try {
            scheduler.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    
    }
    
    
	
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	
   
	
}
