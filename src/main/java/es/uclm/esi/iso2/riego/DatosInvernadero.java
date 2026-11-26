package es.uclm.esi.iso2.riego;

import java.time.LocalTime;

public final class DatosInvernadero {
    private final double humedadSuelo;
    private final double temperaturaAmbiente;
    private final boolean previsionLluvia;
    private final LocalTime hora;

    public DatosInvernadero(double humedadSuelo, double temperaturaAmbiente, 
                            boolean previsionLluvia, LocalTime hora) {
        if (humedadSuelo < 0.0 || humedadSuelo > 100.0) {
            throw new IllegalArgumentException("La humedad del suelo debe estar entre 0% y 100%.");
        }
        if (temperaturaAmbiente < -50.0 || temperaturaAmbiente > 70.0) {
            throw new IllegalArgumentException("La temperatura ambiente está fuera de rango [-50, 70] ºC.");
        }
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede ser nula.");
        }
        this.humedadSuelo = humedadSuelo;
        this.temperaturaAmbiente = temperaturaAmbiente;
        this.previsionLluvia = previsionLluvia;
        this.hora = hora;
    }

    public double getHumedadSuelo() { return humedadSuelo; }
    public double getTemperaturaAmbiente() { return temperaturaAmbiente; }
    public boolean isPrevisionLluvia() { return previsionLluvia; }
    public LocalTime getHora() { return hora; }
}