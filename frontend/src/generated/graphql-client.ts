import { GraphQLClient, RequestOptions } from 'graphql-request'
import gql from 'graphql-tag'
export type Maybe<T> = T | null
export type InputMaybe<T> = Maybe<T>
export type Exact<T extends { [key: string]: unknown }> = {
  [K in keyof T]: T[K]
}
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]?: Maybe<T[SubKey]>
}
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]: Maybe<T[SubKey]>
}
export type MakeEmpty<
  T extends { [key: string]: unknown },
  K extends keyof T,
> = { [_ in K]?: never }
export type Incremental<T> =
  | T
  | {
      [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never
    }
type GraphQLClientRequestHeaders = RequestOptions['requestHeaders']
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string }
  String: { input: string; output: string }
  Boolean: { input: boolean; output: boolean }
  Int: { input: number; output: number }
  Float: { input: number; output: number }
}

export type Actor = {
  __typename?: 'Actor'
  actor_id?: Maybe<Scalars['Int']['output']>
  first_name?: Maybe<Scalars['String']['output']>
  last_name?: Maybe<Scalars['String']['output']>
}

export type Address = {
  __typename?: 'Address'
  address?: Maybe<Scalars['String']['output']>
  address2?: Maybe<Scalars['String']['output']>
  address_id?: Maybe<Scalars['Int']['output']>
  city?: Maybe<City>
  phone?: Maybe<Scalars['String']['output']>
  postal_code?: Maybe<Scalars['String']['output']>
}

export type Category = {
  __typename?: 'Category'
  category_id?: Maybe<Scalars['Int']['output']>
  name?: Maybe<Scalars['String']['output']>
}

export type City = {
  __typename?: 'City'
  city?: Maybe<Scalars['String']['output']>
  city_id?: Maybe<Scalars['Int']['output']>
  country?: Maybe<Country>
}

export type Country = {
  __typename?: 'Country'
  city?: Maybe<Array<Maybe<City>>>
  country?: Maybe<Scalars['String']['output']>
  country_id?: Maybe<Scalars['Int']['output']>
}

export type Customer = {
  __typename?: 'Customer'
  active?: Maybe<Scalars['Boolean']['output']>
  address?: Maybe<Address>
  address_id?: Maybe<Scalars['String']['output']>
  customer_id?: Maybe<Scalars['Int']['output']>
  email?: Maybe<Scalars['String']['output']>
  first_name?: Maybe<Scalars['String']['output']>
  last_name?: Maybe<Scalars['String']['output']>
  payment?: Maybe<Array<Maybe<Payment>>>
  store_id?: Maybe<Scalars['Int']['output']>
}

export type Film = {
  __typename?: 'Film'
  description?: Maybe<Scalars['String']['output']>
  film_actor?: Maybe<Array<Maybe<FilmActor>>>
  film_category?: Maybe<Array<Maybe<FilmCategory>>>
  film_id?: Maybe<Scalars['Int']['output']>
  language_id?: Maybe<Scalars['Int']['output']>
  length?: Maybe<Scalars['Int']['output']>
  rating?: Maybe<Scalars['String']['output']>
  release_year?: Maybe<Scalars['String']['output']>
  rental_duration?: Maybe<Scalars['Int']['output']>
  rental_rate?: Maybe<Scalars['Float']['output']>
  title?: Maybe<Scalars['String']['output']>
}

export type FilmActor = {
  __typename?: 'FilmActor'
  actor?: Maybe<Actor>
}

export type FilmCategory = {
  __typename?: 'FilmCategory'
  category?: Maybe<Category>
}

export type Inventory = {
  __typename?: 'Inventory'
  film_id?: Maybe<Scalars['Int']['output']>
  inventory_id?: Maybe<Scalars['Int']['output']>
  store_id?: Maybe<Scalars['Int']['output']>
}

export type Paginated = {
  totalCount: Scalars['Int']['output']
}

