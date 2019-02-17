package com.hackdfw.serverm;

import com.hackdfw.serverm.tests.EventTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GameServerTest
{
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(EventTestCase.class);
		
		return suite;
	}
}