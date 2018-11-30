package movies


import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@Api(value = "/movies", description = "Handling of creating and retrieving  movies")
@RequestMapping(
        path = ["/movies"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
@RestController
class RestApi {

    @Autowired
    private lateinit var crud: Repository

    @ApiOperation("Get all the movies")
    @GetMapping
    fun get(
            @ApiParam("The movie genre")
            @RequestParam("genre", required = false)
            genre: String?): ResponseEntity<List<Dto>> {

        val list = if (genre.isNullOrBlank()) {
            crud.findAll()
        } else {
            crud.findAllByGenre(genre!!)
        }

        return ResponseEntity.ok(MovieConverter.transform(list))
    }

    @ApiOperation("Get a movie by name")
    @GetMapping(path = ["/{name}"])
    fun getMovie(
            @ApiParam("Name of a movie")
            @PathVariable("name")
            pathName: String?) : ResponseEntity<Dto> {

        val dto: MovieEntity
        try {
            val id = crud.findByName(pathName!!).id!!
            dto = crud.findById(id).get()
        }
        catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        return ResponseEntity.ok(MovieConverter.transform(dto))
    }


    @ApiOperation("Create a movie")
    @PostMapping
    fun createMovie(
            @ApiParam("Spesify name and genre. Do NOT spesify id as it will autogenerate")
            @RequestBody
            dto: Dto) : ResponseEntity<Long> {

        if (!dto.id.isNullOrBlank()){
            return ResponseEntity.status(400).build()
        }

        if (dto.name.isNullOrBlank() || dto.genre.isNullOrBlank()){
            return ResponseEntity.status(400).build()
        }

        try {
            crud.createMovie(dto.name!!, dto.genre!!)
        }
        catch (e: Exception) {
            throw e
        }

        return ResponseEntity.status(201).build()

    }

    @ApiOperation("Delete a movie with  by name")
    @DeleteMapping(path = ["/{name}"])
    fun deleteGame(
            @ApiParam("Name of movie")
            @PathVariable("name")
            pathName: String?): ResponseEntity<Dto>{

        val id: Long
        try {
            id = crud.findByName(pathName!!).id!!
        } catch (e: Exception) {
            return ResponseEntity.status(404).build()
        }

        crud.deleteById(id)
        return ResponseEntity.status(204).build()
    }
}