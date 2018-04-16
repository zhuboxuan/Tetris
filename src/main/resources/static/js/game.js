// game core

var Game = function() {
    var gameDiv,
        nextDiv,
        scoreDiv,
        resultDiv;

    // score
    var score = 0;

    // game area
    var gameData = [
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    ];

    var cur;

    var next;

    var nextDivs = [];
    var gameDivs = [];

    // time
    var setTime = function(time) {
        document.getElementById("time").innerText = time;
    }

    var initDiv = function(container, data, divs) {
        for (var i = 0; i < data.length; i++) {
            var div = [];
            for (var j = 0; j < data[0].length; j++) {
                var newNode = document.createElement("div");
                newNode.className = "none";
                newNode.style.top = (i * 20) + "px";
                newNode.style.left = (j * 20) + "px";
                container.appendChild(newNode);
                div.push(newNode);
            }
            divs.push(div);
        }
    };

    //refresh div
    var refurbishDiv = function(data, divs) {
        for (var i = 0; i < data.length; i++) {
            for (var j = 0; j < data[0].length; j++) {
                if (data[i][j] == 0) {
                    divs[i][j].className = "none";
                } else if (data[i][j] == 1) {
                    divs[i][j].className = "done";
                } else if (data[i][j] == 2) {
                    divs[i][j].className = "current";
                }
            }
        }
    };

    // check point
    var check = function(pos, x, y) {
        if (pos.x + x < 0) {
            return false;
        } else if (pos.x + x >= gameData.length) {
            return false;
        } else if (pos.y + y < 0) {
            return false;
        } else if (pos.y + y >= gameData[0].length) {
            return false;
        } else if (gameData[pos.x + x][pos.y + y] === 1) {
            return false;
        } else {
            return true;
        }
    };

    // check date
    var isValid = function(pos, data) {
        for (var i = 0; i < data.length; i++) {
            for (var j = 0; j < data[0].length; j++) {
                if (data[i][j] != 0) {
                    if (!check(pos, i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // set data
    var setData = function() {
        for (var i = 0; i < cur.data.length; i++) {
            for (var j = 0; j < cur.data[0].length; j++) {
                if (check(cur.origin, i, j)) {
                    gameData[cur.origin.x + i][cur.origin.y + j] = cur.data[i][j];
                }
            }
        }
    };

    // clear data
    var clearData = function() {
        for (var i = 0; i < cur.data.length; i++) {
            for (var j = 0; j < cur.data[0].length; j++) {
                if (check(cur.origin, i, j)) {
                    gameData[cur.origin.x + i][cur.origin.y + j] = 0;
                }
            }
        }
    };


    var down = function() {
        if (cur.canDown(isValid)) {
            clearData();
            cur.down();
            setData();
            refurbishDiv(gameData, gameDivs);
            return true;
        } else {
            return false;
        }
    }


    var left = function() {
        if (cur.canLeft(isValid)) {
            clearData();
            cur.left();
            setData();
            refurbishDiv(gameData, gameDivs);
        }
    }

    var right = function() {
        if (cur.canRight(isValid)) {
            clearData();
            cur.right();
            setData();
            refurbishDiv(gameData, gameDivs);
        }
    }

    var rotate = function() {
        if (cur.canRotate(isValid)) {
            clearData();
            cur.rotate();
            setData();
            refurbishDiv(gameData, gameDivs);
        }
    }

    var fixed = function() {
        for (var i = 0; i < cur.data.length; i++) {
            for (var j = 0; j < cur.data[0].length; j++) {
                if (check(cur.origin, i, j)) {
                    if (gameData[cur.origin.x + i][cur.origin.y + j] == 2) {
                        gameData[cur.origin.x + i][cur.origin.y + j] = 1;
                    }
                }
            }
        }
        refurbishDiv(gameData, gameDivs);
    }


    var checkClear = function() {
        var line = 0;
        for (var i = gameData.length - 1; i >= 0; i--) {
            var clear = true;
            for (var j = 0; j < gameData[0].length; j++) {
                if (gameData[i][j] != 1) {
                    clear = false;
                    break;
                }
            }
            if (clear) {
                line += 1;
                for (var m = i; m > 0; m--) {
                    for (var n = 0; n < gameData[0].length; n++) {
                        gameData[m][n] = gameData[m - 1][n];
                    }
                }
                for (var n = 0; n < gameData[0].length; n++) {
                    gameData[0][n] = 0;
                }
                i++;
            }
        }
        return line;
    }

    // check game over
    var checkGameOver = function() {
        var gameOver = false;
        for (var i = 0; i < gameData[0].length; i++) {
            if (gameData[1][i] === 1) {
                gameOver = true;
            }
        }
        return gameOver;
    }


    var performNext = function(type, dir) {
        cur = next;
        setData();
        next = SquareFactory.prototype.make(type, dir);
        refurbishDiv(gameData, gameDivs);
        refurbishDiv(next.data, nextDivs);
    }

    // init game
    var init = function(doms, type, dir) {
        gameDiv = doms.gameDiv;
        nextDiv = doms.nextDiv;
        scoreDiv = doms.scoreDiv;
        resultDiv = doms.resultDiv;
        // cur = SquareFactory.prototype.make(3, 2);
        next = SquareFactory.prototype.make(type, dir);
        initDiv(gameDiv, gameData, gameDivs);
        initDiv(nextDiv, next.data, nextDivs);
        // setData();
        // refurbishDiv(gameData, gameDivs);
        refurbishDiv(next.data, nextDivs);
    };

    // add score
    var addScore = function(line) {
        var s = 0;
        switch (line) {
            case 1:
                s = 10;
                break;
            case 2:
                s = 30;
                break;
            case 3:
                s = 60;
                break;
            case 4:
                s = 100;
                break;
            default:
                break;
        }
        score = score + s;
        scoreDiv.innerText = score;
    }

    // game over
    var gameOver = function(win) {
        if (win) {
            resultDiv.innerText = "YOU WIN!"
        } else {
            resultDiv.innerText = "GAME OVER!"
        }

    }

    // api
    this.init = init;
    this.down = down;
    this.left = left;
    this.right = right;
    this.rotate = rotate;
    this.fall = function() {
        while (down());
    };
    this.fixed = fixed;
    this.performNext = performNext;
    this.checkClear = checkClear;
    this.checkGameOver = checkGameOver;

    this.setTime = setTime;
    this.addScore = addScore;

    this.gameOver = gameOver;

}