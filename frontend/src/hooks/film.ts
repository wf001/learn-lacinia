import { GetFilmQuery } from '@/generated/graphql-client'
import { graphqlApiClient } from '@/lib/graphql-client'
import { keepPreviousData, useQuery } from '@tanstack/react-query'
import { useRouter } from 'next/router'
import { useMemo } from 'react'

const QUERY_KEY = 'film'

export const useFilm = (filmId: number, enabled: boolean) => {
  const { data, isLoading, error } = useQuery<GetFilmQuery>({
    queryKey: [QUERY_KEY, filmId],
    queryFn: () => graphqlApiClient.getFilm({ filmId }),
    enabled: enabled,
    placeholderData: keepPreviousData,
  })
  return { data, isLoading, error }
}

export const useFilmId = (): number => {
  const router = useRouter()

  return useMemo(() => {
    const filmId = router.query.filmId
    /* graphApiClientがnullを許容しないので、PATHのfilmIdが数値でない場合は存在しないfilmId -1を返す */
    if (!router.isReady || typeof filmId !== 'string') return -1

    const result = parseInt(filmId, 10)
    return !!result ? result : -1
  }, [router])
}
