package de.phip1611.hockeyligamanager;

import de.phip1611.hockeyligamanager.service.api.ExAndImportService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ExAndImportIntegrationTest {

	@Autowired
	private ExAndImportService exAndImportService;

	@Test
	public void textExport() {
		var export = this.exAndImportService.exportData();
		this.exAndImportService.importData(export);
		var export2 = this.exAndImportService.exportData();
		Assert.assertEquals(export.getSpielberichte(), export2.getSpielberichte());
		Assert.assertEquals(export.getSpieler(), export2.getSpieler());
		Assert.assertEquals(export.getStrafEreignisse(), export2.getStrafEreignisse());
		Assert.assertEquals(export.getTeams(), export2.getTeams());
		Assert.assertEquals(export.getTorEreignisse(), export2.getTorEreignisse());
	}

}
