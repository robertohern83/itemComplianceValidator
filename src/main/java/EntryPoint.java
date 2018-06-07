import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baccredomatic.SpringContext;
import com.baccredomatic.ValidationInput;
import com.baccredomatic.ValidatorsOrchestrator;

/**
 * Punto de entrada para ejecución de validaciones sobre archivos fuente base
 * User: rhernandezm
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint {
	static AnnotationConfigApplicationContext context =
			new AnnotationConfigApplicationContext(SpringContext.class);
	ValidatorsOrchestrator validateOrch = context.getBean(ValidatorsOrchestrator.class);
	
    public static void main(String[] args) throws IOException {
    	
    	if(args.length < 1 || null == args[0]){
    		throw new IllegalArgumentException("No se especificó la ruta de ejecución");
    	}
    	
    	Path source = Paths.get(args[0]);
        System.out.println(validateFilesOn(source));
    }

	private static List<String> validateFilesOn(Path source) throws IOException {
		EntryPoint entryPoint = new EntryPoint();
		return Files.walk(source)
        		.filter(Files::isRegularFile)
        		.filter(EntryPoint.filterSQLFiles())
        			.map(entryPoint::validateFile)
        			.flatMap(x -> x.stream())
        			.collect(Collectors.toList());
	}

    /**
     * Filtra los archivos con formato SQL sin importar si el nombre están en mayúscula o minúscula
     * @return Predicado con el filtro correspondiente
     */
	private static Predicate<? super Path> filterSQLFiles() {
		return f->f.getFileName().toString().matches("(?i).*.sql");
	}
	
	/**
	 * Invocación a validación de un archivo específico
	 * @param path
	 * @return Lista de errores encontrados
	 */
	private List<String> validateFile(Path path){
		try {
			return validateOrch.executeValidators(new ValidationInput(Files.newInputStream(path), path.toString()));
		} catch (IOException e) {
			LogManager.getLogger(this.getClass()).error(e);
		}
		return new ArrayList<String>(0);
	}
    
    
    
    
}