export type PaginatedCustomers = Paginated & {
  __typename?: 'PaginatedCustomers'
  customers?: Maybe<Array<Maybe<Customer>>>
  totalCount: Scalars['Int']['output']
}

export type Payment = {
  __typename?: 'Payment'
  amount?: Maybe<Scalars['Float']['output']>
  customer_id?: Maybe<Scalars['Int']['output']>
  payment_date?: Maybe<Scalars['String']['output']>
  rental?: Maybe<Rental>
  staff_id?: Maybe<Scalars['Int']['output']>
}

export type Query = {
  __typename?: 'Query'
  /** Retrieve customer address information */
  customer?: Maybe<Customer>
  /** Retrieve film information */
  film?: Maybe<Film>
  /** Retrieve a list of customers */
  paginatedCustomers?: Maybe<PaginatedCustomers>
}

export type QueryCustomerArgs = {
  customer_id: Scalars['Int']['input']
}

export type QueryFilmArgs = {
  film_id: Scalars['Int']['input']
}

export type QueryPaginatedCustomersArgs = {
  limit: Scalars['Int']['input']
  offset: Scalars['Int']['input']
}

export type Rental = {
  __typename?: 'Rental'
  customer_id?: Maybe<Scalars['Int']['output']>
  inventory?: Maybe<Inventory>
  rental_date?: Maybe<Scalars['String']['output']>
  rental_id?: Maybe<Scalars['Int']['output']>
  return_date?: Maybe<Scalars['String']['output']>
}

export type GetAddressByCustomerQueryVariables = Exact<{
  customerId: Scalars['Int']['input']
}>

export type GetAddressByCustomerQuery = {
  __typename?: 'Query'
  customer?: {
    __typename?: 'Customer'
    first_name?: string | null
    last_name?: string | null
    address?: {
      __typename?: 'Address'
      address?: string | null
      address2?: string | null
      address_id?: number | null
      phone?: string | null
      postal_code?: string | null
      city?: {
        __typename?: 'City'
        city?: string | null
        country?: { __typename?: 'Country'; country?: string | null } | null
      } | null
    } | null
  } | null
}

export type GetCustomersQueryVariables = Exact<{
  offset: Scalars['Int']['input']
  limit: Scalars['Int']['input']
}>

export type GetCustomersQuery = {
  __typename?: 'Query'
  paginatedCustomers?: {
    __typename?: 'PaginatedCustomers'
    totalCount: number
    customers?: Array<{
      __typename?: 'Customer'
      customer_id?: number | null
      first_name?: string | null
      last_name?: string | null
      email?: string | null
    } | null> | null
  } | null
}

export type GetFilmQueryVariables = Exact<{
  filmId: Scalars['Int']['input']
}>

export type GetFilmQuery = {
  __typename?: 'Query'
  film?: {
    __typename?: 'Film'
    film_id?: number | null
    title?: string | null
    description?: string | null
    release_year?: string | null
    language_id?: number | null
    rental_duration?: number | null
    rental_rate?: number | null
    length?: number | null
    rating?: string | null
    film_category?: Array<{
      __typename?: 'FilmCategory'
      category?: { __typename?: 'Category'; name?: string | null } | null
    } | null> | null
    film_actor?: Array<{
      __typename?: 'FilmActor'
      actor?: {
        __typename?: 'Actor'
        first_name?: string | null
        last_name?: string | null
      } | null
    } | null> | null
  } | null
}

export type GetRentalInfoByCustomerQueryVariables = Exact<{
  customerId: Scalars['Int']['input']
}>

export type GetRentalInfoByCustomerQuery = {
  __typename?: 'Query'
  customer?: {
    __typename?: 'Customer'
    first_name?: string | null
    last_name?: string | null
    payment?: Array<{
      __typename?: 'Payment'
      payment_date?: string | null
      amount?: number | null
      rental?: {
        __typename?: 'Rental'
        rental_date?: string | null
        return_date?: string | null
        inventory?: { __typename?: 'Inventory'; film_id?: number | null } | null
      } | null
    } | null> | null
  } | null
}

