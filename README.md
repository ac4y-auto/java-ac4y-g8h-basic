# java-ac4y-g8h-basic

GUID-to-HumanId (G8H) mapping domain objects, persistence adapters, and service layer.

## Coordinates

- **GroupId**: `ac4y`
- **ArtifactId**: `ac4y-g8h-basic`
- **Version**: `1.0.0`

## Description

This library provides the core G8H (GUID-to-HumanId) mapping functionality. It maps globally unique identifiers (GUIDs) to human-readable identifiers with template-based categorization and database persistence.

### Key Classes

- `Ac4yG8H` - Domain object representing a GUID-to-HumanId mapping
- `Ac4yG8HList` / `Ac4yGUIDList` - Collection wrappers
- `Ac4yG8HDBAdapter` - JDBC persistence adapter for G8H records
- `Ac4yG8HTemplateDBAdapter` - Template-specific persistence operations
- `Ac4yIdentificationDBAdapter` - Identification view persistence adapter
- `Ac4yGUID8HumanIdService` - High-level service with JNDI connection management

## Dependencies

- `ac4y-class`, `ac4y-base4jsonandxml` (domain base classes)
- `ac4y-database-basic`, `ac4y-connection-pool` (database connectivity)
- `ac4y-service-domain` (service base class)

## Build

```bash
mvn clean package
```

## Origin

Extracted from `IJAc4yG8HModule/Basic`.
