package dev.passarelli.xprison.mines.model.mine.saver;

import dev.passarelli.xprison.mines.managers.MineManager;
import dev.passarelli.xprison.mines.model.mine.Mine;
import me.lucko.helper.gson.GsonProvider;

import java.io.FileWriter;
import java.io.IOException;

public class MineFileSaver implements MineSaver {

	private final MineManager manager;

	public MineFileSaver(MineManager manager) {
		this.manager = manager;
	}

	@Override
	public void save(Mine mine) {
		try (FileWriter writer = new FileWriter(mine.getFile())) {
			GsonProvider.writeObjectPretty(writer, mine.serialize().getAsJsonObject());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
