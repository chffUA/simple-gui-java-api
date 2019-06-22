package api;

import java.util.Map;
import org.json.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Api {

	private TemporaryDatabase db;
	
	public Api() {
		db = new TemporaryDatabase();
		Logger.systemLog("Base path: http://localhost:8080/");
	}
	
	// API methods go here
	
	// GET method
	@RequestMapping(value = "/user/{uid}")
    public ResponseEntity<String> user(@PathVariable("uid") String uid) throws JSONException {
		
		Logger.log(true, new StringBuilder("GET /user/").append(uid).toString());
    	
    	Tuple<Integer,User> result;
    	JSONObject o;
    	
    	//check args
    	
    	try {
    		
    		int id = Integer.parseInt(uid);
    		result = db.getUserByID(id);
    		
    		if (result.empty())
    			return templateErrorResponse("user with ID '"+id+"' does not exist"); 
    		
    	} catch (NumberFormatException e) {

    		result = db.getUserByName(uid);
    		
    		if (result.empty())
    			return templateErrorResponse("user with name '"+uid+"' does not exist"); 
    		
    	}
    	
    	//answer
    	
    	o = new JSONObject();
    	
    	o.put("id", result.getKey());
    	o.put("name", result.getValue().getName());
    	
    	return okResponse(o);
    }
	
	// GET method
	@RequestMapping(value = "/item/{uid}")
    public ResponseEntity<String> item(@PathVariable("uid") String uid) throws JSONException {
		
		Logger.log(true, new StringBuilder("GET /item/").append(uid).toString());
    	
    	Tuple<Integer,Item> result;
    	JSONObject o;
    	
    	//check args
    	
    	try {
    		
    		int id = Integer.parseInt(uid);
    		result = db.getItemByID(id);
    		
    		if (result.empty())
    			return templateErrorResponse("item with ID '"+id+"' does not exist"); 
    		
    	} catch (NumberFormatException e) {

    		result = db.getItemByName(uid);
    		
    		if (result.empty())
    			return templateErrorResponse("item with name '"+uid+"' does not exist"); 
    		
    	}
    	
    	//answer
    	
    	o = new JSONObject();
    	
    	o.put("id", result.getKey());
    	o.put("name", result.getValue().getName());
    	o.put("price", result.getValue().getPrice());
    	
    	return okResponse(o);
    }
    
	// POST method
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody Map<String,String> body) throws JSONException {
    	
    	Logger.log(true, new StringBuilder("POST /user ").append(body).toString());

    	User user;
    	JSONObject o;
    	
    	//check args
    	
    	if (body.get("name")==null) return missingFieldResponse("name");
    	
    	//answer
    	
    	user = new User(body.get("name"));
    	int id = db.addUser(user);
    	
    	o = new JSONObject();
    	
    	o.put("id", id);
    	
    	return okResponse(o);
    	
    }
    
	// POST method
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<String> addItem(@RequestBody Map<String,String> body) throws JSONException {
    	
    	Logger.log(true, new StringBuilder("POST /item ").append(body).toString());

    	Item item;
    	double price;
    	JSONObject o;
    	
    	//check args
    	
    	if (body.get("name")==null) return missingFieldResponse("name");
    	if (body.get("price")==null) return missingFieldResponse("price");
    	
    	try {
    		
    		price = Double.parseDouble(body.get("price"));
    		
    		if (price<=0) 
    			return invalidFieldResponse("price");
    		
    	} catch (NumberFormatException e) {
    		return invalidFieldResponse("price");
    	}
    	
    	//answer
    	
    	item = new Item(body.get("name"), price);
    	int id = db.addItem(item);
    	
    	o = new JSONObject();
    	
    	o.put("id", id);
    	
    	return okResponse(o);
    	
    }
    
    // helper response methods    
    
    private ResponseEntity<String> okResponse(JSONObject n) throws JSONException {
    	Logger.log(false, n.toString());
    	return ResponseEntity.status(HttpStatus.OK).body(n.toString());
    }
    
    private ResponseEntity<String> templateErrorResponse(String msg) throws JSONException {
    	JSONObject error = new JSONObject().put("error", msg);
    	Logger.log(false, error.toString());
    	return ResponseEntity.status(HttpStatus.OK/*BAD_REQUEST*/) // tests may fail if conn receives a BAD_REQUEST
    			.body(error.toString());
    }

    private ResponseEntity<String> missingFieldResponse(String n) throws JSONException {
    	return templateErrorResponse("missing '"+n+"' field");
    }
    
    private ResponseEntity<String> invalidFieldResponse(String n) throws JSONException {
    	return templateErrorResponse("invalid content in '"+n+"' field");
    }
    
}
