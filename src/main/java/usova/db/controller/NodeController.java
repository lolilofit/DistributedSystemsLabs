package usova.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import usova.db.dao.NodeDao;
import usova.db.repository.NodeJpaRepository;
import usova.db.sevice.DistanceService;

import java.math.BigInteger;

@Controller
@RequestMapping(value = "/node")
public class NodeController {
    @Autowired
    private DistanceService distanceService;

    @Autowired
    private NodeJpaRepository nodeJpaRepository;

    @GetMapping(value = "/dist")
    @ResponseBody
    public NodeDao[] getNearestNodes(@RequestParam(name = "x") Float x, @RequestParam(name = "y") Float y, @RequestParam(name = "r") Float r) {
        return distanceService.getNodeInRadius(x, y, r);
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public NodeDao getById(@RequestParam(name = "id") BigInteger id) {
        return nodeJpaRepository.getNodeDaoById(id);
    }

    @PostMapping(value = "/saveupdate")
    public ResponseEntity<?> saveNode(@RequestBody NodeDao nodeDao) {
        NodeDao node = nodeJpaRepository.save(nodeDao);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @DeleteMapping(value = "/del")
    public ResponseEntity<?> deleteNode(@RequestParam(name = "id") BigInteger id) {
        NodeDao nodeDao = nodeJpaRepository.getNodeDaoById(id);
        nodeJpaRepository.delete(nodeDao);
        return new ResponseEntity<>(nodeDao, HttpStatus.OK);
    }
}
