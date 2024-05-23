package co.edu.uniquindio.proyectofinalprogramacion3.controller;



import co.edu.uniquindio.proyectofinalprogramacion3.controller.service.ActionObserver;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.ReservaDto;

import java.util.ArrayList;
import java.util.List;

public class ControllerManager {
    private static ControllerManager instance;
    private List<ActionObserver> observers = new ArrayList<>();
    private List<ReservaDto> reservas = new ArrayList<>();

    private ControllerManager() {}

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    public void addObserver(ActionObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ActionObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ActionObserver observer : observers) {
            observer.onActionPerformed();
        }
    }

    public void addReserva(ReservaDto reserva) {
        reservas.add(reserva);
        notifyObservers();
    }

    public List<ReservaDto> getReservas() {
        return reservas;
    }
}
