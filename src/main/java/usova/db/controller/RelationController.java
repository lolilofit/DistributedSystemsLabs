package usova.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import usova.db.dao.NodeDao;
import usova.db.dao.RelationDao;
import usova.db.repository.RelationJpaRepository;

import java.math.BigInteger;

@Controller
@RequestMapping(value = "/relation")
public class RelationController {
    @Autowired
    private RelationJpaRepository relationJpaRepository;

    @GetMapping(value = "/get")
    @ResponseBody
    public RelationDao getById(@RequestParam(name = "id") BigInteger id) {
        return relationJpaRepository.getRelationDaoById(id);
    }

    @PostMapping(value = "/saveupdate")
    public ResponseEntity<?> saveNode(@RequestBody RelationDao dao) {
        RelationDao relationDao = relationJpaRepository.save(dao);
        return new ResponseEntity<>(relationDao, HttpStatus.OK);
    }

    @DeleteMapping(value = "/del")
    public ResponseEntity<?> deleteNode(@RequestParam(name = "id") BigInteger id) {
        RelationDao relationDao = relationJpaRepository.getRelationDaoById(id);
        relationJpaRepository.delete(relationDao);
        return new ResponseEntity<>(relationDao, HttpStatus.OK);
    }
}
