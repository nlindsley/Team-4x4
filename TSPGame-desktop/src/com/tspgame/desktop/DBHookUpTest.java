package com.tspgame.desktop;
import static org.junit.Assert.*;
import java.sql.*;

import org.junit.Test;

public class DBHookUpTest {
	
	DBHookUp db = new DBHookUp(); 
	Statement stmt = null;

	@Test
	public void testConn() {
		int result = db.connectDB();
		assertTrue(result == 0);
	}
	public void testDisconn(){
		db.connectDB();
		int result = db.disconnect();
		assertTrue(result == 0);
	}
	public void testUpdateandGetScores(){
		db.connectDB();
		db.updateDB(1);
		int[] checker = db.getScores();
		assertTrue(checker[0]==1);
		assertTrue(checker[1]==0);
		assertTrue(checker[2]==0);
		db.disconnect();
	}
	
	public void testEnsureHighestThree(){
		db.connectDB();
		db.updateDB(2);
		db.updateDB(3);
		db.updateDB(4);
		int[] checker = db.getScores();
		assertTrue(checker[0]==4);
		assertTrue(checker[1]==3);
		assertTrue(checker[2]==2);
		db.disconnect();

	}

}
