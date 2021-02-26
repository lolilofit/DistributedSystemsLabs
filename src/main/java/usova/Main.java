package usova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import usova.db.TimeMeasureImpl;

import java.sql.SQLException;

@SpringBootApplication
public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(Main.class, args);
    }
}
