package fr.porquepix.pathzzle;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.lwjgl.Sys;

public class PathZZle {

	public static final boolean DEV_VERSION = false;
	
	public static void main(String[] args) {		
		
		if (!DEV_VERSION && !args[0].equals("true"))
			System.exit(0);
		
		loadNativeLibs();
		initUncaughtExceptionHandler();
		
		Main main = Main.getInstance();
		main.init();
		
		main.startGameLoop();
		
	}

	private static void initUncaughtExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {				
				e.printStackTrace();
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				
				Sys.alert("Fatal Error", "Internal Fatal Error ! \n\n Thread : " + t.getName() + " \n Error Message : " + e.toString() + " \n Full Error Messgae :  \n " + sw.toString());
			}
		});
		
	}

	private static void loadNativeLibs() {
		String path;
		
		String osName = System.getProperty("os.name");
		
		if (osName.startsWith("Windows"))
			path = "natives/windows";
		else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD") || osName.startsWith("OpenBSD") || osName.startsWith("SunOS") || osName.startsWith("Unix"))
			path = "natives/linux";
		else if (osName.startsWith("Mac OS X") || osName.startsWith("Darwin"))
			path = "natives/macosx";
		else 
			throw new IllegalStateException("Unsupported Operating System");
		
		addLibraryPath(path);
	}

	private static void addLibraryPath(String path) {
		System.setProperty("org.lwjgl.librarypath", new File(path).getAbsolutePath());
	}
	
}
