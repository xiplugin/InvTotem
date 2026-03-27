[**English**](https://github.com/xiplugin/EntityPorter/blob/main/README.en.md)

# InvTotem

**InvTotem** 是一款为 Paper/Spigot 1.21+ 设计的轻量化增强插件。它打破了原版必须将“不死图腾”握在手上的限制，只要玩家背包中存有图腾，在受到致命伤害时就会自动触发保护。

---

## 功能特性

* **全背包检测**: 自动扫描主手、副手以及整个背包空间，优先消耗手中的图腾。
* **自定义药水效果**: 支持在配置文件中自定义触发后获得的药水效果（种类、时长、等级）。
* **虚空救赎**: 专门针对虚空伤害的保护机制，触发图腾时可自动执行预设指令（如 /spawn）防止坠入虚空死亡。
* **视听反馈**: 可配置是否开启原版的图腾碎裂粒子效果与音效，保持原汁原味的游戏体验。
* **现代文本支持**: 消息提示完美支持 MiniMessage 格式，轻松实现渐变色、加粗等炫酷效果。

---

## 指令和权限

| 指令 | 描述 | 默认权限 |
| :--- | :--- | :--- |
| /invtotem reload | 重新加载插件配置文件 | invtotem.admin |

---

## 配置文件示例 (config.yml)

插件会自动生成如下结构的配置文件，你可以根据需求自行调整：

```yaml
# 触发图腾后赋予的效果
# 格式: 效果名称,持续时间(Ticks),等级
# 20 Ticks = 1 秒
give-effects:
  - "regeneration,900,1"
  - "fire_resistance,800,0"
  - "resistance,100,1"

# 当由于掉入虚空伤害触发图腾时，玩家自动执行的命令
void-protect-command: "spawn"

# 是否播放原版图腾粒子与音效
play-particle-and-sound: true

# 提示消息 (支持 MiniMessage 格式)
# 留空 "" 则不发送
send-message: '<yellow>不死图腾已激活</yellow>'
send-actionbar: '<yellow>不死图腾已激活</yellow>'
```

---

## 安装与使用

1. 将编译好的 InvTotem.jar 放入服务器的 plugins 文件夹。
2. 重启服务器以生成默认配置文件。
3. 确保玩家背包中拥有 不死图腾 即可生效，无需刻意手持。

---