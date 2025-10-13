# Hera - AI Chat Application

## Overview
This application implements an AI chat service using OpenAI API, following **Hexagonal Architecture** (Ports and Adapters pattern) principles.

## Architecture

### Hexagonal Architecture Structure

```
┌─────────────────────────────────────────────────────────────┐
│                        API Layer                            │
│  (REST Endpoints - External Interface)                      │
│  - ChatResource (/api/chat)                                 │
│  - DTOs: ChatRequest, ChatResponse                          │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                   Application Layer                          │
│  (Use Cases / Business Orchestration)                        │
│  - ChatService                                               │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                     Domain Layer                             │
│  (Core Business Logic - Independent)                         │
│  - Models: Message, Conversation                             │
│  - Ports: AiChatPort (interface)                            │
└─────────────────────────────────────────────────────────────┘
                       ▲
┌──────────────────────┴──────────────────────────────────────┐
│                 Infrastructure Layer                         │
│  (External Adapters - Implementation)                        │
│  - OpenAiChatAdapter (implements AiChatPort)                │
│  - OpenAiClient (REST Client)                                │
│  - DTOs: OpenAiChatRequest, OpenAiChatResponse              │
└─────────────────────────────────────────────────────────────┘
```

## Project Structure

```
src/main/java/org/example/
├── api/                          # API Layer (Adapters IN)
│   ├── ChatResource.java         # REST endpoint
│   └── dto/
│       ├── ChatRequest.java      # API request DTO
│       └── ChatResponse.java     # API response DTO
│
├── application/                  # Application Layer (Use Cases)
│   └── ChatService.java          # Orchestrates domain logic
│
├── domain/                       # Domain Layer (Core)
│   ├── model/
│   │   ├── Message.java          # Message entity
│   │   └── Conversation.java     # Conversation aggregate
│   └── port/
│       └── AiChatPort.java       # Port interface for AI
│
└── infrastructure/               # Infrastructure Layer (Adapters OUT)
    ├── adapter/
    │   └── OpenAiChatAdapter.java # Implements AiChatPort
    └── openai/
        ├── OpenAiClient.java      # REST client interface
        └── dto/
            ├── OpenAiChatRequest.java
            └── OpenAiChatResponse.java
```

## Key Components

### 1. Domain Layer (Core Business Logic)
- **Message**: Represents a chat message with role (system/user/assistant) and content
- **Conversation**: Manages a list of messages with system instructions
- **AiChatPort**: Interface defining the contract for AI communication (PORT)

### 2. Application Layer
- **ChatService**: Orchestrates the chat flow, manages conversations, and coordinates with the domain

### 3. Infrastructure Layer
- **OpenAiChatAdapter**: Implements `AiChatPort` using OpenAI REST API
- **OpenAiClient**: Quarkus REST client for making HTTP calls to OpenAI
- **DTOs**: Data transfer objects for OpenAI API communication

### 4. API Layer
- **ChatResource**: REST endpoint at `/api/chat` accepting POST requests
- **DTOs**: Request/Response objects for the API

## Configuration

### application.properties

```properties
# OpenAI Configuration
openai.api.key=YOUR_API_KEY_HERE

# OpenAI REST Client Configuration
openai-api/mp-rest/url=https://api.openai.com
openai-api/mp-rest/scope=jakarta.inject.Singleton
```

**Important**: Replace `YOUR_API_KEY_HERE` with your actual OpenAI API key.

## API Usage

### Endpoint: POST /api/chat

**Request Body:**
```json
{
  "message": "What is the capital of France?",
  "systemInstruction": "You are a helpful geography teacher."
}
```

**Response:**
```json
{
  "response": "The capital of France is Paris."
}
```

**Notes:**
- `systemInstruction` is optional. If not provided, defaults to "You are a helpful assistant."
- Each request creates a new conversation with the specified system instruction
- The conversation maintains message history during the request processing

## Dependencies

- **Quarkus**: Lightweight Java framework
- **quarkus-rest**: REST endpoint support
- **quarkus-rest-jackson**: JSON serialization
- **quarkus-rest-client-jackson**: REST client with JSON support

## Benefits of Hexagonal Architecture

1. **Isolation**: Domain logic is independent of external frameworks and APIs
2. **Testability**: Easy to test domain logic without external dependencies
3. **Flexibility**: Can switch from OpenAI to another AI provider by creating a new adapter
4. **Maintainability**: Clear separation of concerns makes code easier to understand and modify
5. **Extensibility**: Easy to add new features without modifying core domain logic

## Future Enhancements

- Add conversation persistence (database adapter)
- Support for multi-turn conversations via session management
- Add different AI providers (Anthropic, Google AI, etc.)
- Implement streaming responses
- Add conversation history endpoints
- Add authentication and authorization
