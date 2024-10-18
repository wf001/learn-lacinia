import {
  GetAddressByCustomerQuery,
  GetRentalInfoByCustomerQuery,
} from '@/generated/graphql-client'
import { graphqlApiClient } from '@/lib/graphql-client'
import { keepPreviousData, useQuery } from '@tanstack/react-query'
import { useRouter } from 'next/router'
import { useMemo } from 'react'

const QUERY_KEY = 'customer'

export const useAddressByCustomer = (customerId: number, enabled: boolean) => {
  const { data, isLoading, error } = useQuery<GetAddressByCustomerQuery>({
    queryKey: [QUERY_KEY, customerId, 'address'],
    queryFn: () => graphqlApiClient.getAddressByCustomer({ customerId }),
    enabled: enabled,
    placeholderData: keepPreviousData,
  })
  return { data, isLoading, error }
}

export const useRentalInfoByCustomer = (
  customerId: number,
  enabled: boolean,
) => {
  const { data, isLoading, error } = useQuery<GetRentalInfoByCustomerQuery>({
    queryKey: [QUERY_KEY, customerId, 'rental'],
    queryFn: () => graphqlApiClient.getRentalInfoByCustomer({ customerId }),
    enabled: enabled,
    placeholderData: keepPreviousData,
  })
  return { data, isLoading, error }
}

export const useCustomerId = (): number => {
  const router = useRouter()

  return useMemo(() => {
    const customerId = router.query.customerId
    /* graphApiClientがnullを許容しないので、PATHのcustomerIdが数値でない場合は存在しないcustomerId -1を返す */
    if (!router.isReady || typeof customerId !== 'string') return -1

    const res = parseInt(customerId, 10)
    return !!res ? res : -1
  }, [router])
}
