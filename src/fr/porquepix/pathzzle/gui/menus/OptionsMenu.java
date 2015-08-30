package fr.porquepix.pathzzle.gui.menus;

import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.gui.Menu;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.components.Button;
import fr.porquepix.pathzzle.gui.components.Label;
import fr.porquepix.pathzzle.gui.components.Slider;
import fr.porquepix.pathzzle.utils.Config;

public class OptionsMenu extends Menu implements IEventGui {

	public OptionsMenu() {
		super();
		
		guis.add(new Button("Save and Back", Display.getWidth() / 2, Display.getHeight() - 48, 300, 48).setEventClick(this));
		
		guis.add(new Slider(Display.getWidth() / 4 - 150, 150, 300, 20));
		((Slider) guis.get(1)).setValue((int) Config.getInstance().get(Config.MUSIC_LEVEL) / 100f);
		
		guis.add(new Slider(3 * Display.getWidth() / 4 - 150, 150, 300, 20));
		((Slider) guis.get(2)).setValue((int) Config.getInstance().get(Config.SOUNDS_LEVEL) / 100f);

		guis.add(new Label("Options", Display.getWidth() / 2, 50, 22, true));
		guis.add(new Label("Music Volume (" + ((Slider) guis.get(1)).getPercentage() + ")", Display.getWidth() / 4, 180, 22, true));
		guis.add(new Label("Sounds Volume (" + ((Slider) guis.get(2)).getPercentage() + ")", 3 * Display.getWidth() / 4, 180, 22, true));
	}
	
	@Override
	public void update() {
		super.update();
		
		((Label) guis.get(4)).setText("Music Volume (" + ((Slider) guis.get(1)).getPercentage() + ")");
		((Label) guis.get(5)).setText("Sounds Volume (" + ((Slider) guis.get(2)).getPercentage() + ")");
	}

	@Override
	public void onClick(GuiComponent c) {
		if (c == guis.get(0)) {
			saveOptions();
			MenuManager.getInstance().setMenu(new LauncherMenu());
		}
	}

	private void saveOptions() {
		Config.getInstance().put(Config.MUSIC_LEVEL, ((Slider) guis.get(1)).getPercentage());
		AudioManager.getInstance().setMusicVolume(((Slider) guis.get(1)).getValue());
		
		Config.getInstance().put(Config.SOUNDS_LEVEL, ((Slider) guis.get(2)).getPercentage());
		AudioManager.getInstance().setSoundsVolume(((Slider) guis.get(2)).getValue());
	}

}
