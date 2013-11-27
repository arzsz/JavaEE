package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.jdbc.domain.Kamera;
import com.example.jdbcdemo.domain.KameraManager;
import com.example.jdbc.domain.Sklep;
import com.example.jdbcdemo.domain.SklepManager;

public class KameraManagerTest {

	KameraManager kameraManager = new KameraManager();
	SklepManager sklepManager = new SklepManager();

	@Before
	public void start(){
		Kamera kamera1 = new Kamera(1, "GoPro", "Hero 3", 2, 1899);
		Kamera kamera2 = new Kamera(2, "Pivothead", "Durango", 1, 1499);
		Kamera kamera3 = new Kamera(3, "Pivothead", "Cameleon", 1, 1499);
		
		kameraManager.AddKamera(kamera1);
		kameraManager.AddKamera(kamera2);
		kameraManager.AddKamera(kamera3);
		
		Sklep sklep1 = new Sklep(1, "KameryAkcji");
		Sklep sklep2 = new Sklep(2, "ItWorks");
		
		sklepManager.AddSklep(sklep1);
		sklepManager.AddSklep(sklep2);
	}
	
	@Test
	// testowanie polaczenie do bazy danych
	public void checkConnection() {
		assertNotNull(kameraManager.getConnection());
        assertNotNull(sklepManager.getConnection());
	}
	
	
	@Test
	// sprawdzanie dodawania do bazy
	public void checkAdding() {
		Kamera kamera1 = new Kamera(1, "GoPro", "Hero 3", 2, 1899);
		kameraManager.AddKamera(kamera1);
		Kamera kam = kameraManager.searchKamera(1);
		assertEquals(1, kam.getId());
		assertEquals("GoPro", kam.getName());
		assertEquals("Hero 3", kam.getType());
		assertEquals(2, kam.getSklep());
		assertEquals(1899, kam.getCena());
	}

	@Test
	// dodawanie sklepu
	public void checkAddingSklep() {
		Sklep sklep1 = new Sklep(1, "KameryAkcji");
		sklepManager.AddSklep(sklep1);

		List<Sklep> skleps = sklepManager.WriteAll();

		Sklep tmp = skleps.get(skleps.size() - 1);

		assertEquals(1, tmp.getId_sklep());
		assertEquals("KameryAkcji", tmp.getName_sklep());
	}

	@Test
	// sprawdzanie wyszukiwania
	public void searchChcek() {
		Kamera kamera = new Kamera(2, "Pivothead", "Durango", 1, 1499);
		Kamera tmp = kameraManager.searchKamera(kamera.getId());

		assertEquals(kamera.getId(), tmp.getId());
		assertEquals(kamera.getName(), tmp.getName());
		assertEquals(kamera.getType(), tmp.getType());
		assertEquals(kamera.getSklep(), tmp.getSklep());
		assertEquals(kamera.getCena(), tmp.getCena());
	}

	@Test
	// wyszukiwanie sklepu
	public void searchChcekSklep() {
		Sklep sklep = new Sklep(1, "KameryAkcji");
		Sklep tmp = sklepManager.searchSklep(sklep.getId_sklep());

		assertEquals(sklep.getId_sklep(), tmp.getId_sklep());
		assertEquals(sklep.getName_sklep(), tmp.getName_sklep());
	}

	@Test
	public void checkUpdate() {
		Kamera kamera = new Kamera(1, "GoPro", "Hero 3", 2, 1899);

		kameraManager.UpdateKamera(kamera);

		Kamera tmp = kameraManager.searchKamera(kamera.getId());

		assertEquals(kamera.getId(), tmp.getId());
		assertEquals(kamera.getName(), tmp.getName());
		assertEquals(kamera.getType(), tmp.getType());
		assertEquals(kamera.getSklep(), tmp.getSklep());
		assertEquals(kamera.getCena(), tmp.getCena());
	}

	@Test
	public void checkUpdateSklep() {
		Sklep sklep = new Sklep(1, "KameryAkcji");

		sklepManager.UpdateSklep(sklep);

		Sklep tmp = sklepManager.searchSklep(sklep.getId_sklep());

		assertEquals(sklep.getId_sklep(), tmp.getId_sklep());
		assertEquals(sklep.getName_sklep(), tmp.getName_sklep());
	}

	@Test
	// sprawdzanie usuwania
	public void delCheck() {
		Kamera kamera = new Kamera();
		int id;
		kamera = kameraManager.searchKamera(2);
		id = kamera.getId();
		kameraManager.clearDB(kamera);

		assertNull(kameraManager.searchKamera(id));
	}

	@Test
	public void delCheckSklep() {
		Sklep sklep = new Sklep();
		int id;
		sklep = sklepManager.searchSklep(1);
		id = sklep.getId_sklep();
		sklepManager.clearDB(sklep);

		assertNull(sklepManager.searchSklep(id));
	}
	
//	@After
//	public void dellAll() {
//        kameraManager.delAll();
//	}
}