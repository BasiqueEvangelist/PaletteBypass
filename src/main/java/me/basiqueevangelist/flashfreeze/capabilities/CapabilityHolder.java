package me.basiqueevangelist.flashfreeze.capabilities;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CapabilityHolder {
    private final Map<Identifier, NbtElement> capabilities = new HashMap<>();

    public void fromTag(NbtCompound tag) {
        capabilities.clear();

        if (tag.contains("ForgeCaps", NbtElement.COMPOUND_TYPE)) {
            NbtCompound componentMap = tag.getCompound("ForgeCaps");
            for (String key : componentMap.getKeys()) {
                Identifier componentId = Identifier.of(key);
                capabilities.put(componentId, componentMap.get(key));
            }
        }
    }

    public void toTag(NbtCompound tag) {
        NbtCompound capabilityMap = new NbtCompound();
        for (Map.Entry<Identifier, NbtElement> entry : capabilities.entrySet()) {
            capabilityMap.put(entry.getKey().toString(), entry.getValue());
        }
        if (!capabilityMap.isEmpty())
            tag.put("ForgeCaps", capabilityMap);
    }
}
