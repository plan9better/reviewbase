package dev.plan9better.webapi.contracts;

public record SubmitFeedbackDto(
        String username,
        Long reviewId,
        String comment

) {
}
