let loggedInUser = JSON.parse(sessionStorage.getItem("user"));
let selectedSongId = null;

// Load all songs from AdminService
function loadSongs() {
    fetch("http://localhost:8080/api/songs") // AdminService URL
        .then(res => res.json())
        .then(songs => populateSongsTable(songs))
        .catch(err => console.error(err));
}

// Populate songs table
function populateSongsTable(songs) {
    const tbody = document.querySelector("#songsTable tbody");
    tbody.innerHTML = "";
    songs.forEach(song => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${song.id}</td>
            <td>${song.name}</td>
            <td>${song.singer}</td>
            <td>${song.musicDirector}</td>
            <td>${song.albumName}</td>
            <td><button onclick="openAddModal(${song.id})">Add</button></td>
        `;
        tbody.appendChild(tr);
    });
}

// Search songs
function searchSongs() {
    const name = document.getElementById("searchName").value;
    const singer = document.getElementById("searchSinger").value;
    const musicDirector = document.getElementById("searchDirector").value;
    const albumName = document.getElementById("searchAlbum").value;

    let query = `?name=${name}&singer=${singer}&musicDirector=${musicDirector}&albumName=${albumName}`;

    fetch(`http://localhost:8081/api/songs/search${query}`)
        .then(res => res.json())
        .then(songs => populateSongsTable(songs))
        .catch(err => console.error(err));
}

// Open add-to-playlist modal
function openAddModal(songId) {
    selectedSongId = songId;
    const modal = document.getElementById("addPlaylistModal");
    modal.style.display = "block";

    // Load user's playlists
    fetch(`/api/playlists/user/${loggedInUser.id}`)
        .then(res => res.json())
        .then(playlists => {
            const select = document.getElementById("playlistSelect");
            select.innerHTML = "";
            playlists.forEach(pl => {
                const option = document.createElement("option");
                option.value = pl.id;
                option.text = pl.name;
                select.appendChild(option);
            });
        });
}

// Close modal
function closeModal() {
    document.getElementById("addPlaylistModal").style.display = "none";
}

// Add song to selected playlist
function addSongToPlaylist() {
    const playlistId = document.getElementById("playlistSelect").value;

    fetch(`/api/playlists/${playlistId}/songs`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ songId: selectedSongId })
    })
    .then(() => {
        alert("Song added to playlist!");
        closeModal();
    })
    .catch(err => console.error(err));
}

// Initialize
document.addEventListener("DOMContentLoaded", () => {
    if (!loggedInUser) window.location.href = "/login";
    loadSongs();
});
