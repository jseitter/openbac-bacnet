# openbac-bacnet
BACnet implementation for Java based on Netty network library

![Build Status](https://github.com/jseitter/openbac-bacnet/actions/workflows/gradle.yml/badge.svg)


#Architecture

```mermaid
BACnetService *-- BACnetServiceRequest
BACnetService *-- BACnetServiceResponse
BACnetResponse <|-- BACnetServiceResponse
BACnetResponse : encode(ByteBuf buf)

```

