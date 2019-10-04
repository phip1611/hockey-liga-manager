package de.phip1611.hockeyligamanager;

import de.phip1611.hockeyligamanager.service.api.ExAndImportService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ExAndImportIntegrationTest {

	@Autowired
	private ExAndImportService exAndImportService;

	@Autowired
	private DateTimeFormatter formatter;

	@Test
	public void textExportAndImport() {
		var export = this.exAndImportService.exportData();
		this.exAndImportService.importData(export);
		var export2 = this.exAndImportService.exportData();
		Assert.assertEquals(export.getSpielberichte(), export2.getSpielberichte());
		Assert.assertEquals(export.getSpieler(), export2.getSpieler());
		Assert.assertEquals(export.getStrafEreignisse(), export2.getStrafEreignisse());
		Assert.assertEquals(export.getTeams(), export2.getTeams());
		Assert.assertEquals(export.getTorEreignisse(), export2.getTorEreignisse());
	}

	@Test
	public void testDateTimeFormatterBean() {
		String timeString = "2019-11-13 11:30";
		LocalDateTime parsed = LocalDateTime.from(formatter.parse(timeString));
		Assert.assertEquals(parsed.getYear(), 2019);
		Assert.assertEquals(parsed.getMonthValue(), 11);
		Assert.assertEquals(parsed.getDayOfMonth(), 13);
		Assert.assertEquals(parsed.getHour(), 11);
		Assert.assertEquals(parsed.getMinute(), 30);
		Assert.assertEquals(formatter.format(parsed), timeString);
	}

}
