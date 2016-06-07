package nl.thehyve.ocdu.factories;

import nl.thehyve.ocdu.models.OCEntities.Event;
import nl.thehyve.ocdu.models.OcUser;
import nl.thehyve.ocdu.models.UploadSession;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

public class EventDataFactoryTests {

    private EventDataFactory factory;
    private UploadSession testSubmission;
    private OcUser testUser;

    @Test
    public void testMapRowWithOnlySomeColumns() {
        HashMap<String, Integer> columnsIndes = new HashMap<>();
        columnsIndes.put("StudySubjectID", 0);
        columnsIndes.put("EventName", 1);
        columnsIndes.put("Study", 2);
        columnsIndes.put("StartDate", 3);
        columnsIndes.put("RepeatNumber", 4);
        String[] row = new String[]{"Ssid0", "Event Name", "Study", "11-Jun-2014", "5"};

        Event event = factory.mapRow(row, columnsIndes);

        assertThat(event, allOf(
                hasProperty("owner", equalTo(testUser)),
                hasProperty("submission", equalTo(testSubmission)),
                hasProperty("ssid", equalTo("Ssid0")),
                hasProperty("eventName", equalTo("Event Name")),
                hasProperty("study", equalTo("Study")),
                hasProperty("startDate", equalTo("11-Jun-2014")),
                hasProperty("repeatNumber", equalTo("5"))
        ));
    }

    @Test
    public void testMapRow() {
        HashMap<String, Integer> columnsIndes = new HashMap<>();
        columnsIndes.put("StudySubjectID", 0);
        columnsIndes.put("EventName", 1);
        columnsIndes.put("Study", 2);
        columnsIndes.put("Site", 3);
        columnsIndes.put("Location", 4);
        columnsIndes.put("StartDate", 5);
        columnsIndes.put("StartTime", 6);
        columnsIndes.put("EndDate", 7);
        columnsIndes.put("EndTime", 8);
        columnsIndes.put("RepeatNumber", 9);
        String[] row = new String[]{"Ssid", "Event Name", "Study", "Site", "Location",
                "21-Feb-2011", "12:00", "22-Feb-2011", "18:00", "3"};

        Event event = factory.mapRow(row, columnsIndes);

        assertThat(event, allOf(
                hasProperty("owner", equalTo(testUser)),
                hasProperty("submission", equalTo(testSubmission)),
                hasProperty("ssid", equalTo("Ssid")),
                hasProperty("eventName", equalTo("Event Name")),
                hasProperty("study", equalTo("Study")),
                hasProperty("site", equalTo("Site")),
                hasProperty("location", equalTo("Location")),
                hasProperty("startDate", equalTo("21-Feb-2011")),
                hasProperty("startTime", equalTo("12:00")),
                hasProperty("endDate", equalTo("22-Feb-2011")),
                hasProperty("endTime", equalTo("18:00")),
                hasProperty("repeatNumber", equalTo("3"))
        ));
    }

    @Test
    public void createEventsData() {
        Path testFilePath = Paths.get("docs/exampleFiles/events.txt");

        List<Event> events = factory.createEventsData(testFilePath);

        assertThat(events, contains(
                allOf(
                        hasProperty("owner", equalTo(testUser)),
                        hasProperty("submission", equalTo(testSubmission)),
                        hasProperty("ssid", equalTo("Subject1")),
                        hasProperty("eventName", equalTo("Event 1")),
                        hasProperty("study", equalTo("Study 1")),
                        hasProperty("site", equalTo("Site 1")),
                        hasProperty("location", equalTo("Location 1")),
                        hasProperty("startDate", equalTo("12-Apr-2013")),
                        hasProperty("startTime", equalTo("10:00")),
                        hasProperty("endDate", equalTo("14-Apr-2013")),
                        hasProperty("endTime", equalTo("14:00")),
                        hasProperty("repeatNumber", equalTo("1"))
                ),
                allOf(
                        hasProperty("owner", equalTo(testUser)),
                        hasProperty("submission", equalTo(testSubmission)),
                        hasProperty("ssid", equalTo("Subject2")),
                        hasProperty("eventName", equalTo("Event 2")),
                        hasProperty("study", equalTo("Study 2")),
                        hasProperty("site", equalTo("Site 2")),
                        hasProperty("location", equalTo("Location 2")),
                        hasProperty("startDate", equalTo("2-Sep-2012")),
                        hasProperty("startTime", equalTo("11:25")),
                        hasProperty("endDate", equalTo("20-Oct-2012")),
                        hasProperty("endTime", equalTo("18:10")),
                        hasProperty("repeatNumber", equalTo("2"))
                )
        ));
    }

    @Before
    public void setUp() throws Exception {
        this.testUser = new OcUser();
        this.testUser.setUsername("tester");
        this.testSubmission = new UploadSession("submission1", UploadSession.Step.MAPPING, new Date(), this.testUser);
        this.factory = new EventDataFactory(testUser, testSubmission);
    }
}
