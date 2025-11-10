# Dynamic Filtering

A Java project demonstrating advanced filtering capabilities for JPA entities using both **JPA Criteria API** or **QueryDSL**.

## Installation & Running

```bash
# Clone and build
git clone https://github.com/dennismuehlegger/dynamic-filtering.git
cd dynamic-filtering
mvn clean install
```

## Features

- **Dual Implementation Approach**
  - JPA Criteria API
  - QueryDSL
- **Dynamic Operator Support** (equals, greater than, less than, like, etc.)
- **Join Support** (left joins, nested joins)
- **Comprehensive Test Coverage**

## Technologies

- **Java 21** - Core language
- **JUnit 4** - Unit and integration testing
- **Maven** - Build and dependency management
- **JPA/Hibernate** - Object-relational mapping for database interactions
- **GitHub Actions** - Automated CI/CD pipeline for building and testing

## Testing
The project includes **9 comprehensive tests for each filter implementation** covering:
- Operator testing
- Negation testing
- Join logic
- Invalid operations

## Entity Model

The project includes sample entities representing a simplified order management system:
- `Kunde` (Customer)
- `Bestellung` (Order)
- `Produkt` (Product)
- `Ort` / `Landkreis` / `Bundesland` (Location hierarchy)
See `src/main/resources/ERModell.png` for the entity relationship diagram.

## Example Usage

### Simple search with Criteria API

```java
@Test
public void searchBundeslandCriteriaBuilder() {
    List<SQLDefinition> params = new ArrayList<>();
    params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));
    
    List<Bundesland> results = builderLogic.searchEntity(Bundesland.class, params);
    
    assertEquals(1, results.size());
    assertEquals("Bayern", results.get(0).getName());
}

OUTPUT:
select b1_0.id,b1_0.name from Bundesland b1_0 where 1=1 and b1_0.name=?
```

### Simple Join with Criteria API

```java
@Test
    public void searchLandkreisSimpleLeftJoinToBundeslandCriteriaBuilder() {
        Bundesland bayern = new Bundesland();
        bayern.setName("Bayern");
        builderLogic.save(bayern);

        Landkreis fuerstenfeldbruck = new Landkreis();
        fuerstenfeldbruck.setName("Fuerstenfeldbruck");
        fuerstenfeldbruck.setBundesland(bayern);
        builderLogic.save(fuerstenfeldbruck);

        List<SQLDefinition> params = new ArrayList<>();
        params.add(new SQLDefinition("name", Operators.EQUAL.toString(), "Bayern"));

        List<Landkreis> results = builderLogic.searchEntityJoin(Landkreis.class, "bundesland", params);

        assertEquals("Should be 1 Landkreis", 1, results.size());
        assertEquals("Landkreis should be Fuerstenfeldbruck", "Fuerstenfeldbruck", results.get(0).getName());
        assertEquals("Bundesland of Fuerstenfeldbruck should be Bayern", "Bayern", results.get(0).getBundesland().getName());
    }

OUTPUT:
select l1_0.id,l1_0.bundesland_id,l1_0.name from Landkreis l1_0 left join Bundesland b1_0 on b1_0.id=l1_0.bundesland_id where 1=1 and b1_0.name=?
```

**Note**: Database configuration is currently handled via `persistence.xml` in test resources. For production use, configure your database connection accordingly.
