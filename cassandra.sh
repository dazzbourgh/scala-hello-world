#!/bin/bash
docker run --name vk-streaming-cassandra --network vk-streaming-network -d cassandra:latest
#docker run -it --network vk-streaming-network --rm cassandra cqlsh vk-streaming-cassandra
