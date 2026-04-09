package com.andre_nathan.gym_webservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
class GymWebserviceApiIntegrationTests {
    private static final String MEMBERSHIP_PLAN_ID = "cab08990-8bbd-4210-b905-ff3ab3665622";
    private static final String ACTIVE_MEMBER_ID = "076d07d8-7189-431c-9508-f19c43209e0f";
    private static final String SECOND_ACTIVE_MEMBER_ID = "bc5b6681-ae4f-493d-b14f-4cea299f25f0";
    private static final String YOGA_TRAINER_ID = "11111111-1111-1111-1111-111111111111";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${local.server.port}")
    private int port;

    @Test
    void memberCrudWorks() throws Exception {
        String email = "new.member." + UUID.randomUUID() + "@example.com";

        HttpResponse<String> createResponse = post("/api/members", Map.ofEntries(
                Map.entry("fullName", "Taylor Rivers"),
                Map.entry("dateOfBirth", "1996-05-03"),
                Map.entry("email", email),
                Map.entry("phone", "+15145550111"),
                Map.entry("membershipPlanId", MEMBERSHIP_PLAN_ID),
                Map.entry("membershipStatus", "ACTIVE"),
                Map.entry("membershipStartDate", "2026-04-01"),
                Map.entry("membershipEndDate", "2027-03-31")
        ));

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode createdBody = body(createResponse);
        assertThat(createdBody.get("email").asText()).isEqualTo(email);

        String memberId = createdBody.get("memberId").asText();
        HttpResponse<String> getResponse = get("/api/members/" + memberId);

        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        JsonNode getBody = body(getResponse);
        assertThat(getBody.get("memberId").asText()).isEqualTo(memberId);
        assertThat(getBody.get("membershipPlanName").asText()).isEqualTo("Standard Plan");
    }

    @Test
    void trainerCrudWorks() throws Exception {
        String email = "trainer." + UUID.randomUUID() + "@example.com";

        HttpResponse<String> createResponse = post("/api/trainers", Map.ofEntries(
                Map.entry("fullName", "Jordan Miles"),
                Map.entry("email", email),
                Map.entry("specialty", "Mobility"),
                Map.entry("active", true),
                Map.entry("certifications", java.util.List.of(Map.ofEntries(
                        Map.entry("certificateName", "Mobility Specialist"),
                        Map.entry("issuedDate", "2025-01-15"),
                        Map.entry("expiryDate", "2027-01-15")
                )))
        ));

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode createdBody = body(createResponse);
        assertThat(createdBody.get("specialty").asText()).isEqualTo("Mobility");

        String trainerId = createdBody.get("trainerId").asText();
        HttpResponse<String> getResponse = get("/api/trainers/" + trainerId);

        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        JsonNode getBody = body(getResponse);
        assertThat(getBody.get("trainerId").asText()).isEqualTo(trainerId);
        assertThat(getBody.get("certifications").get(0).get("certificateName").asText()).isEqualTo("Mobility Specialist");
    }

    @Test
    void scheduleCrudWorks() throws Exception {
        String classSessionId = UUID.randomUUID().toString();

        HttpResponse<String> createResponse = post("/api/schedules", schedulePayload(
                "Evening Yoga",
                "Yoga",
                "2026-05-01T18:00:00",
                "2026-05-01T19:00:00",
                "room-yoga-1",
                "Zen Studio",
                20,
                YOGA_TRAINER_ID,
                15,
                0,
                classSessionId,
                "2026-05-01",
                "SCHEDULED"
        ));

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode createdBody = body(createResponse);
        assertThat(createdBody.get("classSession").get("classSessionId").asText()).isEqualTo(classSessionId);

        String scheduleId = createdBody.get("scheduleId").asText();
        HttpResponse<String> getResponse = get("/api/schedules/" + scheduleId);

        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        JsonNode getBody = body(getResponse);
        assertThat(getBody.get("room").get("roomName").asText()).isEqualTo("Zen Studio");
        assertThat(getBody.get("trainerId").asText()).isEqualTo(YOGA_TRAINER_ID);
    }

