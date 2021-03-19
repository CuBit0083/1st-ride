package org.cubit.riding.riding;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.cubit.riding.api.IRidingType;

@Getter
public class RidingType implements IRidingType  {

    private String name;
    private ItemStack item;
    private double speed;

    public RidingType(String name , short value  , double speed) {
        this.name = name;
        this.item = new ItemStack(Material.GOLD_HOE);
        this.item.setDurability(value);
        this.speed = speed;
    }


}
