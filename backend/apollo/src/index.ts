import { ApolloServer } from "@apollo/server";
import { startStandaloneServer } from "@apollo/server/standalone";
import { PrismaClient } from "@prisma/client";
import {
  typeDefs as scalarTypeDefs,
  resolvers as scalarResolvers,
} from "graphql-scalars";
import * as fs from "fs";

const prisma = new PrismaClient();

const resolvers = {
  Query: {
    customers: () => prisma.customer.findMany({}),
    customer: (_: unknown, args: { customer_id: number }) =>
      prisma.customer.findUnique({
        where: args,
        include: {
          address: { include: { city: { include: { country: true } } } },
          payment: { include: { rental: { include: { inventory: true } } } },
        },
      }),
    film: (_: unknown, args: { film_id: number }) =>
      prisma.film.findUnique({
        where: args,
        include: {
          inventory: true,
          film_actor: { include: { actor: true } },
          film_category: { include: { category: true } },
        },
      }),
  },
};

const typeDefs = fs.readFileSync("../lacinia/resources/schema.graphql", "utf8");

const server = new ApolloServer({
  resolvers: {
    ...scalarResolvers,
    ...resolvers,
  },
  typeDefs: [...scalarTypeDefs, typeDefs],
});

const { url } = await startStandaloneServer(server, {
  listen: { port: 11003, path: "/api" },
});

console.log(`🚀  Server ready at: ${url}`);
