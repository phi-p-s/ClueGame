package experiment;

import java.util.Collections;
import java.util.Set;

public class TestBoard {
	
	//constructor
	public TestBoard() {
		
	}
	
	//method stubs
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets(){
		return Collections.emptySet();
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row, col);
		return cell;
	}
}
