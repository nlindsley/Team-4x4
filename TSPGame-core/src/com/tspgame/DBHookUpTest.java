package com.tspgame;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.ArrayList;

import org.junit.Test;

public class DBHookUpTest {
	
	DBHookUp db = new DBHookUp(); 
	Statement stmt = null;

	@Test
	public void testConn() {
		int result = db.connectDB();
		assertTrue(result == 0);
	}
	@Test
	public void testDisconn(){
		db.connectDB();
		int result = db.disconnect();
		assertTrue(result == 0);
	}
	@Test
	public void testUpdateandGetScores(){
		db.updateDB(1);
		ArrayList<Integer> checker =db.getScores();
		System.out.println(checker.get(0));
		assertTrue(checker.get(0)==4);
		assertTrue(checker.get(1)==4);
		assertTrue(checker.get(2)==4);
	}
	@Test
	public void testEnsureHighestThree(){
		db.updateDB(2);
		db.updateDB(3);
		db.updateDB(4);
		ArrayList<Integer> checker = db.getScores();
		assertTrue(checker.get(0)==4);
		assertTrue(checker.get(1)==4);
		assertTrue(checker.get(2)==4);
	}

}
