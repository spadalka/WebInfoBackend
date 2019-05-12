package ca.cmpt213.demo.controller;

import ca.cmpt213.demo.model.Tokimon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

@RestController
public class TokimonController {
    private final AtomicLong counter = new AtomicLong(0);
    private List<Tokimon> tokimons = new ArrayList<>();

    FileWriter fileWriter;
    {
        try {
            fileWriter = new FileWriter("data/tokimon.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean isPresent = false;

    @GetMapping("/api/tokimon/all")
    public List<Tokimon> getAllTokimons(){
        JSONArray allTokimons = new JSONArray();
        for (Tokimon tokimon : tokimons) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tokimon.getId());
            jsonObject.put("name", tokimon.getName());
            jsonObject.put("height",tokimon.getHeight());
            jsonObject.put("weight", tokimon.getWeight());
            jsonObject.put("ability", tokimon.getAbility());
            jsonObject.put("strength", tokimon.getStrength());
            jsonObject.put("color", tokimon.getColor());
            allTokimons.add(jsonObject);
        }
        try (FileWriter file = new FileWriter("data/tokimon.json")) {
            file.write(allTokimons.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokimons;
    }

    @GetMapping("/api/tokimon/{id}")
    public Tokimon getTokimon(@PathVariable long id){
        isPresent = false;
//        System.out.println("GET Tokimon " + id);
        for(Tokimon tokimon : tokimons) {
            if(tokimon.getId()==id) {
                isPresent = true;
                return tokimon;
            }
        }
        if (isPresent==false){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tokimon not found"
            );
        }
        return null;
    }

    @PostMapping("/api/tokimon/add")
    public ResponseEntity addTokimon(@RequestBody Tokimon newTokimon) {
        Tokimon tokimonToAdd = new Tokimon();
        tokimonToAdd.setName(newTokimon.getName());
        tokimonToAdd.setWeight(newTokimon.getWeight());
        tokimonToAdd.setHeight(newTokimon.getHeight());
        tokimonToAdd.setAbility(newTokimon.getAbility());
        tokimonToAdd.setStrength(newTokimon.getStrength());
        tokimonToAdd.setColor(newTokimon.getColor());

        tokimonToAdd.setId(counter.incrementAndGet());
        tokimons.add(tokimonToAdd);
//        System.out.println(newTokimon.getName() + " added!");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/tokimon/{id}")
    public ResponseEntity deleteTokimon(@PathVariable long id){
        isPresent = false;
        for(Tokimon tokimon : tokimons) {
            if(tokimon.getId()==id) {
                isPresent = true;
                tokimons.remove(tokimon);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
//                System.out.println("Tokimon " + id + " deleted");
            }
        }
        if (isPresent == false){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tokimon not found"
            );
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostConstruct
    public void init() {
        File file = new File("data/tokimon.json");
        if (file.length() == 0){
            return;
        }
        JSONParser parser = new JSONParser();
        try {
            Object objects = parser.parse(new FileReader("data/tokimon.json"));
            JSONArray arrayOfObjects =  (JSONArray) objects;
            for(Object singleJSONObject : arrayOfObjects) {
                Gson gson = new Gson();
                String jsonObjectAsString = singleJSONObject.toString();
                tokimons.add(gson.fromJson(jsonObjectAsString, Tokimon.class));
                counter.incrementAndGet();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
