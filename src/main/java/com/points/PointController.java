package com.points;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.points.domain.DeductionRecord;
import com.points.domain.Record;
import com.points.domain.RecordResult;

@RestController
public class PointController {
	@Autowired PointService service;
	
	@PostMapping("/addPoints")
	public ResponseEntity<?> addPoints(@RequestBody Record record){
		Record rec = service.save(record);
		System.out.println(record);
		if(rec == null) {
			return new ResponseEntity<String> ("You do not have enough points to spend", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Record> (rec, HttpStatus.CREATED);	
	}
	
	@PostMapping("/deductPoints/{deduction}")
	public ResponseEntity<List<DeductionRecord>> addPoints(@PathVariable int deduction){
		return new ResponseEntity<List<DeductionRecord>> (service.deduction(deduction), HttpStatus.CREATED);	
	}
	
	@GetMapping("/deductPoints/")
	public ResponseEntity<List<RecordResult>> getAllRecordsAfterDeduction(){
		return new ResponseEntity<List<RecordResult>> (service.getAllRecordsAfterDeduction(), HttpStatus.CREATED);	
	}
}
