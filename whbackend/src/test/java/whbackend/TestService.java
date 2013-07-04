package whbackend;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.example.domain.WHService;
import com.example.domain.WorkEntry;

public class TestService {

	@Test
	public void test() {
		WHService whService = new WHService();
		
		List<WorkEntry> workEntries = whService.getWorkEntries(new Date());
		assertEquals(1, workEntries.size());
		WorkEntry workEntry = new WorkEntry();
		workEntry.setDate(new Date());
		whService.saveOrPersist(workEntry);
		workEntries = whService.getWorkEntries(new Date());
		assertEquals(2, workEntries.size());
		
	}

}
