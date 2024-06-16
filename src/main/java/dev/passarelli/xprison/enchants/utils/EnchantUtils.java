package dev.passarelli.xprison.enchants.utils;

import dev.passarelli.xprison.enchants.XPrisonEnchants;
import dev.passarelli.xprison.enchants.model.XPrisonEnchantment;
import dev.passarelli.xprison.enchants.model.impl.FortuneEnchant;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public final class EnchantUtils {

    private EnchantUtils() {
        throw new UnsupportedOperationException("Cannot instantiate.");
    }

    public static int getFortuneBlockCount(ItemStack pickaxe, Block block) {
        if (FortuneEnchant.isBlockBlacklisted(block)) {
            return 1;
        }
        return getItemFortuneLevel(pickaxe) + 1;
    }

    public static int getItemFortuneLevel(ItemStack item) {
        if (item == null) {
            return 0;
        }
        XPrisonEnchantment fortuneEnchant = XPrisonEnchants.getInstance().getEnchantsRepository().getEnchantById(3);

        if (fortuneEnchant == null || !fortuneEnchant.isEnabled()) {
            return 0;
        }

        return XPrisonEnchants.getInstance().getEnchantsManager().getEnchantLevel(item, fortuneEnchant);
    }

    public static int getDurability(ItemStack item) {
        return getDurability(item, item.getItemMeta());
    }

    public static int getDurability(ItemStack item, ItemMeta meta) {
        if (isLegacyVersion()) {
            return item.getDurability();
        } else if (meta instanceof Damageable) {
            return ((Damageable) meta).getDamage();
        }
        return 0;
    }

    public static boolean isLegacyVersion() {
        String version = Bukkit.getBukkitVersion();
        String[] parts = version.split("\\.");
        int majorVersion = Integer.parseInt(parts[0]);
        return majorVersion <= 12; // Considering versions 1.12 and below as legacy
    }
}

