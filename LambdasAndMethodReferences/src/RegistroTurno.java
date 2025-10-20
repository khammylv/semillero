import java.time.LocalDate;

public record RegistroTurno(String empleadoId, LocalDate fecha, TipoTurno tipo, int horas) {
}
