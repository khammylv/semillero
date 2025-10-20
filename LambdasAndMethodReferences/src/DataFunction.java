import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataFunction {
    // TODO PREDICATE

    private Predicate<RegistroTurno> PredicateFiltrarTipo(TipoTurno tipoTurno) {
        return turno -> {
            return turno.tipo() == tipoTurno;
        };
    }

    private Predicate<RegistroTurno> PredicateFiltroTipoNegativo(TipoTurno tipoTurno) {
        return turno -> {
            return turno.tipo() != tipoTurno;

        };
    }

    private Predicate<RegistroTurno> filtroPorEmpleado(String id) {
        return turno -> {
            return turno.empleadoId() == id;
        };
    }

    private Predicate<Integer> PredicateHuboCobertura() {
        return n -> n >= 2;
    }

    private Predicate<Empleado> PredicateFiltroPorId(String[] idsArray) {
        // return encontrado = Arrays.asList(palabras).contains(palabraBuscada);
        return emp -> {
            return Arrays.asList(idsArray).contains(emp.id());
        };
    }

    public Predicate<RegistroTurno> PredicateFiltroPorValidacionDeHoras(int validador) {
        return turno -> {

            int horas = turno.horas();

            // 2. Llamar a la función Doublevaltur con el validador y las horas.
            // El valor de retorno (Boolean) de Doublevaltur es el resultado del Predicate.
            return SuperiorAplicarValidacion(validador, horas);
        };
    }

    // TODO FILTER
    private Empleado FilterGetEmpByName(List<Empleado> empleados, String empleado) {
        return empleados
                .stream()
                .filter(emp -> emp.nombre().equals(empleado))
                .findFirst()
                .orElse(null);
    }

    private List<RegistroTurno> FilterFiltrarTipo(List<RegistroTurno> turnos, TipoTurno tipoTurno) {
        return turnos
                .stream()
                .filter(PredicateFiltrarTipo(tipoTurno))
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterFiltrarPorHorasSuperiores(List<RegistroTurno> turnos, int validator) {
        return turnos
                .stream()
                .filter(PredicateFiltroPorValidacionDeHoras(validator))
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterFiltrarTipoNegativo(List<RegistroTurno> turnos, TipoTurno tipoTurno) {
        return turnos
                .stream()
                .filter(PredicateFiltroTipoNegativo(tipoTurno))
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterFiltrarPorEmpleado(List<RegistroTurno> turnos, String id) {
        return turnos
                .stream()
                .filter(filtroPorEmpleado(id))
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterFiltroPorMes(List<RegistroTurno> turnos, int mes) {
        return turnos.stream()
                .filter(obj -> obj.fecha().getMonthValue() == mes)
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterFiltroPorDiayMes(List<RegistroTurno> turnos, LocalDate dia) {
        return turnos.stream()
                .filter(t -> t.fecha().equals(dia))
                .collect(Collectors.toList());
    }

    private List<RegistroTurno> FilterGetTurnosMayores(List<RegistroTurno> turnos, int turno) {
        return turnos.stream()
                .filter(tur -> tur.horas() == turno)
                .collect(Collectors.toList());
    }

    // TODO TRANSFOR DATA

    private Map<String, Integer> TransformHorasPorID(List<RegistroTurno> turnos) {
        return turnos
                .stream()
                .collect(Collectors.groupingBy(
                        RegistroTurno::empleadoId,
                        Collectors.summingInt(RegistroTurno::horas)));
    }

    private Double TransformObtenerDouble(Collection<Integer> horas) {
        return horas.stream()
                .mapToDouble(Integer::doubleValue)
                .sum();

    }

    private Map<String, Integer> TransformMergeListEmpTruno(Map<String, String> empleados,
            Map<String, Integer> turnos) {
        return empleados.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        entry -> turnos.getOrDefault(entry.getKey(), 0),
                        (v1, v2) -> v1));
    }
       private Map<String, Integer> TransformMergeListEmpTruno2(Map<String, String> empleados,
            Map<String, Integer> turnos) {

                /*return empleados.stream()
                .collect(Collectors.toMap(
                        Empleado::nombre,
                        pago -> AuxTurnosNoche(turnos, pago.salarioBaseHora(), pago.id()))); */
        return empleados.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        entry -> turnos.getOrDefault(entry.getKey(), 0),
                        (v1, v2) -> v1));
    }

    public Map<String, List<Integer>> TransformHorasPorUsuario(List<RegistroTurno> turnos) {
        return turnos.stream()
                .collect(Collectors.groupingBy(
                        RegistroTurno::empleadoId,
                        Collectors.mapping(RegistroTurno::horas, Collectors.toList())));
    }

    public Map<String, String> TransformNombreAndId(List<Empleado> empleados) {
        return empleados.stream()
                .collect(Collectors.toMap(
                        Empleado::id,
                        Empleado::nombre));
    }

    /*
     * public Map<LocalDate, String> TransformRegistroCalendario(List<RegistroTurno>
     * turnos) {
     * return turnos.stream()
     * .collect(Collectors.toMap(
     * RegistroTurno::fecha,
     * turno -> turno.tipo().toString(),
     * (valor1, valor2) -> valor1));
     * }
     */
    public List<EventoCalendario> TransformRegistroCalendario(List<RegistroTurno> turnos) {
        return turnos.stream()
                .map(turno -> new EventoCalendario(turno.tipo().name(), turno.fecha()))
                .distinct()
                .toList();

    }

    public Map<String, Double> TransformNomina(List<RegistroTurno> turnos, List<Empleado> empleados) {
        return empleados.stream()
                .collect(Collectors.toMap(
                        Empleado::nombre,
                        pago -> AuxTurnosNoche(turnos, pago.salarioBaseHora(), pago.id())));
    }

    private Collection<Integer> TransforObtenerHoras(Map<String, Integer> horas) {
        return horas.values();
    }

    // TODO FUNCIONES DE ORDEN SUPERIOR
    private Function<Double, Double> crearMultiplicador(double factor) {
        return numero -> numero * factor;
    }

    private Function<Integer, Boolean> ValidadordeTurnos(int turno) {
        return validador -> validador > turno;
    }

    private Boolean SuperiorAplicarValidacion(int validador, int horas) {
        Function<Integer, Boolean> duplicar = ValidadordeTurnos(validador);
        return duplicar.apply(horas);
    }

    private Double SuperiorCalcularHorasNoche(double horas, double factor) {
        Function<Double, Double> duplicar = crearMultiplicador(factor);
        return duplicar.apply(horas);
    }

    // TODO FUNCIONES AUXILIARES

    private String AuxValidarCobertura(int cant) {
        Predicate<Integer> esPar = PredicateHuboCobertura();

        return (esPar.test(cant)) ? "Si Hubo cobertura" : "Cobertura insuficiente";

    }

    private Double AuxTurnosNoche(List<RegistroTurno> turnosMes, double pagoPorHora, String empleadoFiltrado) {
        List<RegistroTurno> turnosEmpleado = FilterFiltrarPorEmpleado(turnosMes, empleadoFiltrado);

        List<RegistroTurno> turnosNocturnos = FilterFiltrarTipo(turnosEmpleado, TipoTurno.NOCHE);
        Map<String, Integer> reporteHorasNocturnas = ReportedeHorasTrabajadas(turnosNocturnos);
        Collection<Integer> horasNoche = TransforObtenerHoras(reporteHorasNocturnas);
        double totalNoche = TransformObtenerDouble(horasNoche);
        double horasNocheAumento = SuperiorCalcularHorasNoche(pagoPorHora, 1.5);
        return SuperiorCalcularHorasNoche(horasNocheAumento, totalNoche);
    }

    private Double AuxTurnosDia(List<RegistroTurno> turnosMes, double pagoPorHora, String empleadoFiltrado) {
        List<RegistroTurno> turnosEmpleado = FilterFiltrarPorEmpleado(turnosMes, empleadoFiltrado);
        List<RegistroTurno> turnosDiferentes = FilterFiltrarTipoNegativo(turnosEmpleado, TipoTurno.NOCHE);
        Map<String, Integer> reporteHoras = ReportedeHorasTrabajadas(turnosDiferentes);
        Collection<Integer> horasDia = TransforObtenerHoras(reporteHoras);
        double totalDia = TransformObtenerDouble(horasDia);
        return SuperiorCalcularHorasNoche(pagoPorHora, totalDia);
    }

    // TODO CLASES PUBLIC

    // EJERCICIO 1
    public Map<String, Integer> ReportedeHorasTrabajadas(List<RegistroTurno> turnos) {

        List<RegistroTurno> filtrados = FilterFiltrarTipoNegativo(turnos, TipoTurno.AUSENCIA);

        return TransformHorasPorID(filtrados);
    }

    // EJERCICIO 2
    public List<String> CompararListas(List<RegistroTurno> turnos, List<Empleado> empleados) {
        List<RegistroTurno> filtrados = FilterFiltrarTipo(turnos, TipoTurno.GUARDIA);

        String[] idsArray = filtrados.stream()
                .map(RegistroTurno::empleadoId)
                .toArray(String[]::new);

        List<Empleado> empleadosFiltrados = empleados
                .stream()
                .filter(PredicateFiltroPorId(idsArray))
                .collect(Collectors.toList());

        return empleadosFiltrados.stream()
                .map(Empleado::nombre)
                .collect(Collectors.toList());

    }

    // EJERCICIO 3
    public String SalarioMensual(List<RegistroTurno> turnos, List<Empleado> empleados, String empleado, int mes) {
        Empleado empleadoFiltrado = FilterGetEmpByName(empleados, empleado);
        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);
        double pagoPorHora = empleadoFiltrado.salarioBaseHora();

        double pagoHorasDia = AuxTurnosDia(turnosMes, pagoPorHora, empleadoFiltrado.id());
        double pagoHorasNoche = AuxTurnosNoche(turnosMes, pagoPorHora, empleadoFiltrado.id());
    
        return "Turnos de noche: " + pagoHorasNoche + " " + "Turnos de día: " + pagoHorasDia;
    }

    // EJERCICIO 4
    public List<RegistroTurno> GeneradordeValidadoresdeTurnos(List<RegistroTurno> turnos, int mes, int validator) {

        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);
        return FilterFiltrarPorHorasSuperiores(turnosMes, validator);

    }

    // EJERCICIO 5
    public List<Map.Entry<String, Integer>> ListaPorNombre(List<RegistroTurno> turnos, List<Empleado> empleados,
            int mes) {
        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);

        Map<String, String> listaEmp = TransformNombreAndId(empleados);
        Map<String, Integer> reporteHoras = TransformHorasPorID(turnosMes);

        Map<String, Integer> mapEmpTurno = TransformMergeListEmpTruno(listaEmp, reporteHoras);

        List<Map.Entry<String, Integer>> listEmpTurno = new ArrayList<>(mapEmpTurno.entrySet());
        Comparator<Map.Entry<String, Integer>> comparatorA = Map.Entry.<String, Integer>comparingByValue().reversed();

        Comparator<Map.Entry<String, Integer>> comparatorB = comparatorA
                .thenComparing((pa, pb) -> pa.getKey().compareTo(pb.getKey()));

        Collections.sort(listEmpTurno, comparatorB);
        return listEmpTurno;

    }

    // EJERCICIO 6
    public Map<String, Long> CantidadAusencias(List<RegistroTurno> turnos, int mes) {
        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);
        List<RegistroTurno> turnosAusencia = FilterFiltrarTipo(turnosMes, TipoTurno.AUSENCIA);

        List<String> listIds = turnosAusencia.stream()
                .map(RegistroTurno::empleadoId)
                .toList();

        return listIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }

    // EJERCICIO 7
    public List<EventoCalendario> Calendario(List<RegistroTurno> turnos, String idEmp) {
        List<RegistroTurno> turnoEmpleado = FilterFiltrarPorEmpleado(turnos, idEmp);

        // Map<LocalDate, String> listaCalendario =
        // TransformRegistroCalendario(turnoEmpleado);

        /*
         * return listaCalendario.entrySet().stream()
         * .map(entry -> new EventoCalendario(entry.getValue(), entry.getKey()))
         * 
         * 
         * .toList();
         */
        return TransformRegistroCalendario(turnoEmpleado);

    }

    // EJERCICIO 8
    public String CoberturaMinima(List<RegistroTurno> turnos, LocalDate dia) {
        List<RegistroTurno> filtrados = FilterFiltrarTipoNegativo(turnos, TipoTurno.AUSENCIA);
        List<RegistroTurno> turnoPorFecha = FilterFiltroPorDiayMes(filtrados, dia);

        return AuxValidarCobertura(turnoPorFecha.size());
    }

    // EJERCICIO 9
    public List<RegistroTurno> TurnoMasLargo(List<RegistroTurno> turnos, int mes) {
        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);
        Comparator<RegistroTurno> comparadorEdadDesc = (p1, p2) -> Integer.compare(p2.horas(), p1.horas());
        turnosMes.sort(comparadorEdadDesc);
        // turnosMes.forEach(System.out::println);

        int primerTurno = turnosMes.getFirst().horas();

        return FilterGetTurnosMayores(turnosMes, primerTurno);

    }

    // EJERCICIO 10
    public Map<String, Double> ListaPagos(List<RegistroTurno> turnos, List<Empleado> empleados, int mes) {
        List<RegistroTurno> turnosMes = FilterFiltroPorMes(turnos, mes);
        Map<String, Double> pagoPorNomina = TransformNomina(turnosMes, empleados);
        pagoPorNomina.entrySet().removeIf(entry -> (entry.getValue() == 0.0));
        return pagoPorNomina;
    }

}
