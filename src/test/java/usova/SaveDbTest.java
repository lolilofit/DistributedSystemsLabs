package usova;

import org.junit.Test;
import usova.db.DbManager;

import java.sql.SQLException;

public class SaveDbTest {
    @Test
    public void testSave() throws SQLException, ClassNotFoundException {
        DbManager dbManager = new DbManager();

        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();
        XmlResponse response = archiveDecompressor.read();

        
    }
}
