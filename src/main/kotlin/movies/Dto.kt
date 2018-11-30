package movies

import io.swagger.annotations.ApiModelProperty

data class Dto(

        @ApiModelProperty("Id of the movie")
        var id: String? = null,

        @ApiModelProperty("Name of the Movie")
        var name: String? = null,

        @ApiModelProperty("type of movie genre ")
        var genre: String? = null

)