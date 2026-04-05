package com.andre_nathan.gym_webservice.modules.enrollment.api;

import com.andre_nathan.gym_webservice.modules.enrollment.api.dto.EnrollmentResponse;
import com.andre_nathan.gym_webservice.modules.enrollment.api.mapper.EnrollmentApiMapper;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.Enrollment;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnrollmentRepresentationAssembler extends RepresentationModelAssemblerSupport<Enrollment, EnrollmentResponse> {

    public EnrollmentRepresentationAssembler() {
        super(EnrollmentController.class, EnrollmentResponse.class);
    }

    @Override
    public EnrollmentResponse toModel(Enrollment enrollment) {
        EnrollmentResponse response = EnrollmentApiMapper.toResponse(enrollment);

        response.add(linkTo(methodOn(EnrollmentController.class).getById(enrollment.getEnrollmentId().value())).withSelfRel());
        response.add(linkTo(methodOn(EnrollmentController.class).getAll()).withRel("enrollments"));
        response.add(linkTo(methodOn(EnrollmentController.class).getByMemberId(enrollment.getMemberId().value())).withRel("member-enrollments"));
        // TODO: Add link to schedule when implemented
        // TODO: Add link to trainer when implemented

        return response;
    }
}