    @Test
    void enrollmentOrchestrationReturnsCoordinatedDataAndLinks() throws Exception {
        String classSessionId = UUID.randomUUID().toString();

        HttpResponse<String> scheduleResponse = post("/api/schedules", schedulePayload(
                "Morning Yoga Flow",
                "Yoga",
                "2026-06-02T08:00:00",
                "2026-06-02T09:00:00",
                "room-orch-1",
                "Flow Studio",
                18,
                YOGA_TRAINER_ID,
                10,
                0,
                classSessionId,
                "2026-06-02",
                "SCHEDULED"
        ));
        assertThat(scheduleResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        HttpResponse<String> enrollResponse = post("/api/enrollments/enroll", Map.ofEntries(
                Map.entry("memberId", ACTIVE_MEMBER_ID),
                Map.entry("classSessionId", classSessionId)
        ));

        assertThat(enrollResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode enrollBody = body(enrollResponse);
        assertThat(enrollBody.get("memberId").asText()).isEqualTo(ACTIVE_MEMBER_ID);
        assertThat(enrollBody.get("memberName").asText()).isEqualTo("Jason Clark");
        assertThat(enrollBody.get("registeredClasses").get(0).get("className").asText()).isEqualTo("Morning Yoga Flow");
        assertThat(enrollBody.get("registeredClasses").get(0).get("trainerName").asText()).isEqualTo("Sophia Tran");
        assertThat(enrollBody.get("registeredClasses").get(0).get("scheduleId").asText()).isNotBlank();
        assertThat(enrollBody.get("_links").get("member").get("href").asText()).isNotBlank();
        assertThat(enrollBody.get("_links").get("class-session").get("href").asText()).isNotBlank();
        assertThat(enrollBody.get("_links").get("trainer").get("href").asText()).isNotBlank();
    }

    @Test
    void validationErrorsReturnConsistentResponse() throws Exception {
        HttpResponse<String> response = post("/api/members", Map.ofEntries(
                Map.entry("fullName", ""),
                Map.entry("dateOfBirth", "1996-05-03"),
                Map.entry("email", "bad-email"),
                Map.entry("phone", ""),
                Map.entry("membershipPlanId", MEMBERSHIP_PLAN_ID),
                Map.entry("membershipStatus", "ACTIVE"),
                Map.entry("membershipStartDate", "2026-04-01"),
                Map.entry("membershipEndDate", "2027-03-31")
        ));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        JsonNode body = body(response);
        assertThat(body.get("message").asText()).isEqualTo("Validation failed.");
        assertThat(body.get("details").isArray()).isTrue();
    }

    @Test
    void classFullConflictReturnsBusinessError() throws Exception {
        String classSessionId = UUID.randomUUID().toString();

        HttpResponse<String> scheduleResponse = post("/api/schedules", schedulePayload(
                "Full Yoga",
                "Yoga",
                "2026-06-03T18:00:00",
                "2026-06-03T19:00:00",
                "room-full-1",
                "Packed Studio",
                10,
                YOGA_TRAINER_ID,
                1,
                1,
                classSessionId,
                "2026-06-03",
                "SCHEDULED"
        ));
        assertThat(scheduleResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        HttpResponse<String> enrollResponse = post("/api/enrollments/enroll", Map.ofEntries(
                Map.entry("memberId", SECOND_ACTIVE_MEMBER_ID),
                Map.entry("classSessionId", classSessionId)
        ));

        assertThat(enrollResponse.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        JsonNode errorBody = body(enrollResponse);
        assertThat(errorBody.get("message").asText()).isEqualTo("Class session is full: " + classSessionId);
    }

    private Map<String, Object> schedulePayload(
            String className,
            String classType,
            String startTime,
            String endTime,
            String roomId,
            String roomName,
            int roomCapacity,
            String trainerId,
            int maxCapacity,
            int enrolledCount,
            String classSessionId,
            String sessionDate,
            String sessionStatus
    ) {
        return Map.ofEntries(
                Map.entry("className", className),
                Map.entry("classType", classType),
                Map.entry("startTime", startTime),
                Map.entry("endTime", endTime),
                Map.entry("roomId", roomId),
                Map.entry("roomName", roomName),
                Map.entry("roomCapacity", roomCapacity),
                Map.entry("trainerId", trainerId),
                Map.entry("maxCapacity", maxCapacity),
                Map.entry("enrolledCount", enrolledCount),
                Map.entry("classSessionId", classSessionId),
                Map.entry("sessionDate", sessionDate),
                Map.entry("sessionStatus", sessionStatus)
        );
    }

    private HttpResponse<String> post(String path, Object payload) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl(path)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payload)))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl(path)))
                .GET()
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private JsonNode body(HttpResponse<String> response) throws Exception {
        assertThat(response.body()).isNotBlank();
        return objectMapper.readTree(response.body());
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
