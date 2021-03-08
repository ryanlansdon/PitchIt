let addPitchMenuOpen = false;
setup();

function setup() {
    // getPitches().then(() => {
    //     checkLogin();
    // });
    checkLogin().then(() => {
        getPitches();
    })
}

async function getPitches() {
    let url = baseUrl;
    if (loggedInUser.role.name == "Author") {
        url += '/author';
    } else {
        url += '/editor';
    }
    
    let response = await fetch(url, {method:'GET'});
    if (response.status == 200) {
        let pitches = await response.json();
        populatePitches(pitches);
    }
}

async function getPitchById(id) {
    let url = baseUrl + '/editor/' + id;

    let response = await fetch(url, {method:'GET'});
    if (response.status == 200) {
        let pitch = await response.json();
        return pitch;
    } else {
        console.log("something went wrong");
    }
}

function populatePitches(pitches) {
    let pitchSection = document.getElementById("pitchSection");
    pitchSection.innerHTML = "";
    if (loggedInUser.role.name == "Author") {
        pitchSection.innerHTML = `
            <button type="button" class="button" id="newPitchBtn" onclick="createPitchPopup()">Create a pitch</button>
        `;
    }

    if(pitches != null && pitches.length > 0) {
        let table = document.createElement('table');
        table.id = 'pitchTable';

        table.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Status</th>
            </tr>
        `;

        for (let pitch of pitches) {
            let tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${pitch.id}</td>
                <td>${pitch.story.title}</td>
                <td>${pitch.story.genre.name}</td>
                <td>${pitch.status.name}</td>
            `;
            let infoBtn = document.createElement('button');
            infoBtn.type = 'button';
            infoBtn.classList.add('button');
            infoBtn.id = pitch.story.name + '_' + pitch.id;
            infoBtn.textContent = 'More info';
            
            let btnTd = document.createElement('td');
            btnTd.appendChild(infoBtn);
            tr.appendChild(btnTd);
            table.appendChild(tr);

            infoBtn.addEventListener('click', function(){pitchInfo(pitch.id);});
        }

        pitchSection.appendChild(table);
    } else {
        if (loggedInUser.role.name == "Author") {
            pitchSection.innerHTML += 'You have no active pitches';
        } else {
            pitchSection.innerHTML += 'No pending pitches';
        }
    }
}

function createPitchPopup() {
    document.getElementById("pitchForm").style.display = "block";
}

async function createPitch() {
    let pitch = {};
    pitch.title = document.getElementById("storyTitle").value;
    pitch.type = document.getElementById("storyType").value;
    pitch.genre = document.getElementById("genre").value;
    pitch.tagline = document.getElementById("tagline").value;
    pitch.description = document.getElementById("description").value;
    pitch.authorInfo = document.getElementById("authorInfo").value;
    pitch.completionDate = document.getElementById("completionDate").value;
    pitch.pendingDate = new Date().toISOString().split("T")[0];

    let url = baseUrl + '/author';
    let response = await fetch(url, {method:'POST', body:JSON.stringify(pitch)});

    switch (response.status) {
        case 201: // success
            let pitches = await response.json();
            closePitchCreate();
            populatePitches(pitches);
            break;
        default:
            alert('Something went wrong.');
            break;
    }
}

function closePitchCreate() {
    document.getElementById("pitchForm").style.display = "none";
}

function infoClose() {
    let infoSection = document.getElementById("infoPopup");
    infoSection.style.display = "none";
}

function deletePitch() {
    // TODO
}

async function sendInfoRequest(pitchId) {
    let pitch = await getPitchById(pitchId);
    let outRequest = document.getElementById("outRequest").value;
    let infoRequest = {};
    infoRequest.id = 0;
    infoRequest.requestText = outRequest;
    infoRequest.responseText = '';
    infoRequest.viewed = false;

    let url = baseUrl + '/editor/pitch/' + pitch.id;
    let response = await fetch(url, {method: 'POST', body:JSON.stringify(infoRequest)});

    switch (response.status) {
        case 200: // success
            infoClose();
            getPitches();
            break;
        default:
            alert('Something went wrong.');
            break;
    }
}

