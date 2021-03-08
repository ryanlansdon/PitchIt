let baseUrl = '/Project1';
let mainDiv = document.getElementById("mainDiv");
let loggedInUser = null;
checkLogin();
setMain();

function setMain() {
    mainDiv.innerHTML = `
        <h2>Welcome to PitchIt</h2><br><br>
    `;
    if (!loggedInUser) {
        mainDiv.innerHTML += `
            <button type="button" class="button" id="loginBtn">Log In</button><br>
            <button type-"button" class="button" id="registerBtn">Register</button>
            
        `;
        let loginBtn = document.getElementById("loginBtn");
        loginBtn.onclick = loginDisplay;
        let registerBtn = document.getElementById("registerBtn");
        registerBtn.onclick = registerDisplay;
    } else {
        mainDiv.innerHTML += `
            <button type="button" class="button" onclick="location.href='myPitches.html';">View Pitches</button>
            <button type="button" class="button" onclick="logout()">Log out</button>
            `;
    }
}

function loginDisplay() {
    mainDiv.innerHTML = `
        <h2>Welcome to PitchIt</h2><br><br>
        <form class="form">
            <label for="username">Username: </label><br>
            <input id="username" name="username" type="text" /><br><br>
            <label for="pass">Password: </label><br>
            <input id="pass" name="pass" type="password" /><br><br>
            <button type="button" class="button" id="loginBtn">Log In</button>
        </form>
    `;

    let loginBtn = document.getElementById("loginBtn");
    if (!loggedInUser) loginBtn.onclick = login;
}

function registerDisplay() {
    mainDiv.innerHTML = `
        <h2>Register a new author</h2><br><br>
        <form class="form">
            <label for="username">Username: </label><br>
            <input id="username" name="username" type="text" /><br><br>
            <label for="pass">Password: </label><br>
            <input id="pass" name="pass" type="password" /><br><br>
            <label for="firstName">First Name: </label><br>
            <input id="firstName" name="firstName" type="text" /><br><br>
            <label for="lastName">Last Name: </label><br>
            <input id="lastName" name="lastName" type="text" /><br><br>
            <button type="button" class="button" id="registerBtn">Register</button>
        </form>
    `;

    let registerBtn = document.getElementById("registerBtn");
    if (!loggedInUser) registerBtn.onclick = register;
}

async function login() {
    let url = baseUrl + '/user/login?';
    url += 'user=' + document.getElementById("username").value + '&';
    url += 'pass=' + document.getElementById("pass").value;
    let response = await fetch(url, {method: 'POST'});

    switch (response.status) {
        case 200: // successful
            loggedInUser = await response.json();
            console.log(loggedInUser);
            setMain();
            break;
        case 400: // incorrect password
            alert('Incorrect password, try again.');
            document.getElementById('pass').value = '';
            break;
        case 404: // user not found
            alert('That user does not exist.');
            document.getElementById('user').value = '';
            document.getElementById('pass').value = '';
            break;
        default: // other error
            alert('Something went wrong.');
            break;
    }
}

async function register() {
    let user = {};
    user.id = 0;
    user.points = 0;
    user.username = document.getElementById("username").value;
    user.password = document.getElementById("pass").value;
    user.firstName = document.getElementById("firstName").value;
    user.lastName = document.getElementById("lastName").value;
    user.role = {};
    user.role.name = "Author";
    user.role.id = 4;
    user.genres = {};
    user.outRequests = {};
    user.inRequests = {};
    user.pitches = {};

    let url = baseUrl + '/user';
    let response = await fetch(url, {method:'POST', body:JSON.stringify(user)});
    
    switch (response.status) {
        case 201: // successful
            loggedInUser = await response.json();
            setMain();
            break;
        case 409: // Username already taken
            alert('That username is already taken, try again.');
            document.getElementById('username').value = '';
            break;
        default: // other error
            alert('Something went wrong.');
            break;
    }
}

async function logout() {
    let url = baseUrl + '/user/login';
    let response = await fetch(url, {method:'DELETE'});

    if (response.status != 200) alert('Something went wrong.');
    loggedInUser = null;
    window.location.href = "index.html";
    //setMain();
}

async function checkLogin() {
    let url = baseUrl + '/user';
    let response = await fetch(url, {method:'GET'});
    if (response.status === 200) {
        loggedInUser = await response.json();
    } 
    setMain();
}