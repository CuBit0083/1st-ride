package org.cubit.riding.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.cubit.riding.api.IRidingType;
import org.cubit.riding.riding.RidingInfo;
import org.cubit.riding.riding.RidingType;

import java.util.*;

public class RidingManager {


    private Map<String, IRidingType> typeMap;
    private Map<UUID, Map<String, RidingInfo>> userRidingMap;
    private Map<UUID, Float> userKeyMap;

    public RidingManager() {
        this.typeMap = new HashMap<>();
        this.userRidingMap = new HashMap<>();
        this.userKeyMap = new HashMap<>();
    }

    public IRidingType createRidingType(String name, short value, double speed) {
        if (this.typeMap == null || this.typeMap.containsKey(name)) {
            return null;
        }
        IRidingType ridingType = new RidingType(name, value, speed);
        this.typeMap.put(name, ridingType);
        return ridingType;
    }

    public void removeRidingType(String name) {
        if (this.typeMap == null || !this.typeMap.containsKey(name)) {
            return;
        }
        this.typeMap.remove(name);
    }

    public IRidingType getRidingType(String name) {
        if (this.typeMap == null || !this.typeMap.containsKey(name)) {
            return null;
        }
        return this.typeMap.get(name);
    }

    public void addRiding(RidingInfo ridingInfo) {
        if (this.userRidingMap.containsKey(ridingInfo.getUuid())) {
            this.userRidingMap.get(ridingInfo.getUuid()).put(ridingInfo.getIRidingType().getName(), ridingInfo);
        } else {
            this.userRidingMap.put(ridingInfo.getUuid(), new HashMap<>());
            this.userRidingMap.get(ridingInfo.getUuid()).put(ridingInfo.getIRidingType().getName(), ridingInfo);
        }
    }

    public void removeRiding(RidingInfo ridingInfo) {
        if (ridingInfo == null || ridingInfo.getUuid() == null || this.userRidingMap.get(ridingInfo.getUuid()) == null) {
            return;
        }
        if (userRidingMap.containsKey(ridingInfo.getUuid())) {
            return;
        }
        this.userRidingMap.get(ridingInfo.getUuid()).remove(ridingInfo.getIRidingType().getName());
    }

    public RidingInfo getRidingInfo(UUID uuid, String name) {
        if (name == null || uuid == null || !this.userRidingMap.get(uuid).containsKey(name)) {
            return null;
        }
        return this.userRidingMap.get(uuid).get(name);
    }


    public void addUserKey(UUID uuid, Float key) {
        this.userKeyMap.put(uuid, key);

    }

    public Float getUserKey(UUID uuid) {
        return this.userKeyMap.get(uuid);
    }


    public void setRidingVelocity(RidingInfo ridingInfo, Entity entity, double speed) {
        if (ridingInfo == null || entity == null) {
            return;
        }
        Vector currentDirection, currentVelocity;
        currentDirection = Bukkit.getPlayer(ridingInfo.getUuid()).getLocation().getDirection();
        currentVelocity = currentDirection.clone().multiply(speed);
        entity.setVelocity(currentVelocity);
    }


    public void startVelocityScheduler(JavaPlugin plugin, RidingInfo ridingInfo) {
        Bukkit.getScheduler().runTaskTimer(plugin, () ->
        {
            System.out.println(this.getUserKey(ridingInfo.getUuid()));
            double speed;
            if (Bukkit.getPlayer(ridingInfo.getUuid()).isInsideVehicle() && this.userKeyMap.containsKey(ridingInfo.getUuid())) {
                if (this.getUserKey(ridingInfo.getUuid()) > 0) {
                    speed = ridingInfo.getIRidingType().getSpeed();
                    System.out.println(speed);
                } else if (this.getUserKey(ridingInfo.getUuid()) < 0) {
                    speed = -ridingInfo.getIRidingType().getSpeed();
                    System.out.println(speed);
                } else {
                    speed = 0;
                    System.out.println(speed);
                }
                this.setRidingVelocity(ridingInfo , Bukkit.getPlayer(ridingInfo.getUuid()).getVehicle() , speed);
            }

        }, 0L, 1L);
    }


}
