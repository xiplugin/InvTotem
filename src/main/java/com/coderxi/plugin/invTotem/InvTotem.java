package com.coderxi.plugin.invTotem;

import com.google.common.base.Strings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InvTotem extends JavaPlugin implements Listener, CommandExecutor {

    private final Set<PotionEffect> effects = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("invtotem").setExecutor(this);
        loadEffectsFromConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("invtotem.admin")) {
            loadEffectsFromConfig();
            sender.sendMessage("Reload Success");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        // condition
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getFinalDamage() < player.getHealth()) return;

        // find totem
        PlayerInventory inv = player.getInventory();
        ItemStack totem = null;
        if (inv.getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) {
            totem = inv.getItemInMainHand();
        } else if (inv.getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) {
            totem = inv.getItemInOffHand();
        } else {
            for (ItemStack item : inv.getContents()) {
                if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                    totem = item;
                    break;
                }
            }
        }
        if (totem == null) return;

        // consume totem
        totem.setAmount(totem.getAmount() - 1);

        // prevent death
        event.setCancelled(true);

        // restore player
        player.setHealth(1.0);
        player.setFireTicks(0);

        // potion effects
        player.addPotionEffects(effects);

        // action
        FileConfiguration config = getConfig();
        // void damage
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            String cmd = config.getString("void-protect-command");
            if (cmd != null && !cmd.isBlank()) {
                Bukkit.dispatchCommand(player, cmd);
            }
        }
        // play particle
        if (config.getBoolean("play-particle-and-sound")) {
            Location loc = player.getLocation().add(0, 1, 0);
            player.spawnParticle(Particle.TOTEM_OF_UNDYING, loc, 30, 0.5, 1, 0.5, 0.1);
            player.playSound(loc, Sound.ITEM_TOTEM_USE, 1f, 1f);
        }
        String message = config.getString("send-message");
        String action = config.getString("send-actionbar");
        MiniMessage mm = MiniMessage.miniMessage();
        if (!Strings.isNullOrEmpty(message)) player.sendMessage(mm.deserialize(message));
        if (!Strings.isNullOrEmpty(action)) player.sendActionBar(mm.deserialize(action));

    }

    public void loadEffectsFromConfig() {
        effects.clear();
        List<String> rawEffects = getConfig().getStringList("give-effects");
        for (String line : rawEffects) {
            String[] parts = line.split(",");
            if (parts.length < 3) continue;
            try {
                PotionEffectType type = Registry.POTION_EFFECT_TYPE.get(NamespacedKey.minecraft(parts[0]));
                if (type == null) {
                    getLogger().warning("Invalid PotionEffectType: " + parts[0]);
                    continue;
                }
                int duration = Integer.parseInt(parts[1].trim());
                int amplifier = Integer.parseInt(parts[2].trim());
                effects.add(new PotionEffect(type, duration, amplifier));
            } catch (NumberFormatException e) {
                getLogger().warning("Invalid PotionEffect Text: " + line);
            }
        }
    }

}
