import { getSdk } from '@/generated/graphql-client'
import { GraphQLClient } from 'graphql-request'

export const graphqlApiClient = getSdk(
  new GraphQLClient(`${process.env.NEXT_PUBLIC_API_ENDPOINT}/api/graphql`),
)