function requestInfo(pitchId) {
    console.log("into function");
    let infoSection = document.getElementById("infoSection");
    infoSection.innerHTML += `
        <form class="form">
            <label for="outRequest">Info request:</label>
            <input name="outRequest" id="outRequest" type="text">
            <button type="button" class="button" onclick="sendInfoRequest(${pitchId})">Send Request</button>
        </form>
    `;
}

async function approvePitch(pitchId) {
    let url = baseUrl + '/editor/accept/' + pitchId;
    let response = await fetch(url, {method:'PUT'});

    switch (response.status) {
        case 200: // success
            infoClose();
            getPitches();
            break;
        default:
            alert('Something went wrong.');
            break;
    }
}

async function submitReject(pitchId) {
    let rejection = {};
    rejection.rejectReason = document.getElementById("rejectReason").value;
    let url = baseUrl + '/editor/reject/' + pitchId;
    let response = await fetch(url, {method:'PUT', body:JSON.stringify(rejection)});

    switch (response.status) {
        case 200: // success
            infoClose();
            getPitches();
            break;
        default:
            alert('Something went wrong.');
            break;
    }
}

function rejectPitch(pitchId) {
    let infoSection = document.getElementById("infoSection");
    infoSection.innerHTML += `
        <label for="rejectReason">Please provide a reason for rejecting the pitch</label>
        <input type="text" name="rejectReason" id="rejectReason"><br>
        <button type="button" class="button" onclick="submitReject(${pitchId})">Reject Pitch</button>
        <button type="button" class="button" onclick="pitchInfo(${pitchId})>Cancel</button>
    `;
}

async function submitDraft(pitchId) {
    let url = baseUrl + '/author/draft/' + pitchId;
    let draft = {};
    draft.draftText = document.getElementById("pitchDraft").value;
    let response = await fetch(url, {method:'PUT', body:JSON.stringify(draft)});
    
    switch (response.status) {
        case 200: // success
            infoClose();
            getPitches();
            break;
        default:
            alert('Something went wrong.');
            break;
    }
}

async function pitchInfo(pitchId) {
    let pitch = await getPitchById(pitchId);
    let infoSection = document.getElementById("infoSection");
    document.getElementById("infoPopup").style.display = "block";
    console.log(pitch);
    
    infoSection.innerHTML = `
        <h3>Story Title: ${pitch.story.title}</h3><br>
        <p>Type: ${pitch.story.type.name}</p><br>
        <p>Genre: ${pitch.story.genre.name}</p><br>
        <p>Tagline: ${pitch.story.tagline}</p><br>
        <p>Description: ${pitch.story.description}</p><br>
        <p>Author Info: ${pitch.story.authorInfo}</p><br>
        <p>Completion Date: ${pitch.story.completionDate.month} ${pitch.story.completionDate.dayOfMonth}, ${pitch.story.completionDate.year}</p><br>
        <p>Status: ${pitch.status.name}</p><br>
        <p>Assistant Editor Status: ${pitch.asstStatus.name}</p><br>
        <p>Editor Status: ${pitch.editorStatus.name}</p><br>
        <p>Senior Editor Status: ${pitch.srStatus.name}</p><br>
    `;

    if (loggedInUser.role.name == "Author") {
        switch (pitch.status.name) {
            case "Rejected":
                infoSection.innerHTML += `
                    <h4>Unfortunately this pitch was rejected for the following reason: ${pitch.rejectReason}</h4><br>
                `;
            case "Accepted":
                infoSection.innerHTML += `
                    <h4>Congratulations, this pitch was accepted by the editorial board! Please sumbit a draft ASAP</h4>
                    <input type="text" id="pitchDraft" placeholder="Enter your draft here">
                    <button type="button" class="button" onclick="submitDraft(${pitchId})">Submit draft</button>
                `;
        }
        infoSection.innerHTML += `
            <button type="button" class="button" onclick="deletePitch()">Cancel Pitch</button>
        `;
    } else {
        infoSection.innerHTML += `
            <button type="button" class="button" onclick="approvePitch(${pitchId})">Approve Pitch</button>
            <button type="button" class="button" onclick="rejectPitch(${pitchId})">Reject Pitch</button>
            <button type="button" class="button" onclick="requestInfo(${pitchId})">Request Info</button>
        `;
    }

    infoSection.innerHTML += `
        <button type="button" class="button" onclick="infoClose()">Close</button>
    `;
}