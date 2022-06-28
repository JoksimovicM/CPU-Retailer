document.addEventListener("DOMContentLoaded", () => {
    readCPUs();
});

function readCPUs() {
    fetch("./resource/cpu/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showCPUList(data);
        })
        .catch(function (error) {
            console.log(error)
        });
}

function showCPUList(data) {
    let tBody = document.getElementById("cpulist")
    data.forEach(cpu => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = cpu.name;
        row.insertCell(-1).innerHTML = cpu.coreCount;
        row.insertCell(-1).innerHTML = cpu.TDP;
        row.insertCell(-1).innerHTML = cpu.frequency;
        row.insertCell(-1).innerHTML = cpu.socket;
        row.insertCell(-1).innerHTML = cpu.price;
        row.insertCell(-1).innerHTML = cpu.series.seriesName;
    });
}