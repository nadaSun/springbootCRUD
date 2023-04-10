package ma.cigma.springsecurity.presentation.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ma.cigma.springsecurity.domaine.EmpVo;
import ma.cigma.springsecurity.service.IEmpService;

public class EmpRestController {

	@Autowired
	private IEmpService service;

	@GetMapping(value = "/rest/emp", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<EmpVo> getAll() {
		return service.getEmployees();
	}

	@GetMapping(value = "/rest/emp/{id}")
	public ResponseEntity<Object> getEmpById(@PathVariable(value = "id") Long empVoId) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		return new ResponseEntity<>(empVoFound, HttpStatus.OK);
	}

	@PostMapping(value = "/rest/emp")
	public ResponseEntity<Object> createEmp(@Valid @RequestBody EmpVo empVo) {
		service.save(empVo);
		return new ResponseEntity<>("employee is created successfully", HttpStatus.CREATED);
	}

	@PutMapping(value = "/rest/emp/{id}")
	public ResponseEntity<Object> updateEmp(@PathVariable(name = "id") Long empVoId, @RequestBody EmpVo empVo) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		empVo.setId(empVoId);
		service.save(empVo);
		return new ResponseEntity<>("Employee is updated successsfully", HttpStatus.OK);
	}

	@DeleteMapping(value = "/rest/emp/{id}")
	public ResponseEntity<Object> deleteEmp(@PathVariable(name = "id") Long empVoId) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		service.delete(empVoId);
		return new ResponseEntity<>("Employee is deleted successsfully", HttpStatus.OK);
	}

	@GetMapping(value = "/rest/sort/{fieldName}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<EmpVo> sortBy(@PathVariable String fieldName) {
		return service.sortBy(fieldName);
	}

	@GetMapping("/rest/pagination/{pageid}/{size}")
	public List<EmpVo> pagination(@PathVariable int pageid, @PathVariable int size, Model m) {
		return service.findAll(pageid, size);
	}

}
