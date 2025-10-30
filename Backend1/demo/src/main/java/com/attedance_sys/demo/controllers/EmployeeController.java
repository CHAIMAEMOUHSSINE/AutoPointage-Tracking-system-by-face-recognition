package com.attedance_sys.demo.controllers;

import com.attedance_sys.demo.Repositories.EmployeeRepository;
import com.attedance_sys.demo.entity.Employee;
import com.attedance_sys.demo.response.CsvResponse;
import com.attedance_sys.demo.response.DashboardResponseEmployee;
import com.attedance_sys.demo.services.EmployeeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "https://localhost:5173")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final EmployeeRepository employeeRepository;

    @PostMapping("/recognize/{id}")
    public ResponseEntity<String> recognize(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Récupérer l’employé et sa photo depuis la BDD
            Employee emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employé introuvable"));
            byte[] refPhoto = emp.getFaceVector(); // photo en BLOB


            // Photo capturée
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return "capture.jpg";
                }
            });

            // Photo de référence depuis la BDD
            body.add("ref", new ByteArrayResource(refPhoto) {
                @Override
                public String getFilename() {
                    return "ref.jpg";
                }
            });

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();

            // Appel à Python
            ResponseEntity<String> response = restTemplate.postForEntity(pythonUrl, requestEntity, String.class);

            // Ici tu peux mettre à jour la présence dans la BDD si c’est un match
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }

    }

    public EmployeeController(EmployeeService employeeService,EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }
    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeService.findAll();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        boolean deleted =employeeService.deleteEmployeeById(id);
        if(deleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }
    @GetMapping("/dashboard/{id}")
    public DashboardResponseEmployee getEmployeeDashboard(@PathVariable Long id){
        return employeeService.getDashboard(id);
    }
    @GetMapping("/csv/{id}")
    public CsvResponse employeeCsv(@PathVariable Long id){
        return new CsvResponse(employeeService.employeeCsv(id));
    }

}
