/*
 * MIT License
 *
 * Copyright (c) 2020 Yanny1337
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

package net.yanny1337.events;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.yanny1337.CombatRewards;

public class KillEntityHandler implements Listener {

	final CombatRewards rew;
	final FileConfiguration fileConfig;

	public KillEntityHandler(CombatRewards cr, FileConfiguration config) {
		rew = cr;
		fileConfig = config;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onMobDeath(EntityDeathEvent e) {
		try {
			final LivingEntity enemy = e.getEntity();
			if (enemy != null && enemy.getKiller() != null) {
				final Player killed = Bukkit.getPlayer(enemy.getUniqueId());
				final Player killer = enemy.getKiller();
				if (killer != null) {
					final String killerName = killer.getName();
					final String killedName = enemy.getName();
					if (killerName != null && killedName != null) {
						boolean killable = false;
						Integer killWorth = 0;
						if (rew != null) {
							if (canReward(killer, enemy)) {
								switch (enemy.getType()) {
								/** FRIENDLY / NO REWARD */
								case BAT:
								case BEE:
								case CAT:
								case CHICKEN:
								case COD:
								case COW:
								case DOLPHIN:
								case DONKEY:
								case FOX:
								case HORSE:
								case IRON_GOLEM:
								case LLAMA:
								case MULE:
								case MUSHROOM_COW:
								case OCELOT:
								case PAINTING:
								case PANDA:
								case PARROT:
								case PIG:
								case POLAR_BEAR:
								case PUFFERFISH:
								case RABBIT:
								case SALMON:
								case SHEEP:
								case SKELETON_HORSE:
								case SNOWMAN:
								case SQUID:
								case TRADER_LLAMA:
								case TROPICAL_FISH:
								case TURTLE:
								case VILLAGER:
								case WANDERING_TRADER:
								case WOLF:
								case ZOMBIE_HORSE:
								case ZOMBIE_VILLAGER:
								case STRIDER:
									killable = true;
									killWorth = 0;
									break;
								/** HOSTILE MOBS / REWARDABLE */
								case ENDERMITE:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Endermite");
									break;
								case SILVERFISH:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Silverfish");
									break;
								case SPIDER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Spider");
									break;
								case CAVE_SPIDER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-CaveSpider");
									break;
								case ZOMBIE:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Zombie");
									break;
								case SKELETON:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Skeleton");
									break;
								case MAGMA_CUBE:
									killable = true;
									killWorth = fileConfig.getInt("PVE-MagmaCube");
									break;
								case GHAST:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Ghast");
									break;
								case SLIME:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Slime");
									break;
								case WITHER_SKELETON:
									killable = true;
									killWorth = fileConfig.getInt("PVE-WitherSkeleton");
									break;
								case HUSK:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Husk");
									break;
								case DROWNED:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Drowned");
									break;
								case STRAY:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Stray");
									break;
								case BLAZE:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Blaze");
									break;
								case PILLAGER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Pillager");
									break;
								case CREEPER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Creeper");
									break;
								case WITCH:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Witch");
									break;
								case VEX:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Vex");
									break;
								case PHANTOM:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Phantom");
									break;
								case ENDERMAN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Enderman");
									break;
								case EVOKER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Evoker");
									break;
								case VINDICATOR:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Vindicator");
									break;
								case SHULKER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Shulker");
									break;
								case RAVAGER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Ravager");
									break;
								case GUARDIAN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Guardian");
									break;
								case ELDER_GUARDIAN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-ElderGuardian");
									break;
								case WITHER:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Wither");
									break;
								case ENDER_DRAGON:
									killable = true;
									killWorth = fileConfig.getInt("PVE-EnderDragon");
									break;
								// [>1.16]:
								case PIGLIN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Piglin");
									break;
								case PIGLIN_BRUTE:
									killable = true;
									killWorth = fileConfig.getInt("PVE-PiglinBrute");
									break;
								case HOGLIN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Hoglin");
									break;
								case ZOGLIN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-Zoglin");
									break;
								case ZOMBIFIED_PIGLIN:
									killable = true;
									killWorth = fileConfig.getInt("PVE-ZombifiedPiglin");
									break;
								case PLAYER:
									if (fileConfig.getBoolean("PVP-RewardEnabled")) {
										killable = true;
										killWorth = fileConfig.getInt("PVP-Reward");
									} else {
										killable = true;
										killWorth = 0;
									}
									break;
								// [<1.16]: case PIG_ZOMBIE: killable = true; killWorth = fileConfig.getBoolean("PVP-PigZombie"); break;
								/** UNKNOWN / UNKILLABLE */
								case AREA_EFFECT_CLOUD:
								case ARMOR_STAND:
								case ARROW:
								case BOAT:
								case DRAGON_FIREBALL:
								case DROPPED_ITEM:
								case EGG:
								case ENDER_CRYSTAL:
								case ENDER_PEARL:
								case ENDER_SIGNAL:
								case EVOKER_FANGS:
								case EXPERIENCE_ORB:
								case FALLING_BLOCK:
								case FISHING_HOOK:
								case FIREBALL:
								case FIREWORK:
								case GIANT:
								case ILLUSIONER: // "Illusioner does not spawn in java edition worlds naturally"
								case ITEM_FRAME:
								case LEASH_HITCH:
								case LIGHTNING:
								case LLAMA_SPIT:
								case MINECART:
								case MINECART_CHEST:
								case MINECART_COMMAND:
								case MINECART_FURNACE:
								case MINECART_HOPPER:
								case MINECART_MOB_SPAWNER:
								case MINECART_TNT:
								case PRIMED_TNT:
								case SHULKER_BULLET:
								case SMALL_FIREBALL:
								case SNOWBALL:
								case SPECTRAL_ARROW:
								case SPLASH_POTION:
								case THROWN_EXP_BOTTLE:
								case TRIDENT:
								case UNKNOWN:
								case WITHER_SKULL:
								default:
									killable = false;
									killWorth = 0;
									break;
								}
							}
							BigDecimal chrBal = null;
							// @DEBUG: System.out.println("Killworth: \"" + killWorth + "\" (&& killable: \"" + killable + "\")");
							if (killable && killWorth > 0) {
								try {
									UUID killeruuid = killer.getUniqueId();
									if (killeruuid != null) {
										chrBal = Economy.getMoneyExact(killeruuid);
									}
								} catch (UserDoesNotExistException udnee) {
									udnee.printStackTrace();
									return;
								}
								boolean dropHead = false;
								final BigDecimal ecoMax = new BigDecimal("9999999999999");
								int arr[] = {
								// @INFO: Vault's 9999999999999 max bal value minus our most expensive killWorth case, so we don't exceed the bounds
										fileConfig.getInt("PVE-Blaze"), fileConfig.getInt("PVE-CaveSpider"),
										fileConfig.getInt("PVE-Creeper"), fileConfig.getInt("PVE-Drowned"),
										fileConfig.getInt("PVE-ElderGuardian"), fileConfig.getInt("PVE-EnderDragon"),
										fileConfig.getInt("PVE-Enderman"), fileConfig.getInt("PVE-Endermite"),
										fileConfig.getInt("PVE-Evoker"), fileConfig.getInt("PVE-Ghast"),
										fileConfig.getInt("PVE-Guardian"), fileConfig.getInt("PVE-Husk"),
										fileConfig.getInt("PVE-MagmaCube"), fileConfig.getInt("PVE-Phantom"),
										fileConfig.getInt("PVE-Pillager"), fileConfig.getInt("PVE-Ravager"),
										fileConfig.getInt("PVE-Shulker"), fileConfig.getInt("PVE-Silverfish"),
										fileConfig.getInt("PVE-Skeleton"), fileConfig.getInt("PVE-Slime"),
										fileConfig.getInt("PVE-Spider"), fileConfig.getInt("PVE-Stray"),
										fileConfig.getInt("PVE-Vex"), fileConfig.getInt("PVE-Vindicator"),
										fileConfig.getInt("PVE-Witch"), fileConfig.getInt("PVE-WitherSkeleton"),
										fileConfig.getInt("PVE-Zombie"), fileConfig.getInt("Wither"),
										fileConfig.getInt("PVP-Reward")
								};
								int max = Arrays.stream(arr).max().getAsInt();
								ecoMax.subtract(new BigDecimal(max));
								if (chrBal != null) {
									if (enemy.getType() == EntityType.PLAYER) {
										final Location loc = killed.getLocation();
										// final String chrName = d.getEntity().getName();
										final UUID uuid = killed.getUniqueId();
										final World w = killed.getWorld();
										final ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
										if (fileConfig.getBoolean("PVP-DropPlayerHead", true)) {
											if (loc != null && w != null && playerHead != null) {
												SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
												if (!killed.isOnline()) {
													final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
													// this.getLogger().info(offlinePlayer.hasPlayedBefore());
													playerHeadMeta.setOwningPlayer(offlinePlayer);
													playerHeadMeta.setDisplayName(offlinePlayer.getName() + "'s skull");
												} else {
													playerHeadMeta.setOwningPlayer(killed);
													playerHeadMeta.setDisplayName(killed.getName() + "'s skull");
												}
												if (playerHeadMeta != null) {
													dropHead = true;
												}
												if (dropHead) {
													final SimpleDateFormat formatUTC = new SimpleDateFormat("yy-MMM-dd HH:mm");
													formatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
													final SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yy-MMM-dd HH:mm");
													try {
														if (dateFormatLocal != null) {
															playerHeadMeta.setLore(Collections.singletonList(ChatColor.GOLD + "" + ChatColor.BOLD + dateFormatLocal.parse(formatUTC.format(new Date()))));
														}
													} catch (ParseException eee) {
														eee.printStackTrace();
														dropHead = false;
													}
													if (dropHead) {
														playerHead.setItemMeta(playerHeadMeta);
														w.dropItemNaturally(loc, playerHead);
													}
												}
											}
										}
									}
									if (chrBal.compareTo(ecoMax) == -1 && (enemy.getUniqueId() != killer.getUniqueId())) {
										rew.sendConsole("eco give " + killerName + " " + killWorth);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception f) {
			f.printStackTrace();
		}
	}

	private boolean canReward(Player player, Entity enemy) {
		if (player == null || enemy == null || player.getEntityId() == enemy.getEntityId()) {
			return false;
		}
		return (isEntity(enemy));
	}

	private boolean isEntity(Entity entity) {
		EntityType type = entity.getType();
		if (type == null) {
			return false;
		}
		return isMobType(type);
	}

	private boolean isMobType(EntityType type) {
		try {
			String mobName = type.name();
			final List<String> MobTypeList = new ArrayList<String>();
			MobTypeList.add("BAT");
			MobTypeList.add("ENDERMITE");
			MobTypeList.add("SILVERFISH");
			MobTypeList.add("SLIME");
			MobTypeList.add("SPIDER");
			MobTypeList.add("CAVE_SPIDER");
			MobTypeList.add("ZOMBIE");
			MobTypeList.add("SKELETON");
			MobTypeList.add("HUSK");
			MobTypeList.add("DROWNED");
			MobTypeList.add("STRAY");
			MobTypeList.add("PILLAGER");
			MobTypeList.add("WITHER_SKELETON");
			MobTypeList.add("CREEPER");
			MobTypeList.add("MAGMA_CUBE");
			MobTypeList.add("WITCH");
			MobTypeList.add("VEX");
			MobTypeList.add("PHANTOM");
			MobTypeList.add("BLAZE");
			MobTypeList.add("ENDERMAN");
			MobTypeList.add("EVOKER");
			MobTypeList.add("VINDICATOR");
			MobTypeList.add("SHULKER");
			MobTypeList.add("RAVAGER");
			MobTypeList.add("GUARDIAN");
			MobTypeList.add("GHAST");
			MobTypeList.add("WITHER");
			MobTypeList.add("PLAYER");
			MobTypeList.add("ELDER_GUARDIAN");
			MobTypeList.add("ENDER_DRAGON");
			// [<1.16]: MobTypeList.add("PIG_ZOMBIE");
			MobTypeList.add("HOGLIN");
			MobTypeList.add("PIGLIN");
			MobTypeList.add("PIGLIN_BRUTE");
			MobTypeList.add("ZOGLIN");
			MobTypeList.add("ZOMBIFIED_PIGLIN");
			// @DEBUG: "this.getLogger().info("ArrayList : " + MobTypeList.toString());"
			return (MobTypeList.contains(mobName));
		} catch (Exception e) {
			System.out.println("[CombatRewards] An internal error occured calculating mob type: \"" + type + "\"");
			e.printStackTrace();
			return false;
		}
	}
}
