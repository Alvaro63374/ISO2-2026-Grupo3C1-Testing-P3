package es.uclm.esi.iso2.riego;

public interface IGestorRiego {
    RecomendacionRiego evaluarCicloRiego(DatosInvernadero datos);
}