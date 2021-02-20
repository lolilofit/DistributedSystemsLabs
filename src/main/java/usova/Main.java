package usova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.db.DbManager;
import usova.db.PostgreConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DbManager dbManager = new DbManager();

        logger.debug("Hello, world");
        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();
        XmlResponse response = archiveDecompressor.read();
        response.print();
    }
}
