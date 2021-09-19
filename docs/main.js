var board1 = ChessBoard("board1");
let elementpieces;
let elementsquares;
let movingpiece;

let tilesdata;
let movestring;
let movearr = [];
let isMoving = false;

let color;
let turn;

let url = window.location.href;
let params = new URL(window.location).searchParams;
let id = params.get("id");
let isPlayerTwo = id === null ? false : true;

let local = "http://localhost:443";
let heroku = "https://restful-chess-server.herokuapp.com";
let server = heroku;

const startbtn = document.querySelector("#createbtn");
if (isPlayerTwo) {
  startbtn.remove();
  join(id);
}

startbtn.addEventListener("click", () => {
  startbtn.remove();
  startNewGame();
});

function addListeners() {
  elementpieces = document.querySelectorAll("[data-piece]");
  elementsquares = document.querySelectorAll("[data-square]");

  elementpieces.forEach((piece) => {
    piece.addEventListener("click", (event) => {
      event.stopPropagation();
      if (turn) {
        if (!isMoving) {
          if (piece.getAttribute("data-piece").charAt(0) === color) {
            highlight(piece);
          }
        } else {
          if (
            movingpiece.getAttribute("data-piece").charAt(0) ===
            piece.getAttribute("data-piece").charAt(0)
          ) {
            if (movingpiece === piece) {
              isMoving = false;
              clearHighlighting();
            } else {
              highlight(piece);
            }
          } else {
            const endsquare = piece.parentElement;
            if (movearr != null) {
              if (
                movearr.includes(
                  endsquare.getAttribute("data-square").toUpperCase()
                )
              ) {
                const start =
                  movingpiece.parentElement.getAttribute("data-square");
                const end = endsquare.getAttribute("data-square");
                endsquare.removeChild(piece);
                endsquare.appendChild(movingpiece);
                isMoving = false;
                clearHighlighting();
                sendMove(start + end + id);
                turn = false;
              } else {
                isMoving = false;
                clearHighlighting();
              }
            }
          }
        }
      }
    });
  });

  elementsquares.forEach((square) => {
    square.addEventListener("click", (event) => {
      if (turn) {
        if (isMoving) {
          if (movearr != null) {
            if (
              movearr.includes(square.getAttribute("data-square").toUpperCase())
            ) {
              const start =
                movingpiece.parentElement.getAttribute("data-square");
              const end = square.getAttribute("data-square");
              square.appendChild(movingpiece);
              isMoving = false;
              clearHighlighting();
              sendMove(start + end + id);
              turn = false;

              //enpassant
              //Moving diagonal to empty square
              if (movingpiece.getAttribute("data-piece").charAt(1) == "P") {
                if (start.charAt(0) != end.charAt(0)) {
                  enpassant(start, end);
                }
              }

              //Castling
              if (movingpiece.getAttribute("data-piece").charAt(1) == "K") {
                castle(start, end);
              }
            } else {
              isMoving = false;
              clearHighlighting();
            }
          }
        }
      }
    });
  });
}

function clearHighlighting() {
  elementsquares.forEach((square) => {
    square.classList.remove("highlight1-32417");
  });
}

function highlight(piece) {
  clearHighlighting();
  const tile = piece.parentElement.getAttribute("data-square");
  movestring =
    tilesdata[tile.charCodeAt(0) - 97][tile.charAt(1) - 1].occupyingPiece
      .viable;
  movearr = movestring.match(/..?/g);
  console.log(movearr);
  piece.parentElement.classList.add("highlight1-32417");
  isMoving = true;
  movingpiece = piece;
  if (movearr != null) {
    elementsquares.forEach((square) => {
      if (movearr.includes(square.getAttribute("data-square").toUpperCase())) {
        square.classList.add("highlight1-32417");
      }
    });
  }
}

function startNewGame() {
  fetch(`${server}/newgame`)
    .then((response) => response.json())
    .then((data) => {
      window.addEventListener("beforeunload", (event) => {
        endGame(id);
      });
      tilesdata = data.tiles;
      id = data.id;
      color = "w";
      turn = true;

      url += "?id=" + id;
      document.querySelector("#instructions").innerHTML =
        "Give this link to a friend:";
      document.querySelector("#gamelink").href = url;
      document.querySelector("#gamelink").innerHTML = url;
      establishConnection(id);
    });
}

function join(id) {
  fetch(`${server}/join?id=${id}`).then(() => {
    window.addEventListener("beforeunload", (event) => {
      endGame(id);
    });
    window.history.pushState(
      "",
      "",
      "https://aaleiadeh.github.io/RESTfulChess/"
    );
    color = "b";
    turn = false;
    board1 = ChessBoard("board1", {
      orientation: "black",
      position: "start",
    });
    addListeners();
    getMove(id);
  });
}

function endGame(id) {
  fetch(`${server}/end`, {
    method: "POST",
    body: id,
  });
}

