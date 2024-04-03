package dev.passarelli.xprison.nicknames.repo;

import dev.passarelli.xprison.interfaces.UPCRepository;
import org.bukkit.OfflinePlayer;

public interface NicknameRepository extends UPCRepository {

	void updatePlayerNickname(OfflinePlayer player);

}
