package test;
//this Test Suite is created with help from [https://examples.javacodegeeks.com/core-java/junit/junit-suite-test-example/]

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestBoard.class,
	TestGame.class,
	TestTile.class,
	TestToken.class
})

public class TestAll {
	
}