let selectedPlaylistId = null;
let allSongs = []; // to store songs globally for lookup
let selectedSong = null; // the song that is clicked for details

document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (!user) {
        window.location.href = "/user/login";
        return;
    }
    document.getElementById("usernameDisplay").innerText = user.name || user.username || "User";
    loadUserPlaylists(user.id);
});

// Load all songs and display them
function loadAllSongs() {
    fetch("/api/songs")
        .then(response => response.json())
        .then(songs => {
            allSongs = songs;
            const tbody = document.getElementById("songsTable").querySelector("tbody");
            tbody.innerHTML = "";
            songs.forEach(song => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${song.id}</td>
                    <td onclick="showSongDetails(${song.id})" style="cursor:pointer; text-decoration: underline;">${song.name}</td>
                    <td>${song.singer}</td>
                    <td>${song.musicDirector}</td>
                    <td>${song.album ? song.album.name : ""}</td>
                    <td><button onclick="addSongToSelectedPlaylist(${song.id})">Add</button></td>
                `;
                tbody.appendChild(tr);
            });
            document.getElementById("songsSection").style.display = "block";
            document.getElementById("playlistSelectDiv").style.display = "block";
        })
        .catch(err => console.error("Error fetching songs:", err));
}

// Show song details in the details section
function showSongDetails(songId) {
    const song = allSongs.find(s => s.id === songId);
    if (!song) return;

    selectedSong = song;

    const detailsDiv = document.getElementById("songDetails");
    detailsDiv.innerHTML = `
        <h3>Song Details</h3>
        <p><strong>Name:</strong> ${song.name}</p>
        <p><strong>Singer:</strong> ${song.singer}</p>
        <p><strong>Music Director:</strong> ${song.musicDirector}</p>
        <p><strong>Release Date:</strong> ${song.releaseDate}</p>
        <p><strong>Album:</strong> ${song.album ? song.album.name : "N/A"}</p>
        <button onclick="addSongToSelectedPlaylist(${song.id})">Add to Playlist</button>
    `;
    detailsDiv.style.display = "block";
}

// Create playlist
function showCreatePlaylistForm() {
    document.getElementById("createPlaylistForm").style.display = "block";
}

function createPlaylist() {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const name = document.getElementById("playlistName").value;
    const description = document.getElementById("playlistDesc").value;

    if (!name) return alert("Please enter playlist name.");

    fetch(`/api/playlists/user/${user.id}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, description })
    })
    .then(response => response.json())
    .then(data => {
        alert("Playlist created successfully!");
        document.getElementById("createPlaylistForm").style.display = "none";
        loadUserPlaylists(user.id);
    })
    .catch(err => console.error(err));
}