export const GetAddressByCustomerDocument = gql`
  query getAddressByCustomer($customerId: Int!) {
    customer(customer_id: $customerId) {
      address {
        address
        address2
        address_id
        city {
          city
          country {
            country
          }
        }
        phone
        postal_code
      }
      first_name
      last_name
    }
  }
`
export const GetCustomersDocument = gql`
  query getCustomers($offset: Int!, $limit: Int!) {
    paginatedCustomers(offset: $offset, limit: $limit) {
      customers {
        customer_id
        first_name
        last_name
        email
      }
      totalCount
    }
  }
`
export const GetFilmDocument = gql`
  query getFilm($filmId: Int!) {
    film(film_id: $filmId) {
      film_id
      title
      description
      release_year
      language_id
      rental_duration
      rental_rate
      length
      rating
      film_category {
        category {
          name
        }
      }
      film_actor {
        actor {
          first_name
          last_name
        }
      }
    }
  }
`
export const GetRentalInfoByCustomerDocument = gql`
  query getRentalInfoByCustomer($customerId: Int!) {
    customer(customer_id: $customerId) {
      payment {
        rental {
          rental_date
          return_date
          inventory {
            film_id
          }
        }
        payment_date
        amount
      }
      first_name
      last_name
    }
  }
`

export type SdkFunctionWrapper = <T>(
  action: (requestHeaders?: Record<string, string>) => Promise<T>,
  operationName: string,
  operationType?: string,
  variables?: any,
) => Promise<T>

const defaultWrapper: SdkFunctionWrapper = (
  action,
  _operationName,
  _operationType,
  _variables,
) => action()

export function getSdk(
  client: GraphQLClient,
  withWrapper: SdkFunctionWrapper = defaultWrapper,
) {
  return {
    getAddressByCustomer(
      variables: GetAddressByCustomerQueryVariables,
      requestHeaders?: GraphQLClientRequestHeaders,
    ): Promise<GetAddressByCustomerQuery> {
      return withWrapper(
        (wrappedRequestHeaders) =>
          client.request<GetAddressByCustomerQuery>(
            GetAddressByCustomerDocument,
            variables,
            { ...requestHeaders, ...wrappedRequestHeaders },
          ),
        'getAddressByCustomer',
        'query',
        variables,
      )
    },
    getCustomers(
      variables: GetCustomersQueryVariables,
      requestHeaders?: GraphQLClientRequestHeaders,
    ): Promise<GetCustomersQuery> {
      return withWrapper(
        (wrappedRequestHeaders) =>
          client.request<GetCustomersQuery>(GetCustomersDocument, variables, {
            ...requestHeaders,
            ...wrappedRequestHeaders,
          }),
        'getCustomers',
        'query',
        variables,
      )
    },
    getFilm(
      variables: GetFilmQueryVariables,
      requestHeaders?: GraphQLClientRequestHeaders,
    ): Promise<GetFilmQuery> {
      return withWrapper(
        (wrappedRequestHeaders) =>
          client.request<GetFilmQuery>(GetFilmDocument, variables, {
            ...requestHeaders,
            ...wrappedRequestHeaders,
          }),
        'getFilm',
        'query',
        variables,
      )
    },
    getRentalInfoByCustomer(
      variables: GetRentalInfoByCustomerQueryVariables,
      requestHeaders?: GraphQLClientRequestHeaders,
    ): Promise<GetRentalInfoByCustomerQuery> {
      return withWrapper(
        (wrappedRequestHeaders) =>
          client.request<GetRentalInfoByCustomerQuery>(
            GetRentalInfoByCustomerDocument,
            variables,
            { ...requestHeaders, ...wrappedRequestHeaders },
          ),
        'getRentalInfoByCustomer',
        'query',
        variables,
      )
    },
  }
}
export type Sdk = ReturnType<typeof getSdk>
