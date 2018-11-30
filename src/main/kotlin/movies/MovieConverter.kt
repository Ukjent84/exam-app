package movies


class MovieConverter {

    companion object {
        fun transform(entity: MovieEntity): Dto {

            return Dto(id = entity.id?.toString(),
                    name = entity.name,
                    genre = entity.genre)
        }

        fun transform(entities: Iterable<MovieEntity>): List<Dto> {
            return entities.map { transform(it) }
        }

    }
}