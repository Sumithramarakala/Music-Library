let loggedInUser = JSON.parse(sessionStorage.getItem("user"));

// Show create playlist form
function showCreatePlaylistForm() {
    document.getElementById("createPlaylistForm").style.display = "block";
}

// Create playlist
function createPlaylist() {
    const name = document.getElementById("playlistName").value;
    const desc = document.getElementById("playlistDesc").value;

    fetch(`/api/playlists/user/${loggedInUser.id}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: name, description: desc })
    })
    .then(response => response.json())
    .then(data => {
        alert("Playlist created!");
        document.getElementById("createPlaylistForm").style.display = "none";
        loadUserPlaylists();
    })
    .catch(err => console.error(err));
}

// Load user's playlists
function loadUserPlaylists() {
    fetch(`/api/playlists/user/${loggedInUser.id}`)
    .then(res => res.json())
    .then(playlists => {
        const tbody = document.querySelector("#playlistsTable tbody");
        tbody.innerHTML = "";
        playlists.forEach(pl => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${pl.id}</td>
                <td>${pl.name}</td>
                <td>${pl.description || ""}</td>
                <td>
                    <button onclick="viewPlaylistSongs(${pl.id})">View Songs</button>
                    <button onclick="deletePlaylist(${pl.id})" style="color:red;">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    });
}

// Delete playlist
function deletePlaylist(id) {
    fetch(`/api/playlists/${id}`, { method: "DELETE" })
    .then(() => loadUserPlaylists())
    .catch(err => console.error(err));
}

// View songs in playlist
function viewPlaylistSongs(playlistId) {
    fetch(`/api/playlists/${playlistId}/songs`)
    .then(res => res.json())
    .then(data => {
        document.getElementById("playlistSongsSection").style.display = "block";
        const tbody = document.querySelector("#playlistSongsTable tbody");
        tbody.innerHTML = "";
        data.forEach(song => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${song.id}</td>
                <td>${song.name}</td>
                <td>${song.singer}</td>
                <td>${song.musicDirector}</td>
                <td>${song.albumName}</td>
                <td>
                    <button onclick="removeSong(${song.playlistSongId})" style="color:red;">Remove</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    });
}

// Remove song from playlist
function removeSong(playlistSongId) {
    fetch(`/api/playlists/songs/${playlistSongId}`, { method: "DELETE" })
    .then(() => alert("Song removed!"))
    .catch(err => console.error(err));
}

// Initialize
document.addEventListener("DOMContentLoaded", () => {
    if (!loggedInUser) window.location.href = "/login";
    loadUserPlaylists();
});
