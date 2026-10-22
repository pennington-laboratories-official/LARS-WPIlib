# LARS CAN Protocol

This document is the protocol design record for the team-facing LARS library.

## FIRST CAN identity

- Device Type: `6` (Distance Sensor)
- Manufacturer ID: NA
- Device Number: `0-63`

## Communication model

LARS uses request/response communication by default.

The library does not create a periodic CAN stream unless an explicit streaming feature is added and enabled in a future protocol revision.

## API identifier layout

The 10-bit API identifier is:

```text
[ API Class: 6 bits ][ API Index: 4 bits ]
```

Current protocol allocation:

```text
Class 0 - Control
  Index 0 - Set Mode
  Index 1 - Set Limit

Class 1 - Request
  Index 0 - Request Distance
  Index 1 - Request Limit State
  Index 2 - Request Status

Class 2 - Response
  Index 0 - Distance
  Index 1 - Limit State
  Index 2 - Status
```


## Distance response

API: `Class 2 / Index 0`

Payload:

```text
Byte 0: distance millimeters, low byte
Byte 1: distance millimeters, high byte
Byte 2: bit 0 = measurement valid
Bytes 3-7: reserved
```

## Limit-state response

API: `Class 2 / Index 1`

Payload:

```text
Byte 0:
  bit 0 = forward limit
  bit 1 = reverse limit
  bit 2 = measurement valid
Bytes 1-7: reserved
```

## Status response

API: `Class 2 / Index 2`

Payload:

```text
Byte 0:
  bit 0 = sensor ready
  bit 1 = measurement valid
  bit 2 = configuration valid

Byte 1: fault flags
Byte 2: reserved
Bytes 3-7: reserved
```
