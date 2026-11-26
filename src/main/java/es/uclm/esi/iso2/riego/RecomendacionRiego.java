package es.uclm.esi.iso2.riego;

public enum RecomendacionRiego {
    RIEGO_INTENSO("Activar Riego Intenso"),
    RIEGO_LIGERO("Activar Riego Ligero"),
    NO_REGAR("No Regar (previsión de lluvia inminente)"),
    DRENAJE_EMERGENCIA("Activar Drenaje de Emergencia (riesgo de congelación)"),
    POSPONER_RIEGO("Posponer riego (franja de máxima insolación 12:00-16:00)"),
    RIEGO_CRITICO("Activar protocolo de Riego Crítico (humedad < 10% en insolación)"),
    SIN_ACCION("No se requiere ninguna acción de riego ni drenaje");

    private final String descripcion;

    RecomendacionRiego(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}