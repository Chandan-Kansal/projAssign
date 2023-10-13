package com.example.first.Controller;
import java.util.List;
import java.util.Optional;
import com.example.first.Entities.*;

import com.example.logicPart.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.first.BookServices.Bookservice;


@RestController                                                                
public class BookController {
	@Autowired
	Bookservice bookservice;
	ReqEntity reqentity;
    Graph graph = new Graph(1000000);
	
	@PostMapping("/add")
    public void addstate(@RequestBody Book state) {
        this.bookservice.addBook(state);
    }
	
	 @GetMapping("/state")
	    public ResponseEntity<List<Book>> getState() {
	        List<Book> list = this.bookservice.findAll();
	        if (list.size() <= 0) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        for(Book b: list) {
	        	String s = b.getSrc();
	        	String d = b.getDest();
	        	String m = b.getMot();
	        	int p = b.getPrice();
	        	float t = b.getTimereq();
//	        	System.out.println(s+" "+d+" "+m+" "+p+" "+t);
	        	graph.makeGraph(s,d,p,t,m);
	        }
	        System.out.println(graph.minimumTimeRoute(graph.stateMappingNumber.get("Delhi"), graph.stateMappingNumber.get("Katra")));
	        return ResponseEntity.of(Optional.of(list));
	    }
//	 
	    @GetMapping("/CheapestPrice")
	    @ResponseBody
	    public int getMinPrice(@RequestBody ReqEntity reqentity ) {
	    	List<Book> list = this.bookservice.findAll();
//	        if (list.size() <= 0) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	        }
	        for(Book b: list) {
	        	String s = b.getSrc();
	        	String d = b.getDest();
	        	String m = b.getMot();
	        	int p = b.getPrice();
	        	float t = b.getTimereq();
//	        	System.out.println(s+" "+d+" "+m+" "+p+" "+t);
	        	graph.makeGraph(s,d,p,t,m);
	        }
		 	String src1 = reqentity.getSrc();
		 	String desc1 = reqentity.getDest();
		 	System.out.println(src1+" "+desc1);
	        int ans = graph.minimumTimeRoute(graph.stateMappingNumber.get(src1),graph.stateMappingNumber.get(desc1));
//
		 	return ans;
//		 	System.out.println(reqentity.getDest());
//		 	return 0;
//	        List<Book> list = this.bookservice.findAll();
//	        if (list.size() <= 0) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	        }
//	        for(Book b: list) {
//	        	String s = b.getSrc();
//	        	String d = b.getDest();
//	        	String m = b.getMot();
//	        	int p = b.getPrice();
//	        	float t = b.getTimereq();
////	        	System.out.println(s+" "+d+" "+m+" "+p+" "+t);
//	        	graph.makeGraph(s,d,p,t,m);
//	        }
//	        System.out.println(graph.minimumTimeRoute(graph.stateMappingNumber.get("Delhi"), graph.stateMappingNumber.get("Katra")));
//	        return ResponseEntity.of(Optional.of(list));
	    }

	
	 
//	 @GetMapping("/cheapestRoute")
//	 public ResponseEntity<List<Book>> getState() {
//	        List<Book> list = this.bookservice.findAll();
//	        for(Book b: list) {
//	        	System.out.println(b.getSrc());
//	 
//	        }
//	        
//	        if (list.size() <= 0) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//	        }
//	        return ResponseEntity.of(Optional.of(list));
//	    }

}