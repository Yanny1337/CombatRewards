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
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.yanny1337.CombatRewards;

public class PlayerDeathHandler implements Listener {

	final CombatRewards rew;
	final FileConfiguration fileConfig;

	public PlayerDeathHandler(CombatRewards cr, FileConfiguration config) {
		rew = cr;
		fileConfig = config;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerDeath(final PlayerDeathEvent d) {
		final String chrName = d.getEntity().getName();
		final UUID uuid = d.getEntity().getUniqueId();
		Integer costRegular = fileConfig.getInt("PVP-DeathCostRegular");
		Integer costReduced = fileConfig.getInt("PVP-DeathCostReduced");
		final BigDecimal punishCost = new BigDecimal(costRegular);
		final BigDecimal punishReduced = new BigDecimal(costReduced);

		if (d.getEntity() == null || d.getEntity().getKiller() == null || d.getEntity().getKiller().getType() == null) {
			// if (d.getEntity().getKiller().getType() != EntityType.PLAYER) {
			if (fileConfig.getBoolean("PVP-CostOnlyInPVP")) {
				costRegular = 0;
				costReduced = 0;
				return;
			}
		}
		if (rew != null && chrName != null) {
			if (d.getEntity().getType() != EntityType.PLAYER) {
				return;
			}
			BigDecimal chrBal = null;
			try {
				if (uuid != null) {
					chrBal = Economy.getMoneyExact(uuid);
				}
			} catch (UserDoesNotExistException udnee) {
				udnee.printStackTrace();
				return;
			}
			if (chrBal != null) {
				if (chrBal.compareTo(punishCost) == 1 || chrBal.compareTo(punishCost) == 0) {
					rew.sendConsole("eco take " + chrName + " " + costRegular);
				} else if (chrBal.compareTo(punishCost) == -1) {
					if (chrBal.compareTo(punishReduced) == 1 || chrBal.compareTo(punishReduced) == 0) {
						rew.sendConsole("eco take " + chrName + " " + costReduced);
					}
				}
			}
		}
	}
}