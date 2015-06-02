package br.com.caelum.vraptor.musicjungle.util.model;

import br.com.ufpi.systematicmap.model.MapStudy;

public class MusicBuilder {

	private final MapStudy music = new MapStudy();
	
	public MusicBuilder withId(Long id) {
		music.setId(id);
		return this;
	}
	
	public MusicBuilder withTitle(String title) {
		music.setTitle(title);
		return this;
	}

	public MusicBuilder withDescription(String description) {
		music.setDescription(description);
		return this;
	}
	
	
	public MapStudy build() {
		return music;
	}
	
}
