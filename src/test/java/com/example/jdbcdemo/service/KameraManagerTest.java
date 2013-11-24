package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;

import com.example.jdbc.domain.Kamera;
import com.example.jdbcdemo.domain.KameraManager;
import com.example.jdbc.domain.Sklep;
import com.example.jdbcdemo.domain.SklepManager;

public class KameraManagerTest {

	KameraManager kameraManager = new KameraManager();
	SklepManager sklepManager = new SklepManager();

	@Test
	// testowanie polaczenie do bazy danych
	public void checkConnection() {
		assertNotNull(kameraManager.getConnection());
        assertNotNull(sklepManager.getConnection());
	}

	@Test
	// sprawdzanie dodawania do bazy
	public void checkAdding() {
		Kamera kamera = new Kamera(1, "Pivothead", "Durango", 1, 10);

		kameraManager.AddKamera(kamera);

		List<Kamera> kameras = kameraManager.WriteAll();

		Kamera tmp = kameras.get(kameras.size() - 1);

		assertEquals(1, tmp.getId());
		assertEquals("Pivothead", tmp.getName());
		assertEquals("Durango", tmp.getType());
		assertEquals(1, tmp.getSklep());
		assertEquals(10, tmp.getCena());
	}

	// dodawanie sklepu
	public void checkAddingSklep() {
		Sklep sklep = new Sklep(1, "Jedynka");

		sklepManager.AddSklep(sklep);

		List<Sklep> skleps = sklepManager.WriteAll();

		Sklep tmp = skleps.get(skleps.size() - 1);

		assertEquals(1, tmp.getId_sklep());
		assertEquals("Pivothead", tmp.getName());
	}

	@Test
	// sprawdzanie wyszukiwania
	public void searchChcek() {
		Kamera kamera = new Kamera(1, "Pivothead", "Durango", 1, 10);
		Kamera tmp = kameraManager.searchKamera(kamera.getId());

		assertEquals(kamera.getId(), tmp.getId());
		assertEquals(kamera.getName(), tmp.getName());
		assertEquals(kamera.getType(), tmp.getType());
		assertEquals(kamera.getSklep(), tmp.getSklep());
		assertEquals(kamera.getCena(), tmp.getCena());
	}

	// wyszukiwanie sklepu
	public void searchChcekSklep() {
		Sklep sklep = new Sklep(1, "Jedynka");
		Sklep tmp = sklepManager.searchSklep(sklep.getId_sklep());

		assertEquals(sklep.getId_sklep(), tmp.getId_sklep());
		assertEquals(sklep.getName(), tmp.getName());
	}

	@Test
	public void checkUpdate() {
		Kamera kamera = new Kamera(1, "GoPro", "Hero 3", 2, 18);

		kameraManager.UpdateKamera(kamera);

		Kamera tmp = kameraManager.searchKamera(kamera.getId());

		assertEquals(kamera.getId(), tmp.getId());
		assertEquals(kamera.getName(), tmp.getName());
		assertEquals(kamera.getType(), tmp.getType());
		assertEquals(kamera.getSklep(), tmp.getSklep());
		assertEquals(kamera.getCena(), tmp.getCena());
	}

	public void checkUpdateSklep() {
		Sklep sklep = new Sklep(1, "Media");

		sklepManager.UpdateSklep(sklep);

		Sklep tmp = sklepManager.searchSklep(sklep.getId_sklep());

		assertEquals(sklep.getId_sklep(), tmp.getId_sklep());
		assertEquals(sklep.getName(), tmp.getName());
	}

	@Test
	// sprawdzanie usuwania
	public void delCheck() {
		Kamera kamera = new Kamera();
		int id;
		kamera = kameraManager.searchKamera(2);
		id = kamera.getId();
		kameraManager.delAll(kamera);

		assertNull(kameraManager.searchKamera(id));
	}

	public void delCheckSklep() {
		Sklep sklep = new Sklep();
		int id;
		sklep = sklepManager.searchSklep(1);
		id = sklep.getId_sklep();
		sklepManager.clearDB(sklep);

		assertNull(sklepManager.searchSklep(id));
	}
}