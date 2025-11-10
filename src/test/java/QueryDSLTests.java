import Entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import QueryDSL.QueryDSLLogic;
import Reusables.Operators;
import Reusables.SQLDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QueryDSLTests {
    private EntityManager entityManager;
    private QueryDSLLogic queryDSLLogic;

    @Before
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-persistence-unit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        queryDSLLogic = new QueryDSLLogic();
        queryDSLLogic.setEntityManager(entityManager);
    }

    @After
    public void cleanup() {
        if (entityManager != null) {
            entityManager.close();
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    public void searchBundeslandQueryDSL() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        queryDSLLogic.save(bayern);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));
        List<Bundesland> results = queryDSLLogic.searchEntity(QBundesland.bundesland, params);


        assertEquals("Should be 1 Bundesland", 1, results.size());
        assertEquals("Bundesland should be Bayern", "Bayern", results.get(0).getName());
    }

    @Test
    public void searchBundeslandQueryDSLIsNull() {
        Produkt produkt = new Produkt();
        produkt.setName("produkt");
        queryDSLLogic.save(produkt);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("preis", Operators.IS_NULL.toString()));
        List<Produkt> results = queryDSLLogic.searchEntity(QProdukt.produkt, params);


        assertEquals("Produkt should be 1", 1, results.size());
        assertEquals("Preis should be empty", null, results.get(0).getPreis());
    }

    @Test
    public void searchProduktQueryDSLLessThan() {
        Produkt produkt = new Produkt();
        produkt.setPreis(2.50);
        queryDSLLogic.save(produkt);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("preis", Operators.LESS_THAN.toString(), 5.00));

        List<Produkt> results = queryDSLLogic.searchEntity(QProdukt.produkt, params);

        assertEquals("Should be 1 Produkte", 1, results.size());
        assertEquals("Preis should be 2.50", 2.50, results.get(0).getPreis(), 0.01);
    }

    @Test
    public void searchBundeslandQueryDSLInvalidOperations() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        queryDSLLogic.save(bayern);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", "abc", "Bayern"));
        assertThrows("IllegalArgumentException should be thrown", IllegalArgumentException.class, () -> queryDSLLogic.searchEntity(QBundesland.bundesland, params));

        List<SQLDefinition> params1 = new ArrayList<>();
        params1.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern", "abc"));
        assertThrows(IllegalArgumentException.class, () -> queryDSLLogic.searchEntity(QBundesland.bundesland, params1));
    }

    @Test
    public void searchBundeslandQueryDSLNegation() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        Bundesland badenWuerttemberg = new Bundesland();
        badenWuerttemberg.setName("Baden-Wuerttemberg");
        queryDSLLogic.save(bayern);
        queryDSLLogic.save(badenWuerttemberg);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern", "not"));
        List<Bundesland> results = queryDSLLogic.searchEntity(QBundesland.bundesland, params);


        assertEquals("Bundesland should be 1", 1, results.size());
        assertEquals("Bundesland should be Baden-Wuerttemberg", "Baden-Wuerttemberg", results.get(0).getName());
    }

    @Test
    public void searchProduktMultipleConditionsQueryDSL() {
        Produkt apfel = new Produkt();
        apfel.setName("Apfel");
        apfel.setPreis(14.99);
        Produkt tomate = new Produkt();
        tomate.setName("Tomate");
        queryDSLLogic.save(apfel);
        queryDSLLogic.save(tomate);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Apfel"));
        params.add(new SQLDefinition("preis", Operators.EQUAL.toString(), "14.99"));
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Tomate", "OR"));

        List<Produkt> results = queryDSLLogic.searchEntity(QProdukt.produkt, params);

        assertEquals("Should be 2 Produkte", 2, results.size());
        assertEquals("Produkt should be Apfel", "Apfel", results.get(0).getName());
        assertEquals("Preis should be 14.99", 14.99, results.get(0).getPreis(), 0.01);
        assertEquals("Second Produkt should be Tomate", "Tomate", results.get(1).getName());
    }

    @Test
    public void searchLandkreisSimpleLeftJoinToBundeslandQueryDSL() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        queryDSLLogic.save(bayern);

        Landkreis fuerstenfeldbruck = new Landkreis();
        fuerstenfeldbruck.setName("Fuerstenfeldbruck");
        fuerstenfeldbruck.setBundesland(bayern);
        queryDSLLogic.save(fuerstenfeldbruck);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));

        List<Landkreis> results = queryDSLLogic.searchEntityJoin(QLandkreis.landkreis,"Bundesland", params);

        assertEquals("Should be 1 Landkreis", 1, results.size());
        assertEquals("Landkreis should be Fuerstenfeldbruck", "Fuerstenfeldbruck", results.get(0).getName());
        assertEquals("Bundesland of Fuerstenfeldbruck should be Bayern", "Bayern", results.get(0).getBundesland().getName());
    }

    @Test
    public void searchLandkreisSimpleLeftJoinToInvalidEntityQueryDSL() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        queryDSLLogic.save(bayern);

        Landkreis fuerstenfeldbruck = new Landkreis();
        fuerstenfeldbruck.setName("Fuerstenfeldbruck");
        fuerstenfeldbruck.setBundesland(bayern);
        queryDSLLogic.save(fuerstenfeldbruck);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));

        assertThrows("IllegalArgumentException should be thrown", IllegalArgumentException.class, () -> queryDSLLogic.searchEntityJoin(QLandkreis.landkreis, "ort", params));
    }

    @Test
    public void searchKundeNestedLeftJoinToBundeslandQueryDSL() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        queryDSLLogic.save(bayern);

        Landkreis fuerstenfeldbruck = new Landkreis();
        fuerstenfeldbruck.setName("Fuerstenfeldbruck");
        fuerstenfeldbruck.setBundesland(bayern);
        queryDSLLogic.save(fuerstenfeldbruck);

        Ort maisach = new Ort();
        maisach.setName("Maisach");
        maisach.setPlz("82216");
        maisach.setLandkreis(fuerstenfeldbruck);
        queryDSLLogic.save(maisach);

        Kunde kunde = new Kunde();
        kunde.setName("Samantha");
        kunde.setOrt(maisach);
        queryDSLLogic.save(kunde);

        Bestellung bestellung = new Bestellung();
        LocalDate bestDat = LocalDate.of(2012, 9, 18);
        LocalDate liefDat = LocalDate.of(2012, 9, 21);
        bestellung.setBestDat(bestDat);
        bestellung.setLiefDat(liefDat);
        bestellung.setKunde(kunde);
        queryDSLLogic.save(bestellung);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));

        List<Bestellung> results = queryDSLLogic.searchEntityJoin(QBestellung.bestellung, "bundesland", params);

        assertEquals("Should be 1 Bestellung", 1, results.size());
        assertEquals("Bestellung bestDat should be 2012-09-18", bestDat, results.get(0).getBestDat());
        assertEquals("Kunde should be Samantha", "Samantha", results.get(0).getKunde().getName());
        assertEquals("Ort should be Maisach", "Maisach", results.get(0).getKunde().getOrt().getName());

        assertEquals("Landkreis should be Fuerstenfeldbruck",
                "Fuerstenfeldbruck", results.get(0).getKunde().getOrt().getLandkreis().getName());

        assertEquals("Bundesland should be Bayern",
                "Bayern", results.get(0).getKunde().getOrt().getLandkreis().getBundesland().getName());
    }

    /*@Test
    public void queryDSLPerformanceTest() {
        for (int i = 0; i < 1000000; i++) {
            Bundesland bundesland = new Bundesland();
            bundesland.setName("Bundesland" + i);
            queryDSLLogic.save(bundesland);
        }

        List<SQLDefinition> params = new ArrayList<>();

        params.add(new SQLDefinition("name", Operators.LIKE.toString(), "B"));

        // searchEntity() takes between 0.7 and 1 seconds with 1 million entries in CriteriaBuilder
        // searchEntity() takes between 1.2 and 1.5 seconds with 1 million entries in QueryDSL
        // -> CriteriaBuilder is slightly faster, but it doesn't make much of a difference
        long startTime = System.currentTimeMillis();
        List<Bundesland> results = queryDSLLogic.searchEntity(QBundesland.bundesland, params);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }*/
}
