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
    	EntryPoint entryPoint = new EntryPoint();
    	Path source = Paths.get("/temp/SQL/");
        System.out.println(Files.walk(source)
        		.filter(Files::isRegularFile)
        		.filter(EntryPoint.filterSQLFiles())
        			.map(entryPoint::validateFile)
        			.flatMap(x -> x.stream())
        			.collect(Collectors.toList()));
    }

	private static Predicate<? super Path> filterSQLFiles() {
		return f->f.getFileName().toString().matches("(?i).*.sql");
	}
	
	private List<String> validateFile(Path path){
		try {
			return validateOrch.executeValidators(new ValidationInput(Files.newInputStream(path), path.toString()));
		} catch (IOException e) {
			LogManager.getLogger(this.getClass()).error(e);
		}
		return new ArrayList<String>(0);
	}
    
    
    
    
}
