package sov.program.controllers;

import org.springframework.web.bind.annotation.*;
import sov.program.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ControllerApi {
    private int counter = 4;
    private List<Map<String, String>> data = new ArrayList<>() {{
         add(new HashMap<>() {{ put("id", "1"); put("text", "one"); }} );
         add(new HashMap<>() {{ put("id", "2"); put("text", "two"); }} );
         add(new HashMap<>() {{ put("id", "3"); put("text", "three"); }} );
    }};

    private Map<String,String> getItem(String id){
        return data.stream()
                .filter(item -> item.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<Map<String, String>> list() {
        return data;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getItem(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> item){
        item.put("id", String.valueOf(counter++));
        data.add(item);
        return item;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> item){
        Map<String, String> updateItem = getItem(id);
        updateItem.putAll(item);
        updateItem.put("id", id);
        return updateItem;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> item = getItem(id);
        data.remove(item);
    }


}
