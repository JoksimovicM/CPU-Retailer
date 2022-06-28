document.addEventListener("DOMContentLoaded", () => {
   readCPU();
   readSeries();
});

function readCPU() {
    const cpuUUID = getQueryParam("uuid");
    fetch("./resource/cpu/read?uuid=" + cpuUUID)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showCPU(data);
        })
        .catch(function (error) {
            console.log(error)
        });
}

function showCPU(data) {
    document.getElementById("name").value = data.name;
    document.getElementById("coreCount").value = data.coreCount;
    document.getElementById("tdp").value = data.tdp;
    document.getElementById("frequency").value = data.frequency;
    document.getElementById("socket").value = data.socket;
    document.getElementById("price").value = data.price;
    document.getElementById("series").value = data.seriesUUID;
}

function readSeries() {
    fetch("./resource/series/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showSeries(data);
        })
        .catch(function (error) {
            console.log(error)
        });
}

function showSeries(data) {
    let dropdown = document.getElementById("series");
    data.forEach(series => {
        let option = document.createElement("option");
        option.text = series.seriesName;
        option.value = series.seriesUUID;
        dropdown.add(option);
    })
}