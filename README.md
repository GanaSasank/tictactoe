# Tic Tac Toe - Multiplayer Web Game

**Live Demo:** [https://tictactoe-wet2.onrender.com/](https://tictactoe-wet2.onrender.com/)

A **real-time, server-authoritative multiplayer Tic Tac Toe game** built with **Spring Boot, WebSocket, and plain HTML/CSS/JS**. Players can enter their name, get matched, play in real-time, and see the winner on a full-screen animated banner.

---

## **Features**

- Server-managed game state (backend authoritative)  
- Real-time multiplayer using WebSocket  
- Matchmaking: players are paired automatically  
- Interactive UI with animations: X/O markers, winner banner  
- Four-stage game flow:
  1. Enter player name  
  2. Searching for another player  
  3. Game board with turn indicators  
  4. Winner page with full-screen animated banner  
- Responsive design with navy blue themed animations  

---

## **Tech Stack**

- **Backend:** Java 17, Spring Boot, WebSocket  
- **Frontend:** HTML, CSS, JavaScript  
- **Build & Deployment:** Maven, Docker  
- **Hosting:** Render.com  

---

## **How to Play**

1. Open the live URL or run locally.  
2. Enter your player name and click **Continue**.  
3. Wait while searching for another player.  
4. Play Tic Tac Toe in real-time:
   - “Your Turn” / “Opponent’s Turn” indicator  
   - Animated X and O markers  
5. After the game ends, the **winner page** shows a full-screen animated banner.  
6. Click **Play Again** to restart.

---

## **Running Locally**

**Requirements:**

- Java 17
- Maven
- Docker containerized deployment

