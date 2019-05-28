package ro.ionutmarin.iehs.model;

import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public class Emergency {
    private RoomEntity roomEntity;
    private List<AlertEntity> alertEntityList = new ArrayList<>();

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public List<AlertEntity> getAlertEntityList() {
        return alertEntityList;
    }

    public void setAlertEntityList(List<AlertEntity> alertEntityList) {
        this.alertEntityList = alertEntityList;
    }
}
