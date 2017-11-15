package test;
import org.junit.Test;
import server.Database;

import static org.junit.Assert.*;

public class TestDatabase {
    
    private Database db;
    
    @Before
    public void initialize() {
        db = new Database("root", "password", "129.82.44.147", "5123");
    }

    @Test
    public void testUpdateAndQuery() {
        //check that test user does not exist
        String result = db.executeQuery("SELECT * FROM user WHERE username='test' AND email='test@test.test' AND password='test'");
        assertEquals("", result);
        //check that insert works correctly
        db.executeUpdate("INSERT INTO user (username, email, password) VALUES ('test', 'test@test.test', 'test')");
        result = db.executeQuery("SELECT * FROM user WHERE username='test' AND email='test@test.test' AND password='test'");
        assertEquals("test,test@test.test,test|", result);
        //check that delete works correctly/reset user table
        db.executeUpdate("DELETE FROM user WHERE username='test' AND email='test@test.test' AND password='test'");
        result = db.executeQuery("SELECT * FROM user WHERE username='test' AND email='test@test.test' AND password='test'");
        assertEquals("", result);
    }
}
