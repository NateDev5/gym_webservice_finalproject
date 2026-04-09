package com.andre_nathan.gym_webservice.shared.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI grouping configuration for modular API documentation.
 * Each module gets its own Swagger group for better organization.
 */
@Configuration
public class OpenApiGroupsConfig {

    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("member")
                .displayName("Member API")
                .pathsToMatch("/api/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi enrollmentApi() {
        return GroupedOpenApi.builder()
                .group("enrollment")
                .displayName("Enrollment API")
                .pathsToMatch("/api/enrollments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi scheduleApi() {
        return GroupedOpenApi.builder()
                .group("schedule")
                .displayName("Schedule API")
                .pathsToMatch("/api/schedules/**")
                .build();
    }

    @Bean
    public GroupedOpenApi trainerApi() {
        return GroupedOpenApi.builder()
                .group("trainer")
                .displayName("Trainer API")
                .pathsToMatch("/api/trainers/**")
                .build();
    }
}
