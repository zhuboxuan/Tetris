var local = new Local();
local.start();

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/api/user", //路径
        dataType: "json",
        success: function (data) {
            $("#max").text(data.maxScore);
        }
    });
    $.ajax({
        type: "GET",
        url: "/api/score/max", //路径
        dataType: "json",
        success: function (data) {
            $("#user").text(data.username);
            $("#bestScore").text(data.maxScore);
        }
    });
});
