# 请使用中文思考并回答问题

# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 2.7.18 demo project showcasing Resilience4j integration with OpenFeign, implementing circuit breaker, retry, rate limiter, bulkhead, and time limiter patterns.

### Multi-Module Structure

- **zxf-springboot-ea-service** (port 8080): Consumer service that calls PA service via both RestTemplate and OpenFeign
- **zxf-springboot-pa-service** (port 8081): Provider service with endpoints that return various HTTP status codes for testing

### Architecture

EA service communicates with PA service using two patterns:
1. **OpenFeign** (`zxf.springboot.ea.client.PAClient`): Declarative HTTP client with built-in circuit breaker support
2. **RestTemplate** (`zxf.springboot.ea.client.PANormalClient`): Programmatic HTTP calls with Resilience4j annotations

The PA service (`zxf.springboot.pa.rest.MyController`) accepts a `task` parameter that determines the HTTP response status, allowing testing of resilience patterns.

## Build and Run

```bash
# Build entire project
mvn clean install

# Run PA service (provider)
cd zxf-springboot-pa-service && mvn spring-boot:run

# Run EA service (consumer) in another terminal
cd zxf-springboot-ea-service && mvn spring-boot:run
```

## Testing Endpoints

PA service endpoints return different responses based on `task` parameter (HTTP status code):
- `task=200`: Returns success response with timestamp
- `task=400`: Returns 400 Bad Request
- `task=408`: Waits 10 seconds then returns (for timeout testing)
- `task=500`: Returns 500 Internal Server Error (for circuit breaker testing)
- `task=503`: Alternates between success and failure (for retry testing)

### Circuit Breaker Testing
```bash
curl http://localhost:8080/feign/a/json?task=500  # Feign client
curl http://localhost:8080/normal/a/json?task=500  # RestTemplate
```

### Retry Testing
```bash
curl http://localhost:8080/normal/b/json?task=503  # Alternates success/failure
```

### Rate Limiter Testing (2 req/60s default, 5 req/60s for ServiceE)
```bash
curl http://localhost:8080/normal/c/json?task=200
curl http://localhost:8080/feign/e/json?task=200
```

### Bulkhead Testing
```bash
curl http://localhost:8080/normal/d/json?task=408
```

### Failover Testing
```bash
curl http://localhost:8080/config?task=200  # Returns A
curl http://localhost:8080/config?task=400  # Failover returns B
```

## Resilience4j Configuration

All Resilience4j configuration is in `zxf-springboot-ea-service/src/main/resources/application.properties`.

### Annotation Execution Order
`Retry -> CircuitBreaker -> RateLimiter -> TimeLimiter -> Bulkhead`

### Naming Conventions

**Feign Client Circuit Breaker Names** (auto-generated, can be overridden with `@CircuitBreaker`):
- Format: `{FeignClassName}{MethodName}{ParameterTypes}`
- Example: `PAClientserviceAInteger` from `PAClient#serviceA(Integer)`

**Feign Client Names** (for configuration):
- Format: `{Feign-Class-Name}#{Method-Name}({Parameter-Types,})`

**Normal (RestTemplate) Service Names** (manually specified in annotations on `PANormalService`):
- Format: `Normal-Service{X}` (e.g., `Normal-ServiceA`, `Normal-ServiceD`)
- `Normal-ServiceD` demonstrates all five patterns stacked on one method

## Actuator Endpoints

Spring Boot Actuator is exposed at `http://localhost:8080/actuator` with monitoring:

- `/actuator/health` - Health check including circuit breakers
- `/actuator/circuitbreakers` - Circuit breaker states
- `/actuator/circuitbreakerevents` - Circuit breaker events
- `/actuator/metrics/resilience4j.*` - Resilience4j metrics
- `/actuator/retries` - Retry status
- `/actuator/bulkheads` - Bulkhead status
- `/actuator/ratelimiters` - Rate limiter status

## Key Implementation Patterns

### Feign Fallback
`PAClient` uses `fallbackFactory = PAClientFallbackFactory.class`. `PAClientFallbackFactory` implements `FallbackFactory<PAClient>` and creates a `PAClientFallback` instance, giving access to the cause exception for logging.

### Inline Fallback Method
`PAConfigService.config()` uses `@CircuitBreaker(name = "Config-Service", fallbackMethod = "configFallback")`. The `configFallback` method must have the same parameters plus an `Exception` parameter, and is called when the circuit opens—it redirects to the `/pa/b/json` endpoint.

### TimeLimiter Constraint
`@TimeLimiter` requires the service method to return `CompletableFuture<T>` and the actual HTTP call must run in a separate thread (via `@Async` on `PANormalClient.callDownstreamAsync`). Without a separate thread, the time limiter cannot interrupt the call.

### task=503 Retry Behavior
PA service uses an `AtomicInteger retryCounter` — odd-numbered requests to `task=503` succeed, even-numbered fail. This means retry testing requires multiple calls to observe the alternating behavior.

## Exception Handling

`Resilience4jExceptionHandler` maps Resilience4j exceptions to HTTP statuses:
- `CallNotPermittedException` → 503 SERVICE_UNAVAILABLE
- `BulkheadFullException` → 509 BANDWIDTH_LIMIT_EXCEEDED
- `RequestNotPermitted` → 429 TOO_MANY_REQUESTS
- `TimeoutException` → 408 REQUEST_TIMEOUT

## Technology Stack

- Java 21
- Spring Boot 3.5.11
- Spring Cloud 2025.0.1
- Spring Cloud OpenFeign
- Resilience4j 2.2.0 (via `spring-cloud-circuitbreaker-resilience4j`)
- Lombok
- Spring Boot Actuator