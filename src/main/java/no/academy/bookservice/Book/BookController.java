package no.academy.bookservice.Book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Operation(summary = "Gets all the books", tags = {"Books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All books returned"),
            @ApiResponse(responseCode = "500", description = "Invalid book ID")})
    @GetMapping("books")
    public ResponseEntity<Iterable<Book>> getAll() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Gets a book by the ID", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book with ID returned"),
            @ApiResponse(responseCode = "500", description = "Invalid book ID")})
    @GetMapping("books/{id}")
    public ResponseEntity<Book> getById(@PathVariable long id) throws BookNotFoundException {
        Optional<Book> user = bookRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new BookNotFoundException();
        }
    }
}
