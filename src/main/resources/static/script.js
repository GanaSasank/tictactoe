let ws;
let playerName;
let game;
let gameId;

const stageName = document.getElementById("stage-name");
const stageSearch = document.getElementById("stage-search");
const stageGame = document.getElementById("stage-game");
const stageWinner = document.getElementById("stage-winner");
const boardDiv = document.getElementById("board");
const turnIndicator = document.getElementById("turnIndicator");
const winnerText = document.getElementById("winnerText");
const playAgainBtn = document.getElementById("playAgainBtn");

document.getElementById("continueBtn").addEventListener("click", () => {
    playerName = document.getElementById("playerName").value.trim();
    if (!playerName) return;

    stageName.style.display = "none";
    stageSearch.style.display = "block";

    connectWebSocket();
});

function connectWebSocket() {
    const protocol = window.location.protocol === "https:" ? "wss://" : "ws://";
ws = new WebSocket(protocol + window.location.host + "/ws/game");

    ws.onopen = () => {
        ws.send(JSON.stringify({ type: "JOIN", playerName }));
    };

    ws.onmessage = (msg) => {
        const data = JSON.parse(msg.data);

        switch (data.type) {
            case "START":
            case "UPDATE":
                game = data.game;
                gameId = game.id;

                if (!game.finished) {
                    stageSearch.style.display = "none";
                    stageGame.style.display = "block";
                    stageWinner.style.display = "none";
                }

                renderBoard();
                break;
        }
    };
}

function renderBoard() {
    boardDiv.innerHTML = "";
    for (let r = 0; r < 3; r++) {
        for (let c = 0; c < 3; c++) {
            const cell = document.createElement("div");
            cell.classList.add("cell");
            cell.textContent = game.board[r][c] || "";
            cell.addEventListener("click", () => makeMove(r, c));
            boardDiv.appendChild(cell);
        }
    }

    if (game.finished) {
        stageGame.style.display = "none";
        stageWinner.style.display = "block";

        if (game.winner === "Draw") {
            winnerText.textContent = "It's a Draw!";
        } else {
            winnerText.textContent = (game.winner === playerName ? "You Win!" : `${game.winner} Wins!`);
        }
    } else {
        if (game.nextTurn === playerName) {
            turnIndicator.textContent = "Your turn";
        } else {
            turnIndicator.textContent = `${game.nextTurn}'s turn`;
        }
    }
}

function makeMove(row, col) {
    if (game.finished) return;
    if (game.board[row][col]) return;
    if (game.nextTurn !== playerName) return;

    ws.send(JSON.stringify({
        type: "MOVE",
        move: { gameId, playerName, row, col }
    }));
}

playAgainBtn.addEventListener("click", () => {
    window.location.reload();
});
