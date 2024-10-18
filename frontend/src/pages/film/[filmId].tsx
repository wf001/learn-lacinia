import { ContentTitle } from '@/components/ContentTitle'
import { EmptyTable } from '@/components/EmptyTable'
import { ErrorAlert } from '@/components/alert/Error'
import { Navigation } from '@/components/Navigation'
import { useFilm, useFilmId } from '@/hooks/film'
import { Table, TableBody, TableCell, TableRow } from '@mui/material'

export default function Film() {
  const filmId = useFilmId()
  const { data, isLoading, error } = useFilm(filmId, filmId !== -1)

  if (isLoading) return null

  const film = data?.film
  return (
    <div>
      <Navigation />
      <ContentTitle title={'Film info'} />

      {error && <ErrorAlert />}

      {!film ? (
        <EmptyTable />
      ) : (
        <div className="text-sm p-5">
          <Table>
            <TableBody>
              <TableRow>
                <TableCell>Title</TableCell>
                <TableCell>
                  {film?.title} ({film?.film_id})
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Description</TableCell>
                <TableCell>{film?.description}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Released at</TableCell>
                <TableCell>{film?.release_year}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Running time</TableCell>
                <TableCell>{film?.length}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Rating:</TableCell>
                <TableCell>
                  {film?.rental_rate}/{film?.rating}
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Language:</TableCell>
                <TableCell>{film?.language_id}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Category:</TableCell>
                <TableCell>
                  <ul>
                    {film?.film_category &&
                      film?.film_category.map((c, key) =>
                        c?.category ? (
                          <li key={key}>{c.category.name}</li>
                        ) : null,
                      )}
                  </ul>
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Actors:</TableCell>
                <TableCell>
                  <ul>
                    {film?.film_actor &&
                      film.film_actor.map((a, key) =>
                        a?.actor ? (
                          <li key={key}>
                            ・ {a.actor.first_name} {a.actor.last_name}
                          </li>
                        ) : null,
                      )}
                  </ul>
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </div>
      )}
    </div>
  )
}
