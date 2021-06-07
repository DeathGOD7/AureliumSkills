package com.archyx.aureliumskills.skills.levelers;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.abilities.Ability;
import com.archyx.aureliumskills.abilities.MiningAbilities;
import com.archyx.aureliumskills.configuration.Option;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.skills.sources.MiningSource;
import com.archyx.aureliumskills.skills.sources.SourceTag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class MiningLeveler extends SkillLeveler implements Listener {

	private final MiningAbilities miningAbilities;

	public MiningLeveler(AureliumSkills plugin) {
		super(plugin, Ability.MINER);
		this.miningAbilities = new MiningAbilities(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (OptionL.isEnabled(Skills.MINING)) {
			// Check cancelled
			if (OptionL.getBoolean(Option.MINING_CHECK_CANCELLED) && event.isCancelled()) {
				return;
			}
			Block block = event.getBlock();
			// Check block replace
			if (OptionL.getBoolean(Option.CHECK_BLOCK_REPLACE) && plugin.getRegionManager().isPlacedBlock(block)) {
				return;
			}

			Player player = event.getPlayer();
			if (blockXpGainLocation(block.getLocation(), player)) return;
			if (blockXpGainPlayer(player)) return;

			// Search through sources until a match is found for the block broken
			for (MiningSource source : MiningSource.values()) {
				// Add XP to player if matched
				if (!source.isMatch(block)) continue;
				plugin.getLeveler().addXp(player, Skills.MINING, getXp(player, source));
				// Apply Luck Miner if has tag
				if (hasTag(source, SourceTag.LUCKY_MINER_APPLICABLE)) {
					miningAbilities.luckyMiner(player, block);
				}
				break; // Stop searching if matched
			}
			// Check custom blocks
			checkCustomBlocks(player, block, Skills.MINING);
		}
	}
}
