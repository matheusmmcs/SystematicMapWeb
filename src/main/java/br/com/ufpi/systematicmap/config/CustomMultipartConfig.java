package br.com.ufpi.systematicmap.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import br.com.caelum.vraptor.observer.upload.DefaultMultipartConfig;

@ApplicationScoped
@Specializes
public class CustomMultipartConfig extends DefaultMultipartConfig {

	// alteramos o tamanho total do upload para 60MB
	@Override
	public long getSizeLimit() {
        return 60 * 1024 * 1024;
    }

    // alteramos o tamanho do upload de cada arquivo para 20MB
    @Override
    public long getFileSizeLimit() {
        return 20 * 1024 * 1024;
    }

}