import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class App {
        public static void main(String[] args) throws Exception {
                // System.out.println("Hello, World!yu");
                // --- Datos de Prueba ---
                List<Empleado> personal = List.of(
                                new Empleado("E01", "Ana Gómez", Area.CARDIOLOGIA, 25.0),
                                new Empleado("E02", "Luis Vera", Area.CIRUGIA, 45.0),
                                new Empleado("E03", "Carlos Rivas", Area.PEDIATRIA, 28.0),
                                new Empleado("E04", "Juan Mora", Area.CARDIOLOGIA, 22.0),
                                new Empleado("E05", "Sofía Castro", Area.ONCOLOGIA, 35.0),
                                new Empleado("E06", "Miguel Peña", Area.URGENCIAS, 38.0),
                                new Empleado("E07", "Laura Díaz", Area.ADMINISTRACION, 18.0),
                                new Empleado("E08", "Fernando Gil", Area.RADIOLOGIA, 33.0),
                                new Empleado("E09", "Isabel Luna", Area.CIRUGIA, 48.0),
                                new Empleado("E10", "Pedro Navas", Area.PEDIATRIA, 29.0),
                                new Empleado("E11", "Valeria Sol", Area.URGENCIAS, 40.0),
                                new Empleado("E12", "Ricardo León", Area.CARDIOLOGIA, 26.5),
                                new Empleado("E13", "Mónica Marín", Area.ONCOLOGIA, 36.0),
                                new Empleado("E14", "Andrés Salas", Area.ADMINISTRACION, 19.5),
                                new Empleado("E15", "Gabriela Paz", Area.RADIOLOGIA, 34.5));

                List<RegistroTurno> registrosMes = List.of(
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 1), TipoTurno.NOCHE, 12),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 1), TipoTurno.DIA, 8),
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 2), TipoTurno.AUSENCIA, 0),
                                // temporal
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 5), TipoTurno.AUSENCIA, 0),
                                new RegistroTurno("E04", LocalDate.of(2024, 10, 2), TipoTurno.DIA, 8),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 3), TipoTurno.GUARDIA, 24),
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 3), TipoTurno.NOCHE, 12),
                                new RegistroTurno("E04", LocalDate.of(2024, 10, 4), TipoTurno.NOCHE, 12),
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 5), TipoTurno.DIA, 8),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 5), TipoTurno.DIA, 8),
                                new RegistroTurno("E04", LocalDate.of(2024, 10, 6), TipoTurno.AUSENCIA, 0),
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 7), TipoTurno.NOCHE, 12),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 8), TipoTurno.GUARDIA, 24),
                                new RegistroTurno("E04", LocalDate.of(2024, 10, 8), TipoTurno.DIA, 8),
                                new RegistroTurno("E01", LocalDate.of(2024, 10, 9), TipoTurno.DIA, 8),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 10), TipoTurno.DIA, 8),
                                new RegistroTurno("E02", LocalDate.of(2024, 10, 10), TipoTurno.DIA, 8));

                DataFunction df = new DataFunction();

                // Ejercicio 1
                Map<String, Integer> reporteHoras = df.ReportedeHorasTrabajadas(registrosMes);
                //reporteHoras.entrySet().forEach(System.out::println);

                // Ejercicio2
                List<String> turnosDeGuardia = df.CompararListas(registrosMes, personal);
                //System.out.println(turnosDeGuardia);

                // Ejercicio 3
                String turnos = df.SalarioMensual(registrosMes, personal, "Ana Gómez", 10);
                //System.out.println(turnos);

                // Ejercicio 4
                List<RegistroTurno> validadorTurnos = df.GeneradordeValidadoresdeTurnos(registrosMes, 10, 20);
                //System.out.println(validadorTurnos);

                // Ejercicio 5
                List<Map.Entry<String, Integer>> listadoCargaLaboral = df.ListaPorNombre(registrosMes, personal, 10);
                //listadoCargaLaboral.forEach(System.out::println);

                // Ejercicio 6
                Map<String, Long> ocurrencias = df.CantidadAusencias(registrosMes, 10);
                //ocurrencias.entrySet().forEach(System.out::println);

                // Ejercicio 7
                List<EventoCalendario> transformToCalendar = df.Calendario(registrosMes, "E02");
                //transformToCalendar.forEach(System.out::println);

                // Ejercicio 8
                String cobertura = df.CoberturaMinima(registrosMes, LocalDate.of(2024, 10, 1));
                //System.out.println(cobertura);
                
                //Ejercicio 9
                List<RegistroTurno> turnosLargos = df.TurnoMasLargo(registrosMes, 10);
                //turnosLargos.forEach(System.out::println);
                
                //Ejercicio 10
                Map<String, Double> pagoPorNomina = df.ListaPagos(registrosMes,personal, 10);
                //pagoPorNomina.entrySet().forEach(System.out::println);
        }
}
