import { GetCustomersQuery } from '@/generated/graphql-client'
import { graphqlApiClient } from '@/lib/graphql-client'
import { keepPreviousData, useQuery } from '@tanstack/react-query'

const QUERY_KEY = 'customers'

export const useCustomers = (offset: number, limit: number) => {
  const { data, isLoading, error } = useQuery<GetCustomersQuery>({
    queryKey: [QUERY_KEY, offset],
    queryFn: () => graphqlApiClient.getCustomers({ offset, limit }),
    placeholderData: keepPreviousData,
  })
  return { data, isLoading, error }
}
