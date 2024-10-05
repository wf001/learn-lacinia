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
  listen: { port: 11003 },
});

console.log(`🚀  Server ready at: ${url}`);
