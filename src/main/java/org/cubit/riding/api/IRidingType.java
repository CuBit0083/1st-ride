package org.cubit.riding.api;

import org.bukkit.inventory.ItemStack;

public interface IRidingType {

    String getName();
    ItemStack getItem();
    double getSpeed();

}
