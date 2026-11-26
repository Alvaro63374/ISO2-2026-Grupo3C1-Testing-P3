package es.uclm.esi.iso2.riego;

import java.time.LocalTime;

public class GestorRiegoImpl implements IGestorRiego {

    private static final LocalTime INICIO_INSOLACION = LocalTime.of(12, 0);
    private static final LocalTime FIN_INSOLACION = LocalTime.of(16, 0);

    @Override
    public RecomendacionRiego evaluarCicloRiego(DatosInvernadero datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del invernadero no pueden ser nulos.");
        }

        double humedad = datos.getHumedadSuelo();
        double temp = datos.getTemperaturaAmbiente();
        boolean lluvia = datos.isPrevisionLluvia();
        LocalTime hora = datos.getHora();

        // 1. Emergencia por helada
        if (humedad > 60.0 && temp < 5.0) {
            return RecomendacionRiego.DRENAJE_EMERGENCIA;
        }

        // 2. Franja de máxima insolación [12:00, 16:00]
        boolean esMaximaInsolacion = !hora.isBefore(INICIO_INSOLACION) && !hora.isAfter(FIN_INSOLACION);
        if (esMaximaInsolacion) {
            if (humedad < 10.0) {
                return RecomendacionRiego.RIEGO_CRITICO;
            }
            return RecomendacionRiego.POSPONER_RIEGO;
        }

        // 3. Previsión de lluvia con humedad baja
        if (humedad < 40.0 && lluvia) {
            return RecomendacionRiego.NO_REGAR;
        }

        // 4. Riego intenso
        if (humedad < 20.0 && temp > 25.0 && !lluvia) {
            return RecomendacionRiego.RIEGO_INTENSO;
        }

        // 5. Riego ligero
        if (humedad >= 20.0 && humedad <= 40.0 && temp > 25.0 && !lluvia) {
            return RecomendacionRiego.RIEGO_LIGERO;
        }

        // 6. Sin acción requerida
        return RecomendacionRiego.SIN_ACCION;
    }
}