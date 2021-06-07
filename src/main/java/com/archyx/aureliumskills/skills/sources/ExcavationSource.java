package com.archyx.aureliumskills.skills.sources;

import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import org.jetbrains.annotations.Nullable;

public enum ExcavationSource implements Source, BlockSource {

    DIRT("DIRT", 0),
    GRASS_BLOCK("GRASS"),
    SAND("SAND", 0),
    GRAVEL,
    MYCELIUM("MYCEL"),
    CLAY,
    SOUL_SAND,
    COARSE_DIRT("DIRT", 1),
    PODZOL("DIRT", 2),
    SOUL_SOIL,
    RED_SAND("SAND", 1),
    ROOTED_DIRT;

    private final String legacyMaterial;
    private final byte legacyData;
    private final boolean allowBothIfLegacy;

    ExcavationSource() {
        this(null, -1, false);
    }

    ExcavationSource(String legacyMaterial) {
        this(legacyMaterial, -1, false);
    }

    ExcavationSource(String legacyMaterial, int legacyData) {
        this(legacyMaterial, legacyData, false);
    }

    ExcavationSource(String legacyMaterial, boolean allowBothIfLegacy) {
        this(legacyMaterial, -1, allowBothIfLegacy);
    }

    ExcavationSource(String legacyMaterial, int legacyData, boolean allowBothIfLegacy) {
        this.legacyMaterial = legacyMaterial;
        this.legacyData = (byte) legacyData;
        this.allowBothIfLegacy = allowBothIfLegacy;
    }

    @Nullable
    @Override
    public String getLegacyMaterial() {
        return legacyMaterial;
    }

    @Override
    public byte getLegacyData() {
        return legacyData;
    }

    @Override
    public boolean allowBothIfLegacy() {
        return allowBothIfLegacy;
    }

    @Override
    public Skill getSkill() {
        return Skills.EXCAVATION;
    }
}
