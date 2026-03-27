[**简体中文**](https://github.com/xiplugin/EntityPorter/blob/main/README.md)

# InvTotem

**InvTotem** is a lightweight enhancement plugin designed for Paper/Spigot 1.21+. It breaks the vanilla restriction of having to hold a "Totem of Undying" in your hand; as long as a player has a totem in their inventory, it will automatically trigger protection upon receiving fatal damage.

---

## Features

* **Full Inventory Detection**: Automatically scans main hand, off-hand, and the entire inventory space, prioritizing totems held in hands.
* **Custom Potion Effects**: Supports customizable potion effects (type, duration, amplifier) granted after triggering.
* **Void Salvation**: Specialized protection mechanism for void damage. Executing preset commands (e.g., /spawn) automatically when a totem is triggered to prevent falling into the void.
* **Audio-Visual Feedback**: Configurable vanilla totem particle effects and sound effects to maintain the original game experience.
* **Modern Text Support**: Message prompts fully support MiniMessage format, easily achieving cool effects like gradients and bold text.

---

## Commands and Permissions

| Command | Description | Default Permission |
| :--- | :--- | :--- |
| /invtotem reload | Reload the plugin configuration file | invtotem.admin |

---

## Configuration Example (config.yml)

The plugin will automatically generate a configuration file with the following structure, which you can adjust according to your needs:

```yaml
# Effects granted after triggering the totem
# Format: effect_name, duration(Ticks), amplifier
# 20 Ticks = 1 second
give-effects:
  - "regeneration,900,1"
  - "fire_resistance,800,0"
  - "resistance,100,1"

# Command automatically executed by the player when the totem is triggered by void damage
void-protect-command: "spawn"

# Whether to play vanilla totem particles and sound effects
play-particle-and-sound: true

# Notification messages (supports MiniMessage format)
# Leave as "" to not send
send-message: '<yellow>Totem of Undying consumed</yellow>'
send-actionbar: '<yellow>Totem of Undying consumed</yellow>'
```

---

## Installation and Usage

1. Place the compiled InvTotem.jar into the server's plugins folder.
2. Restart the server to generate the default configuration file.
3. Ensure players have a Totem of Undying in their inventory for it to take effect; no need to hold it manually.

---