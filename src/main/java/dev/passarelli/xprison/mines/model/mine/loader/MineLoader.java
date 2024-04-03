package dev.passarelli.xprison.mines.model.mine.loader;

import dev.passarelli.xprison.mines.model.mine.Mine;

public interface MineLoader<T> {

	Mine load(T type);
}
