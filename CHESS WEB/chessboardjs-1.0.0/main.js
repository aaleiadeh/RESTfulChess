var board1;
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
const startbtn = document.querySelector("#newgame");
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
              if (start.charAt(0) != end.charAt(0)) {
                const enpassantTile = document.querySelector(
                  "[data-square=" +
                    CSS.escape(end.charAt(0) + start.charAt(1)) +
                    "]"
                );
                const enpassantPiece =
                  enpassantTile.querySelector("[data-piece");
                enpassantTile.removeChild(enpassantPiece);
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

  window.addEventListener("beforeunload", (event) => {
    endGame(id);
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
  fetch("http://localhost:8080/newgame")
    .then((response) => response.json())
    .then((data) => {
      //console.log(data);
      tilesdata = data.tiles;
      id = data.id;
      color = "w";
      turn = true;

      url += "?id=" + id;
      document.querySelector("#gamelink").href = url;
      document.querySelector("#gamelink").innerHTML = url;

      establishConnection(id);
    });
}

function join(id) {
  fetch(`http://localhost:8080/join?id=${id}`)
    .then((response) => response.json())
    .then((data) => {
      tilesdata = data.tiles;
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
  fetch("http://localhost:8080/end", {
    method: "POST",
    body: id,
  });
}

function establishConnection(id) {
  fetch(`http://localhost:8080/establish?id=${id}`).then(() => {
    board1 = ChessBoard("board1", "start");
    addListeners();
    document.querySelector("#gamelink").remove();
  });
}

function sendMove(move) {
  fetch("http://localhost:8080/send", {
    method: "POST",
    body: move,
  })
    .then((response) => response.json())
    .then((data) => {
      tilesdata = data;
      getMove(id);
    });
}

function getMove(id) {
  fetch(`http://localhost:8080/getmove?id=${id}`)
    .then((response) => response.json())
    .then((data) => {
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
      turn = true;

      //enpassant
      if (startpiece.getAttribute("data-piece").charAt(1) === "P") {
        startString = start.getAttribute("data-square");
        endString = end.getAttribute("data-square");
        if (startString.charAt(0) != endString.charAt(0)) {
          if (endpiece === null) {
            const enpassantTile = document.querySelector(
              "[data-square=" +
                CSS.escape(endString.charAt(0) + startString.charAt(1)) +
                "]"
            );
            enpassantTile.removeChild(
              enpassantTile.querySelector("[data-piece]")
            );
          }
        }
      }
    });
}