function rematch(id) {
  fetch(`${server}/rematch?id=${id}`)
    .then((response) => response.json())
    .then((data) => {
      tilesdata = data;
      color = color === "w" ? "b" : "w";
      turn = color === "w" ? true : false;
      if (color === "w") {
        board1 = ChessBoard("board1", "start");
      } else {
        board1 = ChessBoard("board1", {
          orientation: "black",
          position: "start",
        });
        getMove(id);
      }
      addListeners();
      document.querySelector("#winner").innerHTML = "";
    });
}

function establishConnection(id) {
  fetch(`${server}/establish?id=${id}`)
    .then((response) => response.json())
    .then((data) => {
      if (data === false) {
        document.querySelector("#instructions").innerHTML =
          "Link Expired. Please refresh page";
      } else {
        board1 = ChessBoard("board1", "start");
        addListeners();
        document.querySelector("#instructions").remove();
      }
      document.querySelector("#gamelink").remove();
    });
}

function sendMove(move) {
  fetch(`${server}/send`, {
    method: "POST",
    body: move,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data === true) {
        gameOver(true);
      } else {
        getMove(id);
      }
    });
}

function getMove(id) {
  fetch(`${server}/getmove?id=${id}`)
    .then((response) => response.json())
    .then((data) => {
      if (data.move === "TIMEOUT") {
        getMove(id);
      } else {
        tilesdata = data.tiles;
        const start = document.querySelector(
          "[data-square=" + CSS.escape(data.move.substring(0, 2)) + "]"
        );
        const end = document.querySelector(
          "[data-square=" + CSS.escape(data.move.substring(2)) + "]"
        );
        const startpiece = start.querySelector("[data-piece]");
        const endpiece = end.querySelector("[data-piece]");
        start.removeChild(startpiece);
        if (endpiece != null) {
          end.removeChild(endpiece);
        }
        end.appendChild(startpiece);

        start.classList.add("highlight1-32417");
        end.classList.add("highlight1-32417");

        //enpassant
        if (startpiece.getAttribute("data-piece").charAt(1) === "P") {
          startString = start.getAttribute("data-square");
          endString = end.getAttribute("data-square");
          if (startString.charAt(0) != endString.charAt(0)) {
            if (endpiece === null) {
              enpassant(startString, endString);
            }
          }
        }

        //castling
        if (startpiece.getAttribute("data-piece").charAt(1) === "K") {
          const startString = start.getAttribute("data-square");
          const endString = end.getAttribute("data-square");
          castle(startString, endString);
        }

        if (data.defeat) {
          gameOver(false);
        } else {
          turn = true;
        }
      }
    });
}

function gameOver(victory) {
  const rematchbtn = document.createElement("button");
  rematchbtn.innerHTML = "Rematch?(Disabled because bugged for now)";
  document.querySelector("#newgame").appendChild(rematchbtn);
  let winner;
  if (victory) {
    winner = color === "w" ? "White" : "Black";
  } else {
    winner = color === "w" ? "Black" : "White";
  }
  document.querySelector("#winner").innerHTML = winner + " Wins";
  rematchbtn.addEventListener("click", (event) => {
    rematchbtn.remove();
    //rematch(id);
  });
}

function enpassant(start, end) {
  const enpassantTile = document.querySelector(
    "[data-square=" + CSS.escape(end.charAt(0) + start.charAt(1)) + "]"
  );
  const enpassantPiece = enpassantTile.querySelector("[data-piece]");
  enpassantTile.removeChild(enpassantPiece);
}

function castle(start, end) {
  const direction = end.charCodeAt(0) - 97 - (start.charCodeAt(0) - 97);
  if (direction === 2) {
    const rookTileString =
      String.fromCharCode(end.charCodeAt(0) + 1) + end.charAt(1);
    const rookTile = document.querySelector(
      "[data-square=" + CSS.escape(rookTileString) + "]"
    );
    const moveToString =
      String.fromCharCode(end.charCodeAt(0) - 1) + end.charAt(1);
    const moveTo = document.querySelector(
      "[data-square=" + CSS.escape(moveToString) + "]"
    );
    const rook = rookTile.querySelector("[data-piece]");
    rookTile.removeChild(rook);
    moveTo.appendChild(rook);
  }
  if (direction === -2) {
    const rookTileString =
      String.fromCharCode(end.charCodeAt(0) - 2) + end.charAt(1);
    const rookTile = document.querySelector(
      "[data-square=" + CSS.escape(rookTileString) + "]"
    );
    const moveToString =
      String.fromCharCode(end.charCodeAt(0) + 1) + end.charAt(1);
    const moveTo = document.querySelector(
      "[data-square=" + CSS.escape(moveToString) + "]"
    );
    const rook = rookTile.querySelector("[data-piece]");
    rookTile.removeChild(rook);
    moveTo.appendChild(rook);
  }
}
