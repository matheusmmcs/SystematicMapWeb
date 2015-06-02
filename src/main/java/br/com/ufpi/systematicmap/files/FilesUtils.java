package br.com.ufpi.systematicmap.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.ufpi.systematicmap.model.MapStudy;

@Dependent
public class FilesUtils {
	
	private final Path DEFAULT_FOLDER = Paths.get("/tmp/uploads/");
	
	@PostConstruct
	public void init() {
		if(!Files.exists(DEFAULT_FOLDER)) {
			try {
				Files.createDirectories(DEFAULT_FOLDER);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}
	
	public void save(UploadedFile file, MapStudy map) {
		if(file != null) {
    		Path path = DEFAULT_FOLDER.resolve(map.getId().toString());
    		
    		try(InputStream in = file.getFile()) {
    		    Files.copy(in, path,StandardCopyOption.REPLACE_EXISTING);
    		} catch (IOException e) {
    		    throw new IllegalStateException(e);
    		}
		}
	}

	protected String getFileName(MapStudy map) {
		return "Articles_" + map.getId().toString() + ".bib";
	}
	
	public File getFile(MapStudy map) {
		return DEFAULT_FOLDER.resolve(map.getId().toString()).toFile();
	}
}
