package MakeItFit.activities.types;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Classe concreta para testar a classe abstrata
class TestRepetitionsWithWeights extends RepetitionsWithWeights {
    public TestRepetitionsWithWeights(UUID          userCode,
                                      MakeItFitDate realizationDate,
                                      int           expectedDuration,
                                      String        designation,
                                      String        name,
                                      int           repetitions,
                                      int           series,
                                      double        weight) {
        super(userCode,
              realizationDate,
              expectedDuration,
              designation,
              name,
              repetitions,
              series,
              weight);
    }

    public TestRepetitionsWithWeights() {
        super();
    }

    public TestRepetitionsWithWeights(TestRepetitionsWithWeights r) {
        super(r);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        // Implementação simples para teste
    }

    @Override
    public int caloricWaste(float index) {
        // Implementação simples para teste
        return (int) (getRepetitions() * getSeries() * getWeight() * index);
    }

    @Override
    public RepetitionsWithWeights clone() {
        return new TestRepetitionsWithWeights(this);
    }
}

public class RepetitionsWithWeightsTest {

    @Test
    void testConstructorWithParameters() {
        UUID                       userCode = UUID.randomUUID();
        MakeItFitDate              date     = new MakeItFitDate();
        TestRepetitionsWithWeights activity = new TestRepetitionsWithWeights(userCode,
                                                                             date,
                                                                             60,
                                                                             "Workout",
                                                                             "Bench Press",
                                                                             10,
                                                                             3,
                                                                             50.5);

        assertEquals(userCode, activity.getUserCode());
        assertEquals(date, activity.getRealizationDate());
        assertEquals(60, activity.getExpectedDuration());
        assertEquals("Workout", activity.getDesignation());
        assertEquals("Bench Press", activity.getName());
        assertEquals(10, activity.getRepetitions());
        assertEquals(3, activity.getSeries());
        assertEquals(50.5, activity.getWeight());
    }

    @Test
    void testDefaultConstructor() {
        TestRepetitionsWithWeights activity = new TestRepetitionsWithWeights();

        assertNotNull(activity.getUserCode());
        assertNotNull(activity.getRealizationDate());
        assertEquals(0, activity.getExpectedDuration());
        assertEquals(0, activity.getRepetitions());
        assertEquals(0, activity.getSeries());
        assertEquals(0, activity.getWeight());
    }

    @Test
    void testCopyConstructor() {
        UUID                       userCode = UUID.randomUUID();
        MakeItFitDate              date     = new MakeItFitDate();
        TestRepetitionsWithWeights original = new TestRepetitionsWithWeights(userCode,
                                                                             date,
                                                                             60,
                                                                             "Workout",
                                                                             "Bench Press",
                                                                             10,
                                                                             3,
                                                                             50.5);

        TestRepetitionsWithWeights copy = new TestRepetitionsWithWeights(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getRepetitions(), copy.getRepetitions());
        assertEquals(original.getSeries(), copy.getSeries());
        assertEquals(original.getWeight(), copy.getWeight());
    }

    @Test
    void testGetSetWeight() {
        TestRepetitionsWithWeights activity = new TestRepetitionsWithWeights();

        activity.setWeight(75.3);
        assertEquals(75.3, activity.getWeight());

        activity.setWeight(0);
        assertEquals(0, activity.getWeight());

        activity.setWeight(-10.5);
        assertEquals(-10.5, activity.getWeight());
    }

    @Test
    void testCaloricWaste() {
        TestRepetitionsWithWeights activity = new TestRepetitionsWithWeights();
        activity.setRepetitions(10);
        activity.setSeries(3);
        activity.setWeight(20);

        float index    = 0.5f;
        int   expected = (int) (10 * 3 * 20 * 0.5f);
        assertEquals(expected, activity.caloricWaste(index));
    }

    @Test
    void testToString() {
        TestRepetitionsWithWeights activity = new TestRepetitionsWithWeights();
        activity.setRepetitions(10);
        activity.setSeries(3);
        activity.setWeight(50.5);

        String result = activity.toString();
        assertTrue(result.contains("10"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("50.5 Kg"));
    }

    @Test
    void testEquals() {
        UUID                       userCode  = UUID.randomUUID();
        MakeItFitDate              date      = new MakeItFitDate();
        TestRepetitionsWithWeights activity1 = new TestRepetitionsWithWeights(userCode,
                                                                              date,
                                                                              60,
                                                                              "Workout",
                                                                              "Bench Press",
                                                                              10,
                                                                              3,
                                                                              50.5);
        TestRepetitionsWithWeights activity2 = new TestRepetitionsWithWeights(userCode,
                                                                              date,
                                                                              60,
                                                                              "Workout",
                                                                              "Bench Press",
                                                                              10,
                                                                              3,
                                                                              50.5);
        TestRepetitionsWithWeights activity3 = new TestRepetitionsWithWeights(userCode,
                                                                              date,
                                                                              60,
                                                                              "Workout",
                                                                              "Bench Press",
                                                                              10,
                                                                              3,
                                                                              60.0);

        assertEquals(activity1, activity2);
        assertNotEquals(activity1, activity3);
        assertNotEquals(activity1, null);
        assertNotEquals(activity1, new Object());
    }

    @Test
    void testClone() {
        UUID                       userCode = UUID.randomUUID();
        MakeItFitDate              date     = new MakeItFitDate();
        TestRepetitionsWithWeights original =
            new TestRepetitionsWithWeights(userCode, date, 60, "Workout", "Deadlift", 8, 4, 80.0);

        TestRepetitionsWithWeights clone = (TestRepetitionsWithWeights) original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
}