// Load user playlists into table and dropdown
function loadUserPlaylists(userId) {
    fetch(`/api/playlists/user/${userId}`)
        .then(response => response.json())
        .then(playlists => {
            const tbody = document.getElementById("playlistsTable").querySelector("tbody");
            tbody.innerHTML = "";
            const select = document.getElementById("playlistSelect");
            select.innerHTML = "<option value=''>Select Playlist</option>";

            playlists.forEach(pl => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${pl.id}</td>
                    <td>${pl.name}</td>
                    <td>${pl.description || ""}</td>
                    <td>
                        <button onclick="viewPlaylistSongs(${pl.id})">View Songs</button>
                        <button onclick="deletePlaylist(${pl.id})">Delete</button>
                    </td>
                `;
                tbody.appendChild(tr);

                const option = document.createElement("option");
                option.value = pl.id;
                option.text = pl.name;
                select.appendChild(option);
            });
        });
}

// Add song to selected playlist
function addSongToSelectedPlaylist(songId) {
    const playlistSelect = document.getElementById("playlistSelect");
    const playlistId = playlistSelect.value;
    if (!playlistId) return alert("Please select a playlist first.");

    fetch(`/api/playlists/${playlistId}/songs`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ songId: songId })
    })
    .then(response => response.json())
    .then(data => {
        alert("Song added to playlist!");
    })
    .catch(err => console.error(err));
}

// View songs in a playlist
function viewPlaylistSongs(playlistId) {
    fetch(`/api/playlists/${playlistId}/songs`)
        .then(response => response.json())
        .then(playlistSongs => {
            const tbody = document.getElementById("playlistSongsTable").querySelector("tbody");
            tbody.innerHTML = ""; // Clear existing rows

            if (playlistSongs.length === 0) {
                tbody.innerHTML = "<tr><td colspan='4'>No songs in this playlist</td></tr>";
            } else {
                playlistSongs.forEach(playlistSong => {
                    // For each PlaylistSong, fetch the actual song details
                    fetch(`/api/songs/${playlistSong.songId}`)
                        .then(res => res.json())
                        .then(song => {
                            const tr = document.createElement("tr");
                            tr.innerHTML = `
                                <td>${song.name}</td>
                                <td>${song.singer}</td>
                                <td>${song.musicDirector}</td>
                                <td><button onclick="removeSongFromPlaylist(${playlistSong.id})">❌ Remove</button></td>
                            `;
                            tbody.appendChild(tr);
                        })
                        .catch(err => console.error("Error fetching song details:", err));
                });
            }
            document.getElementById("playlistSongsSection").style.display = "block";
        })
        .catch(err => console.error("Error loading playlist songs:", err));
}

// Delete playlist
function deletePlaylist(playlistId) {
    if (!confirm("Are you sure you want to delete this playlist?")) return;

    fetch(`/api/playlists/${playlistId}`, { method: "DELETE" })
        .then(() => {
            alert("Playlist deleted successfully!");
            const user = JSON.parse(sessionStorage.getItem("user"));
            loadUserPlaylists(user.id);
        })
        .catch(err => console.error(err));
}

function viewPlaylistSongs(playlistId) {
    fetch(`/api/playlists/${playlistId}/songs`)
        .then(response => response.json())
        .then(playlistSongs => {
            const tbody = document.getElementById("playlistSongsTable").querySelector("tbody");
            tbody.innerHTML = ""; // Clear existing rows

            if (playlistSongs.length === 0) {
                tbody.innerHTML = "<tr><td colspan='4'>No songs in this playlist</td></tr>";
            } else {
                playlistSongs.forEach(playlistSong => {
                    fetch(`/api/songs/${playlistSong.songId}`)
                        .then(res => res.json())
                        .then(song => {
                            const tr = document.createElement("tr");
                            tr.innerHTML = `
                                <td>${song.name}</td>
                                <td>${song.singer}</td>
                                <td>${song.musicDirector || ""}</td>
                                <td><button onclick="removeSongFromPlaylist(${playlistSong.id})">❌ Remove</button></td>
                            `;
                            tbody.appendChild(tr);
                        })
                        .catch(err => {
                            console.error("Error fetching song details:", err);
                        });
                });
            }
            document.getElementById("playlistSongsSection").style.display = "block";
        })
        .catch(err => {
            console.error("Error loading playlist songs:", err);
        });
}

function removeSongFromPlaylist(playlistSongId) {
    if (!confirm("Are you sure you want to remove this song from the playlist?")) return;

    fetch(`/api/playlists/songs/${playlistSongId}`, { method: "DELETE" })
        .then(() => {
            alert("Song removed from playlist!");
            // Refresh the list
            const playlistSelect = document.getElementById("playlistSelect");
            viewPlaylistSongs(playlistSelect.value);
        })
        .catch(err => console.error("Error removing song:", err));
}

function searchSongs() {
    const query = document.getElementById("searchSongInput").value.toLowerCase();
    const tbody = document.getElementById("songsTable").querySelector("tbody");
    tbody.innerHTML = "";

    const filteredSongs = allSongs.filter(song => {
        return (
            song.name.toLowerCase().includes(query) ||
            song.singer.toLowerCase().includes(query) ||
            (song.album && song.album.name.toLowerCase().includes(query))
        );
    });

    if (filteredSongs.length === 0) {
        tbody.innerHTML = "<tr><td colspan='6'>No songs found.</td></tr>";
    } else {
        filteredSongs.forEach(song => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${song.id}</td>
                <td onclick="showSongDetails(${song.id})" style="cursor:pointer; text-decoration: underline;">${song.name}</td>
                <td>${song.singer}</td>
                <td>${song.musicDirector}</td>
                <td>${song.album ? song.album.name : ""}</td>
                <td><button onclick="addSongToSelectedPlaylist(${song.id})">Add</button></td>
            `;
            tbody.appendChild(tr);
        });
    }
}
 
// Logout function
// Logout function
function logout() {
    sessionStorage.removeItem("user");  // Clear user session data
    window.location.href = "/user/login"; // Redirect to login page
}


