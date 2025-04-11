document.addEventListener('DOMContentLoaded', function () {
    let startTime, endTime, timerInterval;
    const startBtn = document.getElementById('startBtn');
    const stopBtn = document.getElementById('stopBtn');
    const resetBtn = document.getElementById('resetBtn');
    const minutesDisplay = document.getElementById('minutes');
    const secondsDisplay = document.getElementById('seconds');
    const durationDisplay = document.getElementById('duration');
    const frequencyDisplay = document.getElementById('frequency');
    const historyList = document.getElementById('historyList');
    let contractionsHistory = [];

    function formatTime(ms) {
        const date = new Date(ms);
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');
        return `${minutes}:${seconds}`;
    }

    function updateDisplay() {
        const now = new Date();
        const elapsed = new Date(now - startTime);
        minutesDisplay.textContent = elapsed.getMinutes().toString().padStart(2, '0');
        secondsDisplay.textContent = elapsed.getSeconds().toString().padStart(2, '0');
    }

    function calculateFrequency() {
        if (contractionsHistory.length > 1) {
            const lastTwo = contractionsHistory.slice(-2);
            const frequencyMs = lastTwo[1].startTime - lastTwo[0].startTime;
            frequencyDisplay.textContent = formatTime(frequencyMs);
        }
    }

    startBtn.addEventListener('click', function () {
        startTime = new Date();
        startBtn.disabled = true;
        stopBtn.disabled = false;

        timerInterval = setInterval(updateDisplay, 1000);
    });

    stopBtn.addEventListener('click', function () {
        clearInterval(timerInterval);
        endTime = new Date();
        const duration = endTime - startTime;

        const contraction = {
            startTime: startTime,
            duration: duration
        };
        contractionsHistory.push(contraction);

        durationDisplay.textContent = formatTime(duration);
        calculateFrequency();

        const li = document.createElement('li');
        li.textContent = `Contracción ${contractionsHistory.length}: ${formatTime(duration)} de duración`;
        historyList.appendChild(li);

        startBtn.disabled = false;
        stopBtn.disabled = true;
        minutesDisplay.textContent = '00';
        secondsDisplay.textContent = '00';
    });

    resetBtn.addEventListener('click', function () {
        clearInterval(timerInterval);
        startBtn.disabled = false;
        stopBtn.disabled = true;
        minutesDisplay.textContent = '00';
        secondsDisplay.textContent = '00';
        durationDisplay.textContent = '00:00';
        frequencyDisplay.textContent = '--';
        contractionsHistory = [];
        historyList.innerHTML = '';
    });
});
