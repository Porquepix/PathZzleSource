package fr.porquepix.pathzzle.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class AudioManager {

	private static AudioManager _instance = null;
	
	private List<AudioClip> musics;
	private List<AudioClip> sounds;
	
	protected AudioManager() {
		musics = new ArrayList<AudioClip>();
		sounds = new ArrayList<AudioClip>();
		
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static AudioManager getInstance() {
		if (_instance == null) {
			_instance = new AudioManager();
		}
		return _instance;
	}
	
	public void addMusic(AudioClip audio) {
		musics.add(audio);
	}
	
	public void addSounds(AudioClip audio) {
		sounds.add(audio);
	}
	
	public void play(int audioID) {
		AudioClip audio = getClip(audioID);
		if (audio != null) {
			audio.play();
		}
	}
	
	public AudioClip getClip(int audioID) {
		for (AudioClip audio : musics) {
			if (audio.getID() == audioID) {
				return audio;
			}
		}
		for (AudioClip audio : sounds) {
			if (audio.getID() == audioID) {
				return audio;
			}
		}
		return null;
	}
	

	public void dispose() {
		for (AudioClip audio : musics) {
			audio.dispose();
		}
		musics.clear();
		
		for (AudioClip audio : sounds) {
			audio.dispose();
		}
		sounds.clear();
		
		AL.destroy();
	}
	
	public void setMusicVolume(float v) {
		for (AudioClip audio : musics) {
			audio.setVolume(v);
		}
	}
	
	public void setSoundsVolume(float v) {
		for (AudioClip audio : sounds) {
			audio.setVolume(v);
		}
	}
	
}
