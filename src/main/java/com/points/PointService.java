package com.points;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;
import com.points.domain.DeductionRecord;
import com.points.domain.LinkRecord;
import com.points.domain.Record;
import com.points.domain.RecordResult;

@Service
public class PointService {
	Map<String, PriorityQueue<LinkRecord>> recordsMap = new HashMap<>();
	Map<Integer,Record> RecordsOrderedByInsertionMap = new LinkedHashMap<>();
	static int id;	
	
	public Record save(Record record) {
		//if points > 0, add record to both maps
		if(record.getPoints() > 0) {
			id += 1;
			//add record to RecordsOrderedByInsertionMap
			RecordsOrderedByInsertionMap.put(id, record);
			LinkRecord rec = new LinkRecord();
			rec.setId(id);
			rec.setPoints(record.getPoints());
			rec.setTs(record.getTransactionDate());
			
			//add points, the record id from RecordsOrderedByInsertionMap, and time stamp to recordsMap	
			PriorityQueue<LinkRecord> pq = new PriorityQueue<>(new Comparator<LinkRecord>() {
				public int compare(LinkRecord o1, LinkRecord o2) {
					return (int)(o1.getTs().getTime() - o2.getTs().getTime());
				}
			});
			if(recordsMap.containsKey(record.getName())) pq = recordsMap.get(record.getName());
			pq.add(rec);
			recordsMap.put(record.getName(), pq);
		
		//if points < 0, subtract from recordsMap and update RecordsOrderedByInsertionMap using id
		}else if(record.getPoints() < 0){
			int remainPoints = 0;
			if(recordsMap.containsKey(record.getName())) {
				remainPoints = recordsMap.get(record.getName()).peek().getPoints() + record.getPoints();
				while(remainPoints < 0 ) {
					if(!recordsMap.get(record.getName()).isEmpty()) {
						remainPoints = recordsMap.get(record.getName()).peek().getPoints() - Math.abs(remainPoints);
						if(remainPoints < 0) {
							//remove the record that contains the id
							RecordsOrderedByInsertionMap.remove(recordsMap.get(record.getName()).peek().getId()); 
							recordsMap.get(record.getName()).poll();
						}
						else if(remainPoints == 0) {
							//remove the record that contains the id
							RecordsOrderedByInsertionMap.remove(recordsMap.get(record.getName()).peek().getId());
							recordsMap.get(record.getName()).poll();
							break;					
						}
					}else {
						return null; //if get null => don't have enough points to spend
					}
				}
				if(remainPoints > 0) {
					Record r = RecordsOrderedByInsertionMap.get(recordsMap.get(record.getName()).peek().getId());
					r.setPoints(remainPoints); //update points on the record
					RecordsOrderedByInsertionMap.put(recordsMap.get(record.getName()).peek().getId(), r);
					recordsMap.get(record.getName()).peek().setPoints(remainPoints);
				}
			}else {
				return null; // don't have points to spend
			}
		}

		return record;
	}
	
	
	public List<DeductionRecord> deduction(int totalDeductPoints){	
		List<DeductionRecord> deductionList = new ArrayList<>();
		List<Integer> keysToBeRemoved = new ArrayList<>();
		for(Integer key:RecordsOrderedByInsertionMap.keySet()) {
			Record rec = RecordsOrderedByInsertionMap.get(key);
			totalDeductPoints -= rec.getPoints();
			
			//remove and update record on recordsMap
			String name = rec.getName();		
			recordsMap.get(name).removeIf(o -> o.getId() == key);
			if(totalDeductPoints >= 0) {
				DeductionRecord dr = new DeductionRecord();
				dr.setRecName(name);
				dr.setRecPoint(rec.getPoints()*(-1));
				dr.setNow("now");
				deductionList.add(dr);
				keysToBeRemoved.add(key);
								
			}else {	
				LinkRecord lr = new LinkRecord();
				lr.setId(key);
				lr.setPoints(rec.getPoints());
				lr.setTs(rec.getTransactionDate());
				lr.setPoints(Math.abs(totalDeductPoints));
	
				PriorityQueue<LinkRecord> pq = recordsMap.get(name);
				pq.add(lr);
				
				DeductionRecord dr = new DeductionRecord();
				dr.setRecName(name);
				dr.setRecPoint(Math.abs(totalDeductPoints) - rec.getPoints());
				dr.setNow("now");
				deductionList.add(dr);
				
				rec.setPoints(Math.abs(totalDeductPoints));
				RecordsOrderedByInsertionMap.put(key, rec);	 
				break;
			}
		}
		
		for(Integer key: keysToBeRemoved) {
			RecordsOrderedByInsertionMap.remove(key);
		}
		
		return deductionList;
	}
	
	public List<RecordResult> getAllRecordsAfterDeduction(){
		List<RecordResult> res = new ArrayList<>();
		PriorityQueue<LinkRecord> temp = new PriorityQueue<>(new Comparator<LinkRecord>() {
			public int compare(LinkRecord o1, LinkRecord o2) {
				return (int)(o1.getTs().getTime() - o2.getTs().getTime());
			}
		});
		
		for(String key:recordsMap.keySet()) {
			PriorityQueue<LinkRecord> pq = recordsMap.get(key);
			int totalPoints = 0;
			while(!pq.isEmpty()) {
				totalPoints += pq.peek().getPoints();
				temp.add(pq.poll());
			}
			recordsMap.put(key, temp);
			RecordResult rec = new RecordResult();
			rec.setRecName(key);
			rec.setRecPoint(totalPoints);
			res.add(rec);
		}
		return res;
	}
	
}
