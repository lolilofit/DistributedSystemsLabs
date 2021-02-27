package usova.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import usova.db.dao.WayDao;
import usova.db.repository.WayJpaRepository;

import java.math.BigInteger;

@Controller
@RequestMapping(value = "/way")
public class WayController {
    @Autowired
    private WayJpaRepository wayJpaRepository;

    @GetMapping(value = "/get")
    @ResponseBody
    public WayDao getById(@RequestParam(name = "id") BigInteger id) {
        return wayJpaRepository.getWayDaoById(id);
    }

    @PostMapping(value = "/saveupdate")
    public ResponseEntity<?> saveNode(@RequestBody WayDao wayDao) {
        WayDao way = wayJpaRepository.save(wayDao);
        return new ResponseEntity<>(way, HttpStatus.OK);
    }

    @DeleteMapping(value = "/del")
    public ResponseEntity<?> deleteNode(@RequestParam(name = "id") BigInteger id) {
        WayDao wayDao = wayJpaRepository.getWayDaoById(id);
        wayJpaRepository.delete(wayDao);
        return new ResponseEntity<>(wayDao, HttpStatus.OK);
    }
}
