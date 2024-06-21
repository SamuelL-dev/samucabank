package samucabank.apibank.api.config.swagger.documentation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.api.dtos.response.UserResponse;
import samucabank.apibank.api.exceptionHandler.commonStandardError.StandardError;

import java.util.List;

@Tag(name = "User", description = "User related operations")
public interface UserSwagger {

    @Operation(description = "Paginated search operation for all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Users not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<List<UserResponse>> getAllUsers(Pageable pageable);

    @Operation(description = "Operation to search for the user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "User not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<UserResponse> findById(@PathVariable("id") final String id);

    @Operation(description = "Operation to create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "406", description = "Data validation error",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<UserResponse> register(@RequestBody @Valid final UserRequest data);


}
