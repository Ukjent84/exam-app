package movies

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@Entity
class MovieEntity (

        @get:NotBlank @get:Size(max = 64)
        var name: String,

        @get:NotBlank @get:Size(max = 64)
        var genre: String,

        @get:Id @get:GeneratedValue
        var id: Long? = null
)





