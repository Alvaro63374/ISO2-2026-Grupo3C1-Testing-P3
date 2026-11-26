package es.uclm.esi.iso2.riego;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IGestorRiego gestorRiego = new GestorRiegoImpl();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Sistema de Recomendación de Riego para Invernaderos ===");

            System.out.print("Introduce la humedad del suelo (0 - 100 %): ");
            double humedad = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Introduce la temperatura ambiente (-50 a 70 ºC): ");
            double temperatura = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("¿Hay previsión de lluvia inminente? (true/false): ");
            boolean previsionLluvia = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.print("Introduce la hora actual (formato HH:mm, ej. 14:30): ");
            LocalTime hora = LocalTime.parse(scanner.nextLine().trim());

            DatosInvernadero datos = new DatosInvernadero(humedad, temperatura, previsionLluvia, hora);
            RecomendacionRiego resultado = gestorRiego.evaluarCicloRiego(datos);

            System.out.println("\nRecomendación del sistema: " + resultado.name());
            System.out.println("Detalle: " + resultado.getDescripcion());

        } catch (NumberFormatException e) {
            System.err.println("Error de formato: debes introducir valores numéricos válidos.");
        } catch (DateTimeParseException e) {
            System.err.println("Error de formato: la hora debe tener el formato HH:mm (ej. 09:15 o 14:30).");
        } catch (IllegalArgumentException e) {
            System.err.println("Datos fuera de rango: " + e.getMessage());
        }
    }
}