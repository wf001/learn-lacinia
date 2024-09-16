# Learn Lacinia

Contains a sample implementation of a GraphQL API and REST API powered by Clojure. The API allows to get sample data and demonstrates basic GraphQL functionality. 

`FYI`: this repository is fork of [toyokumo/build-api-with-clojure](https://github.com/toyokumo/build-api-with-clojure/tree/4fe72afb467612b5a46d17d3081769069e2415cb). The foundamentals like the usage of some middlewares are explained on [their tech blog](https://tech.toyokumo.co.jp/entry/list-of-clojure-articles).


## What’s Powering the API
- `Lacinia`: A GraphQL server library used to provide GraphQL API by Clojure.
- `Ring`: A Clojure web applications library.
- `Apollo`: A GraphQL server library used to provide GraphQL API by TypeScript.
- `MySQL`: A relational database to store customer and related data.
- `Prisma`: A Node.js ORM used to interact with the database in a more structured way.
- `Sakila sample database`: A standard schema that can be used for examples in books, tutorials, articles, samples, and so forth, developed by Mike Hillyer, a former member of the MySQL AB documentation team. See also [the document](https://dev.mysql.com/doc/sakila/en/). All data depends on Sakila sample database.



## How it works
You can use the following query to retrieve a paginated list of customers for example:

```graphql
query Customers {
    paginatedCustomers(offset: 0, limit: 10) {
        totalCount
        customers {
            customer_id
            store_id
            first_name
            last_name
            email
            address_id
            active
        }
    }
}
```

This will return a paginated list of customers with a total count and basic customer details such as customer_id, first_name, and last_name.

## For more information
For more detailed information such as the way of setting up or running query, please refer to [the full document](https://esa-pages.io/p/sharing/21463/posts/111/e113dfd2123683adf27d.html).



## License
This project is licensed under the MIT License. See [the LICENSE file](https://github.com/wf001/learn-lacinia/blob/main/LICENSE) for more details.
