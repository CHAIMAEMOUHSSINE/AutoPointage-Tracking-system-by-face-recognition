package com.attedance_sys.demo.services;

import com.attedance_sys.demo.Repositories.EmployeeRepository;
import com.attedance_sys.demo.entity.Employee;
import com.attedance_sys.demo.entity.Pointage;
import com.attedance_sys.demo.response.DashboardResponseEmployee;
import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public DashboardResponseEmployee getDashboard(Long id) {
        List<Pointage> pointages=employeeRepository.findById(id).get().getPointages();
        DashboardResponseEmployee dashboardResponseEmployee=new DashboardResponseEmployee();
        double totalHeures = 0.0;

        // Obtenir la semaine et l’année actuelles
        LocalDate aujourdHui = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int semaineActuelle = aujourdHui.get(weekFields.weekOfWeekBasedYear());
        int anneeActuelle = aujourdHui.getYear();

        for (Pointage p : pointages) {
            LocalDateTime arrivee = p.getArrivalTime();
            LocalDateTime depart = p.getDepartureTime();

            int semainePointage = arrivee.get(weekFields.weekOfWeekBasedYear());
            int anneePointage = arrivee.getYear();

            // Comparer avec la semaine actuelle
            if (semainePointage == semaineActuelle && anneePointage == anneeActuelle) {
                Duration duree = Duration.between(arrivee, depart);
                totalHeures += duree.toMinutes() / 60.0;
            }
        }
        dashboardResponseEmployee.setHeures_Hebdomadaires(Double.toString(totalHeures));
        dashboardResponseEmployee.setRestant(Double.toString(40-totalHeures));
        LocalDate today = LocalDate.now();
        LocalTime heureArriveeAujourdhui = pointages.stream()
                .filter(p -> p.getArrivalTime().toLocalDate().isEqual(today))
                .map(p -> p.getArrivalTime().toLocalTime())
                .findFirst()
                .orElse(null);
        if(heureArriveeAujourdhui!=null){
        dashboardResponseEmployee.setHeure_Arrivee(heureArriveeAujourdhui.toString());
        dashboardResponseEmployee.setHeures_totales(Long.toString(Duration.between(LocalTime.now(), heureArriveeAujourdhui).abs().toHours()));}

        else{
            dashboardResponseEmployee.setHeure_Arrivee("Veuillez pointer s'il vous plait ");
            dashboardResponseEmployee.setHeures_totales("0h de travail aujourd'hui");
        }
        LocalDate hier = LocalDate.now().minusDays(1);

        LocalDateTime localDateTimeHier= pointages.stream()
                .filter(p -> p.getArrivalTime().toLocalDate().isEqual(hier))
                .map(Pointage::getArrivalTime)
                .findFirst().orElse(null);
        if (localDateTimeHier != null) {


        dashboardResponseEmployee.setDepart_Hier(localDateTimeHier.toString());
        LocalDateTime localDateTimeDepartHier= pointages.stream()
                .filter(p -> p.getDepartureTime().toLocalDate().isEqual(hier))
                .map(Pointage::getDepartureTime)
                .findFirst().orElse(null);
        dashboardResponseEmployee.setArrivee_Hier(localDateTimeHier.toString());
        dashboardResponseEmployee.setDepart_Hier(localDateTimeDepartHier.toString());}
        dashboardResponseEmployee.setArrivee_Hier("Abscent");
        dashboardResponseEmployee.setDepart_Hier("Abscent");

        return dashboardResponseEmployee;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Employee addEmployee(Employee employee) {
       return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).get();
    }
    public boolean deleteEmployeeById(Long id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public String employeeCsv(Long id){
        List < Pointage > pointages=getEmployeeById(id).getPointages();
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        sb.append("<tr>");
        sb.append("<th>arrivalTime</th>");
        sb.append("<th>departureTime</th>");
        sb.append("<th>Date</th>");
        sb.append("<th>statut</th>");
        sb.append("</tr>");

        for (Pointage p : pointages) {
            sb.append("<tr>");
            sb.append("<td>").append(p.getArrivalTime()).append("</td>");
            sb.append("<td>").append(p.getDepartureTime()).append("</td>");
            sb.append("<td>").append(p.getDate()).append("</td>");
            sb.append("<td>").append(p.getStatut()).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        // Encodage Base64
        String base64 = Base64.getEncoder()
                .encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));

        return base64;
    }

}



