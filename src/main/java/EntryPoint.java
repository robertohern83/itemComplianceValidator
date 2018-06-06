import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baccredomatic.SpringContext;
import com.baccredomatic.ValidatorsOrchestrator;

/**
 * User: luke
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint {
    public static void main(String[] args) throws IOException {
    	AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(SpringContext.class);
    	
    	ValidatorsOrchestrator nature = context.getBean(ValidatorsOrchestrator.class);
    	
    	nature.showValidators();
    	
    	System.out.println(nature.executeValidators(new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nCREATE table")
						.array())));
    	
    	Path source = Paths.get("/temp/SQL/");
        Files.walk(source).filter(Files::isRegularFile).forEach(System.out::println);
        
        walkFiles(source);
    	
    }
    
    public static void walkFiles(Path source){
    	if (Files.isDirectory(source)) {
    		try {
    			Files.walk(source).filter(Files::isDirectory).filter(f->!f.toString().equals(source.toString())).forEach(EntryPoint::walkFiles);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		}
    	else{
    		try {
				Files.lines(source).forEach(System.out::println);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
       
    }
    
    
}
