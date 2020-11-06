/**
 * LICENSE (MIT LICENSE)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **/

package net.yanny1337;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.yanny1337.events.KillEntityHandler;
import net.yanny1337.events.PlayerDeathHandler;

public class CombatRewards extends JavaPlugin {
	FileConfiguration config = getConfig();

	public void onEnable() {
		try {
			this.saveDefaultConfig();
		} catch (Exception e) {
			this.getLogger().info("[CombatRewards] Invalid configuration, please reset/remove the configuration file!");
			e.printStackTrace();
		}
		try {
			getServer().getPluginManager().registerEvents(new PlayerDeathHandler(this, config), this);
			getServer().getPluginManager().registerEvents(new KillEntityHandler(this, config), this);
		} catch (Exception e) {
			this.getLogger().info("[CombatRewards] Something went wrong, therefore the plugin will not be started!");
			e.printStackTrace();
			return;
		}
		this.getLogger().info("[CombatRewards] CombatRewards are now active on the server.");
	}

	public void onDisable() {
		this.getLogger().info("[CombatRewards] CombatRewards have been de-activated from the server!");
	}

	public boolean sendConsole(String message) {
		try {
			final ConsoleCommandSender cs = getServer().getConsoleSender();
			if (cs == null || message == null) {
				return false;
			} else {
				Bukkit.dispatchCommand(cs, message);
			}
		} catch (Exception e) {
			this.getLogger().info("[CombatRewards] An error occurred while sending a reward to the console!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}