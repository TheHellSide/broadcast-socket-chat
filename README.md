# Broadcast Socket Chat

A simple terminal-based chat application in Java using TCP sockets.

The project contains:
- **Server**: accepts multiple clients and broadcasts messages
- **Client**: connects to the server and sends/receives messages in real time

---

## Features

- Multi-client chat over TCP
- Broadcast messaging to all connected users
- Join/leave notifications
- Client username prompt on connect
- Lightweight, no external dependencies

---

## Project Structure

```text
src/
  Server.java   # Chat server and client handler logic
  Client.java   # Terminal chat client
```

---

## Requirements

- Java JDK installed
- Terminal/console access

---

## Compile

From the repository root:

```bash
javac --release 8 src/*.java
```

This creates `.class` files inside the `src/` directory.

---

## Run

Open two or more terminals in the repository root.

### 1) Start server

```bash
java -cp src Server
```

### 2) Start one or more clients

```bash
java -cp src Client
```

On first connection, each client is prompted for a display name, then can send chat messages.

---

## How It Works

1. Server listens on port `5000`.
2. Each incoming connection gets a dedicated handler thread.
3. Server broadcasts join/leave events.
4. Client messages are relayed to all other connected clients.
5. Each client runs:
   - one thread for receiving messages from server
   - main thread for sending user input

---

## Default Network Configuration

- **Host (client):** `127.0.0.1`
- **Port:** `5000`

These values are defined in source code:
- `src/Client.java`
- `src/Server.java`

---

## Notes

- ANSI escape sequences are used for bold labels in terminal output.
- Empty messages are ignored by the server.

---

## License

No license file is currently included in this repository.
