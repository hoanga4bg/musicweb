$(document).ready(function() {
    var audio_player = $("#audio-player");
    var play_button = $('#play');
    var progress_bar = $("#progressbar");
    var time = $("#time");
    var mute_button = $('#mute');
    var volume_bar = $('#volume');
    var more_info = $('#more-info-box');
    var player = $('#player')[0];
    var duration = 0;
    var volume = 0.5;
    var end_time = $('#end-time');
    var repeat= $('#repeat')
    player.onloadedmetadata = function () {
            duration = player.duration;
        var minutes = parseInt(duration / 60, 10);
        var seconds = parseInt(duration % 60);
        // finding and appending full duration of audio
     
        if(minutes<10 &&seconds<10){
            end_time.text('0'+minutes + ':' + '0'+seconds);
        }
        else if(minutes>10 && seconds<10){
            end_time.text(minutes + ':' + '0'+seconds);
        }
        else if(minutes<10 && seconds>10){
            end_time.text('0'+minutes + ':' +seconds);
        }
        else if(minutes>10 && seconds>10){
            end_time.text(minutes + ':'  +seconds);
        }
        console.log('ddd', progress_bar)
        progress_bar.progressbar("option", { 'max': duration });
    };
    player.load();
    player.volume = 0.5;
    player.addEventListener("timeupdate", function () {
        progress_bar.progressbar('value', player.currentTime);
        time.text(getTime(player.currentTime));
    }, false);
    volume_bar.progressbar({
        value: player.volume * 100,
    });
  
    volume_bar.click(function (e) {
        var info = getProgressBarClickInfo($(this), e);
        volume_bar.progressbar('value', info.value);
        player.volume = info.value / info.max;
    });
  
    progress_bar.progressbar({
        value: player.currentTime,
    });
  
    progress_bar.click(function (e) {
        var info = getProgressBarClickInfo($(this), e);
        player.currentTime = player.duration / info.max * info.value;
    });
  
    play_button.click(function () {
        player[player.paused ? 'play' : 'pause']();
        $(this).toggleClass("fa-play", player.paused);
        $(this).toggleClass("fa-pause", !player.paused);
    });
  
    mute_button.click(function () {
        if (player.volume == 0) {
            player.volume = volume;
        } else {
            volume = player.volume;
            player.volume = 0;
        }
  
        volume_bar.progressbar('value', player.volume * 100);
  
        $(this).toggleClass("fa-volume-up", player.volume != 0);
        $(this).toggleClass("fa-volume-off", player.volume == 0);
    });
  
    more_info.click(function () {
        audio_player.animate({
          height: (audio_player.height() == 50) ? 100 : 50
        }, 1000);
    });

    repeat.click(function () {
        if(player.loop==true){
            player.loop=false;
            repeat.css("color","darkgray");
        }
        else{
            player.loop=true;
            repeat.css("color","white");
        }
    });
});
function getTime(t) {
    var m = ~~(t / 60), s = ~~(t % 60);
    return (m < 10 ? "0" + m : m) + ':' + (s < 10 ? "0" + s : s);
}

function getProgressBarClickInfo(progress_bar, e) {
    var offset = progress_bar.offset();
    var x = e.pageX - offset.left; // or e.offsetX (less support, though)
    var y = e.pageY - offset.top;  // or e.offsetY
    var max = progress_bar.progressbar("option", "max");
    var value = (x * max) / progress_bar.width();

    return { x: x, y: y, max: max, value: value };
}