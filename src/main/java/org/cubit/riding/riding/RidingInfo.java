package org.cubit.riding.riding;

import lombok.Getter;
import org.cubit.riding.api.IRidingType;

import java.util.UUID;

@Getter
public class RidingInfo {

    private IRidingType iRidingType;
    private double intimacy;
    private double speed;
    private UUID uuid;

    public RidingInfo(IRidingType iRidingType , UUID uuid) {
        this.iRidingType = iRidingType;
        this.intimacy = 0;
        this.speed = 0;
        this.uuid = uuid;
    }
}
