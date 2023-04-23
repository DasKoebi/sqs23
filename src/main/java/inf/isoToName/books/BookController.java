package inf.isoToName.books;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class BookController {
    @Autowired
    private RulesService rulesService;

    @GetMapping("/v1/books/{identifier}")
    @Operation(summary = "Get Book by ISBN-10 or ISBN-13")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Book successfully found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Rules.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Book was not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internel Server Error",
                    content = @Content)})
    public ResponseEntity<Book> getRulesById(@PathVariable String identifier){
        return rulesService.getRulesByIDorName(identifier);
    }


}
