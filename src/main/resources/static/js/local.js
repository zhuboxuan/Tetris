// game main

var Local = function() {
    var game;

    var INTERVAL = 500;

    var timer = null;

    var timeCount = 0;

    var time = 0;

    var bindKeyEvent = function() {
        document.onkeydown = function(e) {
            if (e.keyCode == 38) { // up
                game.rotate();
            } else if (e.keyCode == 39) { //right
                game.right();
            } else if (e.keyCode == 40) { //down
                game.down();
            } else if (e.keyCode == 37) { //left
                game.left();
            } else if (e.keyCode == 32) { //space
                game.fall();
            }
        }
    }

    var bindClickEvent = function() {
        document.onclick = function(e) {
            var target = e.target.getAttribute("id")
            if (target === "up") {
                game.rotate();
            } else if (target === "right") {
                game.right();
            } else if (target === "down") {
                game.down();
            } else if (target === "left") {
                game.left();
            } else if (target === "fall") {
                game.fall();
            } else if (target === "newGame") {
                document.getElementById("gameOver").innerText = "";
                document.getElementById("time").innerText = "0";
                document.getElementById("score").innerText = "0";
                stop();
                time = 0;
                start();

            }
        }
    }

    var move = function() {
        timeFunc();
        if (!game.down()) {
            game.fixed();
            var line = game.checkClear();
            if (line) {
                game.addScore(line);
            }
            var gameOver = game.checkGameOver();
            if (gameOver) {
                game.gameOver(false);
                stop();
            } else {
                game.performNext(generateType(), generateDir());
            }
        }
    }

    var timeFunc = function() {
        timeCount = timeCount + 1;
        if (timeCount === 2) {
            timeCount = 0;
            time = time + 1;
            game.setTime(time);
        }
    }

    var generateType = function() {
        return Math.floor(Math.random() * 7);
    }

    var generateDir = function() {
        return Math.floor(Math.random() * 4);
    }

    var start = function() {
        var doms = {
            gameDiv: document.getElementById("game"),
            nextDiv: document.getElementById("next"),
            scoreDiv: document.getElementById("score"),
            resultDiv: document.getElementById("gameOver")
        }
        game = new Game();
        game.init(doms, generateType(), generateDir());
        bindKeyEvent();
        bindClickEvent();
        game.performNext(generateType(), generateDir());
        timer = setInterval(move, INTERVAL);
    }

    var stop = function() {
        if (timer) {
            clearInterval(timer);
            timer = null;
        }
        document.onkeydown = null;
    }

    this.start = start;

}