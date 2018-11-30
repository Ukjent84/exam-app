import io.restassured.RestAssured.delete
import io.restassured.RestAssured.given
import movies.Dto
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.springframework.http.MediaType

class RestApiTest : ApiTestBase() {

    private fun createMovies() {
        createMovie("a", "Action")
        createMovie("b",  "Romance")
        createMovie("c",  "Romance")
        createMovie("d",  "Thriller")
        createMovie("e",  "Action")
        createMovie("f",  "Action")
    }

    private fun createMovie(name: String, genre: String) {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Dto(null, name, genre))
                .post()
                .then()
                .statusCode(201)
    }


    @Test
    fun testCreateWrong(){

        given().contentType(MediaType.APPLICATION_XML_VALUE)
                .body("")
                .post()
                .then()
                .statusCode(415)

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))
    }

    @Test
    fun testGetWithGenre(){
        createMovies()

        given().param("genre", "Action")
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(3))
    }

    @Test
    fun testDoubleDelete(){
        val name = "killbill"
        val genre = "Action"
        val dto = Dto(null, name, genre)

        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(1))

        delete("/$name").then().statusCode(204)

        delete("/$name").then().statusCode(404)
    }

}