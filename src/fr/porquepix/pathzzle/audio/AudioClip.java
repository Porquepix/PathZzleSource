package fr.porquepix.pathzzle.audio;

import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;

public class AudioClip {

	private int id;
	private IntBuffer buffer;
	private IntBuffer source;
	
	public AudioClip(int id, String path) {
		this.id = id;
		buffer = BufferUtils.createIntBuffer(1);
		source = BufferUtils.createIntBuffer(1);
		
		alGenBuffers(buffer);
		
		WaveData wavFile = WaveData.create(AudioClip.class.getResource("/audio" + path));
		alBufferData(buffer.get(0), wavFile.format, wavFile.data, wavFile.samplerate);
		wavFile.dispose();
		
		alGenSources(source);
		alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
	}
	
	public AudioClip(int id, String path, boolean loop) {
		this(id, path);
		
		if (loop)
			alSourcei(source.get(0), AL_LOOPING,  AL_TRUE);
	}
	
	public void setVolume(float v) {
		alSourcef(source.get(0), AL_GAIN, v);
	}
	
	public void play() {
		alSourcePlay(source);
	}
	
	public void dispose() {
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}
	
	public int getID() {
		return id;
	}
	
}
